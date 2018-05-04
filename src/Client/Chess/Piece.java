package Client.Chess;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.image.Image ;

import static Client.Chess.Chess.TILE_SIZE;


public class Piece extends StackPane {

    private PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;
    private int movement;

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

    public Piece(PieceType type, int x, int y, int movement) {
        this.type = type;
        this.movement=movement;

        move(x, y);
        GridPane gridpane = new GridPane();
        Image image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                "Chess\\src\\Client\\Pieces\\black_pawn.PNG");
        switch(type){
            case RED_PAWN:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_pawn.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_ROOK_LIGHT:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_ROOK_DARK:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_KING:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_king.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_QUEEN:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_queen.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_BISHOP_LIGHT:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_BISHOP_DARK:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case RED_KNIGHT:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\black_knight.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_PAWN:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_pawn.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_ROOK_LIGHT:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_ROOK_DARK:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_rook.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_KING:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_king.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_QUEEN:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_queen.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_BISHOP_LIGHT:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_BISHOP_DARK:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_bishop.PNG",
                        TILE_SIZE, TILE_SIZE,
                        false, false);
                break;
            case WHITE_KNIGHT:
                image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                        "Chess\\src\\Client\\Pieces\\white_knight.PNG",
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