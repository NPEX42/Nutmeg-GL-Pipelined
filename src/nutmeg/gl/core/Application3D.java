package nutmeg.gl.core;


import static org.lwjgl.opengl.GL46.*;

import nutmeg.gl.rendering.GeometryRenderPipeline;
import nutmeg.gl.rendering.MeshData;

import static nutmeg.gl.Nutmeg.*;

import java.awt.Color;

import org.joml.*;

import nutmeg.gl.core.window.DisplayManager;
public abstract class Application3D extends Application {
	GeometryRenderPipeline pipe;
	float x, y, z, xr, yr, zr, xs, ys, zs, cx, cy, cz, cxr, cyr, czr;
	Vector3f targetDir = NM_POS_Z;
	Vector3f lookDir = NM_POS_Z;
	protected Vector3f forwardVector;
	@Override
	public boolean OnApplicationInit() {
		pipe = new GeometryRenderPipeline(NM_TRIANGLES);
		pipe.SetPerspective(DisplayManager.getWidth(), DisplayManager.getHeight(), 70f, 0.3f, -100f);
		pipe.EnableDepth();
		pipe.EnableBackFaceCullingCCW();
		return true;
	}
	
	public boolean OnApplicationUpdate() {
		return true;
	}

	@Override
	public boolean OnApplicationDestroy() {
		pipe.Destroy();
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		glClearDepthf(-1.0f);
		return true;
	}
	
	protected void DrawMesh(MeshData data) {
		pipe.SetTransform(x, y, z, xr, yr, zr, 1, 1, 1);
		pipe.SetView(cx, cy, cz, cxr, cyr, czr);
		pipe.DrawMesh(data);
	}
	@Override
	public void Background(Color color) {
		pipe.Clear(color);
		
		
		//Update Camera
		Matrix4f cameraRotMat = new Matrix4f();
		//cameraRotMat.rotate(cxr, NM_POS_X);
		cameraRotMat.rotate(cyr, NM_POS_Y);
		//cameraRotMat.rotate(czr, NM_POS_Z);
		Vector4f moveDir = new Vector4f(targetDir, (float) 1.0);
		moveDir = moveDir.mul(cameraRotMat);
		
		forwardVector = new Vector3f(moveDir.x, moveDir.y,moveDir.z);
		
		pipe.SetPerspective(DisplayManager.getWidth(), DisplayManager.getHeight(), 70f, 0.3f, -100f);
	}
	
	public void Rotate(float _x, float _y, float _z) {
		xr += _x;
		yr += _y;
		zr += _z;
	}
	
	public void Translate(float _x, float _y, float _z) {
		x += _x;
		y += _y;
		z += _z;
	}
	
	public void Scale(float _x, float _y, float _z) {
		xs += _x;
		ys += _y;
		zs += _z;
	}
	
	public void ResetTranslation() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public void ResetRotation() {
		xr = 0;
		yr = 0;
		zr = 0;
	}
	
	public void TranslateCamera(float _x, float _y, float _z) {
		cx += _x;
		cy += _y;
		cz += _z;
	} 
	
	public void RotateCamera(float _x, float _y, float _z) {
		cxr += _x;
		cyr += _y;
		czr += _z;
	}
	
	public void SetGlobalLightDir(float _x, float _y, float _z) {
		pipe.SetValue("u_GlobalLightDir", new Vector3f(_x, _y, _z));
	}

}
