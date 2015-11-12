
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

    public static ResultSet queryDatabase(String query){
        ResultSet res = null;
        try {
            if(con==null) {
                connectToDB();
            }
            statement = con.createStatement();
            statement.execute(query);
            res = statement.getResultSet();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public static String updateDatabase(String updateQuery){
        String error = "no error reported";

        try{
            if(con==null){
                connectToDB();
            }

            statement = con.createStatement();
            statement.executeUpdate(updateQuery);
            statement.close();
            statement = null;
            con.close();
            con = null;


        }catch (SQLException ee){
            error = ee.getMessage();
        } finally {
            closeConnection();
        }

        return error;
    }

    public static String queryToAddToDatabase(String sqlQuery){
        Statement state;
        String error = "no error";
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
        try {
            con.close();
            con=null;
            statement.close();
            statement=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
