package com.client;

public class MoveValidator {

	public static boolean validMove(int startx, int starty, int endx, int endy){
		if(startx == endx && starty == endy)
			return false;
		return true;
	}
}
