package emse.fr.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.naming.*;
import javax.sql.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * This class returns the Oracle database connect object from a CentOS Oracle
 * Express Virtual Machine
 *
 * The method and variable in this class are static to save resources You only
 * need one instance of this class running.
 *
 * 
 *
 * 
 *
 *
 *
 */
public class DBClass {
	private DataSource mySQLDataSource = null; // hold the database object
	private Context context = null; // used to lookup the database connection in
									// weblogic

	/**
	 * This is a public method that will return the database connection.
	 *
	 *
	 *
	 * @return Database object
	 * @throws Exception
	 */
	private DataSource getMySQLConnection() throws Exception {
		/**
		 * check to see if the database object is already defined... if it is,
		 * then return the connection, no need to look it up again.
		 */
		// if (mySQLDataSource != null) {
		// return mySQLDataSource;
		// }
		try {
			/**
			 * This only needs to run one time to get the database object.
			 * context is used to lookup the database object in JNDI
			 * 
			 */
			// if (context == null) {
			// context = new InitialContext();
			// }
			/**
			 * ATTENTION: HERE PUT THE JNDI NAME
			 */

			// mySQLDataSource = (DataSource)
			// context.lookup("jdbc/MySQLDataSource");//JNDI NAME HERE

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WS_PROJECT", "root", "mysql");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mySQLDataSource;

	}

	/**
	 * This method will return the connection to the DB
	 * 
	 * @return Connection to MySQL database.
	 */
	public Connection returnConnection() {
		Connection con = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/WS_PROJECT", "root", "mysql");

			// conn = getMySQLConnection().getConnection();
			return con;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
