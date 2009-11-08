package com.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	ArrayList<ArrayList<String>> putMove(int startx, int starty, int endx, int endy);
	
	ArrayList<ArrayList<String>> getBoard();
	
	ArrayList<ArrayList<String>> resetBoard(); 
}
