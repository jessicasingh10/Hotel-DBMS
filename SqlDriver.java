
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

 
/**
 * This program demonstrates how to make database connection with Oracle
 
 *
 */
public class SqlDriver {
 
	private static Connection conn1 = null;
	
    public static boolean connect() {
    	
        boolean isConnected = false;

        try {
 
           
         //   String dbURL1 = "jdbc:oracle:thin:username/password@oracle.scs.ryerson.ca:1521:orcl";  // that is school Oracle database and you can only use it in the labs
																						
         	
             String dbURL1 = "jdbc:oracle:thin:username/password@oracle12c.scs.ryerson.ca:1521:orcl12c";
			/* This XE or local database that you installed on your laptop. 1521 is the default port for database, change according to what you used during installation. 
			xe is the sid, change according to what you setup during installation. */
			
			conn1 = DriverManager.getConnection(dbURL1);
            if (conn1 != null) {
                isConnected = true;
                	
            }
 
 		
			
    
            //In your database, you should have a table created already with at least 1 row of data. In this select query example, table testjdbc was already created with at least 2 rows of data with columns NAME and NUM.
			//When you enter your data into the table, please make sure to commit your insertions to ensure your table has the correct data. So the commands that you need to type in Sqldeveloper are
			// CREATE TABLE TESTJDBC (NAME varchar(8), NUM NUMBER);
            // INSERT INTO TESTJDBC VALUES ('ALIS', 67);
            // INSERT INTO TESTJDBC VALUES ('BOB', 345);
            // COMMIT;
			
        }catch (SQLException ex) {
            ex.printStackTrace();
        } 
		return isConnected;
			

    }
    
