package com.capthed.abyss.phys;

import com.capthed.abyss.math.Vec2;
import com.capthed.abyss.phys.collision.Collider;

public class RigidBody {

	private Collider coll;
	private float mass;
	private Vec2 v; // velocity
	
	public RigidBody(Collider coll, float mass) {
		this.coll = coll;
		this.mass = mass;
	}
	
	public void applyForce(Vec2 f) {}
	
	public void accelerate(Vec2 a) {}
	
	public void move() {
	}
}