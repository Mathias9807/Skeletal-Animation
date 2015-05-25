package skeletal.level;

import java.util.ArrayList;

import org.lwjgl.util.vector.*;

public class Bone {
	
	public Vector3f 	start, dir;
	public float		sphereRadius;
	
	public Bone 		parent;
	
	public ArrayList<Bone> nodes;
	
	// This vector is the origin of any children the bone may have. It should not be used to get the end point of the bone. 
	private Vector3f	end;
	
	public Bone(Vector3f s, Vector3f d, float size) {
		start 			= s;
		dir 			= d;
		sphereRadius 	= size;
		parent			= null;
		nodes 			= new ArrayList<>();
		end				= new Vector3f();
		getBoneEnd();
	}
	
	public Bone extendSkeleton(Vector3f r, float s) {
		Bone b = new Bone(end, r, s);
		b.parent = this;
		nodes.add(b);
		return b;
	}
	
	public void transformSkeleton(Matrix4f m) {
		Vector4f v = new Vector4f(dir.x, dir.y, dir.z, 1);
		Matrix4f.transform(m, v, v);
		dir.set(v.x, v.y, v.z);
		
		for (int i = 0; i < nodes.size(); i++) 
			nodes.get(i).transformSkeleton(m);
	}
	
	public Vector3f getBoneEnd() {
		end.set(Vector3f.add(start, dir, null));
		return new Vector3f(end);
	}
	
}
