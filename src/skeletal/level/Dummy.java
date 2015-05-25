package skeletal.level;

import org.lwjgl.util.vector.Vector3f;

public class Dummy {
	
	public Vector3f pos, rot;
	public double speed, rotSpeed;
	
	public Bone skeleton, head, lArmUp, rArmUp, lower;
	public Bone[] rLeg = new Bone[3], lLeg = new Bone[3];

	public Dummy(Vector3f p) {
		pos 		= p;
		rot 		= new Vector3f();
		speed 		= 1;
		rotSpeed	= 0.8f;
		
		skeleton 	= new Bone(new Vector3f(pos), new Vector3f(0, 0, 0), 0.09f);
		head 		= skeleton.extendSkeleton(new Vector3f(0, 0.3f, 0), 0.12f);
		lower 		= skeleton.extendSkeleton(new Vector3f(0, -0.3f, 0), 0.08f);
		rLeg[0] 	= lower.extendSkeleton(new Vector3f(0.03f, -0.03f, 0), 0.03f);
		lLeg[0] 	= lower.extendSkeleton(new Vector3f(-0.03f, -0.03f, 0), 0.03f);
		rLeg[1] 	= rLeg[0].extendSkeleton(new Vector3f(0.03f, -0.35f, 0), 0.05f);
		lLeg[1] 	= lLeg[0].extendSkeleton(new Vector3f(-0.03f, -0.35f, 0), 0.05f);
		rLeg[2] 	= rLeg[1].extendSkeleton(new Vector3f(0, -0.35f, 0), 0.05f);
		lLeg[2] 	= lLeg[1].extendSkeleton(new Vector3f(0, -0.35f, 0), 0.05f);
	}
	
}
