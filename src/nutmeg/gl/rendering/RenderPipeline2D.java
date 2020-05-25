package nutmeg.gl.rendering;
import static nutmeg.gl.Nutmeg.*;

import java.awt.Color;
import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;

import nutmeg.gl.core.window.DisplayManager;
import nutmeg.gl.rendering.buffers.IndexBuffer;
import nutmeg.gl.rendering.buffers.VertexBuffer;
public class RenderPipeline2D extends RenderPipeline {
	
	Texture2D white, missing;
	
	float[] vertexPos = {
			-0.5f,-0.5f,
			+0.5f,-0.5f,
			+0.5f,+0.5f,
			-0.5f,+0.5f,
	};
	
	float[] textureCoords = {
			0.0f,0.0f,
			1.0f,0.0f,
			1.0f,1.0f,
			0.0f,1.0f,
	};
	
	int[] indices = {
			0,1,2,
			2,3,0
	};
	public RenderPipeline2D(int _mode, String[] _shaderPaths) {
		super(_mode, _shaderPaths);
		currentModel.Bind();
		indicesBuffer = IndexBuffer.Create(indices);
		positionBuffer = VertexBuffer.Create(NM_POSITION, vertexPos, NM_VEC2);
		texCoordBuffer = VertexBuffer.Create(NM_UV, textureCoords, NM_VEC2);
		white = Texture2D.GetWhiteTex();
		missing = Texture2D.GetMissingTex();
		SetOrthographic(DisplayManager.getWidth(), DisplayManager.getHeight(), 1, -1);
	}
	
	@Override
	public void Clear(Color c) {
		super.Clear(c);
		SetOrthographic(DisplayManager.getWidth(), DisplayManager.getHeight(), 1, -1);
	}
	
	public void Clear(Texture2D background) {
		float width, height, x, y;
		width = DisplayManager.getWidth();
		height = DisplayManager.getHeight();
		x = width / 2;
		y = height / 2;
		
		DrawScaledImage(x, y, width, height, background);
	}
	
	public RenderPipeline2D(int _mode) {
		this(_mode, new String[] {"nutmeg/gl/rendering/shaders/2d.v.glsl", "nutmeg/gl/rendering/shaders/2d.f.glsl"});
	}
	
	public void DrawRect(float x, float y, float w, float h, Color c) {
		SetValue("u_TintColor", c);
		SetAlbedo(white);
		
		SetTransform(x, y, 0, 0, 0, 0, w, h, 1);
		
		DrawMesh();
	}
	
	public void DrawRect(Color c) {
		SetValue("u_TintColor", c);
		SetAlbedo(white);
		DrawMesh();
	}
	
	public void DrawImage(float x, float y, Texture2D _image) {
		SetValue("u_TintColor", Color.white);
		SetAlbedo(_image);
		SetTransform(x, y, 0, 0, 0, 0, _image.GetWidth(), _image.GetHeight(), 1);
		DrawMesh();
	}
	
	public void SetTransform2D(float x, float y, float rot, float sx, float sy) {
		SetTransform(x, y, 0, 0, 0, rot, sx, sy, 1);
	}
	
	public void DrawImage(SubTexture2D _image) {
		SetValue("u_TintColor", Color.white);
		SetAlbedo(_image.GetSourceTexture());
		SetTextureCoords(_image.uvs);
		//SetTransform(x, y, 0, 0, 0, 0, _image.GetWidth(), _image.GetHeight(), 1);
		DrawMesh();
		SetTextureCoords(textureCoords);
	}
	
	public void DrawImage(Texture2D _image) {
		SetValue("u_TintColor", Color.white);
		SetAlbedo(_image);
		//SetTransform(x, y, 0, 0, 0, 0, _image.GetWidth(), _image.GetHeight(), 1);
		DrawMesh();
	}
	
	public void DrawImage(float x, float y, SubTexture2D _image) {
		SetValue("u_TintColor", Color.white);
		SetAlbedo(_image.GetSourceTexture());
		SetTextureCoords(_image.uvs);
		SetTransform(x, y, 0, 0, 0, 0, _image.GetWidth(), _image.GetHeight(), 1);
		DrawMesh();
		SetTextureCoords(textureCoords);
	}
	
	public void DrawScaledImage(float x, float y, float w, float h, Texture2D _image) {
		SetValue("u_TintColor", Color.white);
		SetAlbedo(_image);
		SetTransform(x, y, 0, 0, 0, 0, w, h, 1);
		DrawMesh();
		
	}
	
	public void DrawScaledImage(float x, float y, float w, float h, SubTexture2D _image) {
		SetValue("u_TintColor", Color.white);
		SetAlbedo(_image.GetSourceTexture());
		SetTextureCoords(_image.uvs);
		SetTransform(x, y, 0, 0, 0, 0, w, h, 1);
		DrawMesh();
		SetTextureCoords(textureCoords);
	}

}
