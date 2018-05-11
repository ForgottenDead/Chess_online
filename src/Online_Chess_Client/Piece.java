package Online_Chess_Client;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

//import static Client.Chess.Game.Chess.TILE_SIZE;


public class Piece extends StackPane {

    private PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;
    private int movement;
    private int TILE_SIZE = Chess.TILE_SIZE;

    private double currentX, currentY;

    public double getCurrentX(){
        return currentX;
    }

    public double getCurrentY(){
        return currentY;
    }

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public void setMovement() {
        movement++;
    }
    public int getMovement(){
        return movement;
    }

    public void setPieceType(PieceType type) {
        this.type=type;
    }


    public Piece(PieceType type, int x, int y, int movement) {
        this.type = type;
        this.movement=movement;

        move(x, y);
        GridPane gridpane = new GridPane();
        Image image = new Image("file:/Pieces/black_pawn.PNG");
        switch(type){
            case RED_PAWN:
                image = new Image("file:Pieces/black_pawn.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_ROOK_LIGHT:
                image = new Image("file:Pieces/black_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_ROOK_DARK:
                image = new Image("file:Pieces/black_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_KING:
                image = new Image("file:Pieces/black_king.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_QUEEN:
                image = new Image("file:Pieces/black_queen.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_BISHOP_LIGHT:
                image = new Image("file:Pieces/black_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_BISHOP_DARK:
                image = new Image("file:/Pieces/black_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_KNIGHT:
                image = new Image("file:Pieces/black_knight.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_PAWN:
                image = new Image("file:Pieces/white_pawn.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_ROOK_LIGHT:
                image = new Image("file:Pieces/white_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_ROOK_DARK:
                image = new Image("file:Pieces/white_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_KING:
                image = new Image("file:Pieces/white_king.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_QUEEN:
                image = new Image("file:Pieces/white_queen.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_BISHOP_LIGHT:
                image = new Image("file:Pieces/white_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_BISHOP_DARK:
                image = new Image("file:Pieces/white_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_KNIGHT:
                image = new Image("file:Pieces/white_knight.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
        }
        gridpane.getChildren().add(new ImageView(image));
        gridpane.setMaxSize(0.3125, 0.3125);
        gridpane.prefHeight(0.3125);
        gridpane.prefWidth(0.3125);

        getChildren().addAll(gridpane);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}