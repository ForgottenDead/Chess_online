//package Client.Chess.Game;
//
//import java.io.*;
//import java.net.*;
//
//public class Client {
//    public static void main(String argv[]) throws Exception {
//        String sending;
//        String received;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//        Socket clientSocket = new Socket("localhost", 6789);
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//        sending = inFromUser.readLine();
//        outToServer.writeBytes(sending + '\n');
//
//        received = inFromServer.readLine();
//        System.out.println("FROM SERVER: " + received);
//        clientSocket.close();
//    }
//    public static String SignIn(){
//        //accept to who
//        //"--new " + username + "\n"); // Sends the username that was typed in the GUI to the server.
//    }
//    public static String request(){
//        //on game request
//        //output.writeBytes("--join " + username + " " + usernameOfPlayerToJoin + "\n")
//    }
//    public static String accept(){
//        //reject to who
//        //output.writeBytes("--join " + username + " " + usernameOfPlayerToJoin + "\n")
//    }
//    public static String reject(){
//        //reject to who
//        //output.writeBytes("--reject " + username + " " + usernameOfRequestingPlayer + "\n");
//    }
//    public static String[] getList(){
//
//    }
////    public static String removeFromList(){
////            //on join
////            //on exit
////        //output.writeBytes("--exit " + username + "\n");
////    }
//    public static String move(){
//        //on move
//        //getMoveOutput().writeBytes("--move " + Main.getUserMakeAMove() + " " + type + " " + oldMousePressX + " " + oldMousePressY + "\n");
//    }
//}
