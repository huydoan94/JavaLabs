
import java.util.ArrayList;

public class EchoServer extends AbstractServer {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port) {

        super(port);

        try {
            this.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }

    }

    //Instance methods ************************************************
    /**
     * This method handles any messages received from the client.
     *
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof Envelope) {
            Envelope env = (Envelope) msg;
            handleCommandFromClient(env, client);
        } else {
            System.out.println("Message received: " + msg + " from " + client);

            String userId = client.getInfo("userId").toString();

            this.sendToAllClientsInRoom(userId + ": " + msg, client);
        }
    }

    public void handleCommandFromClient(Envelope env, ConnectionToClient client) {
        if (env.getId().equals("login")) {
            String userId = env.getContents().toString();
            client.setInfo("userId", userId);
            client.setInfo("room", "lobby");
        }

        if (env.getId().equals("join")) {
            String roomName = env.getContents().toString();
            client.setInfo("room", roomName);
        }

        if (env.getId().equals("pm")) {
            String target = env.getArg();
            String message = env.getContents().toString();
            sendToAClient(message, target, client);
        }

        if (env.getId().equals("yell")) {
            String message = env.getContents().toString();
            String userId = client.getInfo("userId").toString();
            sendToAllClients(userId + " yells: " + message);
        }

        System.out.println(env.getId());
        if (env.getId().equals("who")) {
            sendRoomListToClient(client);
        }
    }

    public void sendRoomListToClient(ConnectionToClient client) {
        Envelope env = new Envelope();
        env.setId("who");
        ArrayList<String> userList = new ArrayList<String>();
        String room = client.getInfo("room").toString();
        env.setArg(room);

        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String targetRoom = target.getInfo("room").toString();
            if (targetRoom.equals(room)) {
                userList.add(target.getInfo("userId").toString());
            }
        }

        env.setContents(userList);

        try {
            client.sendToClient(env);
        } catch (Exception e) {
            System.out.println("Failed to send userList to client");
        }
    }

    public void sendToAllClientsInRoom(Object msg, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();
        String room = client.getInfo("room").toString();

        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            if (target.getInfo("room").equals(room)) {
                try {
                    target.sendToClient(msg);
                } catch (Exception ex) {
                    System.out.println("failed to send to client");
                }
            }
        }
    }

    public void sendToAClient(Object msg, String pmTarget, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            if (target.getInfo("userId").equals(pmTarget)) {
                try {
                    target.sendToClient(msg);
                } catch (Exception ex) {
                    System.out.println("failed to send to private message");
                }
            }
        }
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * stops listening for connections.
     */
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    //Class methods ***************************************************
    /**
     * This method is responsible for the creation of the server instance (there
     * is no UI in this phase).
     *
     * @param args[0] The port number to listen on. Defaults to 5555 if no
     * argument is entered.
     */
    public static void main(String[] args) {
        int port = 0; //Port to listen on

        try {
            port = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException oob) {
            port = DEFAULT_PORT; //Set port to 5555
        }

        EchoServer sv = new EchoServer(port);

        try {
            sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }

    }

    protected void clientConnected(ConnectionToClient client) {

        System.out.println("<Client Connected:" + client + ">");
        client.setInfo("room", "lobby");
        client.setInfo("userId", "guest");
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        System.out.println("Client shutdown");
    }
    
    

}
//End of EchoServer class
