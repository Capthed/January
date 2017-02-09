package com.capthed.abyss.phys;

import com.capthed.abyss.Log;
import com.capthed.abyss.component.GObject;
import com.capthed.abyss.math.Vec2;

public class RigidBody {

	private float mass;
	private Vec2 f = new Vec2(0, 0); // force
	private Vec2 v = new Vec2(0, 0); // velocity
	private GObject go;
	
	/** @param mass The mass of the body. Mass plays a role in how the object moves and how forces interact with it*/
	public RigidBody(float mass) {
		this.mass = mass;
	}
	
	/** Apply a certain ammount of force to the rigid body. Forces move the body.*/
	public void applyForce(Vec2 f) {
		this.f.add(f);
		accelerate(this.f.div(mass));
	}
	
	// accelerate the body via the force
	private void accelerate(Vec2 a) {
		v.add(a);
	}
	
	/** Called by the engine. Moves the rigid body by it's veloctity*/
	public void move() {
		go.tryMove(v);
		
		f.mult(0);
	}
	
	/** Calculates and implements the drag. Called by the engine*/
	public void drag(float koef) {
		if (v.equals(new Vec2(0, 0))) return;
		
		float speed = v.length();
		float mag = koef * speed * speed;
		
		Vec2 df = new Vec2(v);
		df.mult(-1);
		
		df.normalize().mult(mag);

		if (df.x() < 0.00005f) v.setX(0);
		if (df.y() < 0.00005f) v.setY(0);
		
		applyForce(df);
	}
	
	/** Should be called only by the engine. Sets this rb's object in object constructor*/
	public void setGO(GObject go) { 
		if (!go.isCollidable())
			Log.warn("Non collidable GO in RigidBody: " + go.toString());
		
		this.go = go; 
	}
}