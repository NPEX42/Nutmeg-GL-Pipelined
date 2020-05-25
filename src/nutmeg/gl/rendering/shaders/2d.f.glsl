#version 330 core
out vec4 v_PixelColor;
uniform vec4 u_TintColor;
in vec2 v_UV;
uniform sampler2D t_Albedo;

void main() {
	vec4 texColor = texture(t_Albedo,v_UV);
	if(texColor.a < 1) {
		discard;
	} else {
		v_PixelColor = texture(t_Albedo,v_UV) * u_TintColor;
	}
}