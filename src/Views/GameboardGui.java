package Views;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;

public class GameboardGui extends Applet 
{
    JPanel panel_1;
    JPanel panel_2;
    JButton button_1;
    JLabel label_1;
    JLabel label_2;
    JLabel label_3;
    JLabel label_4;
    JPanel panel_3;
    JPanel panel_4;
    JPanel panel_5;

    public void init() 
    {
        GameboardGuiLayout customLayout = new GameboardGuiLayout();

        setFont(new Font("Helvetica", Font.PLAIN, 12));
        setLayout(customLayout);

        panel_1 = new JPanel();
        add(panel_1);

        panel_2 = new JPanel();
        add(panel_2);

        button_1 = new JButton("button_1");
        add(button_1);

        label_1 = new JLabel("label_1");
        add(label_1);

        label_2 = new JLabel("label_2");
        add(label_2);

        label_3 = new JLabel("label_3");
        add(label_3);

        label_4 = new JLabel("label_4");
        add(label_4);

        panel_3 = new JPanel();
        add(panel_3);

        panel_4 = new JPanel();
        add(panel_4);

        panel_5 = new JPanel();
        add(panel_5);

        setSize(getPreferredSize());

    }

    public static void main(String args[]) 
    {
        GameboardGui applet = new GameboardGui();
        Frame window = new Frame("GameboardGui");

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

class GameboardGuiLayout implements LayoutManager 
{

    public GameboardGuiLayout() 
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
        dim.width = 538 + insets.left + insets.right;
        dim.height = 340 + insets.top + insets.bottom;

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
        if (c.isVisible()) {c.setBounds(insets.left+0,insets.top+0,536,216);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+0,insets.top+216,536,112);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+296,72,24);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+8,insets.top+8,520,32);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+48,insets.top+56,72,24);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+408,insets.top+56,72,24);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+56,72,24);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+408,insets.top+88,72,104);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+224,insets.top+88,72,104);}
        c = parent.getComponent(9);
        if (c.isVisible()) {c.setBounds(insets.left+48,insets.top+88,72,104);}
    }
}