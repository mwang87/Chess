package com.client;

import java.util.ArrayList;

import com.client.BoardBox.color;
import com.client.BoardBox.piecetype;

public class MoveValidator {

	public static boolean validMove(int startx, int starty, int endx, int endy, ArrayList<ArrayList<BoardBox>> board2, boolean turn){
		
		BoardBox startPiece = board2.get(startx).get(starty);
		BoardBox endPiece = board2.get(endx).get(endy);
		
		if(startx == endx && starty == endy)
			return false;
		if(startPiece.getPiece() == piecetype.EMPTY)
			return false;
		
		if(turn){
			//black
			if(startPiece.getColor() != color.BLACK)
				return false;
			if(endPiece.getColor() == color.BLACK && endPiece.getPiece() != piecetype.EMPTY)
				return false;
			
		}
		else{
			//white
			if(startPiece.getColor() != color.WHITE)
				return false;
			
			if(endPiece.getColor() == color.WHITE && endPiece.getPiece() != piecetype.EMPTY)
				return false;
		}
		
		
		
		return true;
	}
}
