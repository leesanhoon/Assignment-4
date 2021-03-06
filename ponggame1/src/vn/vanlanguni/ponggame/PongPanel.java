/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;

	/** Background. */
	private Color backgroundColor = Color.BLACK;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 200;
	private int ballY = 200;
	private int diameter = 30;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;
	//Image
	ImageIcon imgBall = new ImageIcon("Photo/ballsoccer.png");
	ImageIcon imgBackGround = new ImageIcon("Photo/sanbongda.jpg");
	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 220;
	private int playerOneWidth = 10;
	private int playerOneHeight = 60;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 490;
	private int playerTwoY = 220;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	// FIX #2: Thêm = 0 vào private int playerOneScore
	private int playerOneScore = 0;
	// FIX #2: Thêm = 0 vào private int playerTwoScore
	private int playerTwoScore = 0;

	//FIX #17
	static int NumTypeBall=0;
	ImageIcon imColorBall = new ImageIcon("./Photo/ball1.png");
	ImageIcon imTennis = new ImageIcon("./Photo/ball22.png");
	ImageIcon imBasketball = new ImageIcon("./Photo/ball3.png");
	ImageIcon imPokemonball = new ImageIcon("./Photo/ball4.png");
	
	//
	static String sPlayerName1 = "player 1";
	static String sPlayerName2 = "player 2";
	static int NumPaddlesColor;
	static boolean rectinPaddles = false;

	
	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);
		// Ball
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		// call step() 60 fps
		Timer timer = new Timer(1000 / 60, this);
		timer.start();
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			// ballDeltaY;
			int nextBallTop = ballY + ballDeltaY;
			// diameter + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
				Sound.play("Sound/select.wav");
			}
			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {
					playerTwoScore++;
					Sound.play("Sound/ting.wav");//am thanh khi nguoi choi 2 duoc cong diem
					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("Sound/endsound.wav"); //am thanh khi ket thuc (Minhduy)
						//Sound.play("Sound/gameover.wav");// am thanh khi nguoi choi 2 chien thang
					}
					ballX = 250;
					ballY = 250;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("Sound/select.wav");//am thanh khi bong cham paddle
					// sound
					// Sound.play("sound/Speech On.wav");

				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;
					Sound.play("Sound/ting.wav");//am thanh nguoi choi 1 cong diem
					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("Sound/endsound.wav");//am thanh khi ket thuc (Minhduy)
						//Sound.play("Sound/gameover.wav");// am thanh khi nguoi choi 1 chien thang
					}
					ballX = 250;
			 		ballY = 250;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("Sound/select.wav");// am thanh khi bong cham paddle
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */
			g.drawImage(imgBackGround.getImage(), 0,0,500,500, null);
			// Draw game title and start message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
			g.setColor(Color.BLACK);
			g.drawString("Pong Game", 140, 100);

			// FIXME Wellcome message below show smaller than game title
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			g.drawString("Press 'P' to play.", 150, 400);
			g.drawString("Press 'D' to name.", 150, 450);
			g.drawString("Press 'C' to changePaddlesColor", 75, 350);
			g.drawString(sPlayerName1, 15, 40);
			g.drawString(sPlayerName2, 250, 40);
		} else if (playing) {

			/* Game is playing */
			g.drawImage(imgBackGround.getImage(), 0, 0, 500, 500, null);
			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;
			g.setColor(Color.RED);
			 	if (NumTypeBall == 0) {
			 				g.drawImage(imColorBall.getImage(), ballX, ballY, diameter,
			 						diameter, null);
			 			} else if (NumTypeBall == 1) {
			 				g.drawImage(imTennis.getImage(), ballX, ballY, diameter,
			 						diameter, null);
			 			} else if (NumTypeBall == 2) {
			 				g.drawImage(imBasketball.getImage(), ballX, ballY, diameter,
			 						diameter, null);
			 			} else if (NumTypeBall == 3) {
			 				g.drawImage(imPokemonball.getImage(), ballX, ballY, diameter,
			 						diameter, null);
			 			}
			
			// draw dashed line down center
			g.setColor(Color.green);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(250, lineY, 250, lineY + 25);
			}
			g.setColor(Color.gray);
			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			// draw the scores
			g.setColor(Color.PINK);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
															// score

			g.drawString(String.valueOf(playerTwoScore), 400, 100); // Player 2
															// score
		//	g.drawString("'p'", 210, 230);
		//	g.drawString("press 'B'to choose the ball",100,200);
			// draw the ball
			
			//g.drawImage(imgBall.getImage(), ballX, ballY, diameter, diameter, null);
			//Doi mau PAddles
			if (NumPaddlesColor == 0) {
				g.setColor(Color.WHITE);
			} else if (NumPaddlesColor == 1) {
				g.setColor(Color.BLUE);
			} else if (NumPaddlesColor == 2) {
				g.setColor(Color.RED);
			}
			

			// draw the paddles
			
			//g.setColor(Color.BLUE);
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
			g.setColor(Color.BLACK);
		    g.drawString(sPlayerName1, 75, 40);
			g.drawString(sPlayerName2, 310, 40);
		} else if (gameOver) {
			g.drawImage(imgBackGround.getImage(), 0, 0, 520,540 , null);

			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 100);

			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.drawString(sPlayerName1+" Wins!", 130, 200);
			} else {
				g.drawString(sPlayerName2+" Wins!", 130, 200);
			}
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
			g.setColor(Color.BLACK);
			g.drawString("Press 'Space' to restart.", 100, 400);

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			// TODO Draw a restart message
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyCode() == 'P') {
				showTitleScreen = false;
				playing = true;
				Sound.play("Sound/startsound.wav");//am thanh khi bat dau (Minhduy)
			}else if(e.getKeyChar()=='b'|| e.getKeyChar()=='B'){
				ball mainWidow = new ball();
				mainWidow.setVisible(true);
			}
			if (e.getKeyCode() == 'D') {
				NamePlayerClass mainwindow = new NamePlayerClass();
				mainwindow.setVisible(true);
				
			}if (e.getKeyCode() == 'C') {
				Paddles mainwindow = new Paddles();
				mainwindow.setVisible(true);
				
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			playing = true;
			playerOneScore = 0;
			playerTwoScore = 0;
			playerOneY = 200;
			playerTwoY = 200;
			ballX = 250;
			ballY = 250;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

}
