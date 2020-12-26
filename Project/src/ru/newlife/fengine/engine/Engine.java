package ru.newlife.fengine.engine;

import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.opengl.GL30;

import ru.newlife.fengine.engine.input.Keyboard;
import ru.newlife.fengine.engine.input.Mouse;

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
	public void update() 
	{
		float[] v_quad =
			{

				0.5f, 0.5f, 0,  -0.5f, 0.5f, 0,  -0.5f, -0.5f, 0,

				0.5f, 0.5f, 0,  -0.5f, -0.5f, 0,  0.5f, -0.5f, 0
			};
		

		int vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);

		int vboId = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
		
		FloatBuffer fBuffer = this.storeDataInFloatBuffer(v_quad);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, fBuffer, GL30.GL_STATIC_DRAW);
		MemoryUtil.memFree(fBuffer);
		
		GL30.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
		GL30.glBindVertexArray(vaoId);
	
		while(!this.engineWindow.isCloseRequest())
		{
			Keyboard.handleKeyboardInput();
			Mouse.handleMouseInput();
			
			GL11.glClearColor(0, 0.8f, 0, 1);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			GL30.glBindVertexArray(vaoId);
			
			GL30.glEnableVertexAttribArray(0);

			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, v_quad.length / 3);

			GL30.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(vaoId);

			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}

	
}
