package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


// account_id integer primary key auto_increment,
// username varchar(255),
// password varchar(255)

// Connection connection = ConnectionUtil.getConnection();
// try {
//     // Prepare SQL and prepared statement
//     String sql = "";
//     PreparedStatement preparedStatement = connection.prepareStatement(sql);

//     // Set string of prepared statement
//     preparedStatement.setString(1, account.getUsername());
//     preparedStatement.setString(2, account.getPassword());

//     // Execute 


    
// } catch(SQLException e){
//     System.out.println(e.getMessage());
// }
// return null;




public class AccountDAO {
    // Insert a new account 
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Prepare SQL and prepared statement
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set string of prepared statement
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Execute 
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_author_id, account.getUsername(), account.getPassword());
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Delete an account


    // Get account by id
    
    
    // Get account by username


    // Change Username?
}
