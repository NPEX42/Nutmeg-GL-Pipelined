#version 330 core
out vec4 v_PixelColor;
uniform vec4 u_TintColor;
in vec2 v_UV;
in vec4 v_Color;
in float v_Brightness;
uniform sampler2D t_Albedo;
void main() {
	v_PixelColor = texture(t_Albedo,v_UV) * v_Color;
}