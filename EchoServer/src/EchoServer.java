
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;

public class EchoServer extends AbstractServer {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;
    final public static String ROOM_ID = "room";
    final public static String USER_ID = "userId";
    final public static String DEFAULT_USER_NAME = "guest";
    final public static String DEFAULT_ROOM_NAME = "lobby";

    private ChatIF chatConsole;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port, ChatIF chatConsole) {
        super(port);

        this.chatConsole = chatConsole;
        chatConsole.display("Echo Server is initialized with port " + port);
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
                chatConsole.display("Port is set to " + port);
            } catch (Exception e) {
                chatConsole.display("Cannot get port number!");
                chatConsole.display(e.getMessage());
            }
        } else if (message.equals("#start")) {
            try {
                this.listen(); //Start listening for connections
                chatConsole.display("Echo Server is started and ready at port " + this.getPort());
            } catch (Exception ex) {
                chatConsole.display("ERROR - Could not listen for clients!");
                chatConsole.display(ex.getMessage());
            }
        } else if (message.equals("#stop")) {
            Envelope env = new Envelope();
            env.setId("forceLogout");
            env.setContents("Server is shutting down, bye!");

            sendToAllClients(env);

            try {
                this.close();
                chatConsole.display("Echo Server is stopped");
            } catch (IOException ex) {
                chatConsole.display("Cannot close Echo Server");
                chatConsole.display(ex.getMessage());
            }
        } else if (message.equals("#quit")) {
            try {
                this.close();
                System.exit(0);
            } catch (IOException ex) {
                chatConsole.display(ex.getMessage());
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
            String userId = target.getInfo(USER_ID).toString();
            String room = target.getInfo(ROOM_ID).toString();
            if (room.equals(room1)) {
                target.setInfo(ROOM_ID, room2);

                if (!checkDuplicateUserInRoom(target)) {
                    chatConsole.display("Moving " + userId + " to room " + room2);
                } else {
                    target.setInfo(ROOM_ID, room1);
                }
            }
        }
        notifyUserListChanged();
    }

    public void listUsersInRooms() {
        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String userId = target.getInfo(USER_ID).toString();
            String room = target.getInfo(ROOM_ID).toString();
            chatConsole.display(userId + " - " + room);
        }
    }

    public void checkUserIsOn(String user) {
        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String userId = target.getInfo(USER_ID).toString();
            String room = target.getInfo(ROOM_ID).toString();
            if (user.equals(userId)) {
                chatConsole.display(user + " is on in room " + room);
                return;
            }
        }

        chatConsole.display(user + " is not logged in");
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
            chatConsole.display("Message received: " + msg + " from " + client);

            String userId = client.getInfo(USER_ID).toString();

            this.sendToAllClientsInRoom(userId + ": " + msg, client);
        }
    }

    public void handleCommandFromClient(Envelope env, ConnectionToClient client) {
        if (env.getId().equals("login")) {
            String userId = env.getContents().toString();
            String room = client.getInfo(ROOM_ID).toString();

            if (room.length() == 0) {
                room = DEFAULT_ROOM_NAME;
            }

            if (userId.length() == 0) {
                userId = DEFAULT_USER_NAME;
            }

            String prevUserId = client.getInfo(USER_ID).toString();

            client.setInfo(USER_ID, userId);
            client.setInfo(ROOM_ID, room);

            if (!checkDuplicateUserInRoom(client)) {
                notifyUserListChanged();
            } else {
                client.setInfo(USER_ID, prevUserId);
            }
        }

        if (env.getId().equals("join")) {
            String roomName = env.getContents().toString();
            String prevRoomName = client.getInfo(ROOM_ID).toString();

            client.setInfo(ROOM_ID, roomName);

            if (!checkDuplicateUserInRoom(client)) {
                notifyUserListChanged();
            } else {
                client.setInfo(ROOM_ID, prevRoomName);
            }
        }

        if (env.getId().equals("pm")) {
            String target = env.getArg();
            String message = env.getContents().toString();
            sendToAClient(message, target, client);
        }

        if (env.getId().equals("yell")) {
            String message = env.getContents().toString();
            String userId = client.getInfo(USER_ID).toString();
            sendToAllClients(userId + " yells: " + message);
        }

        if (env.getId().equals("who")) {
            sendRoomListToClient(client);
        }

        if (env.getId().indexOf("ttt") == 0) {
            handleTicTacToeCommand(client, env);
        }
    }

    public void handleTicTacToeCommand(ConnectionToClient client, Envelope env) {
        Envelope result = null;
        ConnectionToClient target = null;
        Thread[] clientThreadList = getClientConnections();
        TicTacToe ticTacToeContent = (TicTacToe) client.getInfo("ttt");

        // If TicTacToe in content, meaning TicTacToe processed by client
        if (env.getContents() instanceof TicTacToe) {
            ticTacToeContent = (TicTacToe) env.getContents();
        }

        for (Thread ct : clientThreadList) {
            ConnectionToClient c = (ConnectionToClient) ct;
            String targetName = c.getInfo(USER_ID).toString();

            if (c.equals(client)) {
                continue;
            }

            // For inviting, find from arg to get target user
            if (env.getArg() != null && !env.getArg().isEmpty() && targetName.equals(env.getArg())) {
                target = c;
                break;
            } else if (ticTacToeContent != null) {// Else just find from tic tac toe content
                if (targetName.equals(ticTacToeContent.getPlayer1())) {
                    target = c;
                    break;
                } else if (targetName.equals(ticTacToeContent.getPlayer2())) {
                    target = c;
                    break;
                }
            }
        }

        try {
            if (target == null) {
                client.sendToClient("<ADMIN>Target user is not exist");
                return;
            }

            // This time we need to check game status of receiver
            TicTacToe ongoingContent = (TicTacToe) target.getInfo("ttt");

            // Do not invite other user which is playing (the receiver)!
            if (ongoingContent != null && ongoingContent.getGameState() == 3) {
                if (!client.getInfo(USER_ID).toString().equals(ongoingContent.getPlayer1())
                        && !client.getInfo(USER_ID).toString().equals(ongoingContent.getPlayer2())) {
                    client.sendToClient("<ADMIN>Player " + env.getArg() + " is playing!");
                    return;
                }
            }

            // This time we need to check game status of sender
            ongoingContent = (TicTacToe) client.getInfo("ttt");

            // Don't invite again if you are playing!
            // For server processing, check if envelop has id of tttInvite 
            boolean isInviting = env.getId().equals("tttInvite")
                    // OR, For client processing, check if ticTacToe data sending state is 1
                    || ticTacToeContent.getGameState() == 1;
            if (ongoingContent != null && ongoingContent.getGameState() == 3 && isInviting) {
                client.sendToClient("<ADMIN>You are playing!");

                // And tell client to keep track of ongoing game
                env.setContents(ongoingContent);
                client.sendToClient(env);
                return;
            }

            if (env.getId().equals("tttInvite")) {
                String targetUser = env.getArg();
                result = onTicTacToeInvite(targetUser, client);
            }

            if (env.getId().equals("tttAccept")) {
                result = onTicTacToeAccept(ticTacToeContent, client);
            }

            if (env.getId().equals("tttDecline")) {
                result = onTicTacToeDecline(ticTacToeContent, client);
            }

            if (env.getId().equals("tttMove")) {
                int move = Integer.parseInt(env.getContents().toString());
                result = onTicTacToeMove(move, ticTacToeContent, client);
            }

            // If result is null, something is wrong!
            // Maybe process has exception or command is wrong
            // But with exception with generic #ttt command
            // Meaning result processed in client
            if (result == null && !env.getId().equals("ttt")) {
                return;
            }

            // Get the new tic tac toe content from result
            // If process from server
            if (result != null) {
                ticTacToeContent = (TicTacToe) result.getContents();
            } else {
                result = env; // Set result to data from client
            }

            target.sendToClient(result);
            // Except invite, message must be broadcast to both users
            if (ticTacToeContent.getGameState() != 1) {
                client.sendToClient(result);
            }

            // Save to info of both players
            target.setInfo("ttt", ticTacToeContent);
            client.setInfo("ttt", ticTacToeContent);
        } catch (IOException ex) {
            chatConsole.display("Cannot send tictactoe command to client");
            chatConsole.display(ex.getMessage());
        }
    }

    public Envelope onTicTacToeInvite(String targetUser, ConnectionToClient client) throws IOException {
        Envelope env = new Envelope();
        String senderUser = client.getInfo(USER_ID).toString();

        if (targetUser.equals(DEFAULT_USER_NAME)) {
            client.sendToClient("<ADMIN>Cannot invite guest to play!");
            return null;
        }

        if (senderUser.equals(DEFAULT_USER_NAME)) {
            client.sendToClient("<ADMIN>You must be login to play!");
            return null;
        }

        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.setGameState(1);

        ticTacToe.setPlayer1(senderUser);
        env.setId("ttt");
        env.setContents(ticTacToe);

        return env;
    }

    public Envelope onTicTacToeAccept(TicTacToe t, ConnectionToClient client) throws IOException {
        TicTacToe ticTacToe = new TicTacToe(t);

        if (ticTacToe.getGameState() != 1) {
            return null;
        }

        Envelope env = new Envelope();
        String targetUser = ticTacToe.getPlayer1();
        String senderUser = client.getInfo(USER_ID).toString();

        if (targetUser == null || targetUser.length() == 0 || targetUser.equals(senderUser)) {
            client.sendToClient("<ADMIN>Can not accept game yourself!");
            return null;
        }

        ticTacToe.setPlayer2(senderUser);
        ticTacToe.setGameState(3);
        ticTacToe.setActivePlayer(1);
        ticTacToe.setBoard(new char[3][3]);

        env.setId("ttt");
        env.setContents(ticTacToe);

        return env;
    }

    public Envelope onTicTacToeDecline(TicTacToe t, ConnectionToClient client) throws IOException {
        TicTacToe ticTacToe = new TicTacToe(t);

        if (ticTacToe.getGameState() == 2 || ticTacToe.getGameState() == 4) {
            return null;
        }

        Envelope env = new Envelope();
        ticTacToe.setGameState(2);
        env.setId("ttt");
        env.setContents(ticTacToe);

        return env;
    }

    public Envelope onTicTacToeMove(int move, TicTacToe t, ConnectionToClient client) throws IOException {
        TicTacToe ticTacToe = new TicTacToe(t);

        if (ticTacToe.getGameState() != 3 || !isActivePlayer(ticTacToe, client)) {
            return null;
        }

        Envelope env = new Envelope();

        // Prevent move out of bounds
        if (move >= 9 || move < 0) {
            client.sendToClient("<ADMIN>Try another move! (1-9)");
            return null;
        }

        // Prevent override value
        char board[][] = ticTacToe.getBoard();
        if ((int) board[move / 3][move % 3] != 0) {
            client.sendToClient("<ADMIN>You can not take that position! Try another.");
            return null;
        }

        ticTacToe.updateBoard(move);

        if (ticTacToe.getActivePlayer() == 1) {
            ticTacToe.setActivePlayer(2);
            env.setArg(ticTacToe.getPlayer2());
        } else {
            ticTacToe.setActivePlayer(1);
            env.setArg(ticTacToe.getPlayer1());
        }

        env.setId("ttt");
        env.setContents(ticTacToe);

        return env;
    }

    public boolean isActivePlayer(TicTacToe ticTacToe, ConnectionToClient client) {
        String senderUser = client.getInfo(USER_ID).toString();
        switch (ticTacToe.getActivePlayer()) {
            case 1:
                return ticTacToe.getPlayer1().equals(senderUser);
            case 2:
                return ticTacToe.getPlayer2().equals(senderUser);
            default:
                return false;
        }
    }

    public void sendRoomListToClient(ConnectionToClient client) {
        Envelope env = new Envelope();
        ArrayList<String> userList = new ArrayList<String>();
        String room = client.getInfo(ROOM_ID).toString();

        env.setId("who");
        env.setArg(room);

        Thread[] clientThreadList = getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            String targetRoom = target.getInfo(ROOM_ID).toString();

            if (targetRoom.equals(room) && !target.equals(client)) {
                String encodedString = target.getInfo(USER_ID).toString().replaceAll("&", "&amp;");
                userList.add(encodedString);
            }
        }

        env.setContents(userList);

        try {
            client.sendToClient(env);
        } catch (Exception e) {
            chatConsole.display("Failed to send userList to client");
            chatConsole.display(e.getMessage());
        }
    }

    public void sendToAllClientsInRoom(Object msg, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();
        String room = client.getInfo(ROOM_ID).toString();

        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            if (target.getInfo(ROOM_ID).equals(room)) {
                try {
                    target.sendToClient(msg);
                } catch (Exception ex) {
                    chatConsole.display("failed to send to client");
                    chatConsole.display(ex.getMessage());
                }
            }
        }
    }

    public void sendToAClient(Object msg, String pmTarget, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient target = (ConnectionToClient) clientThreadList[i];
            if (target.getInfo(USER_ID).equals(pmTarget)) {
                try {
                    target.sendToClient(msg);
                } catch (Exception ex) {
                    chatConsole.display("failed to send to private message");
                    chatConsole.display(ex.getMessage());
                }
            }
        }
    }

    public boolean checkDuplicateUserInRoom(ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();
        for (Thread t : clientThreadList) {
            ConnectionToClient otherClient = (ConnectionToClient) t;

            if (client.equals(otherClient)) {
                continue;
            }

            if (client.getInfo(USER_ID).toString().equals(DEFAULT_USER_NAME)) {
                continue;
            }

            if (client.getInfo(ROOM_ID).toString().equals(otherClient.getInfo(ROOM_ID).toString())
                    && client.getInfo(USER_ID).toString().equals(otherClient.getInfo(USER_ID).toString())) {
                try {
                    Envelope env = new Envelope();
                    env.setId("forceLogout");
                    env.setContents("Someone in room have same name");
                    client.sendToClient(env);
                } catch (IOException ex) {
                    chatConsole.display("Failed to send forceLogout");
                    chatConsole.display(ex.getMessage());
                }
                return true;
            }
        }
        return false;
    }

    public void notifyUserListChanged() {
        Envelope env = new Envelope();
        env.setId("userListChanged");
        sendToAllClients(env);
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * starts listening for connections.
     */
    protected void serverStarted() {
        chatConsole.display("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * stops listening for connections.
     */
    protected void serverStopped() {
        chatConsole.display("Server has stopped listening for connections.");
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
//            chatConsole.display("ERROR - Could not listen for clients!");
//        }
//
//    }
    protected void clientConnected(ConnectionToClient client) {
        chatConsole.display("<Client Connected:" + client + ">");
        client.setInfo(ROOM_ID, DEFAULT_ROOM_NAME);
        client.setInfo(USER_ID, DEFAULT_USER_NAME);
        notifyUserListChanged();
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        chatConsole.display("Client shutdown");
        notifyUserListChanged();
    }
}
//End of EchoServer class
