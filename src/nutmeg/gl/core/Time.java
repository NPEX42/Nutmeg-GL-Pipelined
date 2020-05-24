package nutmeg.gl.core;

public class Time {
	private   static float deltaTime;
	public    static float GetDeltaTime(        ) { return deltaTime; }
	protected static void  SetDeltaTime(float dt) { deltaTime = dt;   }
}
