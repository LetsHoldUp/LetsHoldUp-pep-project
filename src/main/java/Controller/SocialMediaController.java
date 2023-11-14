package Controller;

import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    // Add message service

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // 1. new User registration
        app.get("/register", this::insertAccount);

        // 2. Process user logins

        // 3. Creation of new messages

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
        // Add message service
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void insertAccount(Context ctx){

    }

}