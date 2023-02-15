
import java.io.IOException;
import static java.lang.Integer.parseInt;
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

        System.out.println("Echo Server is initialized with port " + port);
    }

    //Instance methods ************************************************
    /**
     * This method handles any messages received from the admin console.
     *
     * @param msg The message received from the console.
     */
    public void handleCommandFromAdmin(String message) {
        if (message.indexOf("#setPort") == 0) {
            try {
                int port = parseInt(message.substring("#setPort".length()).trim());
                this.setPort(port);
                System.out.println("Port is set to " + port);
            } catch (Exception e) {
                System.out.println("Cannot get port number!");
            }
        } else if (message.equals("#start")) {
            try {
                this.listen(); //Start listening for connections
                System.out.println("Echo Server is started and ready at port " + this.getPort());
            } catch (Exception ex) {
                System.out.println("ERROR - Could not listen for clients!");
            }
        } else if (message.equals("#stop")) {
            try {
                this.close();
                System.out.println("Echo Server is stopped");
            } catch (IOException ex) {
                System.out.println("Cannot close Echo Server");
            }
        } else if (message.equals("#quit")) {
            try {
                this.close();
                System.exit(0);
            } catch (IOException ex) {
                System.exit(255);
            }
        } else if (message.indexOf("#ison") == 0) {
            String user = message.substring("#ison".length()).trim();
            checkUserIsOn(user);
        } else if (message.equals("#userstatus")) {
            listUsersInRooms();
        } else if (message.indexOf("#joinroom") == 0) {
            String room1and2 = message.substring("#joinroom".length()).trim();
            String room1 = room1and2.substring(0, room1and2.indexOf(" ")).trim();
            String room2 = room1and2.substring(room1and2.indexOf(" ")).trim();
            moveUsersRoomsToOtherRooms(room1, room2);
        } else {
            this.sendToAllClients("<ADMIN>" + message);
        }
    }

    public void moveUsersRoomsToOtherRooms(String room1, String room2) {
        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String userId = target.getInfo("userId").toString();
            String room = target.getInfo("room").toString();
            if (room.equals(room1)) {
                target.setInfo("room", room2);
                System.out.println("Moving " + userId + " to room " + room2);
            }
        }
    }

    public void listUsersInRooms() {
        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String userId = target.getInfo("userId").toString();
            String room = target.getInfo("room").toString();
            System.out.println(userId + " - " + room);
        }
    }

    public void checkUserIsOn(String user) {
        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String userId = target.getInfo("userId").toString();
            String room = target.getInfo("room").toString();
            if (user.equals(userId)) {
                System.out.println(user + " is on in room " + room);
                return;
            }
        }

        System.out.println(user + " is not logged in");
    }

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

//    //Class methods ***************************************************
//    /**
//     * This method is responsible for the creation of the server instance (there
//     * is no UI in this phase).
//     *
//     * @param args[0] The port number to listen on. Defaults to 5555 if no
//     * argument is entered.
//     */
//    public static void main(String[] args) {
//        int port = 0; //Port to listen on
//
//        try {
//            port = Integer.parseInt(args[1]);
//        } catch (ArrayIndexOutOfBoundsException oob) {
//            port = DEFAULT_PORT; //Set port to 5555
//        }
//
//        EchoServer sv = new EchoServer(port);
//
//        try {
//            sv.listen(); //Start listening for connections
//        } catch (Exception ex) {
//            System.out.println("ERROR - Could not listen for clients!");
//        }
//
//    }
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
