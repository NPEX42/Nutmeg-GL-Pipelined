package nutmeg.gl.rendering;


@SuppressWarnings("unused")
public class MeshData {
	private VertexData vertexData;
	private Texture2D albedo, normalMap, specularMap;
	
	private int vertexCount = 0;
	
	public MeshData(VertexData data) {
		if(data != null) {
			vertexData = data;
			vertexCount += data.GetVertexCount();
		} else {
			throw new RuntimeException("Vertex Data Is Null!");
		}
	}
	
	public MeshData(VertexData data, Texture2D _albedo) {
		if(data != null) {
			vertexData = data;
			vertexCount += data.GetVertexCount();
			albedo = _albedo;
		} else {
			throw new RuntimeException("Vertex Data Is Null!");
		}
	}
	
	public void addVertexData(VertexData data) {
		vertexData.addData(data);
		vertexCount = vertexData.GetVertexCount();
	}
	
	public VertexData GetVertexData () { return vertexData; }
	public Texture2D  GetAlbedo     () { return albedo;     }
	public Texture2D  GetNormalMap  () { return normalMap;  }
	public Texture2D  GetSpecularMap() { return specularMap;}
	
	public void SetAlbedo     (Texture2D _albedo     ) {albedo = _albedo;          }  
	public void SetNormalMap  (Texture2D _normalMap  ) {normalMap = _normalMap;    } 
	public void SetSpecularMAp(Texture2D _specularMap) {specularMap = _specularMap;} 
	
	
	
}
