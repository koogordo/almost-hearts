package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * 
 * 
 */

public class GameboardGui extends JFrame 
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GameboardGui frame = new GameboardGui();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public GameboardGui() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000,800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setPreferredSize(new Dimension(100, 200));
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 3, 0, 0));
		JLabel lblPlayer_1 = new JLabel("Player 1");
		lblPlayer_1.setVerticalAlignment(SwingConstants.TOP);
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon unsizedIcon1 = new ImageIcon("cardImages/rearOfCard.png");
		ImageIcon playerOneCard = new ImageIcon(unsizedIcon1.getImage().getScaledInstance(100, 200, java.awt.Image.SCALE_DEFAULT));
		JLabel p1ImageHolder = new JLabel();
		p1ImageHolder.setIcon(playerOneCard);
		panel_1.add(lblPlayer_1);
		panel_1.add(p1ImageHolder);

		//Player two gui logic
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setPreferredSize(new Dimension(100, 200));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 3, 0, 5));
		JLabel lblPlayer_2 = new JLabel("Player 2");
		lblPlayer_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer_2.setVerticalAlignment(SwingConstants.TOP);
		ImageIcon unsizedIcon2 = new ImageIcon("cardImages/rearOfCard.png");
		ImageIcon playerTwoCard = new ImageIcon(unsizedIcon2.getImage().getScaledInstance(100, 200, java.awt.Image.SCALE_DEFAULT));
		JLabel p2ImageHolder = new JLabel();
		p2ImageHolder.setIcon(playerTwoCard);
		panel_2.add(lblPlayer_2);
		panel_2.add(p2ImageHolder);

		//Player 3 logic
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setPreferredSize(new Dimension(100, 200));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(2, 3, 0, 0));
		JLabel lblPlayer_3 = new JLabel("Player 3");
		lblPlayer_3.setVerticalAlignment(SwingConstants.TOP);
		lblPlayer_3.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon unsizedIcon3 = new ImageIcon("cardImages/rearOfCard.png");
		ImageIcon playerThreeCard = new ImageIcon(unsizedIcon3.getImage().getScaledInstance(100, 200, java.awt.Image.SCALE_DEFAULT));
		JLabel p3ImageHolder = new JLabel();
		p3ImageHolder.setIcon(playerThreeCard);
		panel_3.add(lblPlayer_3);
		panel_3.add(p3ImageHolder);

		//--------------
		JButton hand1 = new JButton("dlakjhsdflkajdsf");
		JButton hand2 = new JButton("dlakjhsdflkajdsf");
		JButton hand3 = new JButton("dlakjhsdflkajdsf");
		JButton hand4 = new JButton("dlakjhsdflkajdsf");
		JButton hand5 = new JButton("dlakjhsdflkajdsf");
		JButton hand6 = new JButton("dlakjhsdflkajdsf");
		JButton hand7 = new JButton("dlakjhsdflkajdsf");
		JButton hand8 = new JButton("dlakjhsdflkajdsf");
		JButton hand9 = new JButton("dlakjhsdflkajdsf");
		JButton hand10 = new JButton("dlakjhsdflkajdsf");
		JButton hand11 = new JButton("dlakjhsdflkajdsf");
		JButton hand12 = new JButton("dlakjhsdflkajdsf");
		JButton hand13 = new JButton("dlakjhsdflkajdsf");
		JButton hand14 = new JButton("dlakjhsdflkajdsf");
		JButton hand15 = new JButton("dlakjhsdflkajdsf");
		JButton hand16 = new JButton("dlakjhsdflkajdsf");
		JButton hand17 = new JButton("dlakjhsdflkajdsf");
		JButton[] cardsInHand = {
		      hand1, hand2, hand3, hand4, hand5, hand6, hand7, hand8, hand9,hand10,hand11,hand12, hand13, hand14, hand15, hand16, hand17
		};

		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4);
		panel_4.setLayout(new GridLayout(2, 18));
		for (int i = 0; i < cardsInHand.length; i++){
		   
		   panel_4.add(cardsInHand[i]);
		   cardsInHand[i].addActionListener(selectedCard());
		}
		JLabel lblTricksWon = new JLabel("TRICKS WON: 0");
		panel_4.add(lblTricksWon, BorderLayout.WEST);
	}
	private ActionListener selectedCard() {
		// TODO Auto-generated method stub
		
		
		return null;
	}
}
