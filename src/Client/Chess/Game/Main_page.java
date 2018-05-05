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

    private void connectToServer() throws Exception{
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
    public static String SignIn(){
        //accept to who
        //"--new " + username + "\n"); // Sends the username that was typed in the GUI to the server.
    }
    public static String request(){
        //on game request
        //output.writeBytes("--join " + username + " " + usernameOfPlayerToJoin + "\n")
    }
    public static String accept(){
        //reject to who
        //output.writeBytes("--join " + username + " " + usernameOfPlayerToJoin + "\n")
    }
    public static String reject(){
        //reject to who
        //output.writeBytes("--reject " + username + " " + usernameOfRequestingPlayer + "\n");
    }
    public static String[] getList(){

    }
    //    public static String removeFromList(){
//            //on join
//            //on exit
//        //output.writeBytes("--exit " + username + "\n");
//    }
    public static String move(){
        //on move
        //getMoveOutput().writeBytes("--move " + Main.getUserMakeAMove() + " " + type + " " + oldMousePressX + " " + oldMousePressY + "\n");
    }


































    public void Sign_in(Stage primaryStage, Scene sceneGame) {
        connectToServer();                                                                          //connect to server

        primaryStage.setTitle("Sign In");
        primaryStage.setMinHeight(600);
        primaryStage.setMinHeight(800);

        Label label1 = new Label("Type your username below.");
        TextField textField = new TextField();

        Button signInButton = new Button("Sign_in");

        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList ( Client.getList());   //get list from server
        list.setItems(items);

        Button updateList = new Button("Update");
        updateList.setDisable(true);
        updateList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Client.getList();
                                                                                                    //update list
            }
        });

        Button yesButton = new Button("Yes");
        yesButton.setDisable(true);
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Client.accept(textField.getText(),InetAddress.getLocalHost());                    //send info
                primaryStage.setScene(sceneGame);                                                       //get game
            }
        });


        Button noButton = new Button("No");
        noButton.setDisable(true);
        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Client.reject();                                                                        //reject
                yesButton.setDisable(true);
                noButton.setDisable(true);
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Client.SignIn(textField.getText(),InetAddress.getLocalHost());                    //send info                                                      //send info
                updateList.setDisable(false);
                yesButton.setDisable(false);
                noButton.setDisable(false);
            }
        });

        HBox layout = new HBox();
        layout.getChildren().addAll(label1, textField, signInButton, list, updateList, yesButton, noButton);
        layout.setSpacing(10);

        Scene scene = new Scene(layout);

    }


}
