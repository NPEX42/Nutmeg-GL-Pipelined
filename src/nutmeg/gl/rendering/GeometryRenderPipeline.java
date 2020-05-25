package nutmeg.gl.rendering;

public class GeometryRenderPipeline extends RenderPipeline3D {

	public GeometryRenderPipeline(int _mode) {
		super(_mode, new String[] {"nutmeg/gl/rendering/shaders/geom.v.glsl", "nutmeg/gl/rendering/shaders/geom.f.glsl"});
		// TODO Auto-generated constructor stub
	}

}
