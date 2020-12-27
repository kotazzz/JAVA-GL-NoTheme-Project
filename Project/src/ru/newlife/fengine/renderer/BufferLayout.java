package ru.newlife.fengine.renderer;

import java.util.ArrayList;
import java.util.List;

public class BufferLayout 
{
	private List<VertexAttribute> attributes;
	private int stride = 0;
	
	public BufferLayout(VertexAttribute... attributes) 
	{
		this.attributes = new ArrayList<VertexAttribute>();
		for (VertexAttribute vertexAttribute : attributes)
		{
			this.attributes.add(vertexAttribute);
		}
		this.calcOffsetAndStride();
	}
	
	public void calcOffsetAndStride()
	{
		int offset = 0;
		stride = 0;
		for (VertexAttribute vertexAttribute : attributes) 
		{
			vertexAttribute.offset = offset;
			offset += vertexAttribute.size;
			stride += vertexAttribute.size;
		}
	}
	
	public List<VertexAttribute> getAttributes() 
	{
		return attributes;
	}
	
	public int getStride() 
	{
		return stride;
	}
}
