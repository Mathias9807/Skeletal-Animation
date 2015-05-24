package skeletal.level;

import java.util.ArrayList;

import org.lwjgl.util.vector.*;

public class Bone {
	
	public Vector3f 	start;
	public Quaternion 	dir;
	public float		length, sphereRadius;
	
	public ArrayList<Bone> nodes;
	
	// This vector is the origin of any children the bone may have. It should not be used to get the end point of the bone. 
	private Vector3f	end;
	
	public Bone(Vector3f s, Quaternion d, float l, float size) {
		start 			= s;
		dir 			= d;
		length 			= l;
		sphereRadius 	= size;
		nodes 	= new ArrayList<>();
		end				= new Vector3f();
		getBoneEnd();
	}
	
	public void extendSkeleton(Quaternion q, float l, float s) {
		nodes.add(new Bone(end, q, l, s));
	}
	
	public Vector3f getBoneEnd() {
		end.set(Vector3f.add(start, (Vector3f) getVecFromQuat(dir).scale(length), null));
		return new Vector3f(end);
	}
	
	public static Vector3f getVecFromQuat(Quaternion q) {
		Vector3f r = new Vector3f();
		r.x = (float) (q.x);
		r.y = (float) (q.y);
		r.z = (float) (q.z);
		return r;
	}
	
}
