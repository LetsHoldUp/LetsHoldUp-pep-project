package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    /**
     * 
     * @param account This is the username and password of the account we are attempting to create
     * @return Returns a newly created account, or returns null if there was already an account with it's username. Or if the username/password is too short
    */ 
    public Account insertAccount(Account account){
        // Check to see if the username is not blank
        if(account.getUsername().length() <= 0){
            return null;
        }

        // Check to see if the password is too short
        if(account.getPassword().length() < 4){
            return null;
        }

        // Check to see if an account exists with this username already
        if(accountDAO.getAccountByUsername(account.getUsername()) == null){
            return accountDAO.insertAccount(account); 
        }
        return null;
    }

    public Account processLogin(Account account){
        // Query the database for an account with a username matching the one we are looking for
        Account found = accountDAO.getAccountByUsername(account.getUsername());

        // Make sure user exists
        if(found == null){
            return null;
        }

        // Now check that the passwords match
        System.out.println("'" + account.getPassword() + "' '" + found.getPassword()+"'");
        if(account.getPassword().equals(found.getPassword())){
            return found; 
        }
        
        return null; 
    }
}
