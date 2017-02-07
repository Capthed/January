package com.capthed.abyss;

import java.util.HashMap;

public class Timer {

	private static long startTime = -1;
	
	// the current code for the timer
	private static int currCode = 0; 
	// the code and runtime of each timer
	private static HashMap<Integer, Long> timers = new HashMap<Integer, Long>();
	
	// starts the timer, called when the engine is started
	static void start() {
		startTime = System.currentTimeMillis();
	}
	
	/** @return Current time in milliseconds */
	public static long getTimeMS() {
		return System.currentTimeMillis();
	}
	
	/** @return Time in milliseconds since the engine has started*/
	public static long getTimeRunningMS() {
		return getTimeMS() - startTime;
	}
	
	/** 
	 * Starts a timer which will run from the moment this is called. Get the timer time from <code>getTimerMS(int code)</code>.
	 * Uses milliseconds.
	 * 
	 * @param code The unique code to access this timer in the future. Recomended to use <code>getUniqueTimerCode()</code>
	 */
	public static void startTimer(int code) {
		timers.put(code, getTimeMS());
	}
	
	/**
	 * @return The time since the given timer has started in milliseconds.
	 * 
	 * @param code The code to access the particular timer.
	 */
	public static long getTimerMS(int code) {
		return getTimeMS() - timers.get(code);
	}
	
	/** @return A unique code to use for the timer. */
	public static int getUniqueTimerCode() {
		return currCode++;
	}
}
