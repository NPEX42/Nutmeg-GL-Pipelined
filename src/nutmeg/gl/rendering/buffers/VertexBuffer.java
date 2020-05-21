package nutmeg.gl.rendering.buffers;

import static org.lwjgl.opengl.GL46.*;

public class VertexBuffer {
	private int id, index;
	private VertexBuffer(int _id, int _index) {
		id = _id;
		index = _index;
		Enable();
	}
	
	public static VertexBuffer Create(int index, float[] data, int coordSize) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new VertexBuffer(id, index);
	}
	
	public static VertexBuffer Create(int index, short[] data, int coordSize) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new VertexBuffer(id, index);
	}
	
	public static VertexBuffer Create(int index, int[] data, int coordSize) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new VertexBuffer(id, index);
	}
	
	public static VertexBuffer Create(int index, long[] data, int coordSize) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new VertexBuffer(id, index);
	}
	
	public static VertexBuffer Create(int index, double[] data, int coordSize) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new VertexBuffer(id, index);
	}
	
	public void Bind()   { glBindBuffer(GL_ARRAY_BUFFER, id); }
	public void Enable() { glEnableVertexAttribArray(index);  }
	public void Delete() { glDeleteBuffers(id);               }
}
