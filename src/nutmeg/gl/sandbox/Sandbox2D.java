package nutmeg.gl.sandbox;

import nutmeg.gl.core.Application2D;
import nutmeg.gl.core.input.Mouse;
import nutmeg.gl.rendering.Texture2D;

import java.awt.Color;

public class Sandbox2D extends Application2D {
	Texture2D image;
	
	
	public static void main(String[] args) {
		new Sandbox2D().ConstructWindow(1080, 720, "Nutmeg-GL", true);
	}

	@Override
	public boolean OnUserCreate() {
		// TODO Auto-generated method stub
		image = Texture2D.Load("res/Pyramid-Test/Triangle.png");
		if(image == null) return false;
		return true;
	}
	float theta = 0;
	@Override
	public boolean OnUserUpdate() {
		// TODO Auto-generated method stub
		Background(Color.GRAY);
		SetTitle("Nutmeg-GL - "+smoothFrameRate+" fps - MX: "+Mouse.GetMouseX()+" - MY: "+Mouse.GetMouseY()+" - Thread: "+GetThreadName());
		Delay(1);
		DrawRect(width / 2,height - image.GetHeight(), 256, 256, image);
		return true;
	}

	@Override
	public boolean OnUserDestroy() {
		image.Delete();
		return true;
	}
}
