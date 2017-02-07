package com.capthed.abyss.input;

import org.lwjgl.glfw.GLFW;

/**
 * This class extends the GLFW class that holds the code for all input keys. Because of this, 
 * all keys should be refered to as in GLFW. This also contains useful methods for dealing with keys. 
 * 
 */
public class Keys extends GLFW {

	// controller keys and axis
	public static final int BUTTON_A = 0;
	public static final int BUTTON_B = 1;
	public static final int BUTTON_X = 2;
	public static final int BUTTON_Y = 3;
	public static final int BUTTON_LB = 4;
	public static final int BUTTON_RB = 5;
	public static final int BUTTON_SELECT = 6;
	public static final int BUTTON_START = 7;
	public static final int BUTTON_LEFTANALOG = 8;
	public static final int BUTTON_RIGHTANALOG = 9;
	public static final int BUTTON_UP = 10;
	public static final int BUTTON_RIGHT = 11;
	public static final int BUTTON_DOWN = 12;
	public static final int BUTTON_LEFT = 13;
	public static final int LA_L_R = 0; // left analog, left-right
	public static final int LA_U_D = 1; // ~, up -down
	public static final int LT_RT = 2; // left trigger, right trigger
	public static final int RA_U_D = 3; // right analog, up-down
	public static final int RA_L_R = 4; // ~, left-right
	
	/** @return The GLFW key code for the given character*/
	public static int getKey(char c) {
		int key = -1;
		
		if (c >= 'A' && c <= 'Z')
			key = (int)c;
		else if (c >= 'a' && c <= 'z')
			key = (int)(c - 32);
		
		return key;
	}
	
	/** @return The character represented by the given GLFW code*/
	public static char getSign(int key) {
		return (char)key;
	}
}
