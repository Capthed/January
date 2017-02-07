package com.capthed.abyss.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import com.capthed.abyss.Log;
import com.capthed.abyss.gfx.shader.ShaderProgram;
import com.capthed.abyss.math.Vec2;

public class Render {

	/**
	 * Draws the given shape to the screen as a polygon
	 * 
	 * @param s The shape to be drawn
	 */
	public static void shape(Shape s) {
		if (s == null) {
			Log.err("Null atempt on <polygon>", 1);
		}
		
		// bind and setup the shape vbo
		glBindBuffer(GL_ARRAY_BUFFER, s.getVBO());
		glVertexPointer(s.getVertexSize(), GL_FLOAT, 0, 0);
		
		if (s.getTexture() != null) {
			// bind and setup the text coordinate vbo
			glEnable(GL_TEXTURE_COORD_ARRAY);
			
			glBindTexture(GL_TEXTURE_2D, s.getTexture().getID());
			glBindBuffer(GL_ARRAY_BUFFER, s.getTexVBO());
			glTexCoordPointer(2, GL_FLOAT, 0, 0);
		} 
		
		// bind and setup the shape color vbo
		glEnable(GL_COLOR_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, s.getColorVBO());
		glColorPointer(s.getColorDepth(), GL_FLOAT, 0, 0);
		
		glEnable(GL_VERTEX_ARRAY);
		
		glDrawArrays(GL_POLYGON, 0 , s.getNumberOfVertices());
		
		glDisable(GL_VERTEX_ARRAY);
		
		if (s.getTexture() != null) {
			glDisable(GL_TEXTURE_COORD_ARRAY);
			glBindTexture(GL_TEXTURE_2D, 0);
		} 
	}
	
	/** 
	 * Renders the desired set of vertices as a polygon on the screen. Less optimised than <code>shape(Shape)</code>
	 */
	public static void debug(Vec2[] v) {
		glColor3f(0, 1, 0);
		glPushMatrix();
		glBegin(GL_LINE_LOOP);
		
		for (int i = 0; i < v.length; i++) {
			glVertex2f(v[i].x(), v[i].y());
		}
		
		glEnd();
		glPopMatrix();
		glColor3f(1, 1, 1);
	}
	
	/** Use the given shader program for rendering. To remove all programs throw in <code>ShaderProgram.NONE</code>*/
	public static void useProgram(ShaderProgram p) {
		if (p == ShaderProgram.NONE){
			glUseProgram(0);
			return;
		}
		
		glUseProgram(p.getProgram());
	}
}
