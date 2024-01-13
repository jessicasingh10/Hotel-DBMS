import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class BrowseData {
	

	public BrowseData() {
		JFrame mainFrame = new JFrame();
		mainFrame.setSize(300,200);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setTitle("Browse Data");
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JButton guest = new JButton("Guests");
		JButton reservation = new JButton("Reservations");
		JButton payment = new JButton("Monthly Revenue");
		JButton rooms = new JButton("Rooms");
		
		guest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Guests();
			}
		});
		
		rooms.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Rooms();
			}
		});
		
		reservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Reservations();
			}
		});
		
		payment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Payments();
			}
		});
		
		
		panel.add(guest);
		panel.add(reservation);
		panel.add(payment);
		panel.add(rooms);
		mainFrame.add(panel);
		mainFrame.setVisible(true);
		
	}
	
	class Guests{
		
		private ArrayList<String[]> guests;
		
		public Guests() {
			
			getGuests("","");
			
			JFrame main = new JFrame();
			main.setSize(900,700);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			main.setTitle("Browse guests");
			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
			mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
			
			TableModel model = new DefaultTableModel() {
				String[] cols = {"guest ID","First Name","Last Name","Email Address","Phone Number"};
				public int getColumnCount() {return 5;}
				public int getRowCount() { return guests.size(); }
				public Object getValueAt(int row,int col) {
					return guests.get(row)[col];
				}
				public String getColumnName(int col) {
					return cols[col];
				}
			};
			JTable table = new JTable(model);
			
			JLabel idLabel = new JLabel("Guest ID");
			JTextField idField = new JTextField("",6);
			
			JLabel nameLabel = new JLabel("First or Last Name");
			JTextField nameField = new JTextField("");
			
			JButton searchButton = new JButton("Search");
			searchButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String name = nameField.getText();
					String id = idField.getText();
					getGuests(id,name);
					table.updateUI();
					
					
					
					
				}
				
			});
			
			JTextField newID = new JTextField("Guest ID");
			JTextField newFName = new JTextField("First Name");
			JTextField newLName = new JTextField("Last Name");
			JTextField newEmail = new JTextField("Email");
			JTextField newPNum = new JTextField("Phone Number",9);
			JButton addGuest = new JButton("Submit");
			
			addGuest.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String message = "Error submitting";
					String ID = newID.getText();
					String fName = newFName.getText();
					String lName = newLName.getText();
					String nEmail = newEmail.getText();
					String PNum = newPNum.getText();
					
					boolean submitted = SqlDriver.addGuest(ID, fName, lName, nEmail, PNum);
					if(submitted) {
						message = "Guest successfully added";
						getGuests("","");
					}
						
					
					JOptionPane.showMessageDialog(mainPanel, message);
					table.updateUI();
					
				}
			});
			
		
			
			
			JPanel searchPanel = new JPanel();
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints constraings = new GridBagConstraints();
			constraings.fill = GridBagConstraints.BOTH;
			constraings.weightx = 1;
			
			GridBagConstraints labelConstraings = new GridBagConstraints();
			labelConstraings.fill = GridBagConstraints.BOTH;
			labelConstraings.weightx = 0.25;
			
			searchPanel.setLayout(gridbag);
			searchPanel.add(idLabel);
			searchPanel.add(idField);
			
			searchPanel.add(nameLabel);
			searchPanel.add(nameField);
			
			searchPanel.add(searchButton);
			
			gridbag.setConstraints(idLabel,labelConstraings);
			gridbag.setConstraints(nameLabel,labelConstraings);
			
			gridbag.setConstraints(idField,constraings);
			gridbag.setConstraints(nameField,constraings);
			gridbag.setConstraints(searchButton,constraings);
			
			JLabel addLabel = new JLabel("Add a new Guest");
			JPanel addPanel = new JPanel();
			GridBagLayout gridbag2 = new GridBagLayout();
			GridBagConstraints constraings2 = new GridBagConstraints();
			constraings2.fill = GridBagConstraints.BOTH;
			constraings2.weightx = 1;
			addPanel.setLayout(gridbag2);
			addPanel.add(newID);
			addPanel.add(newFName);
			addPanel.add(newLName);
			addPanel.add(newEmail);
			addPanel.add(newPNum);
			addPanel.add(addGuest);
			gridbag2.setConstraints(newID,constraings);
			gridbag2.setConstraints(newFName,constraings);
			gridbag2.setConstraints(newLName,constraings);
			gridbag2.setConstraints(newEmail,constraings);
			gridbag2.setConstraints(newPNum,constraings);
			gridbag2.setConstraints(addGuest,constraings);
			
			
			JScrollPane scroll = new JScrollPane(table);
			mainPanel.add(searchPanel);
			mainPanel.add(scroll);
			mainPanel.add(addLabel);
			mainPanel.add(addPanel);
			main.add(mainPanel);
			main.setVisible(true);
			
			
		}
		
		private void getGuests(String id,String name) {
			guests = SqlDriver.getGuests(id,name);
			
			
		}
	}
	
	
	
