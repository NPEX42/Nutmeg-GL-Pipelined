package nutmeg.gl.rendering;
@SuppressWarnings("unused")
public class MeshData {
	private VertexData vertexData;
	private Texture2D albedo, normalMap, specularMap;
	
	private int vertexCount = 0;
	
	public MeshData(VertexData data) {
		vertexData = data;
		vertexCount += data.GetVertexCount();
	}
	
	public void addVertexData(VertexData data) {
		vertexData.addData(data);
		vertexCount = vertexData.GetVertexCount();
	}
	
	
	
}
