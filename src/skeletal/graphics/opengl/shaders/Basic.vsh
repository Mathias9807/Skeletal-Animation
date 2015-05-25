#version 330

layout(location = 0) in vec3 vertex_in;
layout(location = 2) in vec3 normal_in;

out vec3 normal;

uniform mat4 matProj, matView, matModel;

void main() {
	normal = (matModel * vec4(normal_in, 0)).xyz;
    gl_Position = matProj * matView * matModel * vec4(vertex_in, 1);
}
