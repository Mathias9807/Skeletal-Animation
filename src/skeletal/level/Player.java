package skeletal.level;

import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.util.vector.*;

public class Player {
	
	public Vector3f pos, rot;
	public double speed, rotSpeed;
	public boolean walking;
	
	private Vector3f lastPos, lastRot;
	
	public Bone skeleton, head, lArmUp, rArmUp, lower;
	public Bone[] rLeg = new Bone[3], lLeg = new Bone[3];
	
	private float rWalkPoint = 0, lastRWalkPoint = 0, lWalkPoint = 0, lastLWalkPoint = 0, stepLength = 0.4f;
	private boolean rStepping = true;

	public Player() {
		pos 		= new Vector3f(0, 1.03f, 0);
		rot 		= new Vector3f();
		lastPos		= new Vector3f(pos);
		lastRot 	= new Vector3f(rot);
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
	
	public void tick(double delta) {
		Vector3f dir = new Vector3f();
		if (isKeyDown(KEY_S)) 		dir.z += speed * delta;
		if (isKeyDown(KEY_W)) 		dir.z -= speed * delta;
		if (isKeyDown(KEY_SPACE)) 	pos.y += speed * delta;
		if (isKeyDown(KEY_LSHIFT)) 	pos.y -= speed * delta;
		if (dir.z == 0) walking = false;
		else walking = true;
		
		float angle = rot.y;
		pos.z += dir.z * Math.cos(angle);
		pos.x += -dir.z * Math.sin(angle);
		
		if (isKeyDown(KEY_D)) 		rot.y += rotSpeed * delta;
		if (isKeyDown(KEY_A)) 		rot.y -= rotSpeed * delta;
		
		Matrix4f m = new Matrix4f();
		m.setIdentity();
		m.rotate(-Vector3f.sub(rot, lastRot, null).y, new Vector3f(0, 1, 0));
		skeleton.transformSkeleton(m);
		skeleton.start.set(pos);
		
		animateLegs(delta);
		
		lastPos.set(pos);
		lastRot.set(rot);
	}

	private void animateLegs(double delta) {
		if (walking) {
			if (rStepping) {
				rWalkPoint -= speed * delta;
				lWalkPoint += speed * delta;
				if (rWalkPoint < -stepLength) {
					rWalkPoint = -stepLength;
					rStepping = false;
				}
			}else {
				lWalkPoint -= speed * delta;
				rWalkPoint += speed * delta;
				if (lWalkPoint < -stepLength) {
					lWalkPoint = -stepLength;
					rStepping = true;
				}
			}
		}
		
		float rDeltaWalk = rWalkPoint - lastRWalkPoint;
		rLeg[2].dir.z += -rDeltaWalk * Math.cos(rot.y);
		rLeg[2].dir.x += rDeltaWalk * Math.sin(rot.y);
		rLeg[2].dir.y = -0.35f + 1.5f * (float) (Math.cos(rWalkPoint) - Math.cos(stepLength)) * (rStepping ? 0 : 1);
		
		float lDeltaWalk = lWalkPoint - lastLWalkPoint;
		lLeg[2].dir.z += -lDeltaWalk * Math.cos(rot.y);
		lLeg[2].dir.x += lDeltaWalk * Math.sin(rot.y);
		lLeg[2].dir.y = -0.35f + 1.5f * (float) (Math.cos(lWalkPoint) - Math.cos(stepLength)) * (rStepping ? 1 : 0);
		
		lastRWalkPoint = rWalkPoint;
		lastLWalkPoint = lWalkPoint;
	}

}
