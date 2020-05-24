package nutmeg.gl.rendering;

public class TextureAtlas2D {
	protected Texture2D sourceTexture;
	protected float height, width, tileHeight, tileWidth;
	public TextureAtlas2D(Texture2D sourceTexture, float tileHeight, float tileWidth) {
		super();
		this.sourceTexture = sourceTexture;
		this.height = sourceTexture.h;
		this.width = sourceTexture.w;
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
	}
	
	
}
