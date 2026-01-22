package Bank;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection con;
    private Scanner sc;

    public User(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public void register() {
        sc.nextLine();
        System.out.println("Full name: ");
        String name = sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        if(user_exist(email)){
            System.out.println("User already exist for this email account");
        }

        String register_query = "insert into user(full_name, email, password ) values (?,?,?);";

        try{
            PreparedStatement ps = con.prepareStatement(register_query);
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,password);
            int affectedrow = ps.executeUpdate();
            if(affectedrow > 0) System.out.println("Registration successfully");
            else System.out.println("Registration failed");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String login(){
        sc.nextLine();
        System.out.println("Enter email: ");
        String email= sc.nextLine();
        System.out.println("Password: ");
        String password = sc.nextLine();

        String login_query = "select * from user where email = ? and password = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(login_query);
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return email;
            }else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean user_exist(String email){
        String query = "select * from user where email = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return true;
            else return false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
