package skeletal.level;

import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.util.vector.*;

public class Player {
	
	public Vector3f pos, rot;
	public double speed, rotSpeed;
	
	public Bone skeleton;

	public Player() {
		pos 		= new Vector3f(0, 1.5f, 0);
		rot 		= new Vector3f();
		speed 		= 1;
		rotSpeed	= 0.8f;
		
		skeleton = new Bone(new Vector3f(pos), new Quaternion(), 0, 0.15f);
		skeleton.extendSkeleton(new Quaternion(0, -1, 0, 0), 0.6f, 0.08f);
	}
	
	public void tick(double delta) {
		Vector3f dir = new Vector3f();
		if (isKeyDown(KEY_S)) 		dir.z += speed * delta;
		if (isKeyDown(KEY_W)) 		dir.z -= speed * delta;
		if (isKeyDown(KEY_SPACE)) 	pos.y += speed * delta;
		if (isKeyDown(KEY_LSHIFT)) 	pos.y -= speed * delta;
		
		float angle = rot.y;
		pos.z += dir.z * Math.cos(angle);
		pos.x += -dir.z * Math.sin(angle);
		
		if (isKeyDown(KEY_D)) 		rot.y += rotSpeed * delta;
		if (isKeyDown(KEY_A)) 		rot.y -= rotSpeed * delta;
		
		skeleton.start.set(pos);
	}

}