    public static ArrayList<String> getTables() {
    	final ArrayList<String> tableNames = new ArrayList<String>();
    	try {
    	DatabaseMetaData meta = conn1.getMetaData();
    	ResultSet tables = meta.getTables(null, "ALAZARE", null, null);
    	while(tables.next()) {
    		tableNames.add(tables.getString(3));
    		
    	}
    	}catch(SQLException ex){
    		System.out.println("problem fetching data");
    	}
    	return tableNames;
    }
    public static ArrayList<String> getGenericQueries(String filename,ArrayList<String> queries) {
    	
    	try {
    		File queryFile = new File(filename);
        	Scanner fileReader = new Scanner(queryFile);
        	String currentString = "";
        	while(fileReader.hasNextLine()) {
        		
        		String line = (fileReader.nextLine()).trim();
        		
        		if(line.contains(";") ) {
        			queries.add(currentString);
        			currentString = "";
        		}else {
        			currentString += line;
        		}
        	}
        	fileReader.close();
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	
    	return queries;
    }
    public static boolean createTables() {
    	ArrayList<String> queries = new ArrayList<String>();
    	
    	 getGenericQueries("createTables.txt",queries);
    	
    	
    	try {
    		Statement statement = conn1.createStatement();
    		for (String query: queries) {
    			statement.executeQuery(query);
    		}
    		return true;
    	}catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    	
    	
    }
    public static boolean dropTables() {
    	ArrayList<String> queries = new ArrayList<String>();
    	
    	 getGenericQueries("dropTables.txt",queries);
    	
    	
    	try {
    		Statement statement = conn1.createStatement();
    		for (String query: queries) {
    			statement.executeQuery(query);
    		}
    		return true;
    	}catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    	
    	
    }
    
    public static boolean insertDummyData() {
    	ArrayList<String> queries = new ArrayList<String>();
    	
   	 getGenericQueries("insertTables.txt",queries);
   	
   	
	   	try {
			Statement statement = conn1.createStatement();
			for (String query: queries) {
				statement.executeQuery(query);
			}
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public static ArrayList<String[]> getGuests(String id,String name){
    	final ArrayList<String[]> guests = new ArrayList<String[]>();
    
    	String query = "select * from guest";
    	
    	if(id.length() > 0) 
    		query += " WHERE guest_id = '"+id+"'";
    	else if(name.length() > 0) 
    		query += String.format(" WHERE first_name = '%s' OR last_name = '%s' ",name,name);
    	
    
    	
    	try {
    		
    		Statement statement = conn1.createStatement();
    		ResultSet rows = statement.executeQuery(query);
    		
    		while(rows.next() ) {
    			String[] guest = new String[5];
    			guest[0] = rows.getString(1);
    			guest[1] = rows.getString(2);
    			guest[2] = rows.getString(3);
    			guest[3] = rows.getString(4);
    			guest[4] = rows.getString(5);
  
    			guests.add(guest);
    		}
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return guests;
    }
    
    public static boolean addGuest(String guest_id,String first_name,String last_name,String email,String phone_num) {
    	boolean added = false;
    	String query = String.format("INSERT INTO guest VALUES(%s,'%s','%s','%s','%s')",guest_id,first_name,last_name,email,phone_num);
    	try {
    		Statement statement = conn1.createStatement();
    		statement.executeQuery(query);
    		added = true;
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return added;
    }
    
    public static ArrayList<String[]> getRooms(String id,String room_num,String status){
    	final ArrayList<String[]> guests = new ArrayList<String[]>();
    	String query = "select room_number.room_id,room_number,room_info.beds,room_info.room_status,room_info.room_price from room_info,room_number\n"
    			+ "WHERE room_number.room_id = room_info.room_id";
    	if(id.length() > 0)
    		query += String.format(" AND room_number.room_id = '%s'",id);
    	else if(room_num.length() > 0)
    		query += String.format(" AND room_number = '%s'",room_num);
    	else if(status.length() > 0)
    		query += String.format(" AND room_info.room_status = '%s' ",status);
    	try {
    		
    		Statement statement = conn1.createStatement();
    		ResultSet rows = statement.executeQuery(query);
    		
    		while(rows.next() ) {
    			String[] guest = new String[5];
    			guest[0] = rows.getString(1);
    			guest[1] = rows.getString(2);
    			guest[2] = rows.getString(3);
    			guest[3] = rows.getString(4);
    			guest[4] = rows.getString(5);
  
    			guests.add(guest);
    		}
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return guests;
    }
    
    public static ArrayList<String[]> getReservations(String id,String month,String status){
    	final ArrayList<String[]> reservations = new ArrayList<String[]>();
    	String query = "select reservation.reservation_id,room_number.room_number,guest.first_name || ' ' || guest.last_name,guest.email,reservation_info.status,reservation_info.check_in,reservation_info.guests "
    			+ "FROM (((reservation "
    			+ " INNER JOIN room_number ON reservation.room_id = room_number.room_id) "
    			+ " INNER join guest ON reservation.guest_id = guest.guest_id) "
    			+ " INNER join reservation_info ON reservation.reservation_id = reservation_info.reservation_id) ";
    	if(id.length() > 0)
    		query += String.format(" WHERE reservation.reservation_id = '%s'",id);
    	else if(month.length() > 0 && status.length() > 0)
    		query += String.format("WHERE extract(MONTH from to_date(check_in,'dd/mm/yyyy')) = %s AND reservation_info.status = '%s' ",month,status);
    	else if(status.length() > 0)
    		query += String.format(" WHERE reservation_info.status = '%s' ",status);
    	else if(month.length() > 0)
    		query += String.format(" WHERE extract(MONTH from to_date(check_in,'dd/mm/yyyy')) = %s ",month);
    	try {
    		
    		Statement statement = conn1.createStatement();
    		ResultSet rows = statement.executeQuery(query);
    		
    		while(rows.next() ) {
    			String[] reservation = new String[7];
    			reservation[0] = rows.getString(1);
    			reservation[1] = rows.getString(2);
    			reservation[2] = rows.getString(3);
    			reservation[3] = rows.getString(4);
    			reservation[4] = rows.getString(5);
    			reservation[5] = rows.getString(6);
    			reservation[6] = rows.getString(7);
  
    			reservations.add(reservation);
    		}
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return reservations;
    }
    
    public static boolean updateReservation(String reservationID,String status) {
    	boolean updated = false;
    	String query = String.format("UPDATE reservation_info SET status = '%s' WHERE reservation_info.reservation_id = '%s'", status,reservationID);
    	try {
    		Statement statement = conn1.createStatement();
    		statement.executeQuery(query);
    		updated = true;
    		
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return updated;
    }
    
    public static ArrayList<String[]> getPayments(){
    	final ArrayList<String[]> payments = new ArrayList<String[]>();
    	
    	String query = " SELECT extract(MONTH from to_date(payment_date,'dd/mm/yyyy')), SUM(payment_amount) "
    			+ " FROM payment_info "
    			+ " GROUP BY extract(MONTH from to_date(payment_date,'dd/mm/yyyy') ) ";
    	
    
    	try {
    		
    		Statement statement = conn1.createStatement();
    		ResultSet rows = statement.executeQuery(query);
    		
    		while(rows.next() ) {
    			String[] payment = new String[7];
    			payment[0] = rows.getString(1);
    			payment[1] = rows.getString(2);
    			
  
    			payments.add(payment);
    		}
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return payments;
    }
}

