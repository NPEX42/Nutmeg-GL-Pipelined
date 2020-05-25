package nutmeg.gl.rendering;

public class SubTexture2D {
	private TextureAtlas2D atlas;
	float height, width, tileHeight, tileWidth, w, h;
	float[] uvs;
	public SubTexture2D(TextureAtlas2D atlas,  float x, float y, float w, float h) {
		this.atlas = atlas;
		this.height = atlas.height;
		this.width = atlas.width;
		this.tileHeight = atlas.tileHeight;
		this.tileWidth = atlas.tileWidth;
		
		this.w = w;
		this.h = h;
		
		uvs = new float[] {
				( x * tileWidth) / width,      ( y * tileHeight) / height,
				((x + w) * tileWidth) / width, ( y * tileHeight) / height,
				((x + w) * tileWidth) / width, ((y + h) * tileHeight) / height,
				( x * tileWidth) / width,      ((y + h) * tileHeight) / height,
		};
		
	}
	
	public float[] GetTextureCoordinates() {
		return uvs;
	}
	
	public float GetWidth() {return w * tileWidth;}
	public float GetHeight() {return h * tileHeight;}
	
	public Texture2D GetSourceTexture() {
		return atlas.sourceTexture;
	}
	
}
