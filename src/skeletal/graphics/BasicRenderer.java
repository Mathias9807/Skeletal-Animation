package skeletal.graphics;

import static skeletal.graphics.opengl.FBO.setFrameBuffer;
import static skeletal.graphics.opengl.OpenGLUtils.*;
import static skeletal.graphics.opengl.Shaders.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.*;

import skeletal.graphics.opengl.*;
import skeletal.level.Level;

public class BasicRenderer implements RenderEngine {
	
	public static int fov = 65;
	public static float nearPlane = 0.1f, farPlane = 100;
	
	private int shaderBasic;
	
	private Matrix4f matProj, matView, matModel;
	
	public void init() {
		GL11.glClearColor(0.2f, 0.2f, 0.3f, 0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		matProj = new Matrix4f();
		makePerspective(matProj, nearPlane, farPlane, fov);
		
		matView = new Matrix4f();
		matView.translate(new Vector3f(0, 0, 0));
		
		matModel = new Matrix4f();
		
		shaderBasic = Shaders.load("Basic");
		setShader(shaderBasic);
		useMatrix(matProj, "matProj");
		useMatrix(matView, "matView");
		useMatrix(matModel, "matModel");
	}

	public void tick() {
		setFrameBuffer(null);
		setShader(shaderBasic);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		matView.setIdentity();
		matView.rotate(Level.camRot.x, new Vector3f(1, 0, 0));
		matView.rotate(Level.camRot.y, new Vector3f(0, 1, 0));
		matView.translate(Level.camPos.negate(null));
		System.out.println(Level.camPos);
		useMatrix(matView, "matView");
		
		useMatrix(matModel, "matModel");
		Graphics.VAOArray[0].render();
	}

	public void end() {
		
	}

}
