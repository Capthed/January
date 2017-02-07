package com.capthed.abyss.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import com.capthed.abyss.gfx.Display;
import com.capthed.abyss.math.Vec2;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

	private static int size = 12;
	
	// the same principle as in keyboard (see Keyboard)
	private static boolean[] keysDown;
	private static int[] keysPressed;
	
	private static DoubleBuffer x;
    private static DoubleBuffer y;
	
    // init all components
	static void init() {
		keysDown = new boolean [size];
		keysPressed = new int[size];
		
		x = BufferUtils.createDoubleBuffer(1);
	    y = BufferUtils.createDoubleBuffer(1);
		
	    updatePos();
	    
		for (int i = 0; i < size; i++) {
			keysDown[i] = false;
			keysPressed[i] = 0;
		}
	}
	
	// update the position of the mouse
	private static void updatePos() {
		x = BufferUtils.createDoubleBuffer(1);
	    y = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(Display.getDisplay(), x, y);	
	}
	
	/** @return True if the key is held down. */
	public static boolean isKeyDown(int key) {
		boolean check = glfwGetMouseButton(Display.getDisplay(), key) == GLFW_PRESS ? true : false;
		keysDown[key] = check;
		
		return check;
	}
	
	/** 
	 * @return True during the frame the key was pressed, false otherwise.
	 */
	public static boolean isKeyPressed(int key) {
		boolean check = glfwGetMouseButton(Display.getDisplay(), key) == GLFW_PRESS ? true : false;
		
		if (check && keysPressed[key] == 0)
			keysPressed[key] = 1;
		
		if (glfwGetMouseButton(Display.getDisplay(), key) == GLFW_RELEASE)
			keysPressed[key] = 0;
		
		return keysPressed[key] == 1 ? true : false;
	}
	
	/** @return The mouse position represented with a Vec2*/
	public static Vec2 getPos() { return new Vec2(getX(), getY()); }
	
	/** @return The x position of the mouse on the map*/
	public static float getX() {
		updatePos();
		x.rewind();
	
		float fx = (float)(x.get());
		
		return fx;
	}

	/** @return The y position of the mouse on the map*/
	public static float getY() {
		updatePos();
		y.rewind();
		
		float fy = (float)y.get();
		
		return Display.getHeight() - fy;
	}

	// does the logic for the keys being held down, called last in the update loop every frame
	static void postProcess() {
		for (int i = 0; i < size; i++) { 
			if (keysPressed[i] == 1)
				keysPressed[i] = 2;
		}
	}
}
