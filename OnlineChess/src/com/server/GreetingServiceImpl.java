package com.server;

import java.util.ArrayList;
import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import com.client.BoardBox;
import com.client.ChessBoardWidget;
import com.client.GreetingService;
import com.client.MoveValidator;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.client.BoardBox.color;
import com.client.BoardBox.piecetype;
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	//ArrayList<ArrayList<BoardBox>> board2 = null;
	//boolean turn = false;
	public static final String MoveListKey = "MoveList";
	
	
	public ArrayList<ArrayList<BoardBox>> initboard2(){
		ArrayList<ArrayList<BoardBox>> board2 = null;
		if(isPresentCache("board")){
			board2 = new ArrayList<ArrayList<BoardBox>>();
			ArrayList<ArrayList<String>> cacheboard = (ArrayList<ArrayList<String>>) getCached("board");
			for(int i = 0; i < 8; i++){
				ArrayList<BoardBox> row = new ArrayList<BoardBox>();
				for(int j = 0; j < 8; j++){
					row.add(createBoardBox(cacheboard.get(i).get(j)));
				}
				board2.add(row);
			}
		}
		if(board2 == null){
			board2 = new ArrayList<ArrayList<BoardBox>>();
			for(int i = 0; i < 8 ; i++){
				ArrayList<BoardBox> row = new ArrayList<BoardBox>();
				for(int j = 0 ; j < 8; j++){
					BoardBox box = new  BoardBox(piecetype.EMPTY, color.BLACK);
					row.add(box);
				}
				board2.add(row);
			}
			
			for(int i = 0; i < 8; i++){
				board2.get(0).get(i).setColor(color.BLACK);
				board2.get(1).get(i).setColor(color.BLACK);
				board2.get(1).get(i).setPiece(piecetype.PAWN);
				board2.get(6).get(i).setColor(color.WHITE);
				board2.get(7).get(i).setColor(color.WHITE);
				board2.get(6).get(i).setPiece(piecetype.PAWN);
			}
			
			
			board2.get(0).get(0).setPiece(piecetype.ROOK);
			board2.get(0).get(1).setPiece(piecetype.KNIGHT);
			board2.get(0).get(2).setPiece(piecetype.BISHOP);
			board2.get(0).get(3).setPiece(piecetype.QUEEN);
			board2.get(0).get(4).setPiece(piecetype.KING);
			board2.get(0).get(5).setPiece(piecetype.BISHOP);
			board2.get(0).get(6).setPiece(piecetype.KNIGHT);
			board2.get(0).get(7).setPiece(piecetype.ROOK);
			
			board2.get(7).get(0).setPiece(piecetype.ROOK);
			board2.get(7).get(1).setPiece(piecetype.KNIGHT);
			board2.get(7).get(2).setPiece(piecetype.BISHOP);
			board2.get(7).get(3).setPiece(piecetype.QUEEN);
			board2.get(7).get(4).setPiece(piecetype.KING);
			board2.get(7).get(5).setPiece(piecetype.BISHOP);
			board2.get(7).get(6).setPiece(piecetype.KNIGHT);
			board2.get(7).get(7).setPiece(piecetype.ROOK);
			
			saveBoard(board2);
		}
		
		return board2;
	}
	
	public ArrayList<ArrayList<String>> getBoard() {
		ArrayList<ArrayList<BoardBox>> board2 = initboard2();
		
		ArrayList<ArrayList<String>> returnVal = getSerializedBoard(board2);
		/*for(int i = 0; i < 8; i ++){
			ArrayList<String> row = new ArrayList<String>();
			for(int j = 0; j < 8; j++){
				row.add(getPieceString(i,j));
			}
			returnVal.add(row);
		}*/
		
		
		ArrayList<String> additionalParams = new ArrayList<String>();
		Boolean turn = getTurn();
		if(turn)
			additionalParams.add("Black");
		else
			additionalParams.add("White");
		returnVal.add(additionalParams);
		ArrayList<String> moveList = getMoveList();
		returnVal.add(moveList);
		return returnVal;
	}

	public ArrayList<ArrayList<String>> resetBoard(){
		//checking if the user is logged in
		if(!isLoggedIn()){
			//return back to the user that we are not logged in
			ArrayList<ArrayList<String>> returnVal = new ArrayList<ArrayList<String>>();
			ArrayList<String> first = new ArrayList<String>();
			ArrayList<String> second = new ArrayList<String>();
			first.add("Login");
			second.add(getLoginURL());
			returnVal.add(first);
			returnVal.add(second);
			return returnVal;
		}
		
		
		ArrayList<ArrayList<BoardBox>> board2 = new ArrayList<ArrayList<BoardBox>>();
		for(int i = 0; i < 8 ; i++){
			ArrayList<BoardBox> row = new ArrayList<BoardBox>();
			for(int j = 0 ; j < 8; j++){
				BoardBox box = new  BoardBox(piecetype.EMPTY, color.BLACK);
				row.add(box);
			}
			board2.add(row);
		}
		
		for(int i = 0; i < 8; i++){
			board2.get(0).get(i).setColor(color.BLACK);
			board2.get(1).get(i).setColor(color.BLACK);
			board2.get(1).get(i).setPiece(piecetype.PAWN);
			board2.get(6).get(i).setColor(color.WHITE);
			board2.get(7).get(i).setColor(color.WHITE);
			board2.get(6).get(i).setPiece(piecetype.PAWN);
		}
		
		
		board2.get(0).get(0).setPiece(piecetype.ROOK);
		board2.get(0).get(1).setPiece(piecetype.KNIGHT);
		board2.get(0).get(2).setPiece(piecetype.BISHOP);
		board2.get(0).get(3).setPiece(piecetype.QUEEN);
		board2.get(0).get(4).setPiece(piecetype.KING);
		board2.get(0).get(5).setPiece(piecetype.BISHOP);
		board2.get(0).get(6).setPiece(piecetype.KNIGHT);
		board2.get(0).get(7).setPiece(piecetype.ROOK);
		
		board2.get(7).get(0).setPiece(piecetype.ROOK);
		board2.get(7).get(1).setPiece(piecetype.KNIGHT);
		board2.get(7).get(2).setPiece(piecetype.BISHOP);
		board2.get(7).get(3).setPiece(piecetype.QUEEN);
		board2.get(7).get(4).setPiece(piecetype.KING);
		board2.get(7).get(5).setPiece(piecetype.BISHOP);
		board2.get(7).get(6).setPiece(piecetype.KNIGHT);
		board2.get(7).get(7).setPiece(piecetype.ROOK);
		
		saveBoard(board2);
		setTurn(false);
		ArrayList<String> moveList = new ArrayList<String>();
		putCached(MoveListKey, moveList);
		ArrayList<ArrayList<String>> returnVal = getSerializedBoard(board2);
		
		ArrayList<String> additionalParams = new ArrayList<String>();
		Boolean turn = getTurn();
		if(turn)
			additionalParams.add("Black");
		else
			additionalParams.add("White");
		returnVal.add(additionalParams);
		
		
		return returnVal;
	}
	
	public ArrayList<ArrayList<String>> putMove(int startx, int starty, int endx, int endy) {
		//checking if the user is logged in
		if(!isLoggedIn()){
			//return back to the user that we are not logged in
			ArrayList<ArrayList<String>> returnVal = new ArrayList<ArrayList<String>>();
			ArrayList<String> first = new ArrayList<String>();
			ArrayList<String> second = new ArrayList<String>();
			first.add("Login");
			second.add(getLoginURL());
			returnVal.add(first);
			returnVal.add(second);
			return returnVal;
		}
		
		
		
		ArrayList<ArrayList<BoardBox>> board2 = initboard2();

		//BoardBox source = board2[clickx][clicky];
		BoardBox source = board2.get(startx).get(starty);
		//BoardBox target = board2[targetx][targety];
		BoardBox target = board2.get(endx).get(endy);
		
		boolean validturn = false;
		Boolean turn = getTurn();
		if(turn){
			if(source.getColor() == color.BLACK){
				validturn = MoveValidator.validMove(startx, starty, endx, endy, board2, turn);
			}
		}
		else{
			if(source.getColor() == color.WHITE){
				validturn = MoveValidator.validMove(startx, starty, endx, endy, board2, turn);
			}
		}
		
		if(validturn){
			target.setColor(source.getColor());
			target.setPiece(source.getPiece());
			source.setPiece(piecetype.EMPTY);
			turn = !turn;
			setTurn(turn);
			saveBoard(board2);
			
			//Saving move list, MoveListKey
			ArrayList<String> moveList; 
			if(isPresentCache(MoveListKey)){
				moveList = (ArrayList<String>)getCached(MoveListKey);
			}
			else{
				moveList = new ArrayList<String>();
			}
			String currentMove = getPieceString(endx, endy, board2) +" "+ getBoardBoxString(endx, endy);
			moveList.add(currentMove);
			putCached(MoveListKey, moveList);
		}
		
		ArrayList<ArrayList<String>> returnVal = getSerializedBoard(board2);
		
		/*for(int i = 0; i < 8; i ++){
			ArrayList<String> row = new ArrayList<String>();
			for(int j = 0; j < 8; j++){
				row.add(getPieceString(i,j));
			}
			returnVal.add(row);
		}*/
		
		
		ArrayList<String> additionalParams = new ArrayList<String>();
		if(turn)
			additionalParams.add("Black");
		else
			additionalParams.add("White");
		
		//insertMoveList(returnVal);
		return returnVal;
		
	}
	
	ArrayList<ArrayList<String>> getSerializedBoard(ArrayList<ArrayList<BoardBox>> board2){
		ArrayList<ArrayList<String>> returnVal = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 8; i ++){
			ArrayList<String> row = new ArrayList<String>();
			for(int j = 0; j < 8; j++){
				row.add(getPieceString(i,j, board2));
			}
			returnVal.add(row);
		}
		return returnVal;
	}
	
	String getPieceString(int i, int j, ArrayList<ArrayList<BoardBox>> board2){
		
		piecetype piece = (board2.get(i).get(j)).getPiece();
		color thecolor = (board2.get(i).get(j)).getColor();
		
		String returnval = "";
		if(thecolor == color.BLACK)
			returnval = "B-";
		else
			returnval = "W-"; 
		
		if(piece == piecetype.BISHOP)
			returnval += "Bi";
		if(piece == piecetype.EMPTY)
			returnval = "    ";
		if(piece == piecetype.KING)
			returnval += "Ki";
		if(piece == piecetype.KNIGHT)
			returnval += "Kn";
		if(piece == piecetype.PAWN)
			returnval += "Pa";
		if(piece == piecetype.QUEEN)
			returnval += "Qu";
		if(piece == piecetype.ROOK)
			returnval += "Ro";
		
		return returnval;
	}
	
	
	public String getBoardBoxString(int x, int y){
		char y_index = 'a';
		y_index += y;
		x = 8 - x;
		String output = y_index+Integer.toString(x);
		return output;
	}
	
	
	public boolean isPresentCache(String key){
		try{
			Cache cache;
			cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
			return cache.containsKey(key);
		}
		catch(CacheException e){
			return false; 
		}
	}
	public Object getCached(String key){
		if(isPresentCache(key)){
			try{
				Cache cache;
				cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
				return cache.get(key);
			}
			catch(CacheException e){
				return null;
			}
		}
		else{
			return null;
		}
	}
	public void putCached(String key, Object value){
		try{
			Cache cache;
			cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
			cache.put(key, value);
		}
		catch(CacheException e){
			
		}
	}
	
	public Boolean getTurn(){
		if(isPresentCache("turn")){
			return (Boolean)getCached("turn");
		}
		Boolean turn = new Boolean(false);
		putCached("turn", turn);
		return turn;
	}
	
	public void setTurn(Boolean turn){
		putCached("turn",turn);
	}
	
	public void saveBoard(ArrayList<ArrayList<BoardBox>> board2){
		ArrayList<ArrayList<String>> cacheboard = getSerializedBoard(board2);
		putCached("board", cacheboard);
	}
	
	
	
	public BoardBox createBoardBox(String input){
		if(input.length() == 0)
			return new BoardBox(piecetype.EMPTY, color.BLACK);
		color myColor;
		if(input.charAt(0) == 'W')
			myColor = color.WHITE;
		else
			myColor = color.BLACK;
		
		piecetype type = piecetype.EMPTY;
		if(input.subSequence(2, 4).equals("Ki"))
			type = piecetype.KING;
		if(input.subSequence(2, 4).equals("Pa"))
			type = piecetype.PAWN;
		if(input.subSequence(2, 4).equals("Ro"))
			type = piecetype.ROOK;
		if(input.subSequence(2, 4).equals("Kn"))
			type = piecetype.KNIGHT;
		if(input.subSequence(2, 4).equals("Bi"))
			type = piecetype.BISHOP;
		if(input.subSequence(2, 4).equals("Qu"))
			type = piecetype.QUEEN;
		
		return new BoardBox(type, myColor);
	}
	
	public ArrayList<String> getMoveList(){
		ArrayList<String> moveList;
		if(isPresentCache(MoveListKey)){
			moveList = (ArrayList<String>)getCached(MoveListKey);
		}
		else{
			moveList = new ArrayList<String>();
		}
		
		return moveList;
	}
	
	public boolean isLoggedIn(){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(user == null)
			return false;
		return true;
	}
	
	public String getUserName(){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return user.getNickname();
	}
	
	public String getLoginURL(){
		UserService userService = UserServiceFactory.getUserService();
		String sourceURL = this.getServletContext().getContextPath();
		return userService.createLoginURL(sourceURL);
	}
	
}
