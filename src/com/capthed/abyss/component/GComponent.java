package com.capthed.abyss.component;

import java.util.ArrayList;
import java.util.HashMap;

import com.capthed.abyss.Log;

/**
 * The top of the component hierarchy in the engine. A GComponent can be updated and initialised, but not renderd 
 */
public abstract class GComponent {

	private static ArrayList<GComponent> gcs = new ArrayList<GComponent>();				// stores all GComponents created
	private static HashMap<Integer, Integer> idKey = new HashMap<Integer, Integer>();	// stores (id, index) pairs
	
	// the current id, used to keep track of what id it will assign to the next GC
	private static int currID = 0;

	private boolean inited = false;
	
	protected int id;
	protected boolean active = true;
	
	/** Must be called by every subclass*/
	public GComponent() {
		id = currID++;
		
		gcs.add(this);
		idKey.put(id, gcs.size() - 1);
	}
	
	/** @return The unique ID of this GComponent*/
	public int getID() { return id; }
	
	/** @return The GComponent with the given ID*/
	public static GComponent getByID(int i) { 
		if (!idKey.containsKey(i))
			Log.err("No GComponent with such ID: " + i, 1);
		
		if (gcs.size() <= i)
			Log.err("No GComponent with such ID(overflow): " + i, 1);
		
		return gcs.get(idKey.get(i));
	}
 
	/** @return True if there is a component with this id, false otherwise*/
	public static boolean hasID(int i) {
		return idKey.containsKey(i);
	}
	
	/** Called once for every component if it is active. */
	public void init() {}
	
	/** Called once every frame if the component is active */
	public void update() {}
	
	/** Set the value of active to this GComponent. Components that are innactive will not be rendered, initiated or updated.*/
	public void setActive(boolean b) { active = b; }
	
	/** @return If this GComponent is active or not*/
	public boolean isActive() { return active; } 
	
	/** Engine internal. Do not touch*/
	public void setInited(boolean b) { inited = b; }
	
	/** Engine internal. Do not touch*/
	public boolean isInited() { return inited; }
	
	/** @return The name of the GComponent. Every component must have one :-)*/
	public abstract String getName();
	
	/** @return An ArrayList of all GConmpinent*/
	public static ArrayList<GComponent> getGCs() { return gcs; }
	
	public String toString() { return "GC(" + id + ")" + " " + getName(); }
}
