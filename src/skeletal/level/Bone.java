package skeletal.level;

import java.util.ArrayList;

import org.lwjgl.util.vector.*;

public class Bone {
	
	public Vector3f 	start;
	public Quaternion 	dir;
	public float		length, sphereRadius;
	
	public ArrayList<Bone> nodes;
	
	public Bone(Vector3f s, Quaternion d, float l, float size) {
		start 			= s;
		dir 			= d;
		length 			= l;
		sphereRadius 	= size;
		nodes 	= new ArrayList<>();
	}
	
	public void extendSkeleton(Quaternion q, float l, float s) {
		nodes.add(new Bone(getBoneEnd(), q, l, s));
	}
	
	public Vector3f getBoneEnd() {
		return Vector3f.add(start, (Vector3f) getVecFromQuat(dir).scale(length), null);
	}
	
	public static Vector3f getVecFromQuat(Quaternion q) {
		Vector3f r = new Vector3f();
		r.x = (float) (q.x * Math.sin(q.w / 2));
		r.y = (float) (q.y * Math.sin(q.w / 2));
		r.z = (float) (q.z * Math.sin(q.w / 2));
		return r;
	}
	
}
