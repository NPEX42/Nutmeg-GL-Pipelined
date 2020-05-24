#version 330 core
out vec4 v_PixelColor;
uniform vec4 u_TintColor;
in vec2 v_UV;
in float v_Brightness;
in vec3 v_Normal;
uniform sampler2D t_Albedo;

void main() {
	v_PixelColor = vec4(v_UV, 0.0, 1.0);
}