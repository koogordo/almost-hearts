package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblPlayer = new JLabel("Player 1");
		panel_1.add(lblPlayer);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		panel_2.add(lblPlayer_1);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblPlayer_2 = new JLabel("Player 3");
		panel_3.add(lblPlayer_2);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTricksWon = new JLabel("TRICKS WON: 0");
		panel_4.add(lblTricksWon, BorderLayout.SOUTH);
		
		JButton btnSubmit = new JButton("Submit");
		panel_4.add(btnSubmit, BorderLayout.NORTH);
	}

	
	
	/*	Timer code
	 * public class Test {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        final JDialog dialog = new JDialog(f, "Test", true);
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();

        dialog.setVisible(true); // if modal, application will pause here

        System.out.println("Dialog closed");
    }
}
	 */
}
