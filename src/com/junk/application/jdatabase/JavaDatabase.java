
package com.junk.application.jdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for manipulation of database (jdbc) in java
 * @author Elmerhd
 */
public class JavaDatabase {
    private Status status = Status.CLOSED;
    private String DRIVER;
    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Map<String,String> props = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, YYYY @hh:mm:ss a");
    private boolean showActivity = true;
    
    /**
     * Creates a new instance of the database
     * @param DRIVER the driver class
     * @param JDBC_URL the jdbc url
     * @param username the username 
     * @param password the password
     * @throws java.lang.ClassNotFoundException if the driver class is not in the class path.
     * @throws java.lang.InstantiationException  if the jvm fails to load the driver class
     * @throws java.lang.IllegalAccessException if the access is failed
     * @throws java.sql.SQLException if there is currently running database
     */
    public void openConnection(String DRIVER,String JDBC_URL,String username,String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        if(DRIVER == null){
            throw new RuntimeException("Driver class is null.");
        }else if(JDBC_URL == null){
            throw new RuntimeException("jdbc url is null");
        }else{
            this.USERNAME = username;
            this.PASSWORD = password;
            this.DRIVER = DRIVER;
            this.JDBC_URL = JDBC_URL;
            Class.forName(this.DRIVER).newInstance();
            this.connection = DriverManager.getConnection(this.JDBC_URL,username,password);
            status = Status.OPENED;
            setProperties();
            if(showActivity){
                System.out.println("[JD version 5.3] ");
                System.out.println("    → JDBC URL : "+this.JDBC_URL);
                System.out.println("    → Driver Class : "+this.DRIVER);
                System.out.println("    → Username : "+this.USERNAME);
                System.out.println("    → Status : "+getStatus()+" ["+sdf.format(new Date())+"]");
            }
        }
    }

    /**
     * Executes the sql Query.
     * @param sqlStatement  the sql query.
     * @throws SQLException if the statement is unable to execute.
     */
    public void executeQuery(String sqlStatement) throws SQLException{
        this.statement = this.connection.createStatement();
        this.statement.execute(sqlStatement);
        this.statement.close();
        if(showActivity){
            System.out.println("    → Query Executed : "+sqlStatement);
        }
    }

    /**
     * Closes the connection.
     * @throws java.sql.SQLException if cannot close the connection<p>
     * or if there is an existing result set on the connection
     */
    public void closeConnection() throws SQLException{
        getConnection().close();
        status = Status.CLOSED;
        if(showActivity){
            System.out.println("    → Status : "+getStatus()+" ["+sdf.format(new Date())+"]");
        }
    }

    /**
     * Gets the name of the driver.
     * @return the name of the driver.
     */
    public String getDriver(){
        return this.DRIVER;
    }

    /**
     * Gets the JDBC_URL.
     * @return the JDBC_URL.
     */
    public String getJDBC_URL(){
        return this.JDBC_URL;
    }

    /**
     * Gets the connection
     * @return connection
     */
    public Connection getConnection(){
        return this.connection;
    }
    /**
     * Gets the result set of the database.
     * @param query the sql query.
     * @return the result set.
     * @throws SQLException if the query is wrong.
     */
    public ResultSet getResultSet(String query) throws SQLException{
        this.preparedStatement = getConnection().prepareStatement(query);
        this.resultSet = preparedStatement.executeQuery();
        if(showActivity){
            System.out.println("    → Query Executed : "+query);
        }
        return this.resultSet;
    }
    /**
     * Gets the username of the database connection.
     * @return 
     */
    public String getUSERNAME() {
        return USERNAME;
    }
    /**
     * Gets the password of the connection.
     * @return 
     */
    public String getPASSWORD() {
        return PASSWORD;
    }
    
    /**
     * Sets the properties of the database.
     */
    private void setProperties(){
        props.put("JDBC_URL", JDBC_URL);
        props.put("DRIVER", DRIVER);
    }
    /**
     * Gets the properties of the database.
     * @return properties Map.
     */
    public Map<String,String> getProperties(){
        return props;
    }
    /**
     * Gets the PreparedStatement of the sql query.
     * @param sql the sql query.
     * @return the prepared statement.
     * @throws SQLException sql query is incorrect/connection is closed
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException{
        preparedStatement = connection.prepareStatement(sql);
        if(showActivity){
            System.out.println("    → Statement Executed : "+sql);
        }
        return preparedStatement;
    }
    /**
     * Gets the status.
     * @return {@link com.junk.application.jdatabase.Status}
     */
    public Status getStatus(){
        return status;
    }
    /**
     * Toggle to show the activity of the database.
     * @param show 
     */
    public void showActivity(boolean show){
        showActivity = show;
    }
}
