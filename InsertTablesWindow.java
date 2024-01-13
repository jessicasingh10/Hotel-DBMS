import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InsertTablesWindow {
	
	

	public InsertTablesWindow() {
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(500,700);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("Insert Data");
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JButton guest = new JButton("Add Guest");
		JButton reservation = new JButton("Add reservation");
		JButton payment = new JButton("Add Payment");
		
		
		panel.add(guest);
		panel.add(reservation);
		panel.add(payment);
		
		mainFrame.add(panel);
		mainFrame.setVisible(true);
		
	}
}
