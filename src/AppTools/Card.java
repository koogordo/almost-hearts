package AppTools;

import javax.swing.*;
/**
 * A simple class meant to be used as card objects throughout the entire project and extends JLabel.
 * It extends JLabel so it possible to make the object of the card itself the component to display.
 * 
 * The constructor takes in the suit value as a String (lower case) and the value of the card (2-15).
 * It also grabs the image as an ImageIcon from the cardImages resource folder.
 * The rest of the methods in the class return the suit, value, the ImageIcon, and the toString.
 */
public class Card extends JLabel {	
	private String suit; // Initialize suit of type string
	private int value; // Initialize value of type int
	private String imagePath; // Initialize imagePath of type String
	private ImageIcon cardImage; // Initialize cardImage of type ImageIcon
	
	public Card(String s, int v) { // Card Method that take in an int and String
		suit = s; // Set suit to s
		value = v;	// Set value to v
		imagePath = "/cardImages/"+ value + suit + ".png"; // Setting the card image path for the card
		cardImage = new ImageIcon(this.getClass().getResource(imagePath)); // Setting the card's image to the image of the above path
	}
	public String getSuit() { // getSuit Method the returns the card's suit
		return suit;
	}
	public int getValue() { // getValue Method that returns the card's value
		return value;
	}
	public ImageIcon getImage() { // imageIcon Method that returns the card's image
		return cardImage;
	}
	public String toString() {
		return this.suit + " " + this.value;
	}
}

