package Service;

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
        System.out.println("Entering this.accountDAO.getAccountByID");
        if(this.accountDAO.getAccountByID((int)message.getPosted_by()) == null){
            return null;
        }

        // From here persist the message and return it with 'message_id' now filled in 
        System.out.println("Entering messageDAO.persistMessage");
        return this.messageDAO.persistMessage(message);
    }
}
