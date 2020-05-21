//Created: 20/05/2020
package nutmeg.gl.rendering;

import java.awt.Color;

import org.joml.*;

import static org.lwjgl.opengl.GL46.*;
import static nutmeg.gl.Nutmeg.*;
import nutmeg.gl.rendering.buffers.IndexBuffer;
import nutmeg.gl.rendering.buffers.VertexArray;
import nutmeg.gl.rendering.buffers.VertexBuffer;

public abstract class RenderPipeline {
	
	//=======================================================================================================================
	private int mode = GL_TRIANGLES;
	
	private Matrix4f proj, view, model;
	
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
	
	private VertexBuffer 
		positionBuffer,
		texCoordBuffer,
		normalBuffer;
	
	private IndexBuffer indicesBuffer;
	
	public RenderPipeline(int _mode) {
		mode = _mode;
		currentModel = new VertexArray();
		pipeline = ShaderPipeline.BuildFromExtFile("res/shaders/geom.v.glsl", "res/shaders/geom.f.glsl");
		if(pipeline == null) throw new RuntimeException("Unable To Build Shader Pipeline!");
	}
	
	//=======================================================================================================================
	
	public void SetPerspective(float width, float height, float fov, float near, float far) {
		proj = new Matrix4f().perspective(fov, width / height, near, far);
	}
	
	public void SetOrthographic(float height, float width, float near, float far) {
		proj = new Matrix4f().ortho(0, width, height, 0, near, far);
	}
	
	public void SetTransform(float x, float y, float z, float xRot, float yRot, float zRot, float xScale, float yScale, float zScale) {
		model = new Matrix4f().identity();
		model.translate(x, y, z);
		model.rotate(xRot, NM_POS_X);
		model.rotate(yRot, NM_POS_Y);
		model.rotate(zRot, NM_POS_Z);
		model.scale(xScale, yScale, zScale);
	}
	
	public void SetView(float x, float y, float z, float xRot, float yRot, float zRot) {
		Matrix4f temp = new Matrix4f().identity();
		temp.translate(x, y, z);
		temp.rotate(xRot, NM_POS_X);
		temp.rotate(yRot, NM_POS_Y);
		temp.rotate(zRot, NM_POS_Z);
		
		view = temp.invert();
	}
	
	public void SetAlbedo(Texture2D _albedo) {}
	public void SetTextureCoords(float[] _textureCoords) {}
	
	public void SetNormals(float[] _normals) {
		currentModel.Bind();
		if(normalBuffer != null) normalBuffer.Delete();
		normalBuffer = VertexBuffer.Create(2, _normals, NM_VEC3);
		System.err.println("Building Normal Buffer");
	}
	
	public void SetIndices(int[] _indices) {
		currentModel.Bind();
		if(indicesBuffer != null) indicesBuffer.Delete();
		indicesBuffer = IndexBuffer.Create(_indices);
		System.err.println("Building Index Buffer");
	}
	
	public void SetVertexPositions(float[] _vertices) {
		currentModel.Bind();
		if(positionBuffer != null) positionBuffer.Delete();
		positionBuffer = VertexBuffer.Create(0, _vertices, NM_VEC3);
		System.err.println("Building Position Buffer");
	}
	
	public void DrawMesh() {
		if(indicesBuffer != null && positionBuffer != null) {
			currentModel.Bind();
			pipeline.Bind();
			glDrawElements(mode, indicesBuffer.length, NM_UINT32, 0);
		} else {
			throw new RuntimeException("Index Buffer OR Position Buffer has not been setup!");
		}
	}
	
	public void DrawMesh(float[] _vertexPositions) {
		SetVertexPositions(_vertexPositions);
		DrawMesh();
	}
	
	public void Clear(Color c) {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	}
	
	public void Clear(int color) {
		Clear(new Color(color));
	}
	
	public void Clear(float hue, float sat, float bright) {
		Clear(Color.getHSBColor(hue, sat, bright));
	}
	
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
