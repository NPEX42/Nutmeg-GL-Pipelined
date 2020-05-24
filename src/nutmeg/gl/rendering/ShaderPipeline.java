package nutmeg.gl.rendering;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL46.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import nutmeg.gl.core.IO;

import static nutmeg.gl.Nutmeg.*;
@SuppressWarnings("unused")
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
	
	private HashMap<String, Integer> uniformCache = new HashMap<String, Integer>();
	private int getUniform(String name) {
		Bind();
		if(uniformCache.containsKey(name)) {
			//Logger.log(name+": "+uniformCache.get(name));
			return uniformCache.get(name);
		} else {
			int loc = glGetUniformLocation(programID, name);
			if(loc == -1 && NM_DEBUG_MODE) System.err.println("Couldn't Load Uniform '"+name+"'");
			if(loc > -1) uniformCache.put(name, loc);
			return loc;
		}
	}
	
	public void SetValue(String name, int value) {
		glUniform1i(getUniform(name), value);
	} 
	public void SetValue(String name, float value) {
		glUniform1f(getUniform(name), value);
	} 
	public void SetValue(String name, Vector2f vec) {} 
	public void SetValue(String name, Vector3f vec) {
	} 
	public void SetValue(String name, Vector4f vec) {
		glUniform4f(getUniform(name), vec.x, vec.y, vec.z, vec.w);
	} 
	public void SetValue(String name, Matrix2f mat) {
		glUniformMatrix4fv(getUniform(name), false, mat.get(new float[4]));
	} 
	public void SetValue(String name, Matrix3f mat) {
		glUniformMatrix4fv(getUniform(name), false, mat.get(new float[9]));
	} 
	public void SetValue(String name, Matrix4f mat) {
		glUniformMatrix4fv(getUniform(name), false, mat.get(new float[16]));
	} 
	
	public void Delete() {
		glDeleteProgram(programID);
	}
}
