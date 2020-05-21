//Created: 20/05/2020
package nutmeg.gl.rendering;

import java.awt.Color;

import org.joml.*;

import nutmeg.gl.rendering.buffers.DynamicIndexBuffer;
import nutmeg.gl.rendering.buffers.DynamicVertexBuffer;
import nutmeg.gl.rendering.buffers.VertexArray;

public abstract class RenderPipeline {
	
	//=======================================================================================================================
	
	private float[]
		vertexPositions, //Holds 3D OpenGL-Coordinate Positions
		normals, //Holds Normals
		textureCoords; //Holds UV / SV
	
	private int[] 
		indices; //Maps triangles
	
	private Texture2D 
		albedo; //Holds the main 'Diffuse' Texture
	
	private ShaderPipeline
		pipeline; //The Main Shader Pipeline
	
	private VertexArray currentModel; //Holds A Reference to the current OpenGL Model
	private DynamicVertexBuffer 
		positionBuffer,
		texCoordBuffer,
		normalBuffer;
	
	private DynamicIndexBuffer indicesBuffer;
	
	//=======================================================================================================================
	
	public void SetAlbedo(Texture2D _albedo) {}
	public void SetTextureCoords(float[] _textureCoords) {}
	public void SetNormals(float[] _normals) {}
	public void SetVertexPositions(float[] _vertices) {}
	
	public void DrawMesh() {}
	public void DrawMesh(float[] _vertexPositions) {}
	
	//=======================================================================================================================

	public void SetValue(String name, int value) {} 
	public void SetValue(String name, float value) {} 
	public void SetValue(String name, Vector2f vec) {} 
	public void SetValue(String name, Vector3f vec) {} 
	public void SetValue(String name, Vector4f vec) {} 
	public void SetValue(String name, Matrix2f mat) {} 
	public void SetValue(String name, Matrix3f mat) {} 
	public void SetValue(String name, Matrix4f mat) {} 
	
	//=======================================================================================================================
	
	public void SetValue(String name, Color c) {}
	public void SetValue(String name, Texture2D texture, int slot) {}
	
	//=======================================================================================================================
	
	private void Flush() {}
	private void Reset() {}
}
