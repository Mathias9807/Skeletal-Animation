package skeletal.level;

import org.lwjgl.util.vector.*;

import skeletal.graphics.*;

public class Level {
	
	public static Player player;
	public static Dummy dummy;
	
	public static Vector3f camPos, camRot;
	
	public static void init() {
		player = new Player();
		dummy = new Dummy(new Vector3f(0, 1.03f, -5));
		
		camPos = new Vector3f();
		camRot = new Vector3f();
	}
	
	public static void tick(double delta) {
		player.tick(delta);
		
		Matrix4f matView = ((BasicRenderer) Graphics.rEngine).matView;
		matView.setIdentity();
		matView.translate(new Vector3f(0, 0, -3.5f));
//		matView.rotate(0.5f, new Vector3f(1, 0, 0));
		matView.rotate(player.rot.x, new Vector3f(1, 0, 0));
		matView.rotate(player.rot.y + 1.5f, new Vector3f(0, 1, 0));
		matView.translate(player.pos.negate(null));
	}
	
}
