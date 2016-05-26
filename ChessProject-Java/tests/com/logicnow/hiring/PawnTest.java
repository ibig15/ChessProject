package com.logicnow.hiring;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PawnTest {

    private ChessBoard chessBoard;
    private Pawn testSubject;

    @Before
    public void setUp() {
        this.chessBoard = new ChessBoard();
        this.testSubject = new Pawn(PieceColor.BLACK);
    }

    @Test
    public void testChessBoard_Add_Sets_XCoordinate() {
        boolean add = chessBoard.AddPawn(testSubject, 6, 3);
        Piece p = chessBoard.getPiece(6, 3);
        assertEquals(p, testSubject);
        assertEquals(add, true);
    }

    @Test
    public void testChessBoard_Add_Sets_YCoordinate() {
    	boolean add = chessBoard.AddPawn(testSubject, 6, 3);
        Piece p = chessBoard.getPiece(6, 3);
        assertEquals(p, testSubject);
        assertEquals(add, true);
    }


    @Test
    public void testPawn_Move_IllegalCoordinates_Right_DoesNotMove() {
    	chessBoard.AddPawn(testSubject, 6, 3);
        boolean move = chessBoard.MovePawn(testSubject, 6, 3, 7, 3, MovementType.MOVE);
        Piece p1 = chessBoard.getPiece(6, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(7, 3);
        assertEquals(p2, null);
        assertEquals(move, false);
    }

    @Test
    public void testPawn_Move_IllegalCoordinates_Left_DoesNotMove() {
    	chessBoard.AddPawn(testSubject, 6, 3);
        boolean move = chessBoard.MovePawn(testSubject, 6, 3, 4, 3, MovementType.MOVE);
        Piece p1 = chessBoard.getPiece(6, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(4, 3);
        assertEquals(p2, null);
        assertEquals(move, false);
    }
    
    /*
     * NOTE: I believe this test is wrong. 
     * If a pawn is on position (6,3) - line 6, column 3,
     * it cannot move laterally to position (6,2) - line 6, column 2
     * (pawns are allowed to move only forward or diagonally, but not laterally)
     * Instead of moving to position (6,2) I considered this would test moving to position (5,3)
     */
    @Test
    public void testPawn_Move_LegalCoordinates_Forward_UpdatesCoordinates() {
    	chessBoard.AddPawn(testSubject, 6, 3);
        boolean move = chessBoard.MovePawn(testSubject, 6, 3, 5, 3, MovementType.MOVE);
        Piece p1 = chessBoard.getPiece(5, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(6, 3);
        assertEquals(p2, null);
        assertEquals(move, true);
    }
    
    @Test
    public void testPawn_Move_IllegalCoordinates_Back_DoesNotMove(){
    	chessBoard.AddPawn(testSubject, 6, 3);
    	boolean move = chessBoard.MovePawn(testSubject, 6, 3, 7, 3, MovementType.MOVE);
        Piece p1 = chessBoard.getPiece(6, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(7, 3);
        assertEquals(p2, null);
        assertEquals(move, false);
    }
    
    @Test
    public void testPawn_Capture_IllegalCoordinates_DoesNotCapture(){
    	chessBoard.AddPawn(testSubject, 6, 3);
    	boolean move = chessBoard.MovePawn(testSubject, 6, 3, 3, 3, MovementType.CAPTURE);
        Piece p1 = chessBoard.getPiece(6, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(3, 3);
        assertEquals(p2, null);
        assertEquals(move, false);
    }
    
    @Test
    public void testPawn_Capture_LegalCoordinates_EmptyNewPosition_DoesNotCapture(){
    	chessBoard.AddPawn(testSubject, 6, 3);
    	boolean move = chessBoard.MovePawn(testSubject, 6, 3, 5, 3, MovementType.CAPTURE);
        Piece p1 = chessBoard.getPiece(6, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(5, 3);
        assertEquals(p2, null);
        assertEquals(move, false);
    }
    
    @Test
    public void testPawn_Capture_LegalCoordinates_Captures(){
    	chessBoard.AddPawn(testSubject, 6, 3);
    	Pawn pawn2 = new Pawn(PieceColor.WHITE);
    	chessBoard.AddPawn(pawn2, 5, 3);
    	boolean move = chessBoard.MovePawn(testSubject, 6, 3, 5, 3, MovementType.CAPTURE);
        Piece p1 = chessBoard.getPiece(5, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(6, 3);
        assertEquals(p2, null);
        assertEquals(move, true);
    }
    
    @Test
    public void testPawn_Capture_LegalCoordinates_SameColor_DoesNotCapture(){
    	chessBoard.AddPawn(testSubject, 6, 3);
    	Pawn pawn2 = new Pawn(PieceColor.BLACK);
    	chessBoard.AddPawn(pawn2, 5, 3);
    	boolean move = chessBoard.MovePawn(testSubject, 6, 3, 5, 3, MovementType.CAPTURE);
        Piece p1 = chessBoard.getPiece(6, 3);
        assertEquals(p1, testSubject);
        Piece p2 = chessBoard.getPiece(5, 3);
        assertEquals(p2, pawn2);
        assertEquals(move, false);
    }
}