class Rooms{
		
		private ArrayList<String[]> rooms;
		
		public Rooms() {
			
			getRooms("","","");
			
			JFrame main = new JFrame();
			main.setSize(900,700);
			main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			main.setTitle("Browse Rooms");
			JPanel mainPanel = new JPanel();
			mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
			mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
			
			TableModel model = new DefaultTableModel() {
				String[] cols = {"room ID","room Number","Number of Beds","Room status","Price per night"};
				public int getColumnCount() {return 5;}
				public int getRowCount() { return rooms.size(); }
				public String getColumnName(int col) {
					return cols[col];
				}
				public Object getValueAt(int row,int col) {
					return rooms.get(row)[col];
				}
			};
			
			JTable table = new JTable(model);
			
			
			JTextField idField = new JTextField("",6);
			JTextField room_numField = new JTextField("");
			JTextField statusField = new JTextField("");
			
			JLabel idLabel = new JLabel("room ID");
			JLabel room_number_Label = new JLabel("Room Number");
			JLabel statusLabel = new JLabel("Room Status");
			
			JButton searchButton = new JButton("Search");
			searchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = room_numField.getText();
					String id = idField.getText();
					String status = statusField.getText();
					
					getRooms(id,name,status);
					table.updateUI();
					
				} });
			
			JPanel searchPanel = new JPanel();
			
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints constraings = new GridBagConstraints();
			constraings.fill = GridBagConstraints.BOTH;
			constraings.weightx = 1;
			GridBagConstraints labelConstraings = new GridBagConstraints();
			labelConstraings.fill = GridBagConstraints.BOTH;
			labelConstraings.weightx = 0.25;
			
			searchPanel.setLayout(gridbag);
			
			searchPanel.add(idLabel);
			searchPanel.add(idField);
			
			searchPanel.add(room_number_Label);
			searchPanel.add(room_numField);
			
			searchPanel.add(statusLabel);
			searchPanel.add(statusField);
			
			searchPanel.add(searchButton);
			
			gridbag.setConstraints(idLabel,labelConstraings);
			gridbag.setConstraints(room_number_Label,labelConstraings);
			gridbag.setConstraints(statusLabel,labelConstraings);
			
			gridbag.setConstraints(idField,constraings);
			gridbag.setConstraints(room_numField,constraings);
			gridbag.setConstraints(statusField,constraings);
			gridbag.setConstraints(searchButton,constraings);
			
			JScrollPane scroll = new JScrollPane(table);
			
			mainPanel.add(searchPanel);
			mainPanel.add(scroll);
			main.add(mainPanel);
			main.setVisible(true);
			
			
		}
		
		public void getRooms(String id, String room_num,String status) {
			rooms = SqlDriver.getRooms(id, room_num,status);
		}
	}

class Reservations{
	
