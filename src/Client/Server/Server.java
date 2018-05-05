import java.io.*;
import java.net.*;
import java.util.HashMap;

class Server {
    public static void main(String argv[]) throws Exception {
        String receivedMessage;
        ServerSocket serverSocket = new ServerSocket(6789);
        HashMap<String, Socket> loggedInUsers = new HashMap<String, Socket>();
        String[] message;
        String command;
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            receivedMessage = inFromClient.readLine();
            message=receivedMessage.substring(2).split(" ");
            command=message[0];
            if(command.equalsIgnoreCase("new")){
                loggedInUsers.put(message[1], socket);
                outToClient.writeBytes("--names " + loggedInUsers.keySet().toArray() + "\n");
            }
            else if(receivedMessage.substring(2).split(" ").toString()
                    .equalsIgnoreCase("join")){
                outToClient.writeBytes("--request " + message[1] + "\n");
            }
            else if(command.equalsIgnoreCase("accept")){
                outToClient.writeBytes("--accept " + userColor + "\n");
            }
            else if(command.equalsIgnoreCase("reject")){
                outToClient.writeBytes("--reject\n");
            }
            else if(command.equalsIgnoreCase("move")){
                outToClient.writeBytes("--move " + command[2] + " " + command[3] + " " + command[4] + "\n");
            }
        }
    }
    public static void addUser(){
        //add user
    }
    public static void request(){
        //request to join
        //requestPlay.writeBytes("--request " + command[1] + "\n");

    }
    public static void accept(){
        //accept
        //acceptPlay.writeBytes("--accept " + userColor + "\n");
    }
    public static void reject(){
        //acceptPlay.writeBytes("--reject\n");
    }
    public static void move(){
        //move piece
        //sendMove.writeBytes("--move " + command[2] + " " + command[3] + " " + command[4] + "\n");
        //command[2] = the piece being moved,
        // command[3] = the x coordinate.
        // Command[4] is the y coordinates
    }
    public static void sendList(){
        //send to anyone connected
        //sender.writeBytes("--names " + names + "\n");
        // Sends the usernames of all those connected to the client
    }
//    public static void RemoveUsers(){
//
//    }
//    public static void exit(){
//
//    }

}