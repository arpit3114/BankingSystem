package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

    private Connection con;
    private Scanner sc;

    public AccountManager(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public void credit_money(long account_number) throws SQLException{
        sc.nextLine();
        System.out.println("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter security Pin: ");
        String pin = sc.nextLine();

        try{
            con.setAutoCommit(false);
            if(account_number != 0){
                PreparedStatement ps = con.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
                ps.setLong(1,account_number);
                ps.setString(2, pin);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    String credit_query = "update accounts set balance = balance + ? where account_number = ?;";
                    PreparedStatement ps1 = con.prepareStatement(credit_query);
                    ps1.setDouble(1,amount);
                    ps1.setLong(2,account_number);
                    int affectedrow = ps1.executeUpdate();
                    if(affectedrow > 0) {
                        System.out.println(amount + "credited Successfully ");
                        con.commit();
                        con.setAutoCommit(true);
                        return;
                    }else{
                        System.out.println("Transaction failed");
                        con.rollback();
                        con.setAutoCommit(true);
                    }
                }else{
                    System.out.println("Invalid Security Pin");
                }
            }else System.out.println("invalid account number");
        }catch (SQLException e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }

    public void debit_money(long account_number) throws SQLException{
        sc.nextLine();
        System.out.println("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter security Pin: ");
        String pin = sc.nextLine();

        try{
            con.setAutoCommit(false);
            if(account_number != 0){
                PreparedStatement ps = con.prepareStatement("select * from accounts where account_number = ? and security_pin = ?");
                ps.setLong(1,account_number);
                ps.setString(2, pin);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    double current_balance = rs.getDouble("balance");
                    if(current_balance >= amount) {
                        String debit_query = "update accounts set balance = balance - ? where account_number = ?;";
                        PreparedStatement ps1 = con.prepareStatement(debit_query);
                        ps1.setDouble(1, amount);
                        ps1.setLong(2, account_number);
                        int affectedrow = ps1.executeUpdate();
                        if (affectedrow > 0) {
                            System.out.println(amount + "debited Successfully ");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    }else System.out.println("Insufficient Balance");
                }else{
                    System.out.println("Invalid Security Pin");
                }
            }else System.out.println("Invalid account number");
        }catch (SQLException e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }

     public void gatBalance(long account_number) {
        sc.nextLine();
         System.out.println("Enter security Pin: ");
         String pin = sc.nextLine();
         try{
             PreparedStatement ps = con.prepareStatement("Select balance from accounts where account_number = ? and security_pin = ?;");
             ps.setLong(1,account_number);
             ps.setString(2,pin);
             ResultSet rs = ps.executeQuery();
             if(rs.next()){
                 double balance = rs.getDouble("balance");
                 System.out.println("Balance: "+balance);
             }else{
                 System.out.println(" Invalid pin");
             }
         }catch(SQLException e){
             e.printStackTrace();
         }
     }

     public void transfer_money(long sender_account_number) throws SQLException{
        sc.nextLine();
         System.out.println("Enter receiver account number: ");
         long receiver_acc_num = sc.nextLong();
         System.out.println("enter amount: ");
         double amount = sc.nextDouble();
         sc.nextLine();
         System.out.println("Enter security pin: ");
         String pin = sc.nextLine();

         try{
            con.setAutoCommit(false);
            if(sender_account_number != 0 && receiver_acc_num != 0){
                PreparedStatement ps = con.prepareStatement("select * from accounts where account_number =? and security_pin =?;");
                ps.setLong(1,sender_account_number);
                ps.setString(2,pin);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    double current_balance = rs.getDouble("balance");
                    if(current_balance >= amount) {
                        String debit_query = "update accounts set balance = balance - ? where account_number = ?;";
                        String credit_query = "update accounts set balance = balance + ? where account_number = ?";
                        PreparedStatement ps1 = con.prepareStatement(debit_query);
                        PreparedStatement ps2 = con.prepareStatement(credit_query);
                        ps1.setDouble(1, amount);
                        ps1.setLong(2, sender_account_number);
                        ps2.setDouble(1,amount);
                        ps2.setLong(2,receiver_acc_num);

                        int affectedrow = ps1.executeUpdate();
                        int affectedrow2 = ps2.executeUpdate();
                        if (affectedrow > 0 && affectedrow2 > 0) {
                            System.out.println(amount + "Transaction Successfully ");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    }else System.out.println("Insufficient Balance");
                }else{
                    System.out.println("Invalid Security Pin");
                }
            }else System.out.println("Invalid Account number ");
         }catch (SQLException e){
             e.printStackTrace();
         }
         con.setAutoCommit(true);
     }
}
