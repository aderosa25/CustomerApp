package id.co.bankmandiri.customerapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static final String dburl = "jdbc:mysql://localhost:3306/belajar_java";
    private static final String username = "root";
    private static final String password = "";
    private static Connection connection;
    private static Properties properties;

    public static String getProperty(String key){
        if (properties != null){
            return properties.getProperty(key);
        }else {
            properties = new Properties();
            InputStream is = DbUtil.class.getClassLoader().getResourceAsStream("database.properties");
            try {
                properties.load(is);
                return properties.getProperty(key);
            } catch (IOException ioe){
                ioe.printStackTrace();
                return null;
            }
        }
    }
    public static Connection getConnection(){
        if (connection != null){
            return connection;
        } else {
            try {
                connection = DriverManager.getConnection(
                        getProperty("url"),
                        getProperty("username"),
                        getProperty("password")
                );
            }catch (SQLException sqle){
                sqle.printStackTrace();
            }
        }
        return connection;
    }
}
