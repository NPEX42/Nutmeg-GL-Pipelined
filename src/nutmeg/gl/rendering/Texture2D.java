package nutmeg.gl.rendering;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

import java.awt.Color;
import java.nio.ByteBuffer;

import static nutmeg.gl.Nutmeg.*;

public class Texture2D {
	int ID, w, h;
	ByteBuffer texture;
	public Texture2D(ByteBuffer data, int width, int height, int channels, int BytesPerChannel) {
		ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);;
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		texture = data;
		w = width;
		h = height;
		
		System.err.println("Building Texture #"+ID);
	}
	
	public static Texture2D Load(String filePath) {
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		int[] width = new int[1], height = new int[1], channels = new int[1];
		ByteBuffer data;
		data = stbi_load(filePath, width, height, channels, NM_RGBA);
		tp2 = System.currentTimeMillis();
		System.err.println("Texture Loaded In "+(tp2 - tp1)+"ms");
		return LoadRGBA8(data, width[0], height[0]);
	}
	
	public static Texture2D LoadRGBA8(ByteBuffer data, int width, int height) {
		return new Texture2D(data, width, height, NM_RGBA, NM_RGBA8_BYTES);
	}
	
	public void Bind   (        ) { glBindTexture(GL_TEXTURE_2D, ID); }
	public void SetSlot(int Slot) { glActiveTexture(Slot);            }
	public void Delete (        ) { glDeleteTextures(ID);             }
	public int  GetID  (        ) { return ID;                        }
	
	public Color GetPixel2D(int x, int y) {
		return GetPixel1D(y * w + x); 
	} 
	
	public Color GetPixel1D(int pos) {
		if(pos <= 0) pos = 0;
		if(pos >= texture.capacity() - 4) pos = texture.capacity() - 4;
		byte R,G,B,A;
		R = texture.get(pos + 0);
		G = texture.get(pos + 1);
		B = texture.get(pos + 2);
		A = texture.get(pos + 3);
		return new Color(R,G,B,A);
	}
	
	
	
}
