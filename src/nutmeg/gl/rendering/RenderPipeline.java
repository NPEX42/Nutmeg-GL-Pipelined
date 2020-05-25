//Created: 20/05/2020
package nutmeg.gl.rendering;

import java.awt.Color;

import org.joml.*;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL46.*;
import static nutmeg.gl.Nutmeg.*;
import nutmeg.gl.rendering.buffers.IndexBuffer;
import nutmeg.gl.rendering.buffers.VertexArray;
import nutmeg.gl.rendering.buffers.VertexBuffer;
@SuppressWarnings("unused")
public abstract class RenderPipeline {
	
	//=======================================================================================================================
	protected int mode = GL_TRIANGLES;
	private boolean usesDepth = false;
	protected Matrix4f proj, view, model;
	
	protected VertexData currentData;
	
	protected float[]
		vertexPositions, //Holds 3D OpenGL-Coordinate Positions
		normals, //Holds Normals
		textureCoords; //Holds UV / SV
	
	protected int[] 
		indices; //Maps triangles
	
	protected Texture2D 
		albedo; //Holds the main 'Diffuse' Texture
	
	protected ShaderPipeline
		pipeline; //The Main Shader Pipeline
	
	protected VertexArray currentModel; //Holds A Reference to the current OpenGL Model
	
	protected VertexBuffer 
		positionBuffer,
		texCoordBuffer,
		normalBuffer;
	
	protected IndexBuffer indicesBuffer;
	
	public RenderPipeline(int _mode, String[] _shaderPaths) {
		
		mode = _mode;
		currentModel = new VertexArray();
		//pipeline = ShaderPipeline.BuildFromExtFile("res/shaders/geom.v.glsl", "res/shaders/geom.f.glsl");
		pipeline = ShaderPipeline.BuildFromIntFile(RenderPipeline.class.getClassLoader(), _shaderPaths[0], _shaderPaths[1]);
		if(pipeline == null) pipeline = ShaderPipeline.BuildFromExtFile(_shaderPaths[0], _shaderPaths[1]);
		if(pipeline == null) System.err.println("Unable To Create Shader from " +_shaderPaths[0] + " (VS) and  "+ _shaderPaths[1] + " (FS)");
		
		proj = new Matrix4f();
		model = new Matrix4f();
		view = new Matrix4f();
		
		currentData = new VertexData(null, null, null, null);
	}
	
	//=======================================================================================================================
	
	public void SetPerspective(float width, float height, float fov, float near, float far) {
		proj = new Matrix4f().perspective(fov, width / height, near, far);
	}
	
	public void SetOrthographic(float width, float height, float near, float far) {
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
	
	public void SetAlbedo(Texture2D _albedo) {
		if(_albedo != null) {
			SetValue("t_Albedo", _albedo, NM_ALBEDO);
		} else {
			SetValue("t_Albedo", Texture2D.GetWhiteTex(), NM_ALBEDO);
		}
	}
	
	public void SetTextureCoords(float[] _textureCoords) {
		currentModel.Bind();
		if(texCoordBuffer != null) texCoordBuffer.Delete();
		texCoordBuffer = VertexBuffer.Create(NM_UV, _textureCoords, NM_VEC2);
		System.err.println("Building UV Buffer");
	}
	
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
	
	public void SetVertexData(VertexData _data) {
		currentData = _data;
	}
	
	public void DrawMesh() {
		if(indicesBuffer != null && positionBuffer != null) {
			currentModel.Bind();
			pipeline.Bind();
			Matrix4f mvp = new Matrix4f();
			mvp = mvp.mul(proj);
			mvp = mvp.mul(view);
			mvp = mvp.mul(model);
			pipeline.SetValue("u_MVP", mvp);
			pipeline.SetValue("u_WorldMat", model);
			glDrawElements(mode, indicesBuffer.length, NM_UINT32, 0);
		} else {
			throw new RuntimeException("Index Buffer OR Position Buffer has not been setup!");
		}
	}
	
	public void EnableDepth() {
		glEnable(GL_DEPTH_TEST);
		usesDepth = true;
	}
	
	public void EnableBackFaceCullingCCW() {
		glEnable(GL_CULL_FACE);
		glFrontFace(GL_CCW);
	}
	
	public void EnableBackFaceCullingCW() {
		glEnable(GL_CULL_FACE);
		glFrontFace(GL_CW);
	}
	
	public void DrawMesh(float[] _vertexPositions) {
		SetVertexPositions(_vertexPositions);
		DrawMesh();
	}
	
	public void DrawMesh(MeshData _data) {
		if(_data == null) return;
		if(!currentData.equals(_data.GetVertexData())) {
			VertexData _vertData = _data.GetVertexData();
		
			SetAlbedo(_data.GetAlbedo());
			SetNormals(_vertData.getNormals());
			SetVertexPositions(_vertData.getVertexPositions());
			SetTextureCoords(_vertData.getTextureCoords());
			SetIndices(_vertData.getIndices());
			currentData = _vertData;
		}
		
		SetValue("u_TintColor", Color.white);
		DrawMesh();
	}
	
	public void Clear(Color c) {
		if(usesDepth) {
			glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
		} else {
			glClear(GL_COLOR_BUFFER_BIT);
		}
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
	
	public void SetValue(String name, Color c) {
		pipeline.Bind();
		pipeline.SetValue(name, new Vector4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f));
	}
	public void SetValue(String name, Texture2D texture, int slot) {
		pipeline.Bind();
		texture.Bind();
		texture.SetSlot(slot);
		pipeline.SetValue(name, slot);
	}
	
	//=======================================================================================================================
	
	private void Flush() {}
	private void Reset() {}
	
	public void Destroy() {
		if(positionBuffer != null) positionBuffer.Delete();
		if(normalBuffer != null) normalBuffer.Delete();
		if(texCoordBuffer != null) texCoordBuffer.Delete();
		if(indicesBuffer != null) indicesBuffer.Delete();
		if(currentModel != null) currentModel.Delete();
		if(pipeline != null) pipeline.Delete();
	}
	
}