	ArrayList<String[]> reservations;
	public Reservations() {
		
		getReservations("","","");
		
		JFrame main = new JFrame();
		main.setSize(1000,700);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setTitle("Browse guests");
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		TableModel model = new DefaultTableModel() {
			String[] cols = {"Reservation ID","Room Number","Guest","Guest Email","Status","Check-in","Number of Guests"};
			public int getColumnCount() {return 7;}
			public int getRowCount() { return reservations.size(); }
			public Object getValueAt(int row,int col) {
				return reservations.get(row)[col];
			}
			public String getColumnName(int col) {
				return cols[col];
			}
		};
		JTable table = new JTable(model);
		
		JLabel idLabel = new JLabel("Reservation ID");
		JTextField idField = new JTextField("",6);
		
		JLabel monthLabel = new JLabel("Month of the year");
		JTextField monthField = new JTextField("");
		
		JLabel statusLabel = new JLabel("Status");
		JTextField statusField = new JTextField("");
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				String month = monthField.getText();
				String id = idField.getText();
				String status = statusField.getText();
				
				getReservations(id,month,status);
				table.updateUI();
				
				
				
				
			}
			
		});
		
		JTextField newID = new JTextField("Reservation ID");
		JTextField newStatus = new JTextField("Updated Status");
		JButton updateRes = new JButton("Update");
		
		updateRes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
				String id = newID.getText();
				String status = newStatus.getText();
				String message = "Error updating reservation";
				boolean updated = updateStatus(id,status);
			
				if(updated) {
					getReservations("","","");
					table.updateUI();
					message = "Reservation Updated";
				}
				JOptionPane.showMessageDialog(mainPanel, message);
				
				
				
				
				
			}
			
		});
	
		
		JPanel searchPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraings = new GridBagConstraints();
		constraings.fill = GridBagConstraints.BOTH;
		constraings.weightx = 1;
		
		GridBagConstraints labelConstraings = new GridBagConstraints();
		labelConstraings.fill = GridBagConstraints.BOTH;
		labelConstraings.weightx = 0.25;
		
		searchPanel.setLayout(gridbag);
		searchPanel.add(idLabel);
		searchPanel.add(idField);
		
		searchPanel.add(monthLabel);
		searchPanel.add(monthField);
		
		searchPanel.add(statusLabel);
		searchPanel.add(statusField);
		
		searchPanel.add(searchButton);
		
		gridbag.setConstraints(idLabel,labelConstraings);
		gridbag.setConstraints(monthLabel,labelConstraings);
		gridbag.setConstraints(statusLabel,labelConstraings);
		
		gridbag.setConstraints(idField,constraings);
		gridbag.setConstraints(monthField,constraings);
		gridbag.setConstraints(statusField,constraings);
		gridbag.setConstraints(searchButton,constraings);
		
		JLabel addLabel = new JLabel("Update Reservation Status");
		JPanel addPanel = new JPanel();
		GridBagLayout gridbag2 = new GridBagLayout();
		GridBagConstraints constraings2 = new GridBagConstraints();
		constraings2.fill = GridBagConstraints.BOTH;
		constraings2.weightx = 1;
		addPanel.setLayout(gridbag2);
		addPanel.add(newID);
		addPanel.add(newStatus);
		addPanel.add(updateRes);
		
		gridbag2.setConstraints(newID,constraings);
		gridbag2.setConstraints(newStatus,constraings);
		gridbag2.setConstraints(updateRes,constraings);
		
	
		
		
		JScrollPane scroll = new JScrollPane(table);
		mainPanel.add(searchPanel);
		mainPanel.add(scroll);
		mainPanel.add(addLabel);
		mainPanel.add(addPanel);
		main.add(mainPanel);
		main.setVisible(true);
		
		
	}

	private void getReservations(String id,String month,String status) {
		reservations = SqlDriver.getReservations(id,month,status);
		
		
		}
	
	private boolean updateStatus(String id, String status) {
		return SqlDriver.updateReservation(id, status);
	}
	}

class Payments{
	
	private ArrayList<String[]> payments;
	
	public Payments() {
		
		getPayments("");
		
		JFrame main = new JFrame();
		main.setSize(900,700);
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		main.setTitle("Total Revenue per month");
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		TableModel model = new DefaultTableModel() {
			String[] cols = {"month","Total Revenue",};
			public int getColumnCount() {return 2;}
			public int getRowCount() { return payments.size(); }
			public String getColumnName(int col) {
				return cols[col];
			}
			public Object getValueAt(int row,int col) {
				return payments.get(row)[col];
			}
		};
		
		JTable table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
	
		mainPanel.add(scroll);
		main.add(mainPanel);
		main.setVisible(true);
		
		
	}
	
	public void getPayments(String month) {
		payments = SqlDriver.getPayments();
	}
}

}
