package nutmeg.gl.rendering.buffers;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL46.*;


public class IndexBuffer {
	public int id, length;
	public IndexBuffer(int _id, int _length) {
		id = _id;
		length = _length;
	}
	
	public static IndexBuffer Create(int[] indices) {
		int id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		return new IndexBuffer(id, indices.length);
	}
	
	public void Delete() { glDeleteBuffers(id);               }
}
