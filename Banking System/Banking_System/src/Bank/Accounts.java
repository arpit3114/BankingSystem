package Bank;

import java.sql.*;
import java.util.Queue;
import java.util.Scanner;

public class Accounts {
    private Connection con;
    private Scanner sc;

    public Accounts(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public long open_account(String email){
        if(!account_exist(email)){
            String query = "insert into accounts(account_number, full_name, email, balance, security_pin) values (?,?,?,?,?);";
            sc.nextLine();
            System.out.println("Enter full name: ");
            String name = sc.nextLine();
            System.out.println(" enter Initial Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            System.out.println("enter Security Pin: ");
            String pin = sc.nextLine();

            try{
                long account_number = generateAccount(email);
                PreparedStatement ps = con.prepareStatement(query);
                ps.setLong(1,account_number);
                ps.setString(2,name);
                ps.setString(3,email);
                ps.setDouble(4,amount);
                ps.setString(5,pin);

                int rowaffected = ps.executeUpdate();
                if(rowaffected> 0) return account_number;
                else throw new RuntimeException("Account Creation Failed");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account already Exist");
    }

    public long getAccount_number(String email){
        String query = "select account_number from accounts where email = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getLong("account_number");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account not Found");
    }

    public long generateAccount(String email){
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select account_number from accounts order by account_number desc limit 1");
            if(rs.next()){
                long last_account_number = rs.getLong("account_number");
                return last_account_number +1;
            }else{
                return 10000100;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 10000100;
    }

    public boolean account_exist(String email){
        String query = "select account_number from accounts where email = ?";
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

