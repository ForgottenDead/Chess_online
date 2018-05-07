package Client.Chess.Game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Main_page {




    public void Sign_in(Stage primaryStage, Scene sceneGame) {


        primaryStage.setTitle("Sign In");
        primaryStage.setMinHeight(600);
        primaryStage.setMinHeight(800);

        Label label1 = new Label("Type your username below.");
        TextField textField = new TextField();

        Button signInButton = new Button("Sign_in");

        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList ( getList());   //get list from server
        list.setItems(items);
                                                                                  //make list clickable to select name

//        Button updateList = new Button("Update");
//        updateList.setDisable(true);
//        updateList.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                getList();                                                                   //get list
//                                                                                                  //update list
//            }
//        });

        Button yesButton = new Button("Yes");
        yesButton.setDisable(true);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                accept();                                                                               //send accept
//                get move command in scene game                                                        //add move
                primaryStage.setScene(sceneGame);                                                       //get game
            }
        });


        Button noButton = new Button("No");
        noButton.setDisable(true);
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                yesButton.setDisable(true);
                noButton.setDisable(true);
                reject();                                                                        //send reject
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                updateList.setDisable(false);
                yesButton.setDisable(false);
                noButton.setDisable(false);
                SignIn(textField.getText());                                                       //send info

//                connectToServer(SignIn(textField.getText(),InetAddress.getLocalHost()));      //connect to server         //send info
            }
        });

        HBox layout = new HBox();
//        layout.getChildren().addAll(label1, textField, signInButton, list, updateList, yesButton, noButton);
        layout.getChildren().addAll(label1, textField, signInButton, list, yesButton, noButton);
        layout.setSpacing(10);

        Scene scene = new Scene(layout);

    }


    private void connectToServer(String command) throws Exception{
        String sending;
        String received;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sending = inFromUser.readLine();
        outToServer.writeBytes(sending + '\n');

        received = inFromServer.readLine();
        System.out.println("FROM SERVER: " + received);

        clientSocket.close();
    }



    public static String SignIn(String username){
        //accept to who
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes("--new " + username + "\n");
        clientSocket.close();
        //"--new " + username + "\n"); // Sends the username that was typed in the GUI to the server.
    }
    public static String request(String currentUser, String challengedUser){
        //on game request
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes("--join " + currentUser + " " + challengedUser + "\n");
        clientSocket.close();
        //output.writeBytes("--join " + username + " " + usernameOfPlayerToJoin + "\n")
    }
    public static String accept(String currentUser,String userWhoSendChallenge,String color){
        //reject to who
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes("--accept " + currentUser + " " + userWhoSendChallenge + " " + color + "\n");
        clientSocket.close();
        //output.writeBytes("--accept " + username + " " + usernameOfRequestingPlayer + " " + UserColor.getUserColor() + "\n")
    }
    public static String reject(String currentUser, String userWhoSendChallenge){
        //reject to who
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes("--reject " + currentUser + " " + userWhoSendChallenge + "\n");
        clientSocket.close();
        //output.writeBytes("--reject " + username + " " + usernameOfRequestingPlayer + "\n");
    }
    public static String[] getList(){

    }
    public static String move(String currentUser, PieceType type, int x, int y){
        //on move
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes("--move " + currentUser + " " + type + " " + x + " " + y + "\n");
        clientSocket.close();
        //getMoveOutput().writeBytes("--move " + Main.getUserMakeAMove() + " " + type + " " + oldMousePressX + " " + oldMousePressY + "\n");
    }

    //    public static String removeFromList(){
//            //on join
//            //on exit
//        //output.writeBytes("--exit " + username + "\n");
//    }
}
