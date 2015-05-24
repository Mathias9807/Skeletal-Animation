package skeletal.graphics;

import static skeletal.graphics.opengl.FBO.setFrameBuffer;
import static skeletal.graphics.opengl.OpenGLUtils.*;
import static skeletal.graphics.opengl.Shaders.*;
import skeletal.graphics.opengl.*;
import skeletal.level.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.*;

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
		useMatrix(matView, "matView");
		
		matModel.setIdentity();
		matModel.translate(new Vector3f(-10, 0, 10));
		matModel.rotate((float) -Math.PI / 2, new Vector3f(1, 0, 0));
		matModel.scale(new Vector3f(20, 20, 20));
		useMatrix(matModel, "matModel");
		setParam3f("color", 0.12f, 0.12f, 0.12f);
		Graphics.VAOArray[0].render();
		
		setParam3f("color", 1, 0, 0);
		renderBone(Level.player.skeleton);
	}
	
	private void renderBone(Bone b) {
		matModel.setIdentity();
		matModel.translate(b.getBoneEnd());
		System.out.println(b.getBoneEnd() + " " + b.nodes.size());
		matModel.scale(new Vector3f(b.sphereRadius, b.sphereRadius, b.sphereRadius));
		useMatrix(matModel, "matModel");
		
		Graphics.VAOArray[1].render();
		
		for (int i = 0; i < b.nodes.size(); i++) 
			renderBone(b.nodes.get(i));
	}

	public void end() {
	}

}
