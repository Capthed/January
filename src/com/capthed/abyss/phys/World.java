package com.capthed.abyss.phys;

import com.capthed.abyss.component.GComponent;
import com.capthed.abyss.component.GObject;
import com.capthed.abyss.phys.collision.Collider;

/**
 * Regulates the physics world with colliders and rigid bodies 
 */
public abstract class World {

	/** 
	 * Checks if the given GObject is colliding with any other collidable, active GObject
	 * 
	 *  @return True if it is colliding, false otherwise
	 */
	public static boolean isCollidingWithWorld(GObject go) {
		if (!go.isCollidable() || !go.isActive() || !go.getCollider().isActive()) return false;
		
		Collider c = go.getCollider();
		
		for (int i = 0; i < GComponent.getGCs().size(); i++) {
			if (GComponent.getGCs().get(i).getID() == go.getID()) continue;
			
			if (GComponent.getGCs().get(i) instanceof GObject) {
				GObject go1 = (GObject)GComponent.getGCs().get(i);
				
				if (!go1.isCollidable() || !go.isActive()) continue;
				if (!go1.getCollider().isActive()) continue;
				if (!go1.getCollider().getAABB().colliding(c.getAABB())) continue;
				
				if (c.colliding(go1.getCollider())) {
					go.collided(go1);
					go1.collided(go);
					return true;
				}
			}
		}
		
		return false;
	}
}
