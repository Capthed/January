package com.capthed.abyss.gfx;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.capthed.abyss.Log;
import com.capthed.abyss.math.Vec2;

/**
 * Represents the polgyon to be rendered to the screen. Contains the vertices of the
 * polygon and its colors
 */
public class Shape {

	private Vec2[] vertices;
	private Vec2[] texCoords;
	private Texture tex;
	private Animation anim;
	private float[] colors;
	private int numberOfVert;
	private int vertexSize;
	private FloatBuffer buf;
	private FloatBuffer colorBuf;
	private FloatBuffer texBuf;
	private int vbo;
	private int colorVbo;
	private int texVbo;
	private int colorDepth = 4; // the ammount of bits per color (4 = rgba)
	
	/**
	 * @param vertices The vertices of the object (polygon manner) 
	 */
	public Shape(Vec2[] vertecies) {
		this.vertices = vertecies;
		
		numberOfVert = vertices.length;
		
		vertexSize = 2;
		
		setDefColor();
		createFloatBuffer();
		
		initArrayBuffer();
	}
	
	// TODO
	public Shape(Vec2[] vertices, Vec2[] texCoords, Texture tex) {
		this(vertices);
		this.texCoords = texCoords;
		this.tex = tex;

		this.tex.load();
		
		setDefColor();
		createTexCoordBuffer();
		initTexCoordBuffer();
	}
	
	// TODO
	public Shape(Vec2[] vertices, Vec2[] texCoords, Animation anim) {
		this(vertices);
		this.texCoords = texCoords;
		this.anim = anim;

		for (Texture t : anim.getTexs())
			t.load();
			
		setDefColor();
		createTexCoordBuffer();
		initTexCoordBuffer();
	}
	
	// sets the default color to white
	private void setDefColor() {
		// default color
		float[] col = new float[numberOfVert * colorDepth];
		for (int i = 0; i < vertices.length * colorDepth; i++)
			col[i] = 1;
		setColor(col);
	}
	
	// buffer object of the tex coordinates
	private void createTexCoordBuffer() {
		texBuf = BufferUtils.createFloatBuffer(2 * numberOfVert); 
		texBuf.put(toFloatArray(texCoords));
		texBuf.flip();
	}
	
	// create the array object for tex coords
	private void initTexCoordBuffer() {
		texVbo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, texVbo);
		glBufferData(GL_ARRAY_BUFFER, texBuf, GL_STREAM_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	// buffer object of the vertices
	private void createFloatBuffer() {
		buf = BufferUtils.createFloatBuffer(vertexSize * numberOfVert); 
		buf.put(toFloatArray(vertices));
		buf.flip();
	}
	
	// init the vbo and set up the array buffer
	private void initArrayBuffer() {
		// create the buffer object
		vbo = glGenBuffers();
		
		// bind and setup the buffer
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STREAM_DRAW);
		
		// unbind buffer
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	// init the color vbo 
	private void initColorArrayBuffer() {
		colorVbo = glGenBuffers();
		
		glBindBuffer(GL_ARRAY_BUFFER, colorVbo);
		glBufferData(GL_ARRAY_BUFFER, colorBuf, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	// creates the default color buffer
	private void createColorBuffer() {
		colorBuf = BufferUtils.createFloatBuffer(colorDepth * numberOfVert); 
		colorBuf.put(colors);
		colorBuf.flip();
	}
	
	/** @return A float array of the Vec2 array elements*/
	private float[] toFloatArray(Vec2[] v) {
		float[] fa = new float[v.length * 2];
		
		for (int i = 0; i < v.length * 2; i+=2) {
			fa[i] = v[i / 2].x();
			fa[i + 1] = v[i / 2].y();
		}
		
		return fa;
	}
	
	/** @return The vertices of the polygon (new instance)*/
	public Vec2[] getVertices() { 
		Vec2[] n = new Vec2[vertices.length];
		for (int i = 0; i < n.length; i++)
			n[i] = new Vec2(vertices[i]);
		
		return n; 
	}
	
	/** @return The number of vertices the polygon has*/
	public int getNumberOfVertices() { return numberOfVert; }
	
	/** @return The size of each vertex (2 in 2D space)*/
	public int getVertexSize() { return vertexSize; } 
	
	/** @return Ready-to-go FloatBuffer object filled with the vertices info */
	public FloatBuffer getFloatBuffer() { return buf; }

	/** @return Ready-to-go FloatBuffer object filled with the colors for each vertex*/
	public FloatBuffer getColorFloatBuffer() { return colorBuf; }
	
	/** Set the color of the current shape. The ammount of values per vertex depends on color depth.*/
	public void setColor(float[] cols) {
		if (cols.length > numberOfVert * colorDepth)
			Log.err("Invalid <setColor> call: too many colors", 1);
		
		colors = cols;
		
		createColorBuffer();
		initColorArrayBuffer();
	}
	
	/** @return The Vertex Buffer Object of this shape*/
	public int getVBO() { return vbo;}
	
	/** @return The Vertex Buffer Object of the color for this shape*/
	public int getColorVBO() { return colorVbo; }
	
	/** @return The Vertex Buffer Object of the texture coordinates*/
	public int getTexVBO() { return texVbo; }
	
	/** 
	 * Sets the color depth for this shape. Color depth says how many bytes each color will hold 
	 * (eg. 3 = rgb, 4 = rgba). <br>
	 * <b>*** WARNING *** </b> Make sure you change the shader if you are changing the color depth. <br>Default is 4.
	 */
	public void setColorDepth(int i) {
		if (i <= 0) {
			Log.err("Color depth can't be less than or equal to zero", 0);
			return;
		}
		
		colorDepth = i;
	}

	/** @return The color depth of this shape. Color depth is the ammount of bytes per color. */
	public int getColorDepth() { return colorDepth; }
	
	/** @return The texture of this shape. Can be null*/
	public Texture getTexture() { 
		if (anim != null) return anim.getTexture();
		else return tex; 
	}
	
	/** 
	 * Move the shape by the given ammount
	 * 
	 * @param delta The ammount for which to move the shape.
	 */
	public void move(Vec2 delta) {
		for (int i = 0; i < numberOfVert; i++)
			vertices[i].add(delta);
		
		createFloatBuffer();
		initArrayBuffer();
		
	}
	
	/** Called when detroying the object to delete the buffers. */
	public void destroy() {
		glDeleteBuffers(vbo);
		glDeleteBuffers(colorVbo);
	}
}
