package nutmeg.gl.core.input;
import static org.lwjgl.glfw.GLFW.*;

import nutmeg.gl.core.window.DisplayManager;
public class Keyboard {
	public static boolean isKeyPressed(char key) {
		return glfwGetKey(DisplayManager.getWindowID(), key) == GLFW_PRESS;
	}
	
	public static boolean isKeyReleased(char key) {
		return glfwGetKey(DisplayManager.getWindowID(), key) == GLFW_RELEASE;
	}
}
