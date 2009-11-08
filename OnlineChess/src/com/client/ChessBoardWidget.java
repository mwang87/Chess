package com.client;

import java.util.ArrayList;

import com.client.BoardBox.color;
import com.client.BoardBox.piecetype;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChessBoardWidget extends Composite{
	
	static final String boxsize = "60px";
	
	private final GreetingServiceAsync greetingService = GWT
	.create(GreetingService.class);
	VerticalPanel widgetVPanel;
	FlexTable flextable;
	FlexTable moveListTable;
	
	HTML selectedSquareLabel;
	HTML TurnNotificaion;
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
	
	public String getPieceURL(int i, int j){
		piecetype piece = (board2.get(i).get(j)).getPiece();
		color thecolor = (board2.get(i).get(j)).getColor();
		
		String returnval = "https://netfiles.uiuc.edu/dombro2/www/PIECES";
		
		if((i+j)%2==0){
			returnval +="/BlackP";
		}
		else{
			returnval +="/WhiteP";
		}
		
		if(thecolor == color.BLACK)
			returnval += "/B";
		else
			returnval += "/W"; 
		
		if(piece == piecetype.BISHOP)
			returnval += "BISHOP";
		if(piece == piecetype.EMPTY){
			if((i+j)%2==0){
				return "https://netfiles.uiuc.edu/dombro2/www/PIECES/BlackP/BLACK.bmp";
			}
			else{
				return "https://netfiles.uiuc.edu/dombro2/www/PIECES/WhiteP/WHITE.bmp";
			}
		}
		if(piece == piecetype.KING)
			returnval += "KING";
		if(piece == piecetype.KNIGHT)
			returnval += "KNIGHT";
		if(piece == piecetype.PAWN)
			returnval += "PAWN";
		if(piece == piecetype.QUEEN)
			returnval += "QUEEN";
		if(piece == piecetype.ROOK)
			returnval += "ROOK";
		returnval +=".BMP";
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
								Image boardImage = new Image(getPieceURL(i,j));
								boardImage.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										//piececlick(tempj, tempi);
										piececlick(tempi, tempj);
									}
								});
								boardImage.setSize(boxsize, boxsize);
								flextable.setWidget(i, j, boardImage);
							}
						}
						String serverTurn = result.get(8).get(0);
						if(serverTurn.equals("Black")){
							TurnNotificaion.setHTML(" It is Black's Turn");
							turn = true;
						}
						else{
							TurnNotificaion.setHTML(" It is White's Turn");
							turn = false;
						}
						
						ArrayList<String> moveList = result.get(9);
						ArrayList<String> moveListUser = result.get(10);
						moveListTable.clear();
						for(int i = 0 ; i < moveList.size(); i++){
							HTML text = new HTML(moveList.get(i));
							text.setTitle("By "+moveListUser.get(i));
							text.addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
									Widget source = (Widget) event.getSource();
									setupPopup(popup, source.getTitle());
						            int left = source.getAbsoluteLeft() + 10;
						            int top = source.getAbsoluteTop() + 10;
						            popup.setPopupPosition(left, top);

						            // Show the popup
						            popup.show();
								}
							});
							moveListTable.setWidget(i,0,text);
						}
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
		TurnNotificaion = new HTML();
		//Image testImage = new Image("http://boobtube.files.wordpress.com/2008/07/drhorrible.jpg");
		//widgetVPanel.add(testImage);
		resetButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				selectedSquareLabel.setHTML("");
				moveListTable.clear();
				greetingService.resetBoard(new AsyncCallback<ArrayList<ArrayList<String>>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(ArrayList<ArrayList<String>> result) {
						//seeing if user is logged in
						if(result.get(0).get(0).equals("Login")){
							//we are not logged in, redirect
							Window.Location.assign(result.get(1).get(0));
							return;
						}
						
						for(int i = 0; i < 8; i++){
							for(int j = 0; j< 8; j++){
								final int tempj = j;
								final int tempi = i;
								BoardBox box = createBoardBox(result.get(i).get(j));
								board2.get(i).set(j, box);
								//Button newButton = new Button(getPieceString(j,i));
								Image boardImage = new Image(getPieceURL(i,j));
								boardImage.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										//piececlick(tempj, tempi);
										piececlick(tempi, tempj);
									}
								});
								boardImage.setSize(boxsize, boxsize);
								flextable.setWidget(i, j, boardImage);
							}
						}
					}
				});				
			}
		});
		
		selectedSquareLabel = new HTML();
		
		
		
		
		flextable = new FlexTable();
		moveListTable = new FlexTable();
		//creating buttons for each table
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				//final Button button = new Button(getPieceString(j,i));
				//final Button button = new Button(getPieceString(i,j));
				final Image button = new Image(getPieceURL(i, j));
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
		HorizontalPanel topPanel = new HorizontalPanel();
		topPanel.add(resetButton);
		topPanel.add(TurnNotificaion);
		widgetVPanel.add(topPanel);
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(flextable);
		hPanel.add(moveListTable);
		widgetVPanel.add(hPanel);
		//widgetVPanel.add(flextable);
		widgetVPanel.add(selectedSquareLabel);
		
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
				String startLocationString = getBoardBoxString(clickx, clicky);
				selectedSquareLabel.setHTML("You have selected black "+startLocationString);
				
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
				String startLocationString = getBoardBoxString(clickx, clicky);
				selectedSquareLabel.setHTML("You have selected white "+startLocationString);
			}
		}
		else{
			
			//we are clicking a destination
			
			//lets not determine the correct move just yet
			int targetx = i;
			int targety = j;
			if(!MoveValidator.validMove(clickx, clicky, targetx, targety, board2, turn)){
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
			String destinationString = getBoardBoxString(targetx, targety);
			String pieceString = getPieceString(clickx, clicky);
			selectedSquareLabel.setHTML("You have moved: "+pieceString + " to " + destinationString);
			turn = !turn;
			
			greetingService.putMove(clickx, clicky, targetx, targety, new AsyncCallback<ArrayList<ArrayList<String>>>() {
				public void onFailure(Throwable caught) {
					//Window.alert("request failed");
				}
				public void onSuccess(ArrayList<ArrayList<String>> result) {
					//seeing if user is logged in
					if(result.get(0).get(0).equals("Login")){
						//we are not logged in, redirect
						Window.Location.assign(result.get(1).get(0));
						return;
					}
					
					for(int i = 0; i < 8; i++){
						for(int j = 0; j< 8; j++){
							final int tempj = j;
							final int tempi = i;
							BoardBox box = createBoardBox(result.get(i).get(j));
							board2.get(i).set(j, box);
							//Button newButton = new Button(getPieceString(j,i));
							Image boardImage = new Image(getPieceURL(i,j));
							boardImage.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									//piececlick(tempj, tempi);
									piececlick(tempi, tempj);
								}
							});
							boardImage.setSize(boxsize, boxsize);
							flextable.setWidget(i, j, boardImage);
						}
					}
				}
			});
		}
	}
	public String getBoardBoxString(int x, int y){
		char y_index = 'a';
		y_index += y;
		x = 8 - x;
		String output = y_index+Integer.toString(x);
		return output;
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
	
	
	public void setupPopup(DecoratedPopupPanel widget, String inputString){
		widget.ensureDebugId("cwBasicPopup-simplePopup");
		widget.setWidth("150px");
		widget.setWidget(new HTML(inputString));
	}
	
	
}
