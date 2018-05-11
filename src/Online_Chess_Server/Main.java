package Online_Chess_Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

public class Main {

    //volatile private ArrayList<ClientHandler> clients = new ArrayList<>();
    //volatile private ArrayList<String> client_names = new ArrayList<>();

    volatile private Hashtable<String, ClientHandler> clients= new Hashtable<>();

    public static void main(String[] args) throws IOException {
        // write your code here
        new Main().startTheServer();

    }

    public void startTheServer() throws IOException {
        ServerSocket server;
        int port_number = 4000;

        server = new ServerSocket(port_number);

        while (true) {
            Socket client = null;
            try {
                client = server.accept();

                System.out.println("A new client has connected: " + client);

                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());

                System.out.println("Assigning new thread to this client");
                ClientHandler t = new ClientHandler(client, input, output);
                t.start();
            } catch (Exception e) {
                client.close();
                e.printStackTrace();
            }
        }
    }

    class MatchHandler extends Thread {
        private final ClientHandler player_one;
        private final ClientHandler player_two;
        private boolean turn = true; //true = white, false = black
        private final boolean sides; //true = player one is white and player two is black, false = player one is black and player two is white

        public MatchHandler(ClientHandler sender, ClientHandler receiver) throws IOException {
                Random coin_flip = new Random();
                player_one = sender;
                player_two = receiver;

                if (coin_flip.nextInt(100) > 50) {
                    sides = true; //player one is white, player two is black
                    player_one.messageToClient("side white");
                    player_two.messageToClient("side black");
                } else {
                    sides = false; //player one is black, player two is white
                    player_one.messageToClient("side black");
                    player_two.messageToClient("side white");
                }
        }

        //might reverse this so the coordinates can be multiplied by each other to get the location in the array
//        private String[][] board = new String[][]{
//                {"A8", "BR1"}, {"B8", "BK1"}, {"C8", "BB1"}, {"D8", "BKing"}, {"E8", "BQueen"}, {"F8", "BB2"}, {"G8", "BK2"}, {"H8", "BR2"},
//                {"A7", "BP1"}, {"B7", "BP2"}, {"C7", "BP3"}, {"D7", "BP4"}, {"E7", "BP5"}, {"F7", "BP6"}, {"G7", "BP7"}, {"H7", "BP8"},
//                {"A6", ".."}, {"B6", "__"}, {"C6", ".."}, {"D6", "__"}, {"E6", ".."}, {"F6", "__"}, {"G6", ".."}, {"H6", "__"},
//                {"A5", "__"}, {"B5", ".."}, {"C5", "__"}, {"D5", ".."}, {"E5", "__"}, {"F5", ".."}, {"G5", "__"}, {"H5", ".."},
//                {"A4", ".."}, {"B4", "__"}, {"C4", ".."}, {"D4", "__"}, {"E4", ".."}, {"F4", "__"}, {"G4", ".."}, {"H4", "__"},
//                {"A3", "__"}, {"B3", ".."}, {"C3", "__"}, {"D3", ".."}, {"E3", "__"}, {"F3", ".."}, {"G3", "__"}, {"H3", ".."},
//                {"A2", "WP1"}, {"B2", "WP2"}, {"C2", "WP3"}, {"D2", "WP4"}, {"E2", "WP5"}, {"F2", "WP6"}, {"G2", "WP7"}, {"H2", "WP8"},
//                {"A1", "WR1"}, {"B1", "WK1"}, {"C1", "WB1"}, {"D1", "WKing"}, {"E1", "WQueen"}, {"F1", "WB2"}, {"G1", "WK2"}, {"H1", "WR2"},
//        };



        @Override
        public void run() {

        }

        public boolean getTurn(){
            return turn;
        }

        public boolean getSides(){
            return sides;
        }

        //parameter is the player sending the message, it's a bit counterintuitive
        public void sendToOtherPlayer(ClientHandler player, String message) throws IOException{
            if(player == player_one){
                player_two.messageToClient(message);
            }
            else{
                player_one.messageToClient(message);
            }
        }

        //this probably isnt even needed and is on the chopping block
//        public void nextTurn() throws IOException{
//            //if player one is white, and player two is black
//            if(sides){
//                //if it is currently white's turn, switch to black's turn
//                if(turn){
//                    player_two.messageToClient("turn");
//                    turn = !turn;
//                }
//                //if it is black's turn, switch to white
//                else{
//                    player_one.messageToClient("turn");
//                    turn = !turn;
//                }
//            }
//            //if player one is black and player two is white
//            else{
//                //if it is currently white's turn, switch to black's turn
//                if(turn){
//                    player_one.messageToClient("turn");
//                    turn = !turn;
//                }
//                //if it is black's turn, switch to white
//                else{
//                    player_two.messageToClient("turn");
//                    turn = !turn;
//                }
//            }
//
//        }

        public void movePiece(ClientHandler player, String x) throws IOException {
            if(player == player_one){
                player_one.messageToClient("Piece moved");
            }
            else{
                player_two.messageToClient("");
            }
        }
    }

    //inner class
    //Here the ClientHandler thread allows for multiple clients to join the server at once
    //Each client is given a ClientHandler thread
    //The ClientHandler thread then has a ClientHandlerInput thread that will watch for input from the client
    //Depending on the input the Client provides, the ClientHandlerInput thread may call a method from ClientHandler
    //ClientHandler can worry about sending data to the Client, since that doesn't really block up the flow of data like
    //listening for input does
    class ClientHandler extends Thread {
        private final DataInputStream input_stream;
        private final DataOutputStream output_stream;
        private final Socket client_socket;
        private String user_name;
        private boolean in_game = false; // am I in a game?
        private MatchHandler match;
        private boolean my_turn = false; // is it my turn to go?

        public ClientHandler(Socket client, DataInputStream input, DataOutputStream output) throws IOException {
            input_stream = input;
            output_stream = output;
            client_socket = client;
        }

        public String getUser_name() {
            return user_name;
        }

        //create a match for the two opponents, the string parameter is the key of the other player
        public void createMatch(String oppenent) throws IOException{
            try {
                match = new MatchHandler(this, clients.get(oppenent));
                in_game = true;
                clients.get(oppenent).setMatch(match);
                clients.get(oppenent).in_game = true;
                clients.get(oppenent).messageToClient("start");
            }
            catch(NullPointerException e){
                output_stream.writeUTF("No user by that name found.");
            }
        }

        public void setMatch(MatchHandler newMatch){
            match = newMatch;
        }

        public void listUsers() throws IOException {
            String data = "users ";
            for(String key : clients.keySet()){
                data = data + key + " ";
            }
            System.out.println(data);
            output_stream.writeUTF(data);
        }

        public void sendPing(String userName, String[] messageArray) throws IOException {
            String message = "";
            for(String x : messageArray){
                message = message + " " + x;
            }
            try {
                clients.get(userName).messageToClient(message);
            }
            catch(NullPointerException e){
                output_stream.writeUTF("Sorry, that user does not exist");
            }
        }

        public void messageToClient(String message) throws IOException {
            output_stream.writeUTF(message);
        }

        //lists available commands
//        public void listCommands() throws IOException{
//            output_stream.writeUTF("Commands are: \n" +
//                    "--message [user] [text] \n" +
//                    "--join [user]\n" +
//                    "--leave\n" +
//                    "--move [piece name] [destination]\n" +
//                    "--leave");
//        }

        public void setMy_turn() {
            my_turn = true;
        }

        @Override
        public void run() {
            String receiving;
            String returning;
            //this is a sort of login, user provides a username and it's added to the list of users
            try {
                if(!(clients.isEmpty())){
                    listUsers();
                }
                user_name = input_stream.readUTF();
                clients.put(user_name, this);
                //listCommands();

            } catch (IOException e) {
                e.printStackTrace();
            }
            //commands are picked up by this thread
            ClientHandlerInput input = new ClientHandlerInput(client_socket, input_stream);
            input.start();
        }

        public void closeSocket(){
            //everything is closed if it was open
            try

            {
                input_stream.close();
                output_stream.close();
                client_socket.close();
            }
            catch(
                    IOException e)

            {
                e.printStackTrace();
            }
        }



        class ClientHandlerInput extends Thread{
            private final Socket socket;
            private final DataInputStream input;

            public ClientHandlerInput(Socket clientInput, DataInputStream clientInputStream){
                socket = clientInput;
                input = clientInputStream;
            }

            @Override
            public void run(){
                String receiving;
                while(true){
                    try{
                        receiving = input.readUTF();
                        String[] messageArray = receiving.split(" ");
                        System.out.println(receiving);
                        if(receiving.equals("exit")){
                            closeSocket();
                            break;
                        }
                        else if(messageArray[0].equals("--message")){
                            //sends a message to another user
                            sendPing(messageArray[1], Arrays.copyOfRange(messageArray, 2, messageArray.length));
                        }
                        else if(messageArray[0].equals("--join")){
                            if(in_game){
                                messageToClient("error Sorry, you're already in a game");
                            }
                            else if(messageArray[1].equals(getUser_name())){
                                messageToClient("error You can't play against yourself");
                            }
                            else {
                                //tell the other client that there is an invite and who it is from
                                clients.get(messageArray[1]).messageToClient("invite " + getUser_name());
                            }
                        }
                        else if(messageArray[0].equals("--yes")){
                            //set up the game with the two clients
                            createMatch(messageArray[1]);
                        }
                        else if(messageArray[0].equals("--no")){
                            //tell the inviter that the invitee does not want to play
                            clients.get(messageArray[1]).messageToClient("Declined by " + getUser_name());
                        }
                        else if(messageArray[0].equals("--leave")){
                            //leaves a game
                        }
                        else if(messageArray[0].equals("--move")){
                            //moves a piece on the chess board
                            if(getUser_name().equals(match.player_one.getUser_name())){
                                //send to player two
                                clients.get(match.player_two.user_name).messageToClient("move " +
                                        messageArray[1] + " " +
                                        messageArray[2] + " " +
                                        messageArray[3] + " " +
                                        messageArray[4] + " " +
                                        messageArray[5]);
                            }
                            else{
                                //send to player one
                                clients.get(match.player_one.user_name).messageToClient("move " +
                                        messageArray[1] + " " +
                                        messageArray[2] + " " +
                                        messageArray[3] + " " +
                                        messageArray[4] + " " +
                                        messageArray[5]);
                            }
                        }
                        else if(receiving.equals("--list")){
                            //lists the current usernames of the users connected to the server
                            listUsers();
                            continue;
                        }
                        else {
                            //listCommands();
                        }

                    }
                    catch(IOException e){
                        e.printStackTrace();
                        clients.remove(getUser_name());
                        break;
                    }
                }
            }
        }

    }//end of ClientHandler thread

//        class ClientHandlerOutput extends Thread{
//            private final Socket socket;
//            private final DataOutputStream output;
//
//            public ClientHandlerOutput(Socket clientOutput, DataOutputStream clientOutputStream){
//                socket = clientOutput;
//                output = clientOutputStream;
//            }
//
//            @Override
//            public void run(){
//                while(true){
//
//                }
//            }
//        }


}


