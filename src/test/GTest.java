package test;
import com.capthed.abyss.component.GObject;
import com.capthed.abyss.gfx.Shape;
import com.capthed.abyss.phys.collision.Collider;


public class GTest extends GObject {

	public GTest(Shape s, Collider c) {
		super(s, c);
	}

	@Override
	public String getName() {
		return "GTest";
	}

	public void collided(GObject g) {
	}
}
