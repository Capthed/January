package com.capthed.abyss.gfx;

import com.capthed.abyss.Timer;

public class Animation {

	private Texture[] texs;
	private Type t;
	private long dTime;
	private long lastTime = -1;
	private int currTex = 0;
	private int maxTex;
	private boolean var0 = false;
	
	/**
	 * <b>Types of animation:</b>
	 *  <br><code>ONCE</code> - iterate through textures once and stay on the last texture (1 -> 2 -> 3)
	 *  <br><code>LOOP</code> - iterate through textures, return to the begining and iterate again (1 -> 2 -> 3 -> 1 -> 2 ->...)
	 * 	<br><code>BOUNCE_LOOP</code> - iterate through textures, iterate again from behind and from front agaim (1 -> 2 -> 3 -> 2 -> 1 -> 2 -> ...)
	 */
	public enum Type {
		ONCE, LOOP, BOUNCE_LOOP;
	}
	
	/**
	 *  @param texs The array of Textures to be iterated through
	 *  @param dTime The time in ms between each iteration. 
	 *  @param t The type of animation
	 */
	public Animation(Texture[] texs, long dTime, Type t) {
		this.t = t;
		this.texs = texs;
		this.dTime = dTime;
		
		maxTex = texs.length;
	}

	/** @param anim Copies the info from this animation and creates a new identical*/
	public Animation(Animation anim) {
		this(anim.getTexs(), anim.getdTime(), anim.getType());
	}

	/** 
	 * Calculates the texture the animation has this frame
	 * 
	 * @return Current texture
	 */
	public Texture getTexture() {
		if (lastTime == -1) lastTime = Timer.getTimeMS();
		
		long currTime = Timer.getTimeMS();
		
		if (currTime - lastTime >= dTime) {
			lastTime = currTime;
			
			if (t == Type.ONCE && currTex < maxTex) {
				currTex++;
			} 
			else if (t == Type.LOOP) {
				if (currTex == maxTex - 1) currTex = 0;
				else currTex++;
			}
			else if (t == Type.BOUNCE_LOOP) {
				if (currTex == 0) var0 = false;
				else if (currTex == maxTex - 1) var0 = true;
				
				if (!var0) currTex++;
				else currTex--;
			}
		}
		
		return texs[currTex];
	}
	
	/** @return Time between each texture iteration in ms*/
	public long getdTime() {
		return dTime;
	}
	
	/** Set the time between texture iterations in ms*/
	public void setdTime(long t) {
		this.dTime = t;
	}

	/** @return The type of the animation*/
	public Type getType() {
		return t;
	}

	/** Set the animation type*/
	public void setType(Type t) {
		this.t = t;
	}

	/** @return The texture array*/
	public Texture[] getTexs() {
		return texs;
	}
}
