package com.capthed.abyss;

import com.capthed.abyss.gfx.Display;
import com.capthed.abyss.gfx.LayerManager;
import com.capthed.abyss.gfx.RenderUtil;
import com.capthed.abyss.input.Input;

public class GameLoop implements Runnable {

	// default, can change
	private static int FPS = 60;
	
	private static boolean isRunning = false;
	
	// if it is false, the game will render as many times as it can
	private static boolean checkFps = true;
	private static double fpsDelta = (1.0 / (double)FPS) * 1_000_000_000;
	
	// main game loop
	public void run() {
		initSubsystems();
		
		// 60 because it is the UPS
		final double delta = (1.0 / 60.0) * 1_000_000_000;
		
		long lastTime = System.nanoTime();
		long newTime = 0;
		long sinceLast = 0;
		long checker = 0;
		long fpsChecker = 0;
		
		long fpsTimer = 0;
		
		int fpsCounter = 0;
		int upsCounter = 0;
		
		// the actual loop
		while(isRunning) {
			if (Display.isCloseRequested())
				Abyss.stop();
			
			newTime = System.nanoTime();
			sinceLast = newTime - lastTime;
			lastTime = newTime;
			
			checker += sinceLast;
			fpsTimer += sinceLast;
			fpsChecker += sinceLast;
			
			// checks if the last update time has been 1/60 seconds ago
			if (checker >= delta) {
				init();
				update();
				
				upsCounter++;
				checker = 0;
			}
			
			// if a second has passed, calculate the FPS and UPS
			if (fpsTimer >= 1_000_000_000) {
				Log.log("FPS: " + fpsCounter + "; UPS: " + upsCounter);
				fpsTimer = 0;
				fpsCounter = 0;
				upsCounter = 0;
			}
			
			// if the fps is not unlimited then it checks if it's time to render, skips rendering if it is not
			if (checkFps && fpsChecker < fpsDelta) {
				continue;
			}
			
			render();
			fpsCounter++;
			fpsChecker = 0;
		}
		
		Abyss.getGame().closing();
	}
	
	// init all subsystems required to run the engine, also call the init functions of the game
	private void initSubsystems() {
		Abyss.getGame().initDisplay();
		
		RenderUtil.initGL();
		
		Input.init();
		
		Abyss.getGame().init();
	}
	
	private void init() {
		
	}
	
	private void update() {
		Input.pollEvents();
		Input.update();
		
		Abyss.getGame().constUpdate();
	
		Input.postProcess();
	}
	
	private void render() {
		RenderUtil.clearDisplay();
		
		LayerManager.renderAll();
		Abyss.getGame().constRender();
		
		Display.swap();
	}
	
	// sets the conditions for running the main loop
	static void startLoop() {
		isRunning = true;
	}
	
	// stops the loop
	static void stopLoop() {
		isRunning = false;
	}

	/** <b>**Only called by the engine!!**</b>*/
	// called from the display to update the fps if it was changed
	public static void setFPS(int fps) {
		if (fps == -1) {
			checkFps = false;
			FPS = fps;
			
			return;
		}
		
		FPS = fps;
		fpsDelta = (1.0 / (double)FPS) * 1_000_000_000;
	}

	/** @return FPS, return -1 if the FPS has no limit*/
	public static int getFPS() { return FPS; }
}
