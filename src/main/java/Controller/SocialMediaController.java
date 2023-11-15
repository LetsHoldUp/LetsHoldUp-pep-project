package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    ObjectMapper objectMapper;

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // 1. new User registration
        app.post("/register", this::insertAccount);

        // 2. Process user logins
        app.post("/login", this::processLogin);

        // 3. Creation of new messages
        app.post("/messages", this::postMessage);

        // 4. Receive all messages

        // 5. Retrieve a message by ID

        // 6. Delete a message by ID

        // 7. Update a message by ID

        // 8. Retrieve all messages by foreign key 'posted_by' 


        //app.start(8080);
        return app;
    }



    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.objectMapper = new ObjectMapper();
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void insertAccount(Context ctx) throws JsonProcessingException{
        // Convert body into class
        Account inAccount = objectMapper.readValue(ctx.body(), Account.class);

        // Call service insert method, which will return null if a matching username already exists
        Account outAccount = this.accountService.insertAccount(inAccount);

        // Check if outAccount contains an account, if so convert it to Json and return it 
        if(outAccount != null){
            // Success
            ctx.status(200);
            ctx.json(this.objectMapper.writeValueAsString(outAccount));
        }
        // Otherwise send a user error 
        else {
            // Failure
            ctx.status(400);
        }
    }

    private void processLogin(Context ctx) throws JsonProcessingException{
        // Convert body into class
        Account inAccount = objectMapper.readValue(ctx.body(), Account.class);

        // Call service process login method, returns null on failure, an account class of a match
        Account outAccount = this.accountService.processLogin(inAccount);

        // If outAccount is not null, we have successfully logged in
        if(outAccount != null){
            // Success
            ctx.status(200);
            ctx.json(this.objectMapper.writeValueAsString(outAccount));
        } else {
            // Failure
            ctx.status(401);
        }
    }

    private void postMessage(Context ctx) throws JsonProcessingException{
        // Convert json body to Message object 
        Message inMessage = objectMapper.readValue(ctx.body(), Message.class);
        
        // Call service post message method, returns a null on failure or a message object on a success
        Message outMessage = messageService.postMessage(inMessage);

        // If outMessage is not null, we have successfull posted a message
        if(outMessage != null){
            // Success
            ctx.status(200);
            ctx.json(this.objectMapper.writeValueAsString(outMessage));
        } else {
            // Failure
            ctx.status(400);
        }
    }

}