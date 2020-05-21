package nutmeg.gl.sandbox;

import nutmeg.gl.core.window.DisplayManager;

public class Sandbox {
	public static void main(String[] args) {
		DisplayManager.Open(1080, 720, "Nutmeg-GL - Sandbox", true);
		while(DisplayManager.DisplayIsActive()) {
			DisplayManager.Update();
		}
		DisplayManager.Close();
	}
}
