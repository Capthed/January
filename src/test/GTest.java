package test;
import com.capthed.abyss.component.GObject;
import com.capthed.abyss.gfx.Shape;


public class GTest extends GObject {

	public GTest(Shape s) {
		super(s);
	}

	@Override
	public String getName() {
		return "GTest";
	}

}
