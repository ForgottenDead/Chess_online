package Client.Chess.Game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Chess extends Application {

    Scene sceneSignIn;
    Scene sceneOther;

    public static boolean turn = true;                 //whos turn it is true =white
    public static int   movement = 0;
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene sceneGame = new Scene(createContent());
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        Label label = new Label("Type your username below.");
        Button button = new Button("Sign_in");
        button.setOnAction(e -> primaryStage.setScene(sceneGame));
        VBox layoutSignIn = new VBox(20);
        layoutSignIn.getChildren().addAll(label, button, yesButton, noButton);
        sceneSignIn = new Scene(layoutSignIn, 200, 200);



        Button buttonOther = new Button("Back");
        buttonOther.setOnAction(e -> primaryStage.setScene(sceneSignIn));
        StackPane layoutOther = new StackPane();
        layoutOther.getChildren().add(buttonOther);
        sceneOther = new Scene(layoutOther,200,200);

        primaryStage.setTitle("Chess");
        primaryStage.setScene(sceneSignIn);
        primaryStage.show();
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (y == 0) {
                    switch (x){
                        case 0:  piece = makePiece(PieceType.RED_ROOK_LIGHT, x, y, 0);
                            break;
                        case 1:  piece = makePiece(PieceType.RED_KNIGHT, x, y, 0);
                            break;
                        case 2:  piece = makePiece(PieceType.RED_BISHOP_LIGHT, x, y, 0);
                            break;
                        case 3:  piece = makePiece(PieceType.RED_QUEEN, x, y, 0);
                            break;
                        case 4: piece = makePiece(PieceType.RED_KING, x, y, 0);
                            break;
                        case 5: piece = makePiece(PieceType.RED_BISHOP_DARK, x, y, 0);
                            break;
                        case 6: piece = makePiece(PieceType.RED_KNIGHT, x, y, 0);
                            break;
                        case 7: piece = makePiece(PieceType.RED_ROOK_DARK, x, y, 0);
                            break;
                        default:
                    }
                }
                if (y == 1) {
                    piece = makePiece(PieceType.RED_PAWN, x, y, 0);
                }

                if (y == 7) {
                    switch (x){
                        case 0:  piece = makePiece(PieceType.WHITE_ROOK_DARK, x, y, 0);
                            break;
                        case 1:  piece = makePiece(PieceType.WHITE_KNIGHT, x, y, 0);
                            break;
                        case 2:  piece = makePiece(PieceType.WHITE_BISHOP_DARK, x, y, 0);
                            break;
                        case 3:  piece = makePiece(PieceType.WHITE_KING, x, y, 0);
                            break;
                        case 4: piece = makePiece(PieceType.WHITE_QUEEN, x, y, 0);
                            break;
                        case 5: piece = makePiece(PieceType.WHITE_BISHOP_LIGHT, x, y, 0);
                            break;
                        case 6: piece = makePiece(PieceType.WHITE_KNIGHT, x, y, 0);
                            break;
                        case 7: piece = makePiece(PieceType.WHITE_ROOK_LIGHT, x, y, 0);
                            break;
                        default:
                    }
                }
                if (y == 6) {
                    piece = makePiece(PieceType.WHITE_PAWN, x, y, 0);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }


    private Piece makePiece(PieceType type, int x, int y, int movement) {
        Piece piece = new Piece(type, x, y, movement);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    turn=!turn;
                    Check_turn_Results();
                    piece.setMovement();
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    turn=!turn;
                    Check_turn_Results();
                    piece.setMovement();
                    break;
            }
        });
        return piece;
    }

    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }



    private MoveResult tryMove(Piece piece, int newX, int newY) {
        if(newX==piece.getOldX()/100 && newY ==piece.getOldY()/100){             //if no move
            return new MoveResult(MoveType.NONE);
        }
        if (piece.getType()==PieceType.RED_PAWN){
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            if(((newX==(int)(piece.getOldX())/100) && ((newY==(int)(piece.getOldY())/100+1)||
                                        ((newY==(int)(piece.getOldY())/100+2)&&piece.getMovement()==0)))
                &&board[newX][newY].hasPiece()==false){
                return new MoveResult(MoveType.NORMAL);
            }
            if(((newX==(int)(piece.getOldX())/100) && (newY==(int)(piece.getOldY())/100+2))
                    &&board[newX][newY].hasPiece()==false&&piece.getMovement()==0){
                Check_promotion_Results(piece);
                return new MoveResult(MoveType.NORMAL);
            }
            if(board[newX][newY].hasPiece() &&
                    (((newX==(int)(piece.getOldX())/100+1) && (newY==(int)(piece.getOldY())/100+1)) ||
                    ((newX==(int)(piece.getOldX())/100-1) &&  (newY==(int)(piece.getOldY())/100+1)))){
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_PAWN ){
            if(turn==false){
                return new MoveResult(MoveType.NONE);
            }
            if(((newX==(int)(piece.getOldX())/100) && ((newY==(int)(piece.getOldY())/100-1)||
                                        ((newY==(int)(piece.getOldY())/100-2)&&piece.getMovement()==0)))
                &&board[newX][newY].hasPiece()==false){
                Check_promotion_Results(piece);
                return new MoveResult(MoveType.NORMAL);
            }
            if(board[newX][newY].hasPiece()&&
                    (((newX==(int)(piece.getOldX())/100-1) && (newY==(int)(piece.getOldY())/100-1)) ||
                    ((newX==(int)(piece.getOldX())/100+1) && (newY==(int)(piece.getOldY())/100-1)))){
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_ROOK_LIGHT || piece.getType()==PieceType.RED_ROOK_DARK) {
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            if ((((newX == (int) (piece.getOldX()) / 100 )) ||
                    ((newY == (int) (piece.getOldY()) / 100)))
                    && board[newX][newY].hasPiece() == false) {
                return new MoveResult(MoveType.NORMAL);
            }
            if (board[newX][newY].hasPiece() &&
                    (((newX == (int) (piece.getOldX()) / 100 )) ||
                            ((newY == (int) (piece.getOldY()) / 100)))) {
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_ROOK_LIGHT || piece.getType()==PieceType.WHITE_ROOK_DARK) {
            if(turn==false){
                return new MoveResult(MoveType.NONE);
            }
            if ((((newX == (int) (piece.getOldX()) / 100 )) ||
                    ((newY == (int) (piece.getOldY()) / 100)))
                    && board[newX][newY].hasPiece() == false) {
                return new MoveResult(MoveType.NORMAL);
            }
            if (board[newX][newY].hasPiece() &&
                    (((newX == (int) (piece.getOldX()) / 100 )) ||
                            ((newY == (int) (piece.getOldY()) / 100)))) {
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }

        if (piece.getType()==PieceType.RED_KING ){
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            if(((newY<=(int)(piece.getOldY())/100+1)&&(newY>=(int)(piece.getOldY())/100-1)) &&
                    ((newX<=(int)(piece.getOldX())/100+1)&&(newX>=(int)(piece.getOldX())/100-1))
                    &&board[newX][newY].hasPiece()==false){
                return new MoveResult(MoveType.NORMAL);
            }
            if(((newY<=(int)(piece.getOldY())/100+1)&&(newY>=(int)(piece.getOldY())/100-1)) &&
                    ((newX<=(int)(piece.getOldX())/100+1)&&(newX>=(int)(piece.getOldX())/100-1))
                    &&board[newX][newY].hasPiece()==true){
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_KING){
            if(turn==false){
                return new MoveResult(MoveType.NONE);
            }
            if(((newY<=(int)(piece.getOldY())/100+1)&&(newY>=(int)(piece.getOldY())/100-1)) &&
                    ((newX<=(int)(piece.getOldX())/100+1)&&(newX>=(int)(piece.getOldX())/100-1))
                    &&board[newX][newY].hasPiece()==false){
                return new MoveResult(MoveType.NORMAL);
            }
            if(((newY<=(int)(piece.getOldY())/100+1)&&(newY>=(int)(piece.getOldY())/100-1)) &&
                    ((newX<=(int)(piece.getOldX())/100+1)&&(newX>=(int)(piece.getOldX())/100-1))
                    &&board[newX][newY].hasPiece()==true){
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_BISHOP_LIGHT||piece.getType()==PieceType.RED_BISHOP_DARK){
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            if ((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)
                    && board[newX][newY].hasPiece() == false) {
                return new MoveResult(MoveType.NORMAL);
            }
            if ((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)
                    && board[newX][newY].hasPiece() == true) {
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }

        if (piece.getType()==PieceType.WHITE_BISHOP_LIGHT||piece.getType()==PieceType.WHITE_BISHOP_DARK){
            if(turn==false){
                return new MoveResult(MoveType.NONE);
            }
            if ((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)
                    && board[newX][newY].hasPiece() == false) {
                return new MoveResult(MoveType.NORMAL);
            }
            if ((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)
                    && board[newX][newY].hasPiece() == true) {
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }


        if (piece.getType()==PieceType.RED_QUEEN){
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            if (((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)||
                    ((((newX == (int) (piece.getOldX()) / 100 )) || ((newY == (int) (piece.getOldY()) / 100)))))
                    && board[newX][newY].hasPiece() == false) {
                return new MoveResult(MoveType.NORMAL);
            }
            if (((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)||
                    (board[newX][newY].hasPiece() &&
                            (((newX == (int) (piece.getOldX()) / 100 )) ||
                                    ((newY == (int) (piece.getOldY()) / 100)))))
                    && board[newX][newY].hasPiece() == true) {
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }

        if (piece.getType()==PieceType.WHITE_QUEEN){
            if(turn==false){
                return new MoveResult(MoveType.NONE);
            }
            if (((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)||
                    ((((newX == (int) (piece.getOldX()) / 100 )) || ((newY == (int) (piece.getOldY()) / 100)))))
                    && board[newX][newY].hasPiece() == false) {
                return new MoveResult(MoveType.NORMAL);
            }
            if (((Math.abs(newX-piece.getOldX()/100) - Math.abs(newY-piece.getOldY()/100)==0)||
                    (board[newX][newY].hasPiece() &&
                            (((newX == (int) (piece.getOldX()) / 100 )) ||
                                    ((newY == (int) (piece.getOldY()) / 100)))))
                    && board[newX][newY].hasPiece() == true) {
                return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
            } else {
                return new MoveResult(MoveType.NONE);
            }
        }

        if (piece.getType()==PieceType.RED_KNIGHT) {
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            return getMoveResult_Knight(piece, newX, newY);
        }
        if (piece.getType()==PieceType.WHITE_KNIGHT) {
            if(turn==false){
                return new MoveResult(MoveType.NONE);
            }
            return getMoveResult_Knight(piece, newX, newY);
        }

        return new MoveResult(MoveType.NONE);
    }

    private MoveResult getMoveResult_Knight(Piece piece, int newX, int newY) {
        if((Math.abs(newX-piece.getOldX()/100) == 2)&&(Math.abs(newY-piece.getOldY()/100) == 1)){
            if(!board[newX][newY].hasPiece()){
                return new MoveResult(MoveType.NORMAL);
            }
            return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
        }
        else if(Math.abs(newY-piece.getOldY()/100)==2 && (Math.abs(newX-piece.getOldX()/100) == 1)){
            if(!board[newX][newY].hasPiece()){
                return new MoveResult(MoveType.NORMAL);
            }
            return new MoveResult(MoveType.KILL, board[newX][newY].getPiece());
        }
        else{
            return new MoveResult(MoveType.NONE);
        }
    }
    private boolean checkPathBlockageStraight(Piece piece, int newX, int newY){
        int curX = (int)piece.getOldX()/100;
        int curY = (int)piece.getOldY()/100;
        if(piece.getOldX()/100==newX){
            if(piece.getOldY()/100>newY){
                for(int i =1; i < piece.getOldY()/100 - newY; i++){
                    curY++;
                    if(board[curX][curY].hasPiece()){
                        return false;
                    }
                }
            }
            if(piece.getOldY()/100<newY){
                for(int i =1; i > piece.getOldY()/100 - newY; i--){
                    curY++;
                    if(board[curX][curY].hasPiece()){
                        return false;
                    }
                }
            }
        }
        if(piece.getOldY()/100==newY){
            if(piece.getOldX()/100>newX){
                for(int i =1; i < piece.getOldX()/100 - newX; i++){
                    curX++;
                    if(board[curX][curY].hasPiece()){
                        return false;
                    }
                }
            }
            if(piece.getOldX()/100<newX){
                for(int i =1; i < piece.getOldX()/100 - newX; i++){
                    curX++;
                    if(board[curX][curY].hasPiece()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean checkPathBlockageDiagonal(Piece piece, int newX, int newY){
        int curX = (int)piece.getOldX()/100;
        int curY = (int)piece.getOldY()/100;
        if(piece.getOldX()/100>newX && piece.getOldY()/100>newY){
            for(int i =1; i < piece.getOldX()/100 - newX; i++){
                curX++;
                curY++;
                if(board[curX][curY].hasPiece()){
                    return false;
                }
            }
        }
        if(piece.getOldX()/100>newX && piece.getOldY()/100<newY){
            for(int i =1; i < piece.getOldX()/100 - newX; i++){
                curX++;
                curY--;
                if(board[curX][curY].hasPiece()){
                    return false;
                }
            }
        }
        if(piece.getOldX()/100<newX && piece.getOldY()/100>newY){
            for(int i =1; i > piece.getOldX()/100 - newX; i--){
                curX--;
                curY++;
                if(board[curX][curY].hasPiece()){
                    return false;
                }
            }
        }
        if(piece.getOldX()/100<newX && piece.getOldY()/100<newY){
            for(int i =1; i > piece.getOldX()/100 - newX; i--){
                curX--;
                curY--;
                if(board[curX][curY].hasPiece()){
                    return false;
                }
            }
        }
        return true;
    }

    private void Check_promotion_Results(Piece piece){
        if(turn){
            if(piece.getOldY()/100==1){
                Promotion(piece);
            }
        }
        else{
            if(piece.getOldY()/100==7){
                Promotion(piece);
            }
        }
    }

    private static void Promotion(Piece piece){
        Stage promotion = new Stage();
        promotion.initModality(Modality.APPLICATION_MODAL);
        promotion.setTitle("WINNER!");
        promotion.setMinHeight(250);
        promotion.setMinWidth(250);
        Label label = new Label("Pick Promotion");
        Button knightButton = new Button("knight");
        Button bishopButton = new Button("Bishop");
        Button rookButton = new Button("Rook");
        Button queenButton = new Button("Queen");
        if(turn){
            knightButton.setOnAction(e -> piece.setPieceType(PieceType.WHITE_KNIGHT));
            if(piece.getOldX()/100%2==0){
                bishopButton.setOnAction(e -> piece.setPieceType(PieceType.WHITE_BISHOP_DARK));
            }
            else{
                bishopButton.setOnAction(e -> piece.setPieceType(PieceType.WHITE_BISHOP_LIGHT));
            }
            rookButton.setOnAction(e -> piece.setPieceType(PieceType.WHITE_ROOK_DARK));
            queenButton.setOnAction(e -> piece.setPieceType(PieceType.WHITE_QUEEN ));
        }
        else{
            knightButton.setOnAction(e -> piece.setPieceType(PieceType.RED_KNIGHT));
            if(piece.getOldX()/100%2==0){
                bishopButton.setOnAction(e -> piece.setPieceType(PieceType.RED_BISHOP_LIGHT));
            }
            else{
                bishopButton.setOnAction(e -> piece.setPieceType(PieceType.RED_BISHOP_DARK));
            }
            rookButton.setOnAction(e -> piece.setPieceType(PieceType.RED_ROOK_DARK));
            queenButton.setOnAction(e -> piece.setPieceType(PieceType.RED_QUEEN ));
        }
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, knightButton, bishopButton, rookButton, queenButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        promotion.setScene(scene);
        promotion.showAndWait();
    }

    private void Check_turn_Results(){
        String color="grey";
        boolean winner = true;
        for (int x = 0; x < board.length;x++){
            for (int y = 0; y < board[x].length;y++){
                if(turn){
                    color="Red";
                    if(board[x][y].getPiece()==(null)){
                        continue;
                    }
                    if(board[x][y].getPiece().getType().equals(PieceType.WHITE_KING)){
                        winner=false;
                        break;
                    }

                }
                else{
                    color="White";
                    if(board[x][y].getPiece()==(null)){
                        continue;
                    }
                    if(board[x][y].getPiece().getType().equals(PieceType.RED_KING)){
                        winner=false;
                        break;
                    }
                }
            }
            if(!winner){
                break;
            }
        }
        if(winner){
            Winner_Alert( color);
            System.exit(0);
        }
    }

    private static void Winner_Alert(String color){
        Stage winner_Alert = new Stage();
        winner_Alert.initModality(Modality.APPLICATION_MODAL);
        winner_Alert.setTitle("WINNER!");
        winner_Alert.setMinHeight(250);
        winner_Alert.setMinWidth(250);
        Label label = new Label(color+" Wins");
        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> winner_Alert.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        winner_Alert.setScene(scene);
        winner_Alert.showAndWait();
    }
}