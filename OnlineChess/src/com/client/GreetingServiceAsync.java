package com.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(int startx, int starty, int endx, int endy, AsyncCallback<ArrayList<ArrayList<String>>> callback);
	void getBoard(AsyncCallback<ArrayList<ArrayList<String>>> callback);

	void resetBoard(AsyncCallback<ArrayList<ArrayList<String>>> callback);
}
