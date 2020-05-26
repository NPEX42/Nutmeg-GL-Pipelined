package nutmeg.gl.rendering;
public class BatchedRenderPipeline extends RenderPipeline {

	private int maxVertexCount = 1000;
	VertexData data;
	public BatchedRenderPipeline(int _mode, int _maxVertexCount) {
		super(_mode, new String[] {"nutmeg/gl/rendering/shaders/batched.v.glsl", "nutmeg/gl/rendering/shaders/batched.f.glsl"});
		maxVertexCount = _maxVertexCount;
		data = new VertexData();
	}
	

	public void Submit(int[] _indices, float[] _positions, float[] _textureCoords, float[] _normals) {
		data.addVertexPositions(_positions);
		data.addTextureCoords(_textureCoords);
		data.addNormals(_normals);
		data.addIndices(_indices);
		if(data.GetVertexCount() > maxVertexCount) Flush();
	}
	
	public void Flush() {
		System.err.println("Flushing At "+data.GetVertexCount());
		DrawMesh(new MeshData(data));
		Reset();
	}
	
	public void Reset() {
		data = new VertexData();
	}

}
