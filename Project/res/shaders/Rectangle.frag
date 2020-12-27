#version 430 core

layout (location = 0) out vec4 out_Colour;

in vec3 position;
in vec4 color;

void main()
{
	out_Colour = vec4(color);
}