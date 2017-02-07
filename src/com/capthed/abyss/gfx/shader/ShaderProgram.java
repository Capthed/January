package com.capthed.abyss.gfx.shader;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

	/** An invalid ShaderProgram used to unbind programs. */
	public static final ShaderProgram NONE = new ShaderProgram(new Shader[]{});
	
	private Shader[] shaders;
	private int program;
	
	/**
	 * @param shaders The shaders that will make this program
	 */
	public ShaderProgram(Shader[] shaders) {
		this.shaders = shaders;
		
		initProgram();
	}
	
	// inits the program in gl
	private void initProgram() {
		program = glCreateProgram();
		
		for (int i = 0; i < shaders.length; i++)
			glAttachShader(program, shaders[i].getShader());
		
		glLinkProgram(program);
		glValidateProgram(program);
	}

	/** @return The OpenGL pointer to the program*/
	public int getProgram() { return program; }
}
