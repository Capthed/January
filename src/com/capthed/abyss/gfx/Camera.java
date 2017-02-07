package com.capthed.abyss.gfx;

import static org.lwjgl.opengl.GL11.*;

import com.capthed.abyss.math.Vec2;

public class Camera {

	private static Camera def = new Camera(new Vec2(0, 0));
	private static Camera current = def;
	
	private Vec2 pos;
	
	/** @param pos The lower left coordinate of the camera*/
	public Camera(Vec2 pos) {
		this.pos = pos;
	}
	
	/**
	 * Move the camera by the ammount delta 
	 */
	public void move(Vec2 delta) {
		pos.add(delta);
		
		glTranslatef(-pos.x(), -pos.y(), 0f);
	}
	
	/** Set the current camera. Only one camera can be active at a time*/
	public static void setCurrent(Camera c) { 
		glLoadIdentity();
		current = c; 
		
		glTranslatef(-current.getPos().x(), -current.getPos().y(), 0);
	}
	
	/** @return The position of the camera (lower left corner) */
	public Vec2 getPos() { return pos; }
	
	/** @return The current camera */
	public static Camera getCurrent() { return current; }
	
	/** @return The default camera starting at position (0, 0)*/
	public static Camera getDefault() { return def; }
}
