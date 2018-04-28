package Client.Chess;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Chess extends Application {
    public static int TURN = 1;
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    public Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

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
                        case 4: piece = makePiece(PieceType.RED_KING, x, y,0 );
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

    private MoveResult tryMove(Piece piece, int newX, int newY) {

        if (board[newX][newY].hasPiece()) {
            return new MoveResult(MoveType.NONE);
        }

        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }

        //-------------
        if (piece.getType()==PieceType.RED_PAWN){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_ROOK_LIGHT){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_ROOK_DARK){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_KING){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_QUEEN){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_BISHOP_LIGHT){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_BISHOP_DARK){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.RED_KNIGHT) {
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        //-----------
        if (piece.getType()==PieceType.WHITE_PAWN){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_ROOK_LIGHT){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_ROOK_DARK){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_KING){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_QUEEN){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_BISHOP_LIGHT){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_BISHOP_DARK){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        }
        if (piece.getType()==PieceType.WHITE_KNIGHT){
            if(legal()){

            }
            else{
                return new MoveResult(MoveType.NONE);
            }
        //-----------
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y, int movement) {
        Piece piece = new Piece(type, x, y);

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
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}