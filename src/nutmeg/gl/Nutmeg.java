package nutmeg.gl;
import static org.lwjgl.opengl.GL46.*;

import org.joml.Vector3f;
public class Nutmeg {
	public static final int
	NM_RENDER_TRIANGLES = GL_TRIANGLES,
	
	NM_VEC1F_BYTES = 4,
	NM_VEC2F_BYTES = 8,
	NM_VEC3F_BYTES = 12,
	NM_VEC4F_BYTES = 16,
	
	NM_VEC1 = 1,
	NM_VEC2 = 2,
	NM_VEC3 = 3,
	NM_VEC4 = 4,
	
	NM_POSITION = 0,
	NM_UV = 1,
	NM_NORMAL = 2,
	NM_COLOR = 3,
	NM_TEXTURE_BANK = 4,
	
	NM_UINT32 = GL_UNSIGNED_INT,
	NM_UINT16 = GL_UNSIGNED_SHORT,
	NM_UINT8 = GL_UNSIGNED_BYTE,
	NM_FLOAT32 = GL_FLOAT,
	NM_FLOAT64 = GL_DOUBLE,
	NM_INT8 = GL_BYTE,
	NM_INT16 = GL_SHORT,
	NM_INT32 = GL_INT,
	

	NM_FRAGMENT_SHADER = GL_FRAGMENT_SHADER,
	NM_VERTEX_SHADER = GL_VERTEX_SHADER;
	
	public static final Vector3f 
	NM_POS_X = new Vector3f(1,0,0),
	NM_POS_Y = new Vector3f(0,1,0),
	NM_POS_Z = new Vector3f(0,0,1),
	
	NM_NEG_X = new Vector3f(-1,0,0),
	NM_NEG_Y = new Vector3f(0,-1,0),
	NM_NEG_Z = new Vector3f(0,0,-1); 

}
