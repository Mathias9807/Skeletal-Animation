package skeletal.level;

import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.util.vector.*;

public class Player {
	
	public Vector3f pos, rot;
	public double speed;
	
	public Bone skeleton;

	public Player() {
		pos 	= new Vector3f(0, 1.5f, 0);
		rot 	= new Vector3f();
		speed 	= 1;
		
		skeleton = new Bone(new Vector3f(pos), new Quaternion(), 0, 0.15f);
//		skeleton.extendSkeleton(new Quaternion(0, -1, 0, 0), 1, 0.08f);
	}
	
	public void tick(double delta) {
		if (isKeyDown(KEY_W)) 		pos.z -= speed * delta;
		if (isKeyDown(KEY_A)) 		pos.x -= speed * delta;
		if (isKeyDown(KEY_S)) 		pos.z += speed * delta;
		if (isKeyDown(KEY_D)) 		pos.x += speed * delta;
		if (isKeyDown(KEY_SPACE)) 	pos.y += speed * delta;
		if (isKeyDown(KEY_LSHIFT)) 	pos.y -= speed * delta;
		
		skeleton.start.set(pos);
	}

}
