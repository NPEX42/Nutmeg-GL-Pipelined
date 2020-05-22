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
				+0.5f,-0.5f,1.0f,
		};
		
		int[] indices = {
				0,1,2,
				2,3,1
		};
		
		DisplayManager.Open(1080, 720, "Nutmeg-GL - Sandbox", true);
		GeometryRenderPipeline pipe = new GeometryRenderPipeline(NM_RENDER_TRIANGLES);
		pipe.SetIndices(indices);
		pipe.SetPerspective(DisplayManager.getWidth(), DisplayManager.getHeight(), 70, 0.1f, -1000);
		pipe.SetView(0, 0, 10, 0, 0, 0);
		pipe.SetTransform(0, 0, 0, 0, 0, 0, 1, 1, 1);
		Vector3f cameraPos = new Vector3f(0,0,10);
		Vector3f cameraRot = new Vector3f(0,0,0);
		pipe.SetVertexPositions(vertexPos);
		while(DisplayManager.DisplayIsActive()) {
			pipe.Clear(theta, 1, 1);
			
			if(Keyboard.isKeyPressed('A')) {
				cameraPos.add(-0.1f, 0, 0);
			}
			
			if(Keyboard.isKeyPressed('D')) {
				cameraPos.add(+0.1f, 0, 0);
			}
			
			if(Keyboard.isKeyPressed('W')) {
				cameraPos.add(0, 0, -0.1f);
			}
			
			if(Keyboard.isKeyPressed('S')) {
				cameraPos.add(0, 0, +0.1f);
			}
			
			if(Keyboard.isKeyPressed('Q')) {
				cameraRot.add(1, +1f, 0);
			}
			
			if(Keyboard.isKeyPressed('Q')) {
				cameraRot.add(-1, -1f, 0);
			}
			
			pipe.SetTransform(0, 0, 0, theta * 10, 0, 0, 10, 10, 10);
			pipe.SetValue("u_TintColor", Color.getHSBColor(theta + 3.14f, 1, 1));
			pipe.SetView(cameraPos.x, cameraPos.y, cameraPos.z, cameraRot.x, cameraRot.y, cameraRot.z);
			pipe.DrawMesh();
			DisplayManager.Update();
			theta += 0.001f;
		}
		DisplayManager.Close();
	}
}
