package skeletal.level;

import static org.lwjgl.input.Keyboard.*;

import org.lwjgl.util.vector.*;

public class Player {
	
	public Vector3f pos, rot;
	public double speed, rotSpeed;
	public boolean walking;
	public int walkDir;
	
	private Vector3f lastPos, lastRot;
	
	public Bone skeleton, head, lArmUp, rArmUp, lower;
	public Bone[] rLeg = new Bone[3], lLeg = new Bone[3];
	
	private float rWalkPoint = 0, lWalkPoint = 0, stepLength = 0.3f, legLength = 0.4f, 
			legHeight = (float) Math.sqrt(Math.pow(legLength * 2, 2) - Math.pow(stepLength, 2));
	private boolean rStepping = true;

	public Player() {
		pos 		= new Vector3f(0, 1.03f, 0);
		rot 		= new Vector3f();
		lastPos		= new Vector3f(pos);
		lastRot 	= new Vector3f(rot);
		speed 		= 3;
		rotSpeed	= 0.8f;
		
		skeleton 	= new Bone(new Vector3f(pos), new Vector3f(0, 0, 0), 0.09f);
		head 		= skeleton.extendSkeleton(new Vector3f(0, 0.3f, 0), 0.12f);
		lower 		= skeleton.extendSkeleton(new Vector3f(0, -0.3f, 0), 0.08f);
		rLeg[0] 	= lower.extendSkeleton(new Vector3f(0.03f, -0.03f, 0), 0.03f);
		lLeg[0] 	= lower.extendSkeleton(new Vector3f(-0.03f, -0.03f, 0), 0.03f);
		rLeg[1] 	= rLeg[0].extendSkeleton(new Vector3f(0.03f, -legHeight / 2, 0), 0.05f);
		lLeg[1] 	= lLeg[0].extendSkeleton(new Vector3f(-0.03f, -legHeight / 2, 0), 0.05f);
		rLeg[2] 	= rLeg[1].extendSkeleton(new Vector3f(0, -legHeight / 2, 0), 0.05f);
		lLeg[2] 	= lLeg[1].extendSkeleton(new Vector3f(0, -legHeight / 2, 0), 0.05f);
	}
	
	public void tick(double delta) {
		Vector3f dir = new Vector3f();
		if (isKeyDown(KEY_S)) 		dir.z += speed * delta;
		if (isKeyDown(KEY_W)) 		dir.z -= speed * delta;
		if (isKeyDown(KEY_SPACE)) 	pos.y += speed * delta;
		if (isKeyDown(KEY_LSHIFT)) 	pos.y -= speed * delta;
		if (dir.z == 0) walking = false;
		else walking = true;
		walkDir = dir.z > 0 ? 1 : -1;
		
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
					lWalkPoint = stepLength;
					rStepping = false;
				}
			}else {
				lWalkPoint -= speed * delta;
				rWalkPoint += speed * delta;
				if (lWalkPoint < -stepLength) {
					lWalkPoint = -stepLength;
					rWalkPoint = stepLength;
					rStepping = true;
				}
			}
		}
		
		Vector3f lPos = new Vector3f(lLeg[0].getBoneEnd()), 
				rPos = new Vector3f(rLeg[0].getBoneEnd());
		rPos.y = rLeg[0].start.y - legHeight;
		lPos.y = lLeg[0].start.y - legHeight;
		
		rPos.z += -rWalkPoint * Math.cos(rot.y) * -walkDir;
		rPos.x += rWalkPoint * Math.sin(rot.y) * -walkDir;
		rPos.y += 1.5f * (float) (Math.cos(rWalkPoint) - Math.cos(stepLength)) * (rStepping ? 0 : 1);
		
		lPos.z += -lWalkPoint * Math.cos(rot.y) * -walkDir;
		lPos.x += lWalkPoint * Math.sin(rot.y) * -walkDir;
		lPos.y += 1.5f * (float) (Math.cos(lWalkPoint) - Math.cos(stepLength)) * (rStepping ? 1 : 0);
		
		rLeg[1].dir.set((ReadableVector3f) Vector3f.sub(rPos, rLeg[0].start, null).scale(0.5f));
		lLeg[1].dir.set((ReadableVector3f) Vector3f.sub(lPos, lLeg[0].start, null).scale(0.5f));
		
		rLeg[2].setWorldEnd(rPos);
		lLeg[2].setWorldEnd(lPos);
		
		Vector3f xLegParr = new Vector3f((float) Math.cos(rot.y), 0, (float) Math.sin(rot.y));
		Vector3f lLegParr = Vector3f.cross(xLegParr, lLeg[1].dir, null).normalise(null);
		Vector3f rLegParr = Vector3f.cross(xLegParr, rLeg[1].dir, null).normalise(null);
		
		float rExtrude, lExtrude;
		rExtrude = (float) Math.sqrt(Math.abs(legLength * legLength - rLeg[1].dir.lengthSquared()));
		lExtrude = (float) Math.sqrt(Math.abs(legLength * legLength - lLeg[1].dir.lengthSquared()));
		
		Vector3f.add(rLeg[1].dir, (Vector3f) rLegParr.scale(rExtrude), rLeg[1].dir);
		Vector3f.add(lLeg[1].dir, (Vector3f) lLegParr.scale(lExtrude), lLeg[1].dir);
		
		System.out.println(xLegParr);
	}

}
