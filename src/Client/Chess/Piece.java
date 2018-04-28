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

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);


        GridPane gridpane = new GridPane();
        Image image = new Image("File:C:\\Users\\Forgotten\\IdeaProjects\\" +
                "Chess\\src\\Client\\Pieces\\black_bishop.PNG",
                TILE_SIZE, TILE_SIZE,
                false, false);
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