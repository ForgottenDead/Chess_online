package Client.Chess;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Chess extends Application {
    public static boolean turn = true;                 //whos turn it is true =white
    public static int   movement = 0;
    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

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

    private MoveResult tryMove(Piece piece, int newX, int newY) {
        if(newX==piece.getOldX()/100 && newY ==piece.getOldY()/100){             //if no move
            return new MoveResult(MoveType.NONE);
        }
        if (piece.getType()==PieceType.RED_PAWN){
            if(turn==true){
                return new MoveResult(MoveType.NONE);
            }
            if(((newX==(int)(piece.getOldX())/100) && (newY==(int)(piece.getOldY())/100+1))
                &&board[newX][newY].hasPiece()==false){
                return new MoveResult(MoveType.NORMAL);
            }
            if(((newX==(int)(piece.getOldX())/100) && (newY==(int)(piece.getOldY())/100+2))
                    &&board[newX][newY].hasPiece()==false&&piece.getMovement()==0){
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
            if(((newX==(int)(piece.getOldX())/100) && (newY==(int)(piece.getOldY())/100-1))
                &&board[newX][newY].hasPiece()==false){
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
        Piece piece = new Piece(type, x, y, 0);

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
                    piece.setMovement();
                    break;
            }
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}