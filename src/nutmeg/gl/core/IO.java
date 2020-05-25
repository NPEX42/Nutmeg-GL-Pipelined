package nutmeg.gl.core;

import java.io.*;
import java.util.*;
import org.joml.*;

import nutmeg.gl.rendering.MeshData;
import nutmeg.gl.rendering.Texture2D;
import nutmeg.gl.rendering.VertexData;

public class IO {
	public static String loadString(String filePath) {
		try {
			return loadString(new FileInputStream(filePath));
		} catch(IOException ioex) {
			System.err.println(ioex.getMessage());
			return null;
		}
	}
	
	public static String loadString(InputStream stream) throws IOException {
		if(stream == null) throw new IOException("Unable To Use Stream, Stream is Null!");
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder buffer = new StringBuilder();
		while((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
		}
		reader.close();
		tp2 = System.currentTimeMillis();
		System.err.println("File Loaded In "+(tp2 - tp1)+"ms");
		return buffer.toString();
	}
	
	public static MeshData loadMeshData(String filePath) {
		try {
			return loadMeshData(new FileInputStream(filePath));
		} catch(IOException ioex) {
			System.err.println(ioex.getMessage());
			return null;
		}
	}
	
	
	public static MeshData loadMeshData(InputStream _stream) {
		try {
			VertexData data = new VertexData();
			String albedoPath = null;
			String src = loadString(_stream);
			String[] source = src.split("\n");
			for(String line : source) {
				String[] parts = line.split("[\\s]+");
				
				if(parts[0].equals("v")) {
					for(int i = 1; i < parts.length; i++) {
						data.addVertexPositions(new float[] {Float.parseFloat(parts[i])});
					}
				}
				
				if(parts[0].equals("vn")) {
					for(int i = 1; i < parts.length; i++) {
						data.addNormals(new float[] {Float.parseFloat(parts[i])});
					}
				}
				
				if(parts[0].equals("vt")) {
					for(int i = 1; i < parts.length; i++) {
						data.addTextureCoords(new float[] {Float.parseFloat(parts[i])});
					}
				}
				
				if(parts[0].equals("f")) {
					for(int i = 1; i < parts.length; i++) {
						data.addIndices(new int[] {Integer.parseInt(parts[i])});
					}
				}
				
				if(parts[0].equals("albedo")) {
					albedoPath = parts[1];
				}
			}
			if(albedoPath == null) {
				return new MeshData(data, Texture2D.GetWhiteTex());
			} else {
				return new MeshData(data, Texture2D.Load(albedoPath));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public static VertexData loadOBJ(String filePath) {
		try {
			return loadOBJ(new FileInputStream(filePath));
		} catch(IOException ioex) {
			return loadOBJ(IO.class.getClassLoader().getResourceAsStream("nutmeg/gl/rendering/models/ico_Error.obj"));
		}
	}
	
	public static VertexData loadOBJ(InputStream stream) {
		InputStreamReader fr;
		fr = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		try {

			while (true) {
				line = reader.readLine();
				//System.err.println(line);
				String[] currentLine = line.split("[\\s]+");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(
							Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3])
					);
					vertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					textures.add(texture);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}

			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split("[\\s]+");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1,indices,textures,normals,textureArray,normalsArray);
				processVertex(vertex2,indices,textures,normals,textureArray,normalsArray);
				processVertex(vertex3,indices,textures,normals,textureArray,normalsArray);
				line = reader.readLine();
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex:vertices){
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i=0;i<indices.size();i++){
			indicesArray[i] = indices.get(i);
		}
		return new VertexData(verticesArray, textureArray, normalsArray, indicesArray);

	}

	private static void processVertex(String[] vertexData, List<Integer> indices,
			List<Vector2f> textures, List<Vector3f> normals, float[] textureArray,
			float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;	
	}
	
}