package nutmeg.gl.core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
