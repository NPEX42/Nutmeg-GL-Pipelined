#version 330 core
out vec4 v_PixelColor;
uniform vec4 u_TintColor;
in vec2 v_UV;
in float v_Brightness;
in vec3 v_Normal;
uniform sampler2D t_Albedo;

uniform mat4 u_WorldMat;
void main() {
	v_PixelColor = u_WorldMat * vec4(v_Normal,1.0);
}