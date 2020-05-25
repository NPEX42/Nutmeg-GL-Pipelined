package nutmeg.gl.sandbox;

import java.awt.Color;

import nutmeg.gl.core.Application3D;
import nutmeg.gl.core.IO;
import nutmeg.gl.core.Time;
import nutmeg.gl.core.input.Keyboard;
import nutmeg.gl.rendering.MeshData;
import nutmeg.gl.rendering.Texture2D;

public class Sandbox3D extends Application3D {
	private static final int MOVE_SPEED = 5, ROTATE_SPEED = 1;
	MeshData monkey, mountains;
	
	public static void main(String[] args) {
		new Sandbox3D().ConstructWindow(1080, 720,  "Nutmeg-GL", false);
	}
	
	@Override
	public boolean OnUserCreate() {
		monkey = new MeshData(IO.loadOBJ("res/models/cube.obj"));
		Translate(0, 0, -10);
		Scale(100, 100, 1);
		//SetGlobalLightDir(0, 0, -1);
		return true;
	}

	@Override
	public boolean OnUserUpdate() {
		Background(Color.orange);
		Rotate(0, 1 * Time.GetDeltaTime(), 0);
		
//		if(Keyboard.isKeyPressed('A')) TranslateCamera(-1 * MOVE_SPEED * Time.GetDeltaTime(), 0, 0);
//		if(Keyboard.isKeyPressed('D')) TranslateCamera( 1 * MOVE_SPEED * Time.GetDeltaTime(), 0, 0);
		
		if(Keyboard.isKeyPressed('W')) TranslateCamera(-forwardVector.x *  MOVE_SPEED * Time.GetDeltaTime(), -forwardVector.y * MOVE_SPEED *Time.GetDeltaTime(), -forwardVector.z * MOVE_SPEED * Time.GetDeltaTime());
		if(Keyboard.isKeyPressed('S')) TranslateCamera( forwardVector.x * Time.GetDeltaTime(),  forwardVector.y * Time.GetDeltaTime(),  forwardVector.z * Time.GetDeltaTime());
		
		if(Keyboard.isKeyPressed('Q')) RotateCamera(0, 1 * ROTATE_SPEED * Time.GetDeltaTime(), 0);
		if(Keyboard.isKeyPressed('E')) RotateCamera(0,-1 * ROTATE_SPEED * Time.GetDeltaTime(), 0);
		DrawMesh(monkey);
		
		return true;
	}

	@Override
	public boolean OnUserDestroy() {
		// TODO Auto-generated method stub
		return true;
	}

}
