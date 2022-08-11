package com.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SpringBootApplication
public class Application extends JFrame implements ActionListener {
	JPanel textPanel;
	JPanel grid;
	int[][]cells = new int[4][4];
	JLabel[][] cellLabels = new JLabel[4][4];
	JLabel textField;
	JFrame frame;
	int size = 4;

	int score = 0;
	boolean didIMove = false;
	boolean didIWin = false;
	boolean gameOver = false;


	public Application(){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("2048");
		grid = new JPanel();
		grid.setLayout(new GridLayout(size, size));

		textPanel = new JPanel();
		textPanel.setVisible(true);
		textPanel.setBackground(new Color(252, 248, 232));

		textField = new JLabel();
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.setForeground(new Color(148, 180, 159));
		textField.setFont(new Font("mv Boli", Font.BOLD, 24));
		textField.setText("Score : " + score);

		for(int i = 0; i < cells.length; i++){
			for(int x = 0; x < cells.length; x++){
				cellLabels[i][x] = new JLabel();
				cellLabels[i][x].setSize(200, 200);
				cellLabels[i][x].setHorizontalAlignment(SwingConstants.CENTER);
				cellLabels[i][x].setVerticalAlignment(SwingConstants.CENTER);
				cellLabels[i][x].setFont(new Font("mv Boli", Font.BOLD, 16));
				cellLabels[i][x].setBorder(BorderFactory.createLineBorder(new Color(118, 84, 154), 1));
				cellLabels[i][x].setText(" ");
				grid.add(cellLabels[i][x]);
			}
		}

		frame.add(textPanel, BorderLayout.NORTH);
		textPanel.add(textField);
		frame.add(grid);
		frame.setSize(400, 400);
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		frame.addKeyListener(new MyKeyAdapter());
		frame.setVisible(true);
		frame.revalidate();

		startGame();
	}

	private void startGame() {
		generateCell();
		generateCell();
		paintCells();
	}

