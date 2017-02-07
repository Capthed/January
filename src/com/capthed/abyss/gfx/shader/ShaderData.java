package com.capthed.abyss.gfx.shader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.capthed.abyss.Log;

public class ShaderData {

	/**
	 * Reads the shader file and returns it's code
	 * 
	 *  @param path The path to the shader
	 * 
	 *  @return The code of the shader
	 */
	@SuppressWarnings("deprecation")
	public static String read(String path) {
		FileInputStream ins = null;
		try {
			ins = new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			Log.err("Path to shader invalid", 1);
		}
		BufferedInputStream buff = new BufferedInputStream(ins);
		DataInputStream in = new DataInputStream(buff);
		
		String res = "";
		String temp = "";
		
		do {
			res += temp + "\n";
			
			try {
				temp = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (temp != null);
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
