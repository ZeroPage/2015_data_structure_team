package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MainGUI extends JFrame{
	private String srcStation;
	private String dstStation;
	
	private JLabel srcLabel;
	private JLabel dstLabel;
	private JLabel imageLabel;

	private JTextArea srcText;
	private JTextArea dstText;
	
	private JButton searchBtn;
	
	private JPanel mainPanel;
	private JPanel inputPanel;
	private JPanel mapPanel;
		
	private ImageIcon image;
	
	public MainGUI(){
		srcStation = "input Source Station";
		dstStation = "input Destination Station";
		
		srcText = new JTextArea(srcStation);
		dstText = new JTextArea(dstStation);
		srcText.setSize(200, 50);
		dstText.setSize(200, 50);

		srcLabel = new JLabel("Source Station");
		dstLabel = new JLabel("Destination Station");
		srcLabel.setSize(200, 50);
		srcLabel.setBackground(Color.white);
		dstLabel.setSize(200, 50);
		dstLabel.setBackground(Color.white);
		
		searchBtn = new JButton("Search!");
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello");
			}
		});
		
		mainPanel = new JPanel();
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
		inputPanel.add(srcLabel);
		inputPanel.add(srcText);
		inputPanel.add(dstLabel);
		inputPanel.add(dstText);
		inputPanel.add(searchBtn);
		
		image = new ImageIcon("resource/seoul_Map.jpg");

		imageLabel = new JLabel(image);
		
		mapPanel = new JPanel();
		mapPanel.add(imageLabel);
		
		mainPanel.setLayout(new BorderLayout(1, 2));
		mainPanel.add("North", inputPanel);
		mainPanel.add("South", mapPanel);
		
		this.add(mainPanel);
	}
	

}
