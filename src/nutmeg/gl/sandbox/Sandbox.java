package nutmeg.gl.sandbox;

import nutmeg.gl.core.input.Keyboard;
import nutmeg.gl.core.window.DisplayManager;
import nutmeg.gl.rendering.GeometryRenderPipeline;
import static nutmeg.gl.Nutmeg.*;

import java.awt.Color;

import org.joml.Vector3f;
public class Sandbox {
	public static void main(String[] args) {
		
		float theta = 0f;
		
		float[] vertexPos = {
				-0.5f,+0.5f,0.0f,
				+0.5f,+0.5f,0.0f,
				-0.5f,-0.5f,0.0f,
				+0.5f,-0.5f,0.0f,
		};
		
		int[] indices = {
				0,1,2,
				2,3,1
		};
		
		DisplayManager.Open(1080, 720, "Nutmeg-GL - Sandbox", true);
		GeometryRenderPipeline pipe = new GeometryRenderPipeline(NM_RENDER_TRIANGLES);
		pipe.SetIndices(indices);
		pipe.SetOrthographic(DisplayManager.getWidth(), DisplayManager.getHeight(), 1, -1);
		//pipe.SetView(0, 0, 1, 0, 0, 0);
		pipe.SetTransform(0, 0, 0, 0, 0, 0, 1, 1, 1);
		Vector3f cameraPos = new Vector3f(0,0,1);
		Vector3f cameraRot = new Vector3f(0,0,0);
		pipe.SetVertexPositions(vertexPos);
		while(DisplayManager.DisplayIsActive()) {
			pipe.Clear(theta, 1, 1);
			
			if(Keyboard.isKeyPressed('A')) {
				cameraPos.add(-1f, 0, 0);
			}
			
			if(Keyboard.isKeyPressed('D')) {
				cameraPos.add(+1f, 0, 0);
			}
			
			if(Keyboard.isKeyPressed('W')) {
//				float dx = (float) Math.cos(cameraRot.y + Math.toRadians(-90));
//				float dz = (float) Math.sin(cameraRot.y + Math.toRadians(-90));
//				System.err.println("DX = "+dx);
//				System.err.println("DZ = "+dz);
//				System.err.println("YR = "+Math.toDegrees(cameraRot.y));
//				cameraPos.x -= dx;
//				cameraPos.z -= dz;
				cameraPos.y -= 1f;
			}
			
			if(Keyboard.isKeyPressed('S')) {
//				float dx = (float) Math.cos(cameraRot.y + Math.toRadians(-90));
//				float dz = (float) Math.sin(cameraRot.y + Math.toRadians(-90));
//				System.err.println("DX = "+dx);
//				System.err.println("DZ = "+dz);
//				System.err.println("YR = "+Math.toDegrees(cameraRot.y));
//				cameraPos.x += dx;
//				cameraPos.z += dz;
				
				cameraPos.y += 1f;
			}
			
			if(Keyboard.isKeyPressed('Q')) {
				cameraRot.add(0, +0.01f, 0);
			}
			
			if(Keyboard.isKeyPressed('E')) {
				cameraRot.add(0, -0.01f, 0);
			}
			
			
			
			pipe.SetTransform(0, 0, 0, 0, 0, 0, 100, 100, 100);
			pipe.SetValue("u_TintColor", Color.getHSBColor(theta + 3.14f, 1, 1));
			pipe.SetView(cameraPos.x, cameraPos.y, cameraPos.z, cameraRot.x, cameraRot.y, cameraRot.z);
			pipe.DrawMesh();
			DisplayManager.Update();
			theta += 0.001f;
		}
		DisplayManager.Close();
	}
}
