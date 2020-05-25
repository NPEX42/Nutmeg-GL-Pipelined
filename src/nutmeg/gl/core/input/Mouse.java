package nutmeg.gl.core.input;
import static org.lwjgl.glfw.GLFW.*;

import nutmeg.gl.core.window.DisplayManager;
public class Mouse {
	public static boolean IsButtonDown(int button) {
		glfwPollEvents();
		return glfwGetMouseButton(DisplayManager.getWindowID(), button) == GLFW_PRESS;
	}
	
	public static boolean IsButtonReleased(int button) {
		glfwPollEvents();
		return glfwGetMouseButton(DisplayManager.getWindowID(), button) == GLFW_RELEASE;
	}
	
	public static int GetMouseX() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(DisplayManager.getWindowID(), x, y);
		return (int) x[0];
	}
	
	public static int GetMouseY() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(DisplayManager.getWindowID(), x, y);
		return (int) y[0];
	}
	
	static int oldX, oldY;
	public static int GetMouseDY() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(DisplayManager.getWindowID(), x, y);
		int DY = (int) y[0] - oldY;
		oldY = (int) y[0];
		return DY;
	}
	
	public static int GetMouseDX() {
		glfwPollEvents();
		double[] x = new double[1], y = new double[1];
		glfwGetCursorPos(DisplayManager.getWindowID(), x, y);
		int DX = (int) x[0] - oldX;
		oldX = (int) x[0];
		return DX;
	}
}
