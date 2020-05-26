package nutmeg.gl.core;

import java.awt.Color;

import nutmeg.gl.core.window.DisplayManager;

public abstract class Application {
	protected float width, height;
	protected int frameRate = 60, smoothFrameRate;
	protected int frameCount = 0;
	private int FPS_BUF_LEN = 60;
	private float[] FPSBuffer = new float[FPS_BUF_LEN];
	
	public void ConstructWindow(float _width, float _height, String title, boolean vsync) {
		DisplayManager.Open((int)_width, (int)_height, title, vsync);
		long tp1, tp2;
		width = _width;
		height = _height;
		OnApplicationInit();
		if(!OnUserCreate()) { DisplayManager.Close(); System.exit(-1); } 
		while(DisplayManager.DisplayIsActive()) {
			tp1 = System.currentTimeMillis();
			OnApplicationUpdate();
			if(!OnUserUpdate()) break;
			DisplayManager.Update();
			tp2 = System.currentTimeMillis();
			Time.SetDeltaTime((tp2 - tp1) / 1000f);
			width = DisplayManager.getWidth();
			height = DisplayManager.getHeight();
			frameRate = (int) (1 / Time.GetDeltaTime());
			frameCount++;
			
			FPSBuffer[frameCount % FPS_BUF_LEN] = frameRate;
			smoothFrameRate = (int) Average(FPSBuffer);
		}
		if(OnUserDestroy());
		OnApplicationDestroy();
		DisplayManager.Close();
	}
	
	private float Average(float[] array) {
		float sum = 0;
		float length = array.length; 
			for(float f : array) sum += f;
		return sum / length;
	}
	
	public abstract void Background(Color color);
	
	public void SetTitle(String title) {
		DisplayManager.SetTitle(title);
	}
	
	public abstract boolean OnUserCreate();
	public abstract boolean OnUserUpdate();
	public abstract boolean OnUserDestroy();
	
	public abstract boolean OnApplicationInit();
	public abstract boolean OnApplicationDestroy();
	public abstract boolean OnApplicationUpdate();
}
