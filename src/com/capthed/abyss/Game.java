package com.capthed.abyss;

public interface Game {

	/** Called after <code>initDisplay()</code>. Used to initialize components. */
	public void init();
	
	/** Called as soon as the game loop starts (<code>Abyss.start()</code>). Used to initialize the display. */
	public void initDisplay();
	
	/** Called once every update */
	public void constUpdate();
	
	/** Called once every time the game renders. Renders after the last layer */
	public void constRender();
	
	/** Called when the game begings to shut down, in the last iteration of the loop*/
	public void closing();
	
	/** Called when the loop has been closed. */
	public void closed();
}
