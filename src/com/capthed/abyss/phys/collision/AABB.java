package com.capthed.abyss.phys.collision;

import com.capthed.abyss.math.Vec2;

/**
 * Axis aligned bounding boxes serve to check if two colliders are close before calculating if they are intercepting.
 * Each collider has its own AABB which is calculated internally.
 */
public class AABB {

	private Vec2 pos;
	private Vec2 size;
	private Vec2[] vertices; // clockwise starting from bottom left
	
	/**
	 * @param pos The bottom left position of the quad
	 * @param size The dimensins of the quad 
	 */
	public AABB(Vec2 pos, Vec2 size) {
		this.pos = pos;
		this.size = size;
		
		vertices = new Vec2[4];
		
		// make all the vertices of the AABB
		vertices[0] = pos;
		vertices[1] = new Vec2(pos.x(), pos.y() + size.y());
		vertices[2] = Vec2.add(pos, size);
		vertices[3] = new Vec2(pos.x() + size.x(), pos.y());
	}
	
	/**
	 * @return True if this AABB is colliding with the given AABB, false otherwise
	 * */
	public boolean colliding(AABB aabb) {
		Vec2[] vert2 = aabb.getVertices();
		
		// check if this AABB is inside the given
		for (int i = 0; i < vert2.length; i++) {
			Vec2 v = vert2[i];
			
			if (v.x() >= vertices[0].x() && v.x() <= vertices[3].x() && v.y() >= vertices[0].y() && v.y() <= vertices[1].y())
				return true;
		}
		
		// check if the given AABB is inside this one
		for (int i = 0; i < vert2.length; i++) {
			Vec2 v = vertices[i];
			
			if (v.x() >= vert2[0].x() && v.x() <= vert2[3].x() && v.y() >= vert2[0].y() && v.y() <= vert2[1].y())
				return true;
		}
		
		return false;
	}
	
	/** Move the quad by the given Vec2*/
	public void move(Vec2 delta) {
		pos.add(delta);
		
		// make all the vertices of the AABB
		vertices[0] = pos;
		vertices[1] = new Vec2(pos.x(), pos.y() + size.y());
		vertices[2] = Vec2.add(pos, size);
		vertices[3] = new Vec2(pos.x() + size.x(), pos.y());
	}
	
	/** @return The vertices of the AABB (new instance)*/
	public Vec2[] getVertices() {
		Vec2[] n = new Vec2[vertices.length];
		for (int i = 0; i < n.length; i++)
			n[i] = new Vec2(vertices[i]);
		
		return n;
	}
}
