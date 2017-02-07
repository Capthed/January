package com.capthed.abyss.gfx;

import java.util.ArrayList;

import com.capthed.abyss.Log;
import com.capthed.abyss.component.GComponent;
import com.capthed.abyss.component.GObject;

/**
 * The engine renders all layer, starting with the lowest (-) to the highest (+) 
 */
public class RenderLayer {

	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private int depth;
	private String name;
	
	/** 
	 * @arg depth The depth of the layer, + is towards the screen  
	 * @arg name The name of the layer
	 */
	public RenderLayer(int depth, String name) {
		this.depth = depth;
		this.name = name;
		
		LayerManager.add(this);
	}
	
	/** 
	 * @arg depth The depth of the layer, + is towards the screen  
	 * @arg name The name of the layer
	 * @arg ids The id's of the GObjects which you want to put in the layer (you can add more later)
	 */
	public RenderLayer(int depth, String name,  ArrayList<Integer> ids) {
		this.depth = depth;
		this.name = name;
		
		set(ids);
		
		LayerManager.add(this);
	}
	
	/** Add a GObject to the layer via its id*/
	public void add(int id) {
		if(!GComponent.hasID(id))
			Log.err("No GObject with such id in layer "+ name + "[id=" + id + "]", 1);
		
		ids.add(id);
		((GObject)GComponent.getByID(id)).setRenderLayer(this);
	}
	
	/** Remove a GObject from the layer via its id */
	public void remove(int id) {
		for (int i = 0; i < ids.size(); i++)
			if (ids.get(i) == id) ids.remove(i);
		
		if (GComponent.hasID(id))
			((GObject)GComponent.getByID(id)).setRenderLayer(null);
	}
	
	/** Renders all the GObjects in the layer*/
	void render() {
		for (int i : ids) {
			if (GComponent.getByID(i) == null)
				Log.err("Null GComponent in layer " + name + "[id=" + i +"]", 1);
			else if (GComponent.getByID(i).isActive()) 
				((GObject) GComponent.getByID(i)).render();
		}
	}
	
	/** @return The name of the layer*/
	public String getName() { return name; }
	
	/** Set the depth of the layer*/
	public void setDepth(int d) { depth = d; }
	
	/** @return The depth of the layer*/
	public int getDepth() { return depth; }
	
	/** Remove all the previous GObjects from the layer and add these*/
	public void set(ArrayList<Integer> ids) {
		for (int i : ids) {
			add(i);
		}
	}
	
	/** @return The id's of all the GObjects in the layer*/
	public ArrayList<Integer> getObjects() { return ids; }
}
