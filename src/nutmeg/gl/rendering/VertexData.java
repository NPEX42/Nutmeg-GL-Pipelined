package nutmeg.gl.rendering;
import static nutmeg.gl.Nutmeg.*;
@SuppressWarnings("unused")
public class VertexData {
	public static final int VERTEX_SIZE_BYTES = NM_VEC3F_BYTES + NM_VEC2F_BYTES + NM_VEC3F_BYTES + 4;
	private float[]
			vertexPositions, //Holds 3D OpenGL-Coordinate Positions
			normals, //Holds Normals
			textureCoords, //Holds UV / SV
			textureIDs;
	private int[] 
			indices; //Maps triangles
	public VertexData(float[] vertexPositions, float[] normals, float[] textureCoords, int[] indices) {
		super();
		this.vertexPositions = vertexPositions;
		this.normals = normals;
		this.textureCoords = textureCoords;
		this.indices = indices;
	}
	
	public VertexData() {
		vertexPositions = new float[0];
		normals = new float[0];
		textureCoords = new float[0];
		indices = new int[0];
		indexPtr = indices.length;
	}
	
	public final float[] getVertexPositions() {
		return vertexPositions;
	}
	public final void setVertexPositions(float[] vertexPositions) {
		this.vertexPositions = vertexPositions;
	}
	public final float[] getNormals() {
		return normals;
	}
	public final void setNormals(float[] normals) {
		this.normals = normals;
	}
	public final float[] getTextureCoords() {
		return textureCoords;
	}
	public final void setTextureCoords(float[] textureCoords) {
		this.textureCoords = textureCoords;
	}
	public final int[] getIndices() {
		return indices;
	}
	public final void setIndices(int[] indices) {
		this.indices = indices;
	}
	
	public final void addData(VertexData _data) {
		vertexPositions = combine(vertexPositions, _data.vertexPositions);
		normals = combine(normals, _data.normals);
		textureCoords = combine(textureCoords, _data.textureCoords);
		
		indices = combine(indices, _data.indices);
	}
	
	public void addVertexPositions(float[] data) {
		vertexPositions = combine(vertexPositions, data);
	}
	
	public void addNormals(float[] data) {
		normals = combine(normals, data);
	}
	
	public void addTextureCoords(float[] data) {
		textureCoords = combine(textureCoords, data);
	}
	int indexPtr;
	public void addIndices(int[] data) {
		System.err.println(indexPtr);
		for(int i = 0; i < data.length; i++) data[i] += indexPtr;
		indices = combine(indices, data);
		indexPtr = indices.length;
	}
	public int[] combine(int[] a, int[] b){
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	public float[] combine(float[] a, float[] b){
        int length = a.length + b.length;
        float[] result = new float[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	public final int GetVertexCount() {
		return indices.length;
	}
}
