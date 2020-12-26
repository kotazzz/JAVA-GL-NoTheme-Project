package ru.newlife.fengine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import ru.newlife.fengine.input.Keyboard;
import ru.newlife.fengine.input.Mouse;

import static org.lwjgl.opengl.GL46C.*;
import static ru.newlife.fengine.util.MemoryManagment.*;

public class Engine {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "NewLife`s Engine ";
	
	public EngineWindow engineWindow;
	
	public  void run() {
		this.init();
	}
	
	public  void init() {
		this.engineWindow = new EngineWindow(WIDTH, HEIGHT, TITLE);
		this.engineWindow.create();
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
		float[] v_quad = { 0.5f, 0.5f, 0,   -0.5f, 0.5f, 0,   -0.5f, -0.5f, 0,   0.5f, -0.5f, 0 };
		int[] indices = {0,1,2, 0,2,3};
		

		int vertexArray = glCreateVertexArrays();
		glBindVertexArray(vertexArray);

		int indexBuffer = glCreateBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);

		glBufferData(GL_ELEMENT_ARRAY_BUFFER, putData(indices), GL_STATIC_DRAW);
		
		int vertexBuffer = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, putData(v_quad), GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBindVertexArray(vertexArray);
	
		while(!this.engineWindow.isCloseRequest())
		{
			Keyboard.handleKeyboardInput();
			Mouse.handleMouseInput();
			
			glClearColor(0, 0.8f, 0, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBindVertexArray(vertexArray);
			

			//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_quad.length / 3);
			glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

			glBindVertexArray(vertexArray);

			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}

	
}
