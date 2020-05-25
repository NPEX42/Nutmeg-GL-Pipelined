package nutmeg.gl.sandbox;

import nutmeg.gl.core.Application2D;
import nutmeg.gl.rendering.Texture2D;

import java.awt.Color;

public class Sandbox2D extends Application2D {
	Texture2D image;
	
	//int[] memory = new int[]
	
	static float tileH = 8, tileW = 8, w = 60, h = 55;
	int[] memory = new int[(int) (w * h)];
	Color[] pallete = {
			new Color(0x000000),
			new Color(0x0000FF),
			new Color(0x00FF00),
			new Color(0x00FFFF),
			new Color(0xFF0000),
			new Color(0xFF00FF),
			new Color(0xFFFF00),
			new Color(0xFFFFFF),
	};
	public static void main(String[] args) {
		new Sandbox2D().ConstructWindow(tileW * w - tileW, tileH * h - tileH, "Nutmeg-GL", false);
	}

	@Override
	public boolean OnUserCreate() {
		// TODO Auto-generated method stub
		image = Texture2D.Load("res/textures/UV_Test.png");
		return true;
	}
	float theta = 0;
	@Override
	public boolean OnUserUpdate() {
		// TODO Auto-generated method stub
		Background(Color.black);
		SetTitle("Nutmeg-GL - "+smoothFrameRate+" fps - Rendering "+(w * h)+" Tiles Per Frame");
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				//Color color = pallete[(memory[(int) (y * w + x)]) % pallete.length];
				Color color = pallete[x % pallete.length];
				DrawRect(x * tileW, y * tileH, tileW, tileH,color);
			}
		} 
		return true;
	}

	@Override
	public boolean OnUserDestroy() {
		image.Delete();
		return true;
	}
}
