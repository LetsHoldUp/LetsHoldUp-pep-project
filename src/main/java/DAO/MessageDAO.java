package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public Message persistMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Prepare SQL and prepared statement
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set string of prepared statement
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            // Execute 
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                System.out.println(generated_message_id);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        // List of messages to return
        List<Message> retList = new ArrayList<>();
        try {
            // Prepare SQL and prepared statement
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set string of prepared statement
            // None this time :-)

            // Execute and save results
            ResultSet rs = preparedStatement.executeQuery();

            // Process results
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                retList.add(message);
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return retList;
    }

    public Message getMessageByID(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        // List of messages to return
        try {
            // Prepare SQL and prepared statement
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set string of prepared statement
            preparedStatement.setInt(1, messageID);

            // Execute and save results
            ResultSet rs = preparedStatement.executeQuery();

            // Process results
            while(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessageById(int messageID){
        Connection connection = ConnectionUtil.getConnection();
        Message messageToReturn = this.getMessageByID(messageID);
        try {
            // Prepare SQL and prepared statement
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set string of prepared statement
            preparedStatement.setInt(1, messageID);

            // Execute 
            preparedStatement.executeUpdate();


        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messageToReturn;
    }

    public Message updateMessageByID(int messageID, String messageBody){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Prepare SQL and prepared statement
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set string of prepared statement
            preparedStatement.setString(1, messageBody);
            preparedStatement.setInt(2, messageID);

            // Execute 
            preparedStatement.executeUpdate();


        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return this.getMessageByID(messageID);
    }

    public List<Message> getMessagesFromUser(int userID) {
        // List to fill and return
        List<Message> retList = new ArrayList<>();
        
        // Get connection
        Connection connection = ConnectionUtil.getConnection();

        try {
            // Prepare SQL and prepared statement
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set int of prepared statement
            preparedStatement.setInt(1, userID);

            // Execute and save results
            ResultSet rs = preparedStatement.executeQuery();

            // Process results
            while(rs.next()){
                Message temp = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                retList.add(temp);
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return retList;
    }

    // Get all messages by a user ID
    // public List<Message> getMessagesById(int userID){
    //     Connection connection = ConnectionUtil.getConnection();
    //     // List of messages to return
    //     List<Message> retList = new ArrayList<>();
    //     try {
    //         // Prepare SQL and prepared statement
    //         String sql = "SELECT * FROM Message WHERE posted_by = ?";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //         // Set string of prepared statement
    //         preparedStatement.setInt(1, userID);

    //         // Execute and save results
    //         ResultSet rs = preparedStatement.executeQuery();

    //         // Process results
    //         while(rs.next()){
    //             Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
    //             retList.add(message);
    //         }
            
    //     } catch(SQLException e) {
    //         System.out.println(e.getMessage());
    //     }
    //     return retList;
    // }

}
