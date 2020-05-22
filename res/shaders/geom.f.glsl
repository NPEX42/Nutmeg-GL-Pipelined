#version 330 core
out vec4 v_PixelColor;
uniform vec4 u_TintColor;
void main() {
	v_PixelColor = u_TintColor;
}