package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public Message postMessage(Message message){
        // Verify message is acceptable. 0 < length of message < 255
        long messageLength = message.getMessage_text().length();
        if(messageLength <= 0 || messageLength >= 255){
            return null;
        }

        
        // Todo: check if posted by refers to a real user. If so, then persist message
        if(this.accountDAO.getAccountByID((int)message.getPosted_by()) == null){
            return null;
        }

        // From here persist the message and return it with 'message_id' now filled in 
        System.out.println("Entering messageDAO.persistMessage");
        return this.messageDAO.persistMessage(message);
    }

    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageByID(int messageID){
        return this.messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessageById(int messageID){
        return this.messageDAO.deleteMessageById(messageID);
    }

    public Message updateMessageByID(int messageID, String messageBody){
        // Ensure the message is not too long or short
        if(messageBody.length() == 0 || messageBody.length() >= 255){
            return null;
        }

        // Ensure the message already exists
        if(this.getMessageByID(messageID) == null){
            return null;
        }

        return this.messageDAO.updateMessageByID(messageID, messageBody);
    }

    public List<Message> getMessagesFromUser(int userID){
        // Check if user exists, if it does not return an empty list
        if (this.accountDAO.getAccountByID((long)userID) == null){
            return new ArrayList<Message>();
        }

        // Otherwise call messageDAO.getMesagesFromUser
        return this.messageDAO.getMessagesFromUser(userID);
    }
}
