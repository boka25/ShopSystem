package loginapp;

import dbUtil.dbConnection;

import javax.security.auth.spi.LoginModule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;

    public LoginModel(){
        try{
            this.connection = dbConnection.getConnection();
            System.out.println("conn");
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        if(this.connection == null){
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected(){
        return this.connection != null;
    }

    public boolean isLogin (String user, String pass, String opt) throws SQLException {

        PreparedStatement pr = null ;
        ResultSet rs = null;

        String sql = "SELECT * FROM login where username = ? and password = ? and division = ?";

        try{
            pr = this.connection.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, pass);
            pr.setString(3, opt);

            rs = pr.executeQuery();
            System.out.println(sql);
            System.out.println(user + pass + opt);
            boolean boll1;

            if(rs.next()){
                System.out.println("true");
                return true;

            }
            System.out.println("folse");
            return false;
        }catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("cach");
            return false;
        }

        finally {
            pr.close();
            rs.close();
        }
    }

}
