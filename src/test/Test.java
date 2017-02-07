package test;
import com.capthed.abyss.Abyss;
import com.capthed.abyss.Game;
import com.capthed.abyss.gfx.Animation;
import com.capthed.abyss.gfx.Animation.Type;
import com.capthed.abyss.gfx.Display;
import com.capthed.abyss.gfx.RenderLayer;
import com.capthed.abyss.gfx.Shape;
import com.capthed.abyss.gfx.Texture;
import com.capthed.abyss.input.Keyboard;
import com.capthed.abyss.input.Keys;
import com.capthed.abyss.math.Vec2;
import com.capthed.abyss.phys.collision.Collider;

public class Test implements Game {
	
	Collider c1, c2;
	GTest t1, t2;
	
	public static void main(String[] args) {
		Abyss.create(new Test());
		Abyss.start();
	}

	@Override
	public void init() {
		RenderLayer l1 = new RenderLayer(6, "Prvi");
		RenderLayer l2 = new RenderLayer(10, "Drugi");
		
		Texture tex1 = new Texture("enres//tnt.png");
		Texture tex2 = new Texture("enres//tnt2.png");
		Animation anim = new Animation(new Texture[]{tex1, tex2}, 500, Type.LOOP);
		
		Shape s1 = new Shape(new Vec2[]{
				new Vec2(100, 100),
				new Vec2(100, 200),
				new Vec2(200, 200),
				new Vec2(200, 100)
		});
		s1.move(new Vec2(200, 200));
		
		c1 = new Collider(s1.getVertices());
		
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
		}, anim);
		
		c2 = new Collider(s2.getVertices());
		
		s1.setColor(new float[] {1, 0, 0, 0.5f, 0, 1, 0, 0.5f,0, 0,1, 0.5f, 0, 0,1, 0.5f});
		
		t1 = new GTest(s1, c1);
		t2 = new GTest(s2, c2);
		l1.add(t1.getID());
		l2.add(t2.getID());
	}

	@Override
	public void initDisplay() {
		Display.setMonitor(1);
		Display.setFullscreen(false);
		Display.create(1280, 720, "Isus", true);
	}

	@Override
	public void constUpdate() {
		if (Keyboard.isKeyPressed(Keys.GLFW_KEY_ESCAPE))
			Abyss.stop();
		
		float speed  = 2;
		if (Keyboard.isKeyDown(Keys.GLFW_KEY_LEFT_SHIFT)) speed = 10;
		
		Vec2 delta = new Vec2(0, 0);
		if (Keyboard.isKeyDown(Keys.GLFW_KEY_W))
			delta.add(new Vec2(0, speed));
		if (Keyboard.isKeyDown(Keys.GLFW_KEY_S))
			delta.add(new Vec2(0, -speed));
		if (Keyboard.isKeyDown(Keys.GLFW_KEY_D))
			delta.add(new Vec2(speed, 0));
		if (Keyboard.isKeyDown(Keys.GLFW_KEY_A))
			delta.add(new Vec2(-speed, 0));
		
		if (!delta.equals(new Vec2(0, 0)))
			t1.tryMove(delta);
	}

	@Override
	public void constRender() {
		c1.renderDebug();
		c2.renderDebug();
	}

	@Override
	public void closing() {
		
	}

	@Override
	public void closed() {
		
	}
}
