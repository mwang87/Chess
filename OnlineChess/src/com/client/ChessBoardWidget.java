package com.client;

import java.util.ArrayList;

import com.client.BoardBox.color;
import com.client.BoardBox.piecetype;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChessBoardWidget extends Composite{
	
	static final String boxsize = "60px";
	
	private final GreetingServiceAsync greetingService = GWT
	.create(GreetingService.class);
	VerticalPanel widgetVPanel;
	FlexTable flextable;
	
	HTML selectedSquareLabel;
	ArrayList<ArrayList<BoardBox>> board2 = new ArrayList<ArrayList<BoardBox>>();
	
	public void initBoard(){
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
			board2.get(i).get(0).setColor(color.BLACK);
			board2.get(i).get(1).setColor(color.BLACK);
			board2.get(i).get(1).setPiece(piecetype.PAWN);
			board2.get(i).get(6).setColor(color.WHITE);
			board2.get(i).get(7).setColor(color.WHITE);
			board2.get(i).get(6).setPiece(piecetype.PAWN);
		}
		
		
		board2.get(0).get(0).setPiece(piecetype.ROOK);
		board2.get(1).get(0).setPiece(piecetype.KNIGHT);
		board2.get(2).get(0).setPiece(piecetype.BISHOP);
		board2.get(3).get(0).setPiece(piecetype.QUEEN);
		board2.get(4).get(0).setPiece(piecetype.KING);
		board2.get(5).get(0).setPiece(piecetype.BISHOP);
		board2.get(6).get(0).setPiece(piecetype.KNIGHT);
		board2.get(7).get(0).setPiece(piecetype.ROOK);
		
		board2.get(0).get(7).setPiece(piecetype.ROOK);
		board2.get(1).get(7).setPiece(piecetype.KNIGHT);
		board2.get(2).get(7).setPiece(piecetype.BISHOP);
		board2.get(3).get(7).setPiece(piecetype.QUEEN);
		board2.get(4).get(7).setPiece(piecetype.KING);
		board2.get(5).get(7).setPiece(piecetype.BISHOP);
		board2.get(6).get(7).setPiece(piecetype.KNIGHT);
		board2.get(7).get(7).setPiece(piecetype.ROOK);
		
		/*
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				BoardBox box = new BoardBox(piecetype.EMPTY, color.BLACK);
				board2[i][j] = box;
			}
		}
		for(int i = 0; i < 8; i++){
			board2[i][0].setColor(color.BLACK);
			board2[i][1].setColor(color.BLACK);
			board2[i][1].setPiece(piecetype.PAWN);
			board2[i][6].setColor(color.WHITE);
			board2[i][7].setColor(color.WHITE);
			board2[i][6].setPiece(piecetype.PAWN);
		}
		
		board2[0][0].setPiece(piecetype.ROOK);
		board2[1][0].setPiece(piecetype.KNIGHT);
		board2[2][0].setPiece(piecetype.BISHOP);
		board2[3][0].setPiece(piecetype.QUEEN);
		board2[4][0].setPiece(piecetype.KING);
		board2[5][0].setPiece(piecetype.BISHOP);
		board2[6][0].setPiece(piecetype.KNIGHT);
		board2[7][0].setPiece(piecetype.ROOK);
		
		board2[0][7].setPiece(piecetype.ROOK);
		board2[1][7].setPiece(piecetype.KNIGHT);
		board2[2][7].setPiece(piecetype.BISHOP);
		board2[3][7].setPiece(piecetype.QUEEN);
		board2[4][7].setPiece(piecetype.KING);
		board2[5][7].setPiece(piecetype.BISHOP);
		board2[6][7].setPiece(piecetype.KNIGHT);
		board2[7][7].setPiece(piecetype.ROOK);*/
	}
	
	public String getPieceString(int i, int j){
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
	
	public void UpdateBoard(){
		Timer t = new Timer() {
			public void run() {
				greetingService.getBoard(new AsyncCallback<ArrayList<ArrayList<String>>>() {
					public void onFailure(Throwable caught) {
						//Window.alert("request failed");
					}
					public void onSuccess(ArrayList<ArrayList<String>> result) {
						for(int i = 0; i < 8; i++){
							for(int j = 0; j< 8; j++){
								final int tempj = j;
								final int tempi = i;
								BoardBox box = createBoardBox(result.get(i).get(j));
								board2.get(i).set(j, box);
								//Button newButton = new Button(getPieceString(j,i));
								Button newButton = new Button(getPieceString(i,j));
								newButton.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										//piececlick(tempj, tempi);
										piececlick(tempi, tempj);
									}
								});
								newButton.setSize(boxsize, boxsize);
								flextable.setWidget(i, j, newButton);
							}
						}
						String serverTurn = result.get(8).get(0);
						if(serverTurn.equals("Black")){
							turn = true;
						}
						else
							turn = false;
					}
				});
			}
		};
		
		
		t.scheduleRepeating(2000);
	}
	
	public ChessBoardWidget(){
		initBoard();
		widgetVPanel = new VerticalPanel();
		Button resetButton = new Button("New Game");
		resetButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				greetingService.resetBoard(new AsyncCallback<ArrayList<ArrayList<String>>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<ArrayList<String>> result) {
						for(int i = 0; i < 8; i++){
							for(int j = 0; j< 8; j++){
								final int tempj = j;
								final int tempi = i;
								BoardBox box = createBoardBox(result.get(i).get(j));
								board2.get(i).set(j, box);
								//Button newButton = new Button(getPieceString(j,i));
								Button newButton = new Button(getPieceString(i,j));
								newButton.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										//piececlick(tempj, tempi);
										piececlick(tempi, tempj);
									}
								});
								newButton.setSize(boxsize, boxsize);
								flextable.setWidget(i, j, newButton);
							}
						}
					}
				});				
			}
		});
		
		selectedSquareLabel = new HTML();
		
		
		widgetVPanel.add(resetButton);
		widgetVPanel.add(selectedSquareLabel);
		
		flextable = new FlexTable();
		
		//creating buttons for each table
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				//final Button button = new Button(getPieceString(j,i));
				final Button button = new Button(getPieceString(i,j));
				button.setSize(boxsize, boxsize);
				final int tempi = i;
				final int tempj = j;
				button.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						//piececlick(tempj, tempi);
						piececlick(tempi, tempj);
					}
				});
				flextable.setWidget(i, j, button);
			}
		}
		
		//Button testButton = new Button("ming");
		widgetVPanel.add(flextable);
		
		initWidget(widgetVPanel);
		UpdateBoard();
	}
	
	static boolean piececlickstatus = false; //true if we have clicked a piece to move
	static int clickx;
	static int clicky;
	static boolean turn = false;		//true black, false white
	public void piececlick(int i , int j){
		if(!piececlickstatus){
			clickx = i;
			clicky = j;
			//checking if click the correct thing
			//Window.alert(getPieceString(clickx, clicky));
			//BoardBox box = board2[clickx][clicky];
			BoardBox box = board2.get(clickx).get(clicky);
			if(turn){
				//its blacks turn
				if(box.getColor() != color.BLACK){
					//wrong piece color;
					piececlickstatus = false;
					return;
				}
				
				//Window.alert("You have clicked a white piece");
				piececlickstatus = true;
				selectedSquareLabel.setHTML("You have selected black "+clickx+" "+clicky);
				
			}
			else{
				//its whites turn
				if(box.getColor() != color.WHITE){
					//wrong piece color;
					piececlickstatus = false;
					return;
				}
				
				//Window.alert("You have clicked a white piece");
				piececlickstatus = true;			
				selectedSquareLabel.setHTML("You have selected white "+clickx+" "+clicky);
			}
		}
		else{
			
			//we are clicking a destination
			
			//lets not determine the correct move just yet
			int targetx = i;
			int targety = j;
			if(!MoveValidator.validMove(clickx, clicky, targetx, targety)){
				piececlickstatus = false;
				selectedSquareLabel.setHTML("Invalid Move");
				return;
			}
			/*
			//BoardBox source = board2[clickx][clicky];
			BoardBox source = board2.get(clickx).get(clicky);
			//BoardBox target = board2[targetx][targety];
			BoardBox target = board2.get(targetx).get(targety);
			
			target.setColor(source.getColor());
			target.setPiece(source.getPiece());
			source.setPiece(piecetype.EMPTY);
			Button newButton = new Button(getPieceString(targetx,targety));
			newButton.setSize("50px", "50px");
			flextable.setWidget(targety, targetx, newButton);
			Button oldButton = new Button(getPieceString(clickx,clicky));
			oldButton.setSize("50px", "50px");
			flextable.setWidget(clicky, clickx, oldButton);*/
			piececlickstatus = false;
			selectedSquareLabel.setHTML("You have moved");
			turn = !turn;
			
			greetingService.greetServer(clickx, clicky, targetx, targety, new AsyncCallback<ArrayList<ArrayList<String>>>() {
				public void onFailure(Throwable caught) {
					//Window.alert("request failed");
				}
				public void onSuccess(ArrayList<ArrayList<String>> result) {
					for(int i = 0; i < 8; i++){
						for(int j = 0; j< 8; j++){
							final int tempj = j;
							final int tempi = i;
							BoardBox box = createBoardBox(result.get(i).get(j));
							board2.get(i).set(j, box);
							//Button newButton = new Button(getPieceString(j,i));
							Button newButton = new Button(getPieceString(i,j));
							newButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									//piececlick(tempj, tempi);
									piececlick(tempi, tempj);
								}
							});
							newButton.setSize(boxsize, boxsize);
							flextable.setWidget(i, j, newButton);
						}
					}

				}
			});

			
		}
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
	
	
}