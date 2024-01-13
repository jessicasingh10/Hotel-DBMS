import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainClass {
	
	private static boolean connected;
	
	public MainClass(){
		
		
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(300,200);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		JButton insertButton = new JButton("Insert dummy Data");
		JButton createButton = new JButton("Create tables");
		JButton dropButton = new JButton("Drop tables");
		JButton viewTables = new JButton("Browse");
		JLabel connectionLabel = new JLabel("Connected: " + connected );
		
		insertButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				final boolean inserted = insertTables();
				String message = "Error inserting data";
				if(inserted)
					message = "Dummy data successfully inserted";
				
				JOptionPane.showMessageDialog(panel, message);
			}
			
		});
		dropButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				final boolean inserted = dropTables();
				String message = "Error dropping data";
				if(inserted)
					message = "Tables dropped";
				
				JOptionPane.showMessageDialog(panel, message);
			}
			
		});
		
		createButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final boolean created = createTables();
				String message = "Error creating some tables";
				if(created)
					message = "All tables created";
				JOptionPane.showMessageDialog(panel, message);
			}
			
		});
		
		viewTables.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openViewWindow();
			}
		});
		
	
		
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(connectionLabel);
		
		panel.add(createButton);
		panel.add(insertButton);
		panel.add(dropButton);
		panel.add(viewTables);
		mainFrame.add(panel);
		
		mainFrame.setTitle("Hotel Management System");
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		connected = SqlDriver.connect();
		new MainClass();
		
	}
	
	private boolean dropTables() {
		return SqlDriver.dropTables();
	}
	
	private boolean insertTables() {
		
		return SqlDriver.insertDummyData();
	}
	
	private boolean createTables() {
		return SqlDriver.createTables();
	
		
	}
	
	private void openViewWindow() {
		new BrowseData();
	}
	
	

	
}
