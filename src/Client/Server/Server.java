import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Server {
    public static void main(String argv[]) throws Exception {
        String receivedMessage;
        ServerSocket serverSocket = new ServerSocket(6789);
        HashMap<String, Socket> loggedInUsers = new HashMap<>();
        HashMap<String, String> joinedUsers = new HashMap<>();
        String[] message;
        String command;
        String color;
        String requestee;
        String requesting;
        String receiver;
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receivedMessage = inFromClient.readLine();
            message=receivedMessage.substring(2).split(" ");
            command=message[0];

            //DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            DataOutputStream outToClient;
            if(command.equalsIgnoreCase("new")){
                loggedInUsers.put(message[1], socket);

                Iterator it = loggedInUsers.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry pair = (Map.Entry)it.next();
                    outToClient = new DataOutputStream(((Socket)pair.getValue()).getOutputStream());
                    outToClient.writeBytes("--names " + loggedInUsers.keySet().toArray() + "\n");
                    it.remove();
                }
            }
            else if(command.equalsIgnoreCase("join")){
                requestee=message[1];
                requesting=message[2];
                outToClient = new DataOutputStream(loggedInUsers.get(requestee).getOutputStream());
                outToClient.writeBytes("--request " + requesting + "\n");
            }
            else if(command.equalsIgnoreCase("accept")){
                requestee=message[1];
                requesting=message[2];
                joinedUsers.put(requesting, requestee);
                color=message[3];
                outToClient = new DataOutputStream(loggedInUsers.get(requestee).getOutputStream());
                outToClient.writeBytes("--accept " + color + "\n");
            }
            else if(command.equalsIgnoreCase("reject")){
                requestee=message[1];
                requesting=message[2];
                outToClient = new DataOutputStream(loggedInUsers.get(requestee).getOutputStream());
                outToClient.writeBytes("--reject\n");
            }
            else if(command.equalsIgnoreCase("move")){
                receiver=joinedUsers.get(message[1]);
                outToClient = new DataOutputStream(loggedInUsers.get(receiver).getOutputStream());
                outToClient.writeBytes("--move " + message[2] + " " + message[3] + " " + message[4] + "\n");
            }
        }
    }
}