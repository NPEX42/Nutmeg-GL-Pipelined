#version 330 core
layout(location = 0) in vec3 a_Position;

void main() {
	gl_Position = vec4(a_Position + vec3(0.5,0,0), 1.0);
}