package Views;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;

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
}