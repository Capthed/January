package com.capthed.abyss;

import com.capthed.abyss.gfx.Display;

/**
 * create -> start -> initDisplay -> init
 * 
 */
public class Abyss {

	private static final String VERSION = "2.0.a1";
	
	private static Game game;
	private static Thread mainLoopThread;
	
	/** 
	 * Creates the engine. Called first. 
	 *  
	 * @param game The game object to which the engine will refer to.
	 */
	public static void create(Game game) {
		if (game == null)
			Log.err("Invalid game argument: null", 1);
		
		mainLoopThread = new Thread(new GameLoop());
		
		Abyss.game = game;
	}
	
	/** Starts the engine. Engine must first be created. */
	public static void start() {
		if (game == null)
			Log.err("The engine must be created before starting it", 1);
		
		// starts the main loop
		GameLoop.startLoop();
		mainLoopThread.start();
		
		Timer.start();
	}
	
	/** Stops the engine.*/
	public static void stop() {
		if (mainLoopThread == null)
			Log.err("Game loop must first be started to stop", 0);
		
		GameLoop.stopLoop();
		
		Display.destroy();
		
		game.closed();
	}
	
	/** 
	 * Can be called any time. <b>FPS above 60 causes problems</b>
	 * 
	 * @param fps The FPS to which the engine will be set
	 */
	public static void setFPS(int fps) {
		GameLoop.setFPS(fps);
	}
	
	/** @return FPS, return -1 if the FPS has no limit*/
	public static int getFPS() { return GameLoop.getFPS(); }
	
	/** @return The game object*/
	public static Game getGame() { return game; }
	
	/** @return The current Abyss version */
	public static String getVersion() { return VERSION; }
}
