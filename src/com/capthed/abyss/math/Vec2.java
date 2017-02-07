package com.capthed.abyss.math;

public class Vec2 {

	private float x, y;
	
	/** Copies the given vector to a new object */
	public Vec2(Vec2 v) {
		this.x = v.x();
		this.y = v.y();
	}
	
	/** Create vector with x and y coordinates*/
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/** @return x coordinate*/
	public float x() {
		return x;
	}

	/** Set the x coordinate*/
	public void setX(float x) {
		this.x = x;
	}

	/** @return y coordinate*/
	public float y() {
		return y;
	}

	/** Set the y coordinate */
	public void setY(float y) {
		this.y = y;
	}
	
	/** 
	 * Add the given vector to this vector and save the changes in this vector
	 * 
	 * @return This changed vector
	 */
	public Vec2 add(Vec2 v) {
		this.x += v.x();
		this.y += v.y();
		
		return this;
	}
	
	/** @return The sum of the two vectors*/
	public static Vec2 add(Vec2 v1, Vec2 v2) {
		return new Vec2(v1.x() + v2.x(), v1.y() + v2.y());
	}
	
	/** 
	 * Subtract the given vector from this vector and save the changes in this vector
	 * 
	 * @return This changed vector
	 */
	public Vec2 sub(Vec2 v) {
		this.x -= v.x();
		this.y -= v.y();
		
		return this;
	}
	
	/** @return The difference between the two vectors */
	public static Vec2 sub(Vec2 v1, Vec2 v2) {
		return new Vec2(v1.x() - v2.x(), v1.y() - v2.y());
	}
	
	/** 
	 * Multiply the given vector with this vector and save the changes in this vector
	 * 
	 * @return This changed vector
	 */
	public Vec2 mult(Vec2 v) {
		this.x *= v.x();
		this.y *= v.y();
		
		return this;
	}
	
	/** 
	 * Multiply each coordinate of this vector with the given float and save the change in this vector
	 * 
	 * @return This changed vector
	 */
	public Vec2 mult(float s) {
		this.x *= s;
		this.y *= s;
		
		return this;
	}
	
	/** @return The product of the two vectors*/
	public static Vec2 mult(Vec2 v1, Vec2 v2) {
		return new Vec2(v1.x() * v2.x(), v1.y() * v2.y());
	}
	
	/** 
	 * Divide the given vector with this vector and save the changes in this vector
	 * 
	 * @return This changed vector
	 */
	public Vec2 div(Vec2 v) {
		this.x /= v.x();
		this.y /= v.y();
		
		return this;
	}
	
	/** 
	 * Divide each coordinate of this vector with the given float and save the change in this vector
	 * 
	 * @return This changed vector
	 */
	public Vec2 div(float s) {
		this.x /= s;
		this.y /= s;
		
		return this;
	}
	
	/** @return The quotient of the two vectors*/
	public static Vec2 div(Vec2 v1, Vec2 v2) {
		return new Vec2(v1.x() / v2.x(), v1.y() / v2.y());
	}
	
	/**
	 * Create the dot product of this vector and the given vector, stores the save in this vector
	 * 
	 * @return This changed vector
	 */
	public float dot(Vec2 v) {
		return (x * v.x()) + (y * v.y()); 
	}
	
	/** @return The dot product of the two vectors*/
	public static float dot (Vec2 v1, Vec2 v2) {
		return (v1.x() * v2.x()) + (v1.y() * v2.y());
	}
	
	/** @return The squared length of the vector*/
	public float sqrLength() {
		return (x*x) + (y*y);
	}
	
	/** @return The length of the vector. For comparison <code>sqrLength</code> is recomended*/
	public float length() {
		return (float)Math.sqrt(sqrLength());
	}
	
	/** 
	 * Normalize the vector and save the changes in it
	 * 
	 * @return This changed vector
	 */
	public Vec2 normalize() {
		float l = length();
		
		this.x /= l;
		this.y /= l;
		
		return this;
	}
	
	/** @return The normalized version of the given vector (creates new instance)*/
	public static Vec2 normalize(Vec2 v) {
		return new Vec2(v.x() / v.length(), v.y() / v.length());
	}

	/**
	 * Makes the coordinates of the vector positive and stores it in this vector 
	 * 
	 * @return This changed vector
	 */
	public Vec2 abs() {
		x = Math.abs(x);
		y = Math.abs(y);
		
		return this;
	}
	
	/** @return The absolute value of the vector (creates new instance)*/
	public static Vec2 abs(Vec2 v) {
		return new Vec2(Math.abs(v.x()), Math.abs(v.y()));
	}
	
	/** @return True if this vector and the given vector are equal*/
	public boolean equals(Vec2 v) {
		if (this.x == v.x() && this.y == v.y())
			return true;
		
		return false;
	}
	
	/** @return True if the difference between the vectors is <= the set error margin. */
	public boolean equals(Vec2 v, float err) {
		if (Math.abs(this.x - v.x()) <= err && Math.abs(this.y - v.y()) <= err)
			return true;
		
		return false;
	}
	
	/** 
	 * @param pos The position of the quad
	 * @param size The size of the quad
	 * 
	 * @return True if this vector is within the quad set by the arguments
	 */
	public boolean intersects(Vec2 pos, Vec2 size) {
		return Vec2.intersects(this, pos, size);
	}
	
	/**
	 * @param point The point we are checking 
	 * @param pos The position of the quad
	 * @param size The size of the quad
	 * 
	 * @return True if the point is within the quad given by the remaning two vector arguments
	 */
	public static boolean intersects(Vec2 point, Vec2 pos, Vec2 size) {
		Vec2 add = Vec2.add(pos, size);
		if (size.x() <= 0 && size.y() <= 0) {
			if (point.x <= pos.x() && point.x >= add.x() && point.y <= pos.y() && point.y >= add.y())
				return true;
			else
				return false;
		}
		else if (size.x() <= 0 && size.y() > 0) {
			if (point.x <= pos.x() && point.x >= add.x() && point.y >= pos.y() && point.y <= add.y()) {
				return true;
			}
			else
				return false;
		}
		else if (size.x() > 0 && size.y() <= 0) {
			if (point.x >= pos.x() && point.x <= add.x() && point.y <= pos.y() && point.y >= add.y()) {
				return true;
			}
			else
				return false;
		}
		else if (point.x >= pos.x() && point.x <= add.x() && point.y >= pos.y() && point.y <= add.y())
			return true;
		else 
			return false;
	}
	
	/** Kurac picka sisa pero*/
	public String toString() { return "(x: " + x + ", y: " + y + ")"; }
}
