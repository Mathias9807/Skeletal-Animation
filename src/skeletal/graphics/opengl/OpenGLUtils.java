package skeletal.graphics.opengl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;

import static skeletal.graphics.opengl.Shaders.*;

public class OpenGLUtils {
	
	public static void useMatrix(Matrix4f m, String var) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		m.store(buffer);
		buffer.flip();
		setParam4m(var, buffer);
	}
	
	public static void makePerspective(Matrix4f m, float near, float far, float fov) {
		float tweenP = far - near;
		float sumP   = far + near;
		float aspect = (float) Display.getWidth() / Display.getHeight();
		
		m.setIdentity();
		m.m00 = (float) (1 / Math.tan(fov / 360d * Math.PI)) / aspect; // fov / 2 / 180 = fov / 360
		m.m11 = (float) (1 / Math.tan(fov / 360d * Math.PI));
		m.m22 = -sumP / tweenP;
		m.m23 = -1;
		m.m32 = -2 * near * far / tweenP;
		m.m33 = 0;
	}
	
	public static Matrix4f lookAt(Vector3f dir) {
		Matrix4f r = new Matrix4f();
		r.setIdentity();
		
		Vector3f z = dir.normalise(null);
		
		Vector3f up;
		if (z.y == 1) up = new Vector3f(0, 0, -1);
		else if (z.y == -1) up = new Vector3f(0, 0, 1);
		else up = new Vector3f(0, 1, 0);
		
		Vector3f x = Vector3f.cross(up, dir, null).normalise(null);
		Vector3f y = Vector3f.cross(dir, x, null).normalise(null);
		r.m00 = x.x;
		r.m01 = x.y;
		r.m02 = x.z;
		r.m10 = y.x;
		r.m11 = y.y;
		r.m12 = y.z;
		r.m20 = z.x;
		r.m21 = z.y;
		r.m22 = z.z;
		
		return r;
	}
	
	public static Matrix4f getRotMatAngle(Vector3f angles) {
		Matrix4f m = new Matrix4f();
		m.rotate(angles.x, new Vector3f(1, 0, 0));
		m.rotate(angles.y, new Vector3f(0, 1, 0));
		m.rotate(angles.z, new Vector3f(0, 0, 1));
		return m;
	}
	
	public static Vector4f rotate(Vector3f point, Vector3f angles) {
		Matrix4f m = getRotMatAngle(angles);
		Vector4f v = new Vector4f(point.x, point.y, point.z, 1);
		Matrix4f.transform(m, v, v);
		point.set(v.x, v.y, v.z);
		return v;
	}
	
	public static Matrix4f getMat4(Matrix3f m) {
		Matrix4f r = new Matrix4f();
		r.m00 = m.m00;
		r.m01 = m.m01;
		r.m02 = m.m02;
		r.m10 = m.m10;
		r.m11 = m.m11;
		r.m12 = m.m12;
		r.m20 = m.m20;
		r.m21 = m.m21;
		r.m22 = m.m22;
		return r;
	}
	
	public static Vector4f getVec4(Vector3f v) {
		Vector4f r = new Vector4f();
		r.x = v.x;
		r.y = v.y;
		r.z = v.z;
		r.w = 1;
		return r;
	}
	
	public static Vector3f getVec3(Vector4f v) {
		Vector3f r = new Vector3f();
		r.x = v.x;
		r.y = v.y;
		r.z = v.z;
		return r;
	}
	
}
