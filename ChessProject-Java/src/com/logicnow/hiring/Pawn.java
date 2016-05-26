package com.logicnow.hiring;

import java.lang.Math;

public class Pawn extends Piece {

    public Pawn(PieceColor pieceColor) {
    	super(pieceColor, PieceType.PAWN);
    }
    
    public boolean IsLegalMove(int oldX, int oldY, int newX, int newY) {
    	if ( (this.pieceColor == PieceColor.WHITE && newX - oldX == 1 && Math.abs(oldY - newY) <= 1) ||
    		 (this.pieceColor == PieceColor.BLACK && oldX - newX == 1 && Math.abs(oldY - newY) <= 1)){
    		return true;
    	}
    	else{
   			return false;
   		}
    }
}
