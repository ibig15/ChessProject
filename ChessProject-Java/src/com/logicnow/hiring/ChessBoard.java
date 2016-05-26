package com.logicnow.hiring;

import com.sun.org.apache.xpath.internal.operations.Bool;
import java.util.stream.IntStream;

public class ChessBoard {

    public static int MAX_BOARD_WIDTH = 8;
    public static int MAX_BOARD_HEIGHT = 8;
    
    public static int MAX_WHITE_PIECES = 16;
    public static int MAX_BLACK_PIECES = 16;
    
    public static int MAX_KINGS = 1;
    public static int MAX_QUEENS = 1;
    public static int MAX_ROOKS = 2;
    public static int MAX_KNIGHTS = 2;
    public static int MAX_BISHOPS = 2;
    public static int MAX_PAWNS = 8;
    
    public static int WHITE_PAWN_LINE = 1;
    public static int BLACK_PAWN_LINE = 6;
    
    private Piece[][] pieces;
    private int[][] removedPieces;
    
    public ChessBoard() {
        this.pieces = new Piece[MAX_BOARD_WIDTH][MAX_BOARD_HEIGHT];
        this.removedPieces = new int[2][6];	// 1st line BLACK, 2nd line WHITE
        // each line contains the count of removed pieces in this order: KING, QUEEN, ROOK, KNIGHT, BISHOP, PAWN;
    }
    
    public Piece getPiece(int x, int y){
    	return pieces[x][y];
    }
	
	public int[] getColorPieces(PieceColor color) {
		// count of: KING, QUEEN, ROOK, KNIGHT, BISHOP, PAWN;
		int[] colorPieces = new int[]{0, 0, 0, 0, 0, 0};
		for (int i = 0; i < pieces.length; i++){
			for(int j = 0; j < pieces[i].length; j++){
				if (pieces[i][j] != null && pieces[i][j].getPieceColor() == color){
					PieceType type = pieces[i][j].getPieceType();
					colorPieces[type.ordinal()]++;
				}
			}
		}	
		return colorPieces;
	}
	
	public int getColorPiecesCount(PieceColor color){
		int[] colorPieces = getColorPieces(color);
		return IntStream.of(colorPieces).sum();
	}
	
	public boolean AddPawn(Pawn pawn, int xCoordinate, int yCoordinate) {
        if (IsLegalBoardPosition(xCoordinate, yCoordinate)){
        	int[] piecesOnBoard = getColorPieces(pawn.getPieceColor());
        	PieceColor color = pawn.getPieceColor();
        	if (pieces[xCoordinate][yCoordinate] == null && piecesOnBoard[5] + removedPieces[color.ordinal()][5] < MAX_PAWNS ){
        		pieces[xCoordinate][yCoordinate] = pawn;
        		return true;
        	}
        }
        return false;
    }
	
	public boolean MovePawn(Pawn pawn, int oldX, int oldY, int newX, int newY, MovementType movementType){
		if (pawn.IsLegalMove(oldX, oldY, newX, newY)){
    		Piece p = getPiece(newX, newY);
    		boolean capture = (movementType == MovementType.CAPTURE);
    		if ( (!capture && p == null) || (capture && p != null && p.getPieceColor() != pawn.getPieceColor()) ){
    			boolean ok = true;
    			if (capture) ok = RemovePiece(newX, newY, capture);
    			ok = ok && RemovePiece(oldX, oldY, false);
    			return ok && AddPawn(pawn, newX, newY);
    		}
    	}
		return false;
	}
	
	public boolean RemovePiece(int xCoordinate, int yCoordinate, boolean capture){
		if (pieces[xCoordinate][yCoordinate] != null){
			if (capture){
				PieceColor color = pieces[xCoordinate][yCoordinate].getPieceColor();
				PieceType type = pieces[xCoordinate][yCoordinate].getPieceType();
				removedPieces[color.ordinal()][type.ordinal()]++;
			}
			pieces[xCoordinate][yCoordinate] = null;
			return true;
		}
		return false;
	}
	
    public boolean IsLegalBoardPosition(int xCoordinate, int yCoordinate) {
		if (xCoordinate >= 0 && xCoordinate < MAX_BOARD_WIDTH && yCoordinate >= 0 && yCoordinate < MAX_BOARD_HEIGHT ){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
}
