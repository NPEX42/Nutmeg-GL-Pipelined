package nutmeg.gl.sandbox;

import nutmeg.gl.core.window.DisplayManager;
import nutmeg.gl.rendering.GeometryRenderPipeline;
import static nutmeg.gl.Nutmeg.*;
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
		pipe.SetVertexPositions(vertexPos);
		while(DisplayManager.DisplayIsActive()) {
			pipe.Clear(theta, 1, 1);
			pipe.DrawMesh();
			DisplayManager.Update();
			theta += 0.001f;
		}
		DisplayManager.Close();
	}
}
