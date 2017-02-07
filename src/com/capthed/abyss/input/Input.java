package com.capthed.abyss.input;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public abstract class Input {

	// init all input classes
	public static void init() {
		Keyboard.init();
		Mouse.init();
		Controller.init();
	}
	
	// update the input elements that need updating
	public static void update() {
		Controller.update();
	}
	
	// poll events from the display
	public static void pollEvents() {
		glfwPollEvents();
	}
	
	// called last in every update iterration
	public static void postProcess() {
		Keyboard.postProcess();
		Mouse.postProcess();
	}
}
