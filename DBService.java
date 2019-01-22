
package com.rcpl.jeep;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBService 
{  
    static private Connection con;
    
    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jeep","root","*******");
            if(con!=null)
                 {
             System.out.println("Connection is OK.");   
                  }
            else
                    {
                    System.out.println("Connection is not OK.");
                    }            
            }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static Connection getConnection()
    {
        return con;
    }

}


