package nutmeg.gl.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
	
}