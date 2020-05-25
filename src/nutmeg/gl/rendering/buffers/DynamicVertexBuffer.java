package nutmeg.gl.rendering.buffers;

import static org.lwjgl.opengl.GL46.*;
@Deprecated
@SuppressWarnings("unused")
public class DynamicVertexBuffer {
	private int index, id, size;
	private DynamicVertexBuffer(int _index, int _id, int _size) {
		index = _index;
		size = _size;
		id = _id;
	}
	public static DynamicVertexBuffer Create(int index, float[] data, int coordSize) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, data, GL_DYNAMIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new DynamicVertexBuffer(index, id, data.length);
	}
	
	public static DynamicVertexBuffer CreateFloatBuffer(int index, int coordSize, int size) {
		int id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);
		glVertexAttribPointer(index, coordSize, GL_FLOAT, false, 0, 0);
		return new DynamicVertexBuffer(index, id, size);
	}
	
	public void Bind() {
		glBindBuffer(GL_ARRAY_BUFFER, id);
	}
	@Deprecated
	public void ClearData() {
		Bind();
		//glBufferData(GL_ARRAY_BUFFER, null, GL_DYNAMIC_DRAW);
	}
}
