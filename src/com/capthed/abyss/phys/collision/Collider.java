package com.capthed.abyss.phys.collision;

import com.capthed.abyss.gfx.Render;
import com.capthed.abyss.math.Vec2;

/**
 * Represents a polygon around the game object which collides with other colliders.
 * <br>
 * 
 * <b>**WARNING**</b> Only works for convex shapes
 */
public class Collider {

	private Vec2[] vertices;
	private Vec2[] sepAxis; // the axis to check in the intersection
	private Vec2[] normSepAxis; // normalized axis to check
	private AABB aabb;
	private boolean active = true;
	
	/**
	 * @param vertices The vertices that determine the location, shape and size of the collider 
	 */
	public Collider(Vec2[] vertices) {
		this.vertices = vertices;
		
		sepAxis = new Vec2[vertices.length];
		normSepAxis = new Vec2[vertices.length];
		
		calcSepAxis();
		
		aabb = calcAABB();
	}
	
	/** Move the collider by the ammount designated by the Vec2 argument. */
	public void move(Vec2 delta) {
		for (int i = 0; i < vertices.length; i++)
			vertices[i].add(delta);
		
		aabb.move(delta);
		
		calcSepAxis();
	}
	
	/** @return The vertices of the collider (new instance) */
	public Vec2[] getVertices() { 
		Vec2[] n = new Vec2[vertices.length];
		for (int i = 0; i < n.length; i++)
			n[i] = new Vec2(vertices[i]);
		
		return n; 
	}
	
	// calculates the separating axis of this collider, used for collisions via SAT method
	private void calcSepAxis() {
		for (int i = 0; i < vertices.length; i++) {
			// current vertex
			Vec2 curr = vertices[i];
			
			// the next vertex in line
			Vec2 next = vertices[ i + 1 == vertices.length ? 0 : i + 1];
			
			// edge vector
			Vec2 edge = Vec2.sub(curr, next);
			
			// get the perpendicurlar vector to the edge (the normal)
			Vec2 perp = new Vec2(edge.y(), -edge.x());
			
			sepAxis[i] = perp;
			normSepAxis[i] = Vec2.normalize(perp);
		}
	}
	
	// calculated the AABB for this collider. It doe not have to be updated because the AABB <move> method updates it
	private AABB calcAABB() {
		float minx = vertices[0].x();
		float miny = vertices[0].y();
		float maxx = minx;
		float maxy = miny;
		
		for (int i = 0; i < vertices.length; i++) {
			float cx = vertices[i].x();
			float cy = vertices[i].y();
			
			if (cx < minx) minx = cx;
			else if (cx > maxx) maxx = cx;
			
			if (cy < miny) miny = cy;
			else if (cy > maxy) maxy = cy;
		}
		
		return new AABB(new Vec2(minx, miny), new Vec2(maxx - minx, maxy - miny));
	}

	/** @return The AABB of this collider */
	public AABB getAABB() { return aabb; }
	
	/** @return The axis for which to check if the collider is colliding with another object. */
	public Vec2[] getSepAxis() { return sepAxis; }
	
	/** @return The normalized version of <code>getSepAxis()</code> */
	public Vec2[] getNormSepAxis() { return normSepAxis; }
	
	/** 
	 * Checks if this collider is intersecting the given collider
	 * 
	 *  @param c The collider with which intersection is being checked
	 *  @return True if there is an intersection, false otherwise
	 */
	public boolean colliding(Collider c) {
		if (!isActive() || !c.isActive()) return false;
		
		// c1 axis
		for(int i = 0; i < sepAxis.length; i++) {
			
			// c1 axis and c1 vert
			float min1 = Vec2.dot(normSepAxis[i], vertices[0]);
			float max1 = min1;
			for (int j = 1; j < vertices.length; j++) {
				
				float temp = Vec2.dot(normSepAxis[i], vertices[j]);
				
				if (temp < min1) min1 = temp;
				else if (temp > max1) max1 = temp;
			}
			
			
			// c1 axis and c2 vert
			float min2 = Vec2.dot(normSepAxis[i], c.getVertices()[0]);
			float max2 = min2;
			for (int j = 1; j < c.getVertices().length; j++) {
				
				float temp = Vec2.dot(normSepAxis[i], c.getVertices()[j]);
				
				if (temp < min2) min2 = temp;
				else if (temp > max2) max2 = temp;
			}

			if (min1 >= max2 || max1 <= min2) return false;
		}
		
		
		// c2 axis
		for(int i = 0; i < c.getSepAxis().length; i++) {
			
			// c2 axis and c1 vert
			float min1 = Vec2.dot(c.getNormSepAxis()[i], vertices[0]);
			float max1 = min1;
			for (int j = 1; j < vertices.length; j++) {
				
				float temp = Vec2.dot(c.getNormSepAxis()[i], vertices[j]);
				
				if (temp < min1) min1 = temp;
				else if (temp > max1) max1 = temp;
			}
			
			
			// c2 axis and c2 vert
			float min2 = Vec2.dot(c.getNormSepAxis()[i], c.getVertices()[0]);
			float max2 = min2;
			for (int j = 1; j < c.getVertices().length; j++) {
				
				float temp = Vec2.dot(c.getNormSepAxis()[i], c.getVertices()[j]);
				
				if (temp < min2) min2 = temp;
				else if (temp > max2) max2 = temp;
			}

			if (min1 >= max2 || max1 <= min2) return false;
		}
		
		return true;
	}

	/** Renders the colliders and its AABB via <code>Render.debug</code>*/
	public void renderDebug() {
		Render.debug(vertices, true);
		Render.debug(aabb.getVertices(), false);
	}
	
	/** Set if the collider is active or not. If it is active, it will partake in the physics*/
	public void setActive(boolean b) { active = b; }
	
	/** @return True if the collider is active, false otherwise*/
	public boolean isActive() { return active; }
}
