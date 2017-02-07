package com.capthed.abyss;

public class Log {

	public static void log(String s) {
		System.out.println(s);
		System.out.flush();
	}
	
	public static void err(String er, int exCode) {
		System.err.println("#<" + exCode + "> " + er);
		System.err.flush();
		
		if (exCode == 1) {
			Abyss.stop();
			System.exit(1);
		}
	}
	
	public static void warn(String w) {
		System.out.println("#<w> " + w);
		System.out.flush();
	}
}
