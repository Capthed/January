package com.capthed.abyss.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.lwjgl.BufferUtils;

import com.capthed.abyss.Log;

public class Texture {

	private ByteBuffer image;
	private int w, h, comp;
	private int texID;
	private String path;
	private boolean loaded = false;
	
	/** Init a new texture with an image at the given location. This does not load the texture*/
	public Texture (String path) {
		this.path = path;
	}
	
	/** Loads the image to memory. Should be called from the engine because of the OpenGL context*/
	public void load() {
		if (loaded) {
			Log.warn("Already loaded texture " + path);
			return;
		}
		
		ByteBuffer imageBuffer = null;
		try {
			imageBuffer = ioResourceToByteBuffer(path, 8 * 1024);
		} catch (IOException e) {
			Log.err("No texture at path " + path, 1);
		}

		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);

		image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);

		this.w = w.get(0);
		this.h = h.get(0);
		this.comp = comp.get(0);
		
		texID = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, texID);
		
		if ( this.comp == 3 ){
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, this.w, this.h, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
		} else {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.w, this.h, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
		}

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		loaded = true;
	}
	
	/** @return The pointer to the memory location. Used for binding*/
	public int getID() { return texID; }
	
	// copied
	private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
		ByteBuffer buffer;

		File file = new File(resource);
		if ( file.isFile() ) {
			FileInputStream fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);

			while ( fc.read(buffer) != -1 ) ;

			fc.close();
			fis.close();
		} else {
			buffer = BufferUtils.createByteBuffer(bufferSize);

			InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if ( source == null )
				throw new FileNotFoundException(resource);

			try {
				ReadableByteChannel rbc = Channels.newChannel(source);
				try {
					while ( true ) {
						int bytes = rbc.read(buffer);
						if ( bytes == -1 )
							break;
						if ( buffer.remaining() == 0 )
							buffer = resizeBuffer(buffer, buffer.capacity() * 2);
					}
				} finally {
					rbc.close();
				}
			} finally {
				source.close();
			}
		}

		buffer.flip();
		return buffer;
	}
	
	// copied
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}
}
