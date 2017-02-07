package com.capthed.abyss.gfx.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import com.capthed.abyss.Log;

public class Shader {
	
	/** The types of shaders */
	public enum TYPE {
		VERTEX, FRAGMENT
	}
	
	private String code;
	private TYPE t;
	private int shader;
	
	/**
	 * @param code The code read from the shader file. Use <code>ShaderData</code>
	 * @param t The type of the shader (vertex, fragment)
	 */
	public Shader(String code, TYPE t) {
		this.code = code;
		this.t = t;
		
		compile();
	}
	
	// creates and compiles the shader
	private void compile() {
		// create the shader
		shader = glCreateShader(getGLType(t));
		
		// link the shader to the code
		glShaderSource(shader, code);
		
		// compile
		glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            Log.err("Shader of type " + getTypeName(t) + " has compiled with an error", 1);
        }
	}

	/** @return The OpenGL pointer to the shader in memory*/
	public int getShader() { return shader; }
	
	/** @return The OpenGL code for the given type of shader*/
	public static int getGLType(TYPE t) {
		if (t == TYPE.VERTEX) return GL_VERTEX_SHADER;
		else if (t == TYPE.FRAGMENT) return GL_FRAGMENT_SHADER;
		else return -1;
	}
	
	/** @return The type-name of the given shader (e.g. Type.VERTEX = "vertex")*/
	public static String getTypeName(TYPE t) {
		if (t == TYPE.VERTEX) return "vertex";
		else if (t == TYPE.FRAGMENT) return "fragment";
		else return "";
	}
}
