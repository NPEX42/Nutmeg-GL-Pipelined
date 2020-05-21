package nutmeg.gl.rendering;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL46.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import nutmeg.gl.core.IO;

import static nutmeg.gl.Nutmeg.*;
public class ShaderPipeline {
	private int programID, vertexID, fragmentID;
	
	private ShaderPipeline(int programID, int vertexID, int fragmentID) {
		super();
		this.programID = programID;
		this.vertexID = vertexID;
		this.fragmentID = fragmentID;
	}

	public static ShaderPipeline Build(String vertexSource, String fragmentSource) {
		int programID = glCreateProgram();
		int vertexID = glCreateShader(NM_VERTEX_SHADER);
		int fragmentID = glCreateShader(NM_FRAGMENT_SHADER);
		
		glShaderSource(vertexID, vertexSource);
		glShaderSource(fragmentID, fragmentSource);
		
		glCompileShader(vertexID);
		glCompileShader(fragmentID);
		
		glAttachShader(programID, vertexID);
		glAttachShader(programID, fragmentID);
		
		if(glGetShaderi(vertexID, GL_COMPILE_STATUS ) == GL_FALSE){
			System.err.println("Unable to Compile Vertex Shader");
			System.err.println(glGetShaderInfoLog(vertexID));
			return null;
		}
		
		if(glGetShaderi(fragmentID, GL_COMPILE_STATUS ) == GL_FALSE){
			System.err.println("Unable to Compile Fragment Shader");
			System.err.println(glGetShaderInfoLog(fragmentID));
			return null;
		}
		
		glLinkProgram(programID);
		
		if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println(glGetProgramInfoLog(programID, 1024));
			return null;
		}
		
		glUseProgram(programID);
		
		return new ShaderPipeline(programID, vertexID, fragmentID);
	}
	
	public static ShaderPipeline BuildFromExtFile(String vertexPath, String fragmentPath) {
		
		String vertexSource = null, fragmentSource = null;
		
		vertexSource = IO.loadString(vertexPath);
		if(vertexSource == null) return null;
		
		fragmentSource = IO.loadString(fragmentPath);
		if(fragmentSource == null) return null;
	
		
		return Build(vertexSource, fragmentSource);
	}
	
	public static ShaderPipeline BuildFromIntFile(ClassLoader  loader, String vertexPath, String fragmentPath) {
		String vertexSource = null, fragmentSource = null;
		
		try {
			vertexSource = IO.loadString(loader.getResourceAsStream(vertexPath));
			if(vertexSource == null) throw new FileNotFoundException("Couldn't Locate File '"+vertexPath+"'");
			
			fragmentSource = IO.loadString(loader.getResourceAsStream(fragmentPath));
			if(fragmentSource == null) throw new FileNotFoundException("Couldn't Locate File '"+fragmentPath+"'");
		} catch (IOException e) {
			return null;
		}
	
		
		return Build(vertexSource, fragmentSource);
	}
	
	public void Bind() { glUseProgram(programID); }
}
