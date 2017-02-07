package com.capthed.abyss.gfx;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWVidMode;

import com.capthed.abyss.Log;
import com.capthed.abyss.input.Keyboard;

public class Display {

	private static int width, height;
	private static String title;
	private static long display = -1;
	private static boolean isFull = false;
	private static int monitor = 0;
	private static Keyboard keyboard;
	
	/**
	 * Creates the display. Modifications like fullscreen must be called before this method.
	 * 
	 * @param w Width of display
	 * @param h Height of display <b>(width and height act as resolution, not size if the display is fullscreen)</b>
	 * @param title Title to be displayed on the window
	 * @param decorated If true, the window will have boundaries, otherwise it wont
	 */
	public static void create(int w, int h, String title, boolean decorated) {
		width = w;
		height = h;
		Display.title = title;
		
		// init the window in glfw
		glfwInit();
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		// TODO
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		// sets the decoration
		glfwWindowHint(GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);

		// sets the desired monitor and checks if it exists
		PointerBuffer p = glfwGetMonitors();
		long mon = -1;
		try {
			mon = p.get(monitor);
		} catch(Exception e) {
			Log.err("No such monitor", 1);
		}
		
		//get the desired monitor
		GLFWVidMode vidmode = glfwGetVideoMode(mon);

		// checks if the window is within the screen boundaries (only if it is not fullscreen)
		if (!isFull && w > vidmode.height() || h > vidmode.height())
			Log.err("Display can't be larger than the screen", 1);
		
		if (isFull) {
			display = glfwCreateWindow(w, h, title, mon, NULL);
		} else {
			// creates the window and saves it in the pointer
			display = glfwCreateWindow(w, h, title, NULL, NULL);
		}
		
		if (display == NULL) 
			Log.err("Error while creating display", 1);
		
		glfwMakeContextCurrent(display);
		
		int var0 = 0;
		if (monitor != 0) {
			GLFWVidMode vd = glfwGetVideoMode(glfwGetPrimaryMonitor());
			var0 = vd.width();
		}
		
		// set the keyboard object to listen to the display
		keyboard = new Keyboard();
		glfwSetKeyCallback(display, keyboard);
		
		int posx = var0 + vidmode.width() / 2 - w / 2;
		int posy = vidmode.height() / 2 - h / 2;
		glfwSetWindowPos (display, posx, posy);
		
		glfwShowWindow(display);
	}
	
	// called in the render method
	public static void swap() {
		if (display == -1)
			Log.err("Display not created", 1);
		
		glfwSwapBuffers(display);
	}

	/** Set the monitor on which the display will run. First monitor is 0, second 1 */
	public static void setMonitor(int i) { monitor = i; }
	
	/** @return The current monitor of the display. */
	public static int getCurrMonitor() { return monitor; }
	
	/** Set if the display is fullscreen or not. Can only be done <i><b>before</b></i> calling <code>create</code>*/
	public static void setFullscreen(boolean f) { isFull = f; }
	
	/** @return Is the display fullscreen or not*/
	public static boolean isFullscreen() { return isFull; }
	
	/** @return Display width*/
	public static int getWidth() { return width; }
	
	/** @return Display height*/
	public static int getHeight() { return height; }
	
	/** @return Title of the display */
	public static String getTitle() { return title; }
	
	/** @return True if the window is being closed, false otherwise. */
	public static boolean isCloseRequested() { return glfwWindowShouldClose(display) == GLFW_TRUE ? true : false; }
	
	/** @return The GLFW pointer to the display. */
	public static long getDisplay() { return display; }
	
	/** Destroys the display.*/
	public static void destroy() {
		if (display == -1)
			Log.err("Display not created", 1);
		
		Log.log("Destroying window");
		glfwDestroyWindow(display);
	}
}
