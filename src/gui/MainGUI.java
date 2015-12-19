package gui;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainGUI extends JFrame{
	String srcStation;
	String dstStation;
	
	JLabel srcLabel;
	JLabel dstLabel;
	
	JTextArea srcText;
	JTextArea dstText;
	
	JButton searchBtn;
	
	JPanel mainPanel;
	JPanel inputPanel;
	
	public MainGUI(){
		srcStation = "input Source Station";
		dstStation = "input Destination Station";
		
		srcText = new JTextArea(srcStation);
		dstText = new JTextArea(dstStation);
		
		srcLabel = new JLabel("Source Station");
		dstLabel = new JLabel("Destination Station");
		
		searchBtn = new JButton("Search!");
	}
	

}
