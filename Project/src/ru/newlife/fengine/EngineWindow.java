package com.kenny.engine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import com.kenny.engine.input.Mouse;


public class EngineWindow 
{
	private int width;
	private int height;
	public IntBuffer bufferedWidth;
	public IntBuffer bufferedHeight;
	private GLFWVidMode videoMode;
	private String title;
	public long id;
	public static EngineWindow instance;
	
	public EngineWindow(int width, int height, String title) 
	{
		instance = this;
		this.width = width;
		this.height = height;
		this.title = title;
	}

	public void create()
	{
		if(!GLFW.glfwInit())
		{
			System.err.println("GLFW не инициализирована!");
			System.exit(-1);
		}
		
		this.id = GLFW.glfwCreateWindow(this.width, this.height, this.title, 
				0, 0);
		
		if(this.id == 0)
		{
			System.err.println("Окно равно 0! Не могу создать окно!");
			System.exit(-1);
		}
		
		try (MemoryStack mem = MemoryStack.stackPush())
		{
			this.bufferedWidth = BufferUtils.createIntBuffer(1);
			
			this.bufferedHeight = BufferUtils.createIntBuffer(1);
			GLFW.glfwGetWindowSize(this.id, this.bufferedWidth, this.bufferedHeight);
		} 
		catch (Exception e) 
		{
		}
		
		this.videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwSetWindowTitle(this.id, this.title);
		GLFW.glfwSetWindowSize(this.id, this.width, this.height);
		GLFW.glfwSetWindowAspectRatio(this.id, this.width, this.height); // width / height
		GLFW.glfwSetWindowPos(this.id, 
				((this.videoMode.width() - this.bufferedWidth.get(0)) / 2), 
				(this.videoMode.height() - this.bufferedHeight.get(0)) / 2);
		GLFW.glfwSetWindowSizeLimits(this.id, this.width, this.height, 1600, 900);
		
		GLFW.glfwMakeContextCurrent(this.id);
		GL.createCapabilities();
		GL11.glViewport(0, 0, this.bufferedWidth.get(0), this.bufferedHeight.get(0));
		
		Mouse.setMouseCallbacks(this.id);
	
	}
	
	public void update()
	{
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(this.id);
	}
	
	public void destroy()
	{
		GLFW.glfwDestroyWindow(this.id);
	}
	
	public boolean isCloseRequest()
	{
		return GLFW.glfwWindowShouldClose(this.id);
	}
	
	public static EngineWindow getWindow()
	{
		return instance;
	}
	
	public int getWidth() 
	{
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public int getHeight() 
	{
		return height;
	}

	public void setHeight(int height) 
	{
		this.height = height;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	
}
