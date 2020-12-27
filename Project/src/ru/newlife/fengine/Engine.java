package ru.newlife.fengine;

import static org.lwjgl.opengl.GL46C.*;

import ru.newlife.fengine.input.Keyboard;
import ru.newlife.fengine.input.Mouse;
import ru.newlife.fengine.renderer.BufferLayout;
import ru.newlife.fengine.renderer.Shader;
import ru.newlife.fengine.renderer.VertexAttribute;
import ru.newlife.fengine.renderer.VertexAttribute.ShaderDataType;
import ru.newlife.fengine.util.MemoryManagment;

public class Engine 
{
	public static final int WIDHT = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "Engine 0.0.1 pre-alpha";
	private EngineWindow engineWindow;
	public Shader shader;
	
	public void run() 
	{
		this.init();
	}
	
	public void init() 
	{
		this.engineWindow = new EngineWindow(WIDHT, HEIGHT, TITLE);
		this.engineWindow.create();
		this.shader = new Shader("/shaders/Rectangle.vert", "/shaders/Rectangle.frag");
		this.update();
	}
	
	public void update() 
	{
		float[] vertices =
			{
//                  positions          colours
				0.5f,  0.5f, 0,  1.0f, 1.0f, 0.0f, 1.0f,
			   -0.5f,  0.5f, 0,  1.0f, 0.0f, 1.0f, 1.0f,
			   -0.5f, -0.5f, 0,  1.0f, 1.0f, 0.0f, 1.0f,
			    0.5f, -0.5f, 0,  0.0f, 1.0f, 0.0f, 1.0f
			};
		
		int [] indices = {0, 1, 2, 0, 2, 3};
		
		// Чистый код хранение данных в одном Vertex Buffer c атрибутами без модификаций
		// с моей стороны.
		/*
		int vertexArray = glCreateVertexArrays();
		glBindVertexArray(vertexArray);
		int vertexBuffer = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, MemoryManagment.putData(vertices), GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 4 * 7, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 4 * 7, 0 + 12);
		int indexBuffer = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, MemoryManagment.putData(indices), GL_STATIC_DRAW);
		*/
		// ====================================================================
		
		
		int vertexArray = glCreateVertexArrays();
		glBindVertexArray(vertexArray);
		
		int vertexBuffer = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, MemoryManagment.putData(vertices.length * 4, vertices), GL_STATIC_DRAW);
		
		BufferLayout layout = new BufferLayout
		(
			new VertexAttribute("attrib_Position", ShaderDataType.t_float3),
			new VertexAttribute("attrib_Colour", ShaderDataType.t_float4)
		);
		
		int attributeId = 0;
		for(VertexAttribute attribute : layout.getAttributes())
		{
			addAttribute(attributeId, attribute, layout.getStride());
			attributeId++;
		}
		
		int indexBuffer = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, MemoryManagment.putData(indices.length * 4, indices), GL_STATIC_DRAW);
				
		while(!this.engineWindow.isCloseRequest())
		{
			Keyboard.handleKeyboardInput();
			Mouse.handleMouseInput();
			
			glClearColor(0, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBindVertexArray(vertexArray);
			this.shader.bind();
			glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
			this.shader.unBind();
			glBindVertexArray(0);
			
			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}
	
	public static void addAttribute(int attributeId, VertexAttribute attribute,
			 int bufferStride)
	{
		glEnableVertexAttribArray(attributeId);
		glVertexAttribPointer
		(
			attributeId,
			attribute.getElementAttribSize(),
			attribute.convertedType,
			attribute.normalized ? true : false,
			bufferStride,
		    attribute.offset
		);
	}

	public EngineWindow getEngineWindow() 
	{
		return this.engineWindow;
	}
}
