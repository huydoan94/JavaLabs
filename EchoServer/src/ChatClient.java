
import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable. It allows the implementation of the display
     * method in the client.
     */
    ChatIF clientUI;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the chat client.
     *
     * @param host The server to connect to.
     * @param port The port number to connect on.
     * @param clientUI The interface type variable.
     */
    public ChatClient(String host, int port, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
    }

    //Instance methods ************************************************
    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        if (msg instanceof Envelope) {
            Envelope env = (Envelope) msg;
            handleCommandFromServer(env);
        } else {
            clientUI.display(msg.toString());
        }
    }

    public void handleCommandFromServer(Envelope env) {
        if (env.getId().equals("who")) {
            ArrayList<String> userList = (ArrayList<String>) env.getContents();
            String room = env.getArg();
            clientUI.display("User in " + room + ":");
            for (String s : userList) {
                clientUI.display(s);
            }
        }
    }

    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {

        if (message.charAt(0) == '#') {

            handleClientCommand(message);

        } else {
            try {
                sendToServer(message);
            } catch (IOException e) {
                clientUI.display("Could not send message to server.  Terminating client.......");
                quit();
            }
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }

    public void connectionClosed() {

        System.out.println("Connection closed");

    }

    public void handleClientCommand(String message) {

        if (message.equals("#quit")) {
            clientUI.display("Shutting Down Client");
            quit();

        }

        if (message.equals("#logoff")) {
            clientUI.display("Disconnecting from server");
            try {
                closeConnection();
            } catch (IOException e) {
            };

        }

        if (message.indexOf("#setHost") > 0) {

            if (isConnected()) {
                clientUI.display("Cannot change host while connected");
            } else {
                setHost(message.substring(8, message.length()));
            }

        }

        if (message.indexOf("#setPort") > 0) {

            if (isConnected()) {
                clientUI.display("Cannot change port while connected");
            } else {
                setPort(Integer.parseInt(message.substring(8, message.length())));
            }

        }

        if (message.indexOf("#login") == 0) {

            if (isConnected()) {
                clientUI.display("already connected");
            } else {
                try {

                    openConnection();
                    String userName = message.substring(6, message.length()).trim();

                    Envelope env = new Envelope("login", "", userName);
                    this.sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }

        if (message.indexOf("#join") == 0) {
            try {
                String roomName = message.substring(5, message.length()).trim();
                Envelope env = new Envelope("join", "", roomName);
                this.sendToServer(env);
            } catch (IOException e) {
                clientUI.display("failed to join a room");
            }
        }

        if (message.indexOf("#pm") == 0) {
            try {
                String targetAndMessage = message.substring(3, message.length()).trim();

                String target = targetAndMessage.substring(0, targetAndMessage.indexOf(" ")).trim();
                String pm = targetAndMessage.substring(targetAndMessage.indexOf(" "), targetAndMessage.length()).trim();

                Envelope env = new Envelope("pm", target, pm);
                this.sendToServer(env);
            } catch (IOException e) {
                clientUI.display("could not private message user");
            }
        }

        if (message.indexOf("#yell") == 0) {

            try {
                String yellMessage = message.substring(5, message.length()).trim();
                String upper = yellMessage.toUpperCase();
                Envelope env = new Envelope("yell", "", upper);
                this.sendToServer(env);

            } catch (IOException e) {
                System.out.println("failed to yell");
            }
        }

        if (message.indexOf("#who") == 0) {

            try {
                Envelope env = new Envelope("who", "", "");
                this.sendToServer(env);

            } catch (IOException e) {
                System.out.println("failed to acquire user list");
            }
        }

    }

}
//End of ChatClient class
