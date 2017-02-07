package com.capthed.abyss.component;

import com.capthed.abyss.Log;
import com.capthed.abyss.gfx.Render;
import com.capthed.abyss.gfx.RenderLayer;
import com.capthed.abyss.gfx.Shape;
import com.capthed.abyss.math.Vec2;
import com.capthed.abyss.phys.World;
import com.capthed.abyss.phys.collision.Collider;

/**
 * Subclass of GComponent. Can render a <code>Shape</code> to the screen and can have a <code>Collider</code> or a <code>RigidBody</code>
 */
public abstract class GObject extends GComponent {

	private boolean isRenderable = true;
	private boolean isCollidable = true;
	
	protected Shape shape;
	protected Collider collider;
	protected RenderLayer renlay;
	
	/** GObject represented by the given shape on the screen. Has no collider*/
	public GObject(Shape s) {
		this.shape = s;
		
		isCollidable = false;
	}

	/** GObject represented by the given collider. Has no shape to display on screen*/
	public GObject(Collider c) {
		this.collider = c;
		
		isRenderable = false;
	}
	
	/** GObject with the given shape and collider*/
	public GObject(Shape s, Collider c) {
		this.shape = s;
		this.collider = c;
	}
	
	/** Renders the GObject shape if it has one*/
	public void render() {
		if (isRenderable && isActive())
			Render.shape(shape);
	}

	/** @return If the object has a shape instance or not*/
	public boolean isRenderable() { return isRenderable; }
	
	/** @return If the object has a collider instance or not. <b>***WARNING***</b> Called by the engine, not the user*/
	public boolean isCollidable() { return isCollidable; }
	
	/** Called when this object collides with another object. The argument is the object it collided with.*/
	public void collided(GObject object) {}
	
	/** Moves the GObject regardless of physics*/
	public void move(Vec2 delta) {
		if (shape != null) shape.move(delta);
		if (collider != null) collider.move(delta);
 	}
	
	/** Try to move the GObject respectfully to colliders/physics*/
	public void tryMove(Vec2 delta) {
		if(!isCollidable()) {
			Log.warn("Non-collidable in <tryMove>");
			
			return;
		}
		
		if (World.isCollidingWithWorld(this)) return;
		
		Vec2 dir = new Vec2(delta).normalize().mult(-.1f);
		this.move(delta);
		while(World.isCollidingWithWorld(this)) {
			this.move(dir);
		} 
	}
	
	/** Set this GObjects render layer*/
	public void setRenderLayer(RenderLayer rl) { renlay = rl; }
	
	/** @return This objects render layer*/
	public RenderLayer getRenderLayer() { return renlay; }
	
	/** @return The shape that represents the object on screen. Can be null*/
	public Shape getShape() { return shape; }
	
	/** @return The collider of the object. Can be null */
	public Collider getCollider() { return collider; }
}
