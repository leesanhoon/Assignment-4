/**
 * 
 */
package vn.vanlanguni.ponggame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Halerluyar
 *
 */
public class NamePlayerClass extends JDialog {
	JTextField txt1 = new JTextField(PongPanel.sPlayerName1),
			txt2 = new JTextField(PongPanel.sPlayerName2);
	JLabel lblplayer1 = new JLabel("Player 1:"),
			lblplayer2 = new JLabel("Player 2:");
	JButton btnOk = new JButton("OK");
	public NamePlayerClass(){
		setTitle("Add Name Player");
		setSize(300, 200);
		setLayout(null);
		setModal(true);
		//add
		add(lblplayer1);
		add(lblplayer2);
		add(txt1);
		add(txt2);
		add(btnOk);
		//set
		lblplayer1.setBounds(10, 30, 50, 25);
		lblplayer2.setBounds(10	, 60, 50, 25);
		txt1.setBounds(70, 30, 150, 25);
		txt2.setBounds(70, 60, 150, 25);
		btnOk.setBounds(100	, 100	, 70, 30);
		//actionlistening
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PongPanel.sPlayerName1 = txt1.getText();
				PongPanel.sPlayerName2 = txt2.getText();
				
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NamePlayerClass mainwindow = new NamePlayerClass();
		mainwindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainwindow.setVisible(true);
	}

}
