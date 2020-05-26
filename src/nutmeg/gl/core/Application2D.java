package nutmeg.gl.core;

import nutmeg.gl.core.window.DisplayManager;
import nutmeg.gl.rendering.RenderPipeline2D;
import nutmeg.gl.rendering.Texture2D;

import static nutmeg.gl.Nutmeg.*;

import java.awt.Color;

public abstract class Application2D extends Application{
	private RenderPipeline2D pipeline;
	
	@Override
	public void ConstructWindow(float _width, float _height, String title, boolean vsync) {
		super.ConstructWindow(_width, _height, title, vsync);
	}
	
	public boolean OnApplicationInit() {
		pipeline = new RenderPipeline2D(NM_TRIANGLES);
		return true;
	}
	
	public boolean OnApplicationUpdate() {
		//pipeline.Flush();
		pipeline.SetOrthographic(DisplayManager.getWidth(), DisplayManager.getHeight(), 1, -1);
		return true;
	}
	
	public boolean OnApplicationDestroy() {
		pipeline.Destroy();
		return true;
	}

	protected void DrawRect(float x, float y, float w, float h, Color color) {
		pipeline.DrawRect(x, y, w, h, color);
	}
	
	protected void DrawRect(float x, float y, float rot, float w, float h, Color color) {
		pipeline.SetTransform2D(x, y, rot, w, h);
		pipeline.DrawRect(color);
	}
	
	protected void DrawRect(float x, float y, Texture2D image) {
		pipeline.DrawImage(x, y, image);
	}
	
	protected void DrawRect(float x, float y, float w, float h,  Texture2D image) {
		pipeline.DrawScaledImage(x, y, w, h, image);
	}
	
	protected void DrawRect(float x, float y, float rot, float w, float h,  Texture2D image) {
		pipeline.SetTransform2D(x, y, rot, w, h);
		pipeline.DrawImage(image);
	}
	
	public void Background(Color color) {
		pipeline.Clear(color);
	}
	
	public void Delay(int milliseconds) {
		try { Thread.sleep(milliseconds); } catch(InterruptedException inex) {}
	}
	
	public String GetThreadName() {
		return Thread.currentThread().getName();
	}
	
}
