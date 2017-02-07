package com.capthed.abyss.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

	private static int size = 65536;
	
	public static boolean[] keysDown; // contains all keys, 2 if it is being kept down for more frames, 1 if it was clikced this frame, 0 if it is not down
	public static int[] keysPressed; // contains all of the keys at their indices, 1 if pressed this frame, 0 otherwise
	
	// init all of the components
	static void init() {	
		keysDown = new boolean[size];	
		keysPressed = new int[size];	
		
		for (int i = 0; i < size; i++) {
			keysDown[i] = false;
			keysPressed[i] = 0;
		}
	}
	
	// the method supplied by the GLFWKeyCallback class, gets called when a key is being
	// interacted with
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (action == GLFW_RELEASE)
			keysDown[key] = false;
		else
			keysDown[key] = true;
			
		if (action == GLFW_PRESS && keysPressed[key] == 0)
			keysPressed[key] = 1;
		else if (action == GLFW_RELEASE)
			keysPressed[key] = 0;
	}

	/** 
	 * @return True during the frame the key was pressed, false otherwise.
	 */
	public static boolean isKeyPressed(int key) {
		return keysPressed[key] == 1 ? true : false;
	}
	
	/** @return True if the key is held down. */
	public static boolean isKeyDown(int key) {
		return keysDown[key];
	}
	
	// does the logic for the keys being held down, called last in the update loop every frame
	static void postProcess() {
		for (int i = 0; i < size; i++) { 
			if (keysPressed[i] == 1)
				keysPressed[i] = 2;
		}
	}
}
