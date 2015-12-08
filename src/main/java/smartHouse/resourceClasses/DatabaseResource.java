
package smartHouse.resourceClasses;

import java.sql.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import org.json.JSONObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author davidmunro
 */
public class DatabaseResource implements ServletContextListener{

    public static Connection con = null;
    private static Statement statement;

    public DatabaseResource(){
    }

    public static void connectToDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://br-cdbr-azure-south-a.cloudapp.net/SmartHouseTestDatabase?user=b946337fb358ff&password=b88095dc");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeStatement(Statement statement){
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
    }

    public static ResultSet queryDatabase(String query){
        ResultSet res = null;
        try {
            if(con==null) {
                connectToDB();
            }
            statement = con.createStatement();
            statement.executeQuery(query);

            res = statement.getResultSet();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public static boolean updateDatabase(String updateQuery){
        boolean successfull = false;
        Statement state;
        try{
            if(con==null){
                connectToDB();
            }
            statement = con.createStatement();
            int c = statement.executeUpdate(updateQuery);
            if(c>0) successfull = true;
        }catch (SQLException ee){
            ee.getMessage();
        }
        return successfull;
    }

    public static void queryToAddToDatabase(String sqlQuery){
        Statement state;
        try{
            if(con==null){
                connectToDB();
            }
            state = con.createStatement();
            state.executeUpdate(sqlQuery);
            state.close();

        }catch(SQLException ee){
            ee.getMessage();
        }
    }

    public static String updateDatabaseWithError(String sqlQuery){
        String error = "No error";
        Statement state;
        try{
            if(con==null){
                connectToDB();
            }
            state = con.createStatement();
            state.executeUpdate(sqlQuery);
            state.close();

        }catch(SQLException ee){
            error = ee.getMessage();
        }

        return error;
    }

    public static void closeConnection(){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con = null;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        Driver driver = null;

        while(drivers.hasMoreElements()){
            try{
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try{
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
