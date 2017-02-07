package com.capthed.abyss.gfx;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class LayerManager {

	private static ArrayList<RenderLayer> layers = new ArrayList<RenderLayer>();
	
	public static void add(RenderLayer rl) {
		layers.add(rl);
		
		layers.sort(Comparator.comparingInt(RenderLayer::getDepth));
	}
	
	public static void renderAll() {
		for (RenderLayer rl : layers)
			rl.render();
	}
}
