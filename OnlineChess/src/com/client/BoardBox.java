package com.client;

public class BoardBox {
	piecetype piece;
	color color;
	
	
	public BoardBox(piecetype piece, color color){
		this.piece = piece;
		this.color = color;
	}
	public piecetype getPiece() {
		return piece;
	}
	public void setPiece(piecetype piece) {
		this.piece = piece;
	}
	public color getColor() {
		return color;
	}
	public void setColor(color color) {
		this.color = color;
	}
	
	
	public enum piecetype{
		KING,
		QUEEN,
		PAWN,
		ROOK,
		BISHOP,
		KNIGHT,
		EMPTY
	}
	public enum color{
		WHITE,
		BLACK
	}
}
