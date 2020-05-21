package nutmeg.gl.rendering.buffers;
import static org.lwjgl.opengl.GL46.*;
public class VertexArray {
	private int ID;
	public VertexArray() {
		ID = glGenVertexArrays();
	}
	
	public void Bind() { glBindVertexArray(ID); }
}
