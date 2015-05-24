package skeletal.level;

import org.lwjgl.util.vector.Vector3f;

public class Level {
	
	public static Player player;
	
	public static Vector3f camPos, camRot;
	
	public static void init() {
		player = new Player();
		
		camPos = new Vector3f();
		camRot = new Vector3f();
	}
	
	public static void tick(double delta) {
		player.tick(delta);
		
		camPos.set(player.pos);
		camPos.z += 1;
		
		camRot = player.rot;
	}
	
}
