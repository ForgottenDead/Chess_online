package Client.Chess.Game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y) {
        setWidth(Chess.TILE_SIZE);
        setHeight(Chess.TILE_SIZE);

        relocate(x * Chess.TILE_SIZE, y * Chess.TILE_SIZE);

        setFill(light ? Color.valueOf("#ffeebb") : Color.valueOf("#665f4a"));
    }
}