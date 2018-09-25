package Views;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.applet.Applet;
import javax.swing.*;

<<<<<<< HEAD
public class GameMenu extends Applet 
{
    JButton button_2;
    JButton button_3;
    JTextField textfield_1;
    JLabel label_1;

    public void init() 
    {
        GameMenuLayout customLayout = new GameMenuLayout();

        setFont(new Font("Helvetica", Font.PLAIN, 12));
        setLayout(customLayout);

        button_2 = new JButton("button_2");
        add(button_2);

        button_3 = new JButton("button_3");
        add(button_3);

        textfield_1 = new JTextField("textfield_1");
        add(textfield_1);

        label_1 = new JLabel("label_1");
        add(label_1);

        setSize(getPreferredSize());
    }

    public static void main(String args[]) 
    {
        GameMenu applet = new GameMenu();
        Frame window = new Frame("GameMenu");

        window.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });

        applet.init();
        window.add("Center", applet);
        window.pack();
        window.setVisible(true);
    }
}

class GameMenuLayout implements LayoutManager 
{

    public GameMenuLayout() 
    {
    	
    }

    public void addLayoutComponent(String name, Component comp) 
    {
    	
    }

    public void removeLayoutComponent(Component comp) 
    {
    	
    }
    
    public Dimension preferredLayoutSize(Container parent) 
    {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 530 + insets.left + insets.right;
        dim.height = 331 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) 
    {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) 
    {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+64,insets.top+144,72,72);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+384,insets.top+144,72,72);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+192,insets.top+64,144,32);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+192,insets.top+24,144,24);}
    }
=======
import AppTools.*;

public class GameMenu {
	static JButton joinExistingButton;
	static JButton createNewButton;
	static JTextField name;
	static JLabel namePrompt;
	static JPanel south;
    
	public static void main(String args[]) {
		JFrame frame = new JFrame("GameMenu");
		frame.setSize(400, 200);
		frame.setResizable(false);
		BorderLayout layout = new BorderLayout();
		frame.setLayout(layout);
		
		joinExistingButton = new JButton("Join existing game");
		joinExistingButton.setEnabled(false);
		joinExistingButton.addActionListener(new ButtonHandler());
		frame.add(joinExistingButton, BorderLayout.EAST);
		
		createNewButton = new JButton("Create a new game");
		createNewButton.setEnabled(false);
		createNewButton.addActionListener(new ButtonHandler());
		frame.add(createNewButton, BorderLayout.WEST);
		
		south = new JPanel();
		frame.add(south, BorderLayout.SOUTH);
		south.setLayout(new GridLayout(2,1));
		
		namePrompt = new JLabel("Please enter your name:");
		namePrompt.setHorizontalAlignment(SwingConstants.CENTER);
		south.add(namePrompt);
		
		name = new JTextField();
		name.addKeyListener(new TextFieldHandler());
		south.add(name);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
		    	System.exit(0);
		    }
		});
		frame.setVisible(true);
	}
	
	private static class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals(createNewButton.getText())) {
				ExecutorService executorService = Executors.newCachedThreadPool();
				executorService.execute(new Server());
			}
			Client client = new Client(name.getText());
			
			//here we use client.getSocket() function to return the socket from client
			//and use that socket for entering the start of the game/waiting area
		}
		
	}
	private static class TextFieldHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			if(name.getText().equals("")){
				joinExistingButton.setEnabled(false);
				createNewButton.setEnabled(false);
			}
			else {
				joinExistingButton.setEnabled(true);
				createNewButton.setEnabled(true);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

>>>>>>> MenuAndClientConnect
}