package com.capthed.abyss.input;

import static org.lwjgl.glfw.GLFW.GLFW_JOYSTICK_1;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.capthed.abyss.Log;

public class Controller {

	private static ByteBuffer buff;
	private static FloatBuffer buff2;
	private static int[] buttons;
	private static float[] axis;
	private static boolean has = true;
	
	// init all components
	public static void init() {
		try {
			buff = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
			buff2 = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
			
			buttons = new int[buff.limit()];
			axis = new float[buff2.limit()];
			has = true;
		} catch (Exception e) {
			Log.warn("No controller found");
			has = false;
		}
	}
	
	// update the contents of the controller
	public static void update() {
		if (!has) return;
		int i = 0;
		buff = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
		while(buff.hasRemaining()) {
			int b = buff.get();
			if ((buttons[i] == 1 || buttons[i] == 2)&& b == 1) {
				buttons[i] = 2;
			}
			else
				buttons[i] = b;
			i++;
		}
		
		// get the axis values
		int i2 = 0;
		buff2 = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
		while(buff2.hasRemaining()) {
			axis[i2++] = buff2.get();
		}
	}
	
	/** @return True if the button is held down. */
	public static boolean isButtonDown(int b) {
		if (!has) return false;
		return buttons[b] == 0 ? false : true;
	}
	
	/** 
	 * @return True during the frame it was pressed, false otherwise. 
	 */
	public static boolean isButtonPressed(int b) {
		if (!has) return false;
		return buttons[b] == 1 ? true : false;
	}
	
	// TODO axis names
	/** @return The value of the axis requested (between -1 and 1)*/
	public static float getAxis(int id) {
		if (!has) return 0;
		return axis[id];
	}
	
	/** @return True if there is a controller connected, false otherwise*/
	public static boolean has() { return has; }
}
