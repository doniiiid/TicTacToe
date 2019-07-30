package ticTacToe;



import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class ttt extends JFrame implements ActionListener{
	tile [][] buttons = new tile[3][3];
	JLabel message;
	//GameListener listener = new GameListener();
	private char[][] board;
	private char currentPlayerMark;
	private int row;
	private int col;
	private int AIx;
	private int AIy;
	private boolean win;
	private boolean draw;
	
	
	public void initialize() {
		//initializes board
		win = false;
		board = new char[3][3];
		setLayout(new GridLayout(3,3));
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = ' ';
				buttons[i][j] = new tile();
				buttons[i][j].setCordinates(i,j);
				buttons[i][j].addActionListener(this);
				add(buttons[i][j]);
				this.setVisible(true);
			}
		}
		System.out.println("Initialized Board!");
	}
	public void resetTiles() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				buttons[i][j].resetTile();
			}
		}
	}
	public void winMessage() {
		JFrame x = new JFrame();
		JButton b = new JButton("Reset");
		
		x.setSize(300, 100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		x.setLocation(dim.width/2-x.getSize().width/2, dim.height/2-x.getSize().height/2);
		x.setVisible(true);
		
		x.getContentPane().setLayout(new FlowLayout());
		message = new JLabel("Winner",10);
		x.getContentPane().add(message);
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				x.dispose();
				resetTiles();
			}
		});
		x.add(b);
	}
	public void loseMessage() {
		JFrame x = new JFrame();
		JButton b = new JButton("Reset");
		
		x.setSize(300, 100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		x.setLocation(dim.width/2-x.getSize().width/2, dim.height/2-x.getSize().height/2);
		x.setVisible(true);
		
		x.getContentPane().setLayout(new FlowLayout());
		if(draw == false)
			message = new JLabel("Loser",10);
		else
			message = new JLabel("Draw",10);
		x.getContentPane().add(message);
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				x.dispose();
				resetTiles();
			}
		});
		x.add(b);
	}
	public void IndexRow(int x) {
		row = x;
	}
	public void returnIndexCol(int y) {
		col = y;
	}
	public boolean isFull() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(!buttons[i][j].isMarked())
					return false;
			}
		}
		return true;
	}
	public void setAllMarked() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				buttons[i][j].lock();
			}
		}
	}
	public void checkWin() {
		if(win == false) {
			setAllMarked();
			loseMessage();
		}else {
			setAllMarked();
			winMessage();
		}
		
	}
	public boolean checkWinRow(int r, int c, char m) {
		int counter = 0;
		for(int j = 0; j < 3; j++) {
			if(buttons[r][j].isMarked() && buttons[r][j].getMark() == m)
				counter++;
		}
		if(counter == 3)
			return true;
		return false;
	}
	public boolean checkWinCol(int r, int c, char m) {
		int counter = 0;
		for(int i = 0; i < 3; i++) {
			if(buttons[i][c].isMarked() && buttons[i][c].getMark() == m)
				counter++;
		}
		if(counter == 3)
			return true;
		return false;
	}
	public boolean checkWinDiag(int r, int c, char m) {
		int counter = 0;
		int index = 0;
		for(int i = 0; i < 3; i++) {
			if(buttons[i][i].isMarked() && buttons[i][i].getMark() == m)
				counter++;
		}
		if(counter == 3)
			return true;
		counter = 0;
		for(int i = 2; i >= 0; i--) {
			if(buttons[i][index].isMarked() && buttons[i][index].getMark() == m)
				counter++;
			index++;
		}
		if(counter == 3)
			return true;
		
		return false;
	}
	public void setAI() {
		Random rand = new Random();
		boolean found = false;
		int x = 0;
		int y = 0;
		//System.out.println(Integer.toString(x) + ", " + Integer.toString(y));
		while(!found) {
			x = rand.nextInt(3);
			y = rand.nextInt(3);
			System.out.println("Test Placing: " + "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
			if(!buttons[x][y].isMarked()) {
			System.out.println("AI Placing: " + "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
			found = true;
			}
		}
		buttons[x][y].setAIMarker(x, y);
		
		this.checkWinRow(x, y, buttons[x][y].getMark());
		this.checkWinCol(x, y, buttons[x][y].getMark());
		this.checkWinDiag(x, y, buttons[x][y].getMark());
		
		if(this.checkWinRow(x, y, buttons[x][y].getMark()) || this.checkWinCol(x, y, buttons[x][y].getMark()) || this.checkWinDiag(x, y, buttons[x][y].getMark())) {
			win = false;
			checkWin();
			System.out.println("You Lose!");
		}
	}
	public ttt() {

		setTitle("Tic Tac Toe");
		setSize(300, 400);
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		
		initialize();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		tile b = (tile) e.getSource();
		if(!b.isMarked()) {
		b.setMarked();
		b.printCordinates();
		if(this.checkWinRow(b.getRow(), b.getCol(), b.getMark()) || this.checkWinCol(b.getRow(), b.getCol(), b.getMark()) || this.checkWinDiag(b.getRow(), b.getCol(), b.getMark())) {
			win = true;
			checkWin();
			System.out.println("Winner!");
			
		}
		if(!this.isFull()) {
			//System.out.println("Not full yet!");
			this.setAI();
		}
		else if(this.isFull() && win != true){
			draw = true;
			loseMessage();
			System.out.println("Game Over");
			}
		
		}
		b.setVisible(true);
		
	}
}
class tile extends JButton{
	private int row;
	private int col;
	private boolean marked;
	private char mark;
	
	public tile() {
		marked = false;
		row = -1;
		col = -1;
	}
	public void resetTile() {
		marked = false;
		mark = ' ';
		this.setText("");
	}
	public char getMark() {
		return mark;
	}
	public void setCordinates(int r, int c) {
		row = r;
		col = c;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void printCordinates() {
		System.out.println("(" + Integer.toString(row) + ", " + Integer.toString(col) + ")");
	}
	public void setMarked() {
		this.setText("x");
		mark = 'x';
		marked = true;
	}
	public void lock() {
		marked = true;
	}
	public boolean isMarked() {
		return marked;
	}
	public void setAIMarker(int r, int c) {
		this.setText("o");
		mark = 'o';
		marked = true;
		row = r;
		col = c;
	}
}
