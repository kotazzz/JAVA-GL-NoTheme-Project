#version 430 core

layout(location = 0) in vec3 attrib_Position;
layout(location = 1) in vec4 attrib_Colour;

out vec3 position;
out vec4 color;


void main()
{
	position = attrib_Position;
	color = attrib_Colour;
	gl_Position = vec4(attrib_Position, 1.0f);
}