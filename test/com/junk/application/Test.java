package com.junk.application;

import com.junk.application.jdatabase.JavaDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Elmerhd
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        JavaDatabase database = new JavaDatabase();
        database.openConnection("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/sample", "app", "app");
        String query = "select customer_id,name,email from customer";
        ResultSet rs = database.getResultSet(query);
        while (rs.next()) {            
            System.out.println("    data:"+rs.getInt(1));
            System.out.println("    data:"+rs.getString(2));
            System.out.println("    data:"+rs.getString(3));
            System.out.println("");
        }
        database.closeConnection();
        System.out.println("");
        
        
        //mysql
//        JavaDatabase database1 = new JavaDatabase();
//        database1.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/test_jax?zeroDateTimeBehavior=convertToNull", "root", "mysql");
//        database1.closeConnection();
    }
}
