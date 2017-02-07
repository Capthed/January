package com.capthed.abyss.gfx;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

public class RenderUtil {
	
	// init the OpenGL components
	public static void initGL() {
		int w = Display.getWidth();
		int h = Display.getHeight();
		
		GL.createCapabilities();
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
			
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, w, 0, h, -1, 1);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, w, h);
	}

	// set the clear color to the given values
	public static void setClearColor(float r, float g, float b) {
		glClearColor(r, g, b, 1);
	}
	
	// clears the window and sets it to the selected color
	public static void clearDisplay() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
/*	// loads and sets up the standard shaders
	public static void loadStdShaders() {
		Shader f = new Shader(ShaderData.read("enres//shaders//fragment.fs"), TYPE.FRAGMENT);
		Shader v = new Shader(ShaderData.read("enres//shaders//vertex.vs"), TYPE.VERTEX);
		
		ShaderProgram p = new ShaderProgram(new Shader[] {f, v});
		Render.useProgram(p);
	}*/
}
