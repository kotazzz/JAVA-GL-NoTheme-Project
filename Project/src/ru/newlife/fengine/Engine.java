package ru.newlife.fengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import ru.newlife.fengine.input.Keyboard;
import ru.newlife.fengine.input.Mouse;
import ru.newlife.fengine.renderer.Shader;

import static org.lwjgl.opengl.GL46C.*;
import static ru.newlife.fengine.util.MemoryManagment.*;

public class Engine {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "NewLife`s Engine ";
	public EngineWindow engineWindow;
	
	public Shader shader;
	
	public  void run() {
		this.init();
	}
	
	public  void init() {
		this.engineWindow = new EngineWindow(WIDTH, HEIGHT, TITLE);
		this.engineWindow.create();
		
		this.shader = new Shader("/shaders/Rectangle.vert", "/shaders/Rectangle.frag");
	    
		this.update();
	}

	public FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
		buffer.put(data);		
		buffer.flip();
		return buffer;
	}

	public IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
		buffer.put(data);		
		buffer.flip();
		return buffer;
	}
	
	public void update() 
	{
		float[] v_positions = { 0.5f, 0.5f, 0,   -0.5f, 0.5f, 0,   -0.5f, -0.5f, 0,   0.5f, -0.5f, 0 };
		float[] v_colors = {
				0.6f, 0.0f, 0.7f, 1f,
				0.8f, 0.4f, 0.6f, 1f,
				0.2f, 0.6f, 0.6f, 1f,
				0.6f, 0.7f, 0.0f, 1f
				};
		int[] indices = {0,1,2, 0,2,3};
		

		int vertexArray = glCreateVertexArrays();
		glBindVertexArray(vertexArray);

		
		
		int indexBuffer = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);

		glBufferData(GL_ELEMENT_ARRAY_BUFFER, putData(indices), GL_STATIC_DRAW);

		int vertexBuffer = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, putData(v_positions), GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		int vertexBufferc = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferc);
		glBufferData(GL_ARRAY_BUFFER, putData(v_colors), GL_STATIC_DRAW);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferc);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBindVertexArray(vertexArray);
	
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
			glBindVertexArray(vertexArray);

			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}

	
}
