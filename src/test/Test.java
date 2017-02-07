package test;
import com.capthed.abyss.Abyss;
import com.capthed.abyss.Game;
import com.capthed.abyss.gfx.Display;
import com.capthed.abyss.gfx.RenderLayer;
import com.capthed.abyss.gfx.Shape;
import com.capthed.abyss.gfx.Texture;
import com.capthed.abyss.math.Vec2;

public class Test implements Game {
	
	public static void main(String[] args) {
		Abyss.create(new Test());
		Abyss.start();
	}

	@Override
	public void init() {
		RenderLayer l1 = new RenderLayer(6, "Prvi");
		RenderLayer l2 = new RenderLayer(10, "Drugi");
		
		Texture tex1 = new Texture("enres//tnt.png");
		
		Shape s1 = new Shape(new Vec2[]{
				new Vec2(100, 100),
				new Vec2(100, 200),
				new Vec2(200, 200),
				new Vec2(750, 100)
		});
		
		Shape s2 = new Shape(new Vec2[]{
				new Vec2(150, 150),
				new Vec2(150, 250),
				new Vec2(250, 250),
				new Vec2(250, 150)
		}, new Vec2[] {
				new Vec2(1, 1),
				new Vec2(1, 0),
				new Vec2(0, 0),
				new Vec2(0, 1)
		}, tex1);
		
		s1.setColor(new float[] {1, 0, 0, 0.5f, 0, 1, 0, 0.5f,0, 0,1, 0.5f, 0, 0,1, 0.5f});
		
		GTest t1 = new GTest(s1);
		GTest t2 = new GTest(s2);
		l1.add(t1.getID());
		l2.add(t2.getID());
	}

	@Override
	public void initDisplay() {
		Display.setMonitor(1);
		Display.setFullscreen(false);
		Display.create(800, 600, "Isus", true);
	}

	@Override
	public void constUpdate() {
		
	}

	@Override
	public void constRender() {

		
	}

	@Override
	public void closing() {
		
	}

	@Override
	public void closed() {
		
	}
}
