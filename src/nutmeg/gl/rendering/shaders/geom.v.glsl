#version 330 core
layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec2 a_UV;
layout(location = 2) in vec3 a_Normal;
layout(location = 3) in vec4 a_Color;
out vec2 v_UV;
out vec3 v_Normal;
out float v_Brightness;
out vec4 v_Color;
uniform vec3 u_GlobalLightDir;
uniform mat4 u_MVP;
uniform mat4 u_WorldMat;
void main() {
	gl_Position = u_MVP * vec4(a_Position, 1.0);
	vec3 normNormal = normalize(vec4(a_Normal,1.0) * u_WorldMat).xyz;
	vec3 normLight = normalize(u_GlobalLightDir);
	
	v_Brightness = dot(normNormal, normLight);
	v_Normal = vec3(v_Brightness,0.0,0.0);
	v_UV = a_UV;
	v_Color = a_Color;
}