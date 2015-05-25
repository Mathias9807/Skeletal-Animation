#version 330

layout(location = 0) out vec4 outColor;

in vec3 normal;

uniform vec3 color;

void main() {
	vec3 normalI = normalize(normal);
    outColor = vec4(color 
    	* max(dot(normalI, normalize(vec3(1, 1, 1))), 0), 
    1);
}
