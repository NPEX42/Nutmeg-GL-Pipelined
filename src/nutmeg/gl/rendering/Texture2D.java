package nutmeg.gl.rendering;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

import static nutmeg.gl.Nutmeg.*;

public class Texture2D {
	
	public static Texture2D whiteTex, missingTex;
	
	int ID, w, h;
	ByteBuffer texture;
	public Texture2D(ByteBuffer data, int width, int height, int channels, int BytesPerChannel) {
		ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glGenerateMipmap(GL_TEXTURE_2D);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		texture = data;
		w = width;
		h = height;
		
		System.err.println("Building Texture #"+ID);
	}
	
	public static Texture2D Build(Color[] pixels, int width, int height) {
		ByteBuffer textureBuffer = MemoryUtil.memAlloc(pixels.length * Integer.BYTES);
		for(Color c : pixels) {
			//OpenGL uses RGBA
			textureBuffer.put((byte) c.getRed());
			textureBuffer.put((byte) c.getGreen());
			textureBuffer.put((byte) c.getBlue());
			textureBuffer.put((byte) c.getAlpha());
		}
		
		return new Texture2D(textureBuffer, width, height, NM_RGBA, NM_RGBA8_BYTES);
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
	
	public void Bind     (        ) { glBindTexture(GL_TEXTURE_2D, ID); }
	public void SetSlot  (int Slot) { glActiveTexture(Slot);            }
	public void Delete   (        ) { glDeleteTextures(ID);             }
	public int  GetID    (        ) { return ID;                        }
	public int  GetHeight(        ) { return h;                         } 
	public int  GetWidth (        ) { return w;                         } 
	
	public static Texture2D GetWhiteTex() {
		if(whiteTex == null) whiteTex = Load("nutmeg/gl/rendering/textures/white.png", Texture2D.class.getClassLoader());
		return whiteTex;
	} 
	
	public static Texture2D GetMissingTex() {
		if(missingTex == null) missingTex = Load("nutmeg/gl/rendering/textures/missing.png", Texture2D.class.getClassLoader());
		return missingTex;
	} 
	
	private static Texture2D Load(String filePath, ClassLoader classLoader) {
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		BufferedImage img = null;
		try {
			
			InputStream stream = classLoader.getResourceAsStream(filePath);
			if(stream == null) { System.err.println("Unable To Load file '"+filePath+"'"); return null; }
		    img = ImageIO.read(stream);
		    Texture2D tex = loadTexture(img);
		    tp2 = System.currentTimeMillis();
			System.err.println("Texture Loaded In "+(tp2 - tp1)+"ms");
			return tex;
		} catch (IOException e) {
			System.err.println("Unable To Load file '"+filePath+"'");
			return null;
		}
	}
	
	private final static int BYTES_PER_PIXEL = 4;
	private static Texture2D loadTexture(BufferedImage image){
		 
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = MemoryUtil.memAlloc(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		 
		for(int y = 0; y < image.getHeight(); y++){
		   for(int x = 0; x < image.getWidth(); x++){
		       int pixel = pixels[y * image.getWidth() + x];
		       buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
		       buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
		       buffer.put((byte) (pixel & 0xFF));               // Blue component
		       buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
		   }
		}
		 
		 buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS
		 return new Texture2D(buffer, image.getWidth(), image.getHeight(), 4, 1);
	}

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
