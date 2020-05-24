package nutmeg.gl.core.window;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
public final class DisplayManager {
	public static final int 
		NM_DEFAULT = 0;
	private static long windowID;
	private static float width, height;
	
	public static void Open(int _width, int _height, String _title, boolean _EnableVsync)  {
		if(!glfwInit() || windowID != 0) throw new RuntimeException("Unable To Open window...");
		windowID = glfwCreateWindow(_width, _height, _title, NM_DEFAULT, NM_DEFAULT);
		glfwMakeContextCurrent(windowID);
		createCapabilities();
		glfwShowWindow(windowID);
		
		glfwSetWindowSizeCallback(windowID, (long id, int w, int h) -> {
			glViewport(0, 0, w, h);
			width = w;
			height = h;
		});
		
		width = _width;
		height = _height;
		glfwSwapInterval((_EnableVsync ? 1 : 0));
	}
	
	public static void Update() {
		glfwSwapBuffers(windowID);
		glfwPollEvents();
	}
	
	public static void Close() {
		glfwDestroyWindow(windowID);
		glfwTerminate();
	}
	
	public static boolean DisplayIsActive() {
		return !glfwWindowShouldClose(windowID);
	}

	public static final float getWidth() {
		return width;
	}

	public static final float getHeight() {
		return height;
	}
	
	public static final long getWindowID() {
		return windowID;
	}
	
	public static void SetTitle(String _title) {
		//System.err.println("Debug--"+_title);
		glfwSetWindowTitle(windowID, _title);
	} 
}