	private void generateCell() {
		int num = 2;
		if((int)Math.floor(Math.random() * 10) == 4){
			num = 4;
		}
		int randomOne = (int)Math.floor(Math.random() * size);
		int randomTwo = (int)Math.floor(Math.random() * size);
		if(cellLabels[randomOne][randomTwo].getText().equals(" ")){
			cells[randomOne][randomTwo] = num;
			cellLabels[randomOne][randomTwo].setText("" + num);
		} else {
			generateCell();
		}

	}



	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			switch(e.getKeyCode()){
				case KeyEvent.VK_RIGHT:
					if(!didIWin){
						didIMove = false;
						moveCells('R');
						sumDoubles('R');
						moveCells('R');
						if(didIMove){
							generateCell();
						}
						textField.setText("Score : " + score);
						paintCells();
						checkGameOver();
						checkVictory();
					}
					break;
				case KeyEvent.VK_LEFT:
					if(!didIWin) {
						didIMove = false;
						moveCells('L');
						sumDoubles('L');
						moveCells('L');
						if (didIMove) {
							generateCell();
						}
						textField.setText("Score : " + score);
						paintCells();
						checkGameOver();
						checkVictory();
					}
					break;
				case KeyEvent.VK_UP:
					if(!didIWin) {
						didIMove = false;
						moveCells('U');
						sumDoubles('U');
						moveCells('U');
						if (didIMove) {
							generateCell();
						}
						textField.setText("Score : " + score);
						paintCells();
						checkGameOver();
						checkVictory();
					}
					break;
				case KeyEvent.VK_DOWN:
					if(!didIWin) {
						didIMove = false;
						moveCells('D');
						sumDoubles('D');
						moveCells('D');
						if (didIMove) {
							generateCell();
						}
						textField.setText("Score : " + score);
						paintCells();
						checkGameOver();
						checkVictory();
					}
					break;
			}
		}
	}

	private void checkVictory() {
		for (int i = 0; i < cells.length; i++) {
			for (int x = 0; x < cells.length; x++) {
				if(cells[i][x] == 2048){
					textField.setText("You Won!!");
					didIWin = true;
					setMeInDB();
					break;
				}
			}
		}
	}

	private void checkGameOver() {
		int previousNum = 25;
		for (int i = 0; i < cells.length; i++) {
			for (int x = 0; x < cells.length; x++) {
				if(cellLabels[i][x].getText().equals(" ") || cells[i][x] == previousNum){
					return;
				} else if(x != 3) {
					previousNum =  cells[i][x];
				} else if(x == 3){
					previousNum = 25;
				}
			}
		}
		previousNum = 25;
		for (int x = 0; x < cells.length; x++) {
			for (int i = 0; i < cells.length; i++) {
				if(cellLabels[i][x].getText().equals(" ") || cells[i][x] == previousNum){
					return;
				} else if(i != 3) {
					previousNum =  cells[i][x];
				} else if(i == 3){
					previousNum = 25;
				}
			}
		}
		textField.setText("You lost");

		if (!gameOver){
			gameOver = true;
			setMeInDB();
		}

	}

	private void setMeInDB() {
		DBpopup popup = new DBpopup(score);
	}

	private void paintCells(){
		for (int i = 0; i < cells.length; i++) {
			for (int x = 0; x < cells.length; x++) {
				if(cells[i][x] == 0){
					cellLabels[i][x].setOpaque(false);
				}
				if(cells[i][x] == 2){
					cellLabels[i][x].setBackground(new Color(119, 221, 118));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 4){
					cellLabels[i][x].setBackground(new Color(189, 231, 189));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 8){
					cellLabels[i][x].setBackground(new Color(231, 241, 232));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 16){
					cellLabels[i][x].setBackground(new Color(255, 213, 212));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 32){
					cellLabels[i][x].setBackground(new Color(255, 182, 179));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 64){
					cellLabels[i][x].setBackground(new Color(255, 105, 98));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 128){
					cellLabels[i][x].setBackground(new Color(195, 43, 14));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 256){
					cellLabels[i][x].setBackground(new Color(255, 152, 0));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 512){
					cellLabels[i][x].setBackground(new Color(255, 193, 0));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 1024){
					cellLabels[i][x].setBackground(new Color(255, 236, 25));
					cellLabels[i][x].setOpaque(true);
				}
				if(cells[i][x] == 2048){
					cellLabels[i][x].setBackground(new Color(255, 105, 180));
					cellLabels[i][x].setOpaque(true);
				}

			}

		}
	}

	private void moveCells(char direction) {
		if (direction == 'L') {
			for (int y = 0; y < cells.length; y++) {
				for (int x = 0; x < cells.length; x++) {
					if (cells[y][x] != 0) {
						for (int i = 0; i < cells.length; i++) {
							if (!cellLabels[y][i].getText().equals(" ") && x == i) {
								break;
							} else if (cellLabels[y][i].getText().equals(" ")) {
								cellLabels[y][i].setText("" + cells[y][x]);
								cells[y][i] = cells[y][x];
								cells[y][x] = 0;
								cellLabels[y][x].setText(" ");
								didIMove = true;
								break;
							}
						}

					}

				}
			}
		}

		if (direction == 'R') {

			for (int y = 0; y < cells.length; y++) {
				for (int x = cells.length - 1; x >=0; x--) {
					if (cells[y][x] != 0) {
						for (int i = cells.length - 1; i >= 0; i--) {
							if (!cellLabels[y][i].getText().equals(" ") && x == i) {
								break;
							} else if (cellLabels[y][i].getText().equals(" ")) {
								cellLabels[y][i].setText("" + cells[y][x]);
								cells[y][i] = cells[y][x];
								cells[y][x] = 0;
								cellLabels[y][x].setText(" ");
								didIMove = true;
								break;
							}
						}

					}

				}
			}
		}

		if (direction == 'U') {
			for (int x = 0; x < cells.length; x++) {
				for (int y = 0; y < cells.length; y++) {
					if (cells[y][x] != 0) {
						for (int i = 0; i < cells.length; i++) {
							if (!cellLabels[i][x].getText().equals(" ") && y == i) {
								break;
							} else if (cellLabels[i][x].getText().equals(" ")) {
								cellLabels[i][x].setText("" + cells[y][x]);
								cells[i][x] = cells[y][x];
								cells[y][x] = 0;
								cellLabels[y][x].setText(" ");
								didIMove = true;
								break;
							}
						}

					}

				}
			}
		}

		if (direction == 'D') {
			for (int x = 0; x < cells.length; x++) {
				for (int y = cells.length - 1; y >=0; y--) {
					if (cells[y][x] != 0) {
						for (int i = cells.length - 1; i >= 0; i--) {
							if (!cellLabels[i][x].getText().equals(" ") && y == i) {
								break;
							} else if (cellLabels[i][x].getText().equals(" ")) {
								cellLabels[i][x].setText("" + cells[y][x]);
								cells[i][x] = cells[y][x];
								cells[y][x] = 0;
								cellLabels[y][x].setText(" ");
								didIMove = true;
								break;
							}
						}

					}

				}
			}
		}
	}

	private void sumDoubles(char direction) {
		if (direction == 'L') {
			for (int y = 0; y < cells.length; y++) {
				for (int i = 1; i < cells.length; i++) {
					if (cells[y][i] != 0) {
						if (Integer.parseInt(cellLabels[y][i - 1].getText()) == cells[y][i]) {
							cellLabels[y][i - 1].setText("" + cells[y][i] * 2);
							cells[y][i - 1] = cells[y][i] * 2;
							score += cells[y][i] * 2;
							cells[y][i] = 0;
							cellLabels[y][i].setText(" ");
							didIMove = true;
							i++;
						}

					}

				}
			}
		}

		if (direction == 'R') {
			for (int y = 0; y < cells.length; y++) {
				for (int i = cells.length - 2; i >= 0; i--) {
					if (cells[y][i] != 0) {
						if (Integer.parseInt(cellLabels[y][i + 1].getText()) == cells[y][i]) {
							cellLabels[y][i + 1].setText("" + cells[y][i] * 2);
							cells[y][i + 1] = cells[y][i] * 2;
							score += cells[y][i] * 2;
							cells[y][i] = 0;
							cellLabels[y][i].setText(" ");
							didIMove = true;
							i--;
						}

					}

				}
			}
		}

		if (direction == 'U') {
			for (int x = 0; x < cells.length; x++) {
				for (int y = 1; y < cells.length; y++) {
					if (cells[y][x] != 0) {
						if (Integer.parseInt(cellLabels[y - 1][x].getText()) == cells[y][x]) {
							cellLabels[y - 1][x].setText("" + cells[y][x] * 2);
							cells[y - 1][x] = cells[y][x] * 2;
							score += cells[y][x] * 2;
							cells[y][x] = 0;
							cellLabels[y][x].setText(" ");
							didIMove = true;
							y++;
						}

					}

				}
			}
		}

		if (direction == 'D') {
			for (int x = 0; x < cells.length; x++) {
				for (int y = cells.length - 2; y >= 0; y--) {
					if (cells[y][x] != 0) {
						if (Integer.parseInt(cellLabels[y + 1][x].getText()) == cells[y][x]) {
							cellLabels[y + 1][x].setText("" + cells[y][x] * 2);
							cells[y + 1][x] = cells[y][x] * 2;
							score += cells[y][x] * 2;
							cells[y][x] = 0;
							cellLabels[y][x].setText(" ");
							didIMove = true;
							y--;
						}

					}

				}
			}
		}
	}


	public static void main(String args[]) {

		SpringApplication.run(Application.class, args);
	}




}


