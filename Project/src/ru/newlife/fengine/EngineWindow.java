package ru.newlife.fengine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import ru.newlife.fengine.input.Mouse;

public class EngineWindow {
	
	private int width;
	private int height;
	public IntBuffer bufferedWidth;
	public IntBuffer bufferedHeight;
	public  GLFWVidMode videoMode;
	private String title;
	public long id;
	public static EngineWindow instance;
	
	public EngineWindow(int width, int height, String title) {
		instance = this;
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	
	public void create() 
	{
		if(!GLFW.glfwInit())
		{
			System.out.println("Cannot to init GLFW !");
			System.exit(-1);
		}
		
		this.id = GLFW.glfwCreateWindow(this.width, this.height, this.title, 
				0, 0);
		
		if(this.id == 0) 
		{
			System.out.println("Cannot to create window");
			System.exit(-1);
		}
		
		try (MemoryStack mem = MemoryStack.stackPush())
		{
			this.bufferedWidth = BufferUtils.createIntBuffer(1);
			this.bufferedHeight = BufferUtils.createIntBuffer(1);
			GLFW.glfwGetWindowSize(this.id, this.bufferedWidth, this.bufferedHeight);
			
			this.videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			
			GLFW.glfwSetWindowTitle(this.id, this.title);
			GLFW.glfwSetWindowSize(this.id, this.width, this.height);
			GLFW.glfwSetWindowSizeLimits(this.id, this.width, this.height, 1600, 900);
			GLFW.glfwSetWindowAspectRatio(this.id, this.width, this.height); // width / height
			GLFW.glfwSetWindowPos(this.id, 
					((this.videoMode.width() - this.bufferedWidth.get(0)) / 2), 
					((this.videoMode.height() - this.bufferedHeight.get(0)) / 2));
			
		} catch (Exception e) 
		{
		}
		
		GLFW.glfwMakeContextCurrent(this.id);
		GL.createCapabilities();
		GL11.glViewport(0, 0, this.bufferedWidth.get(), this.bufferedHeight.get());
		
		Mouse.setMouseCallbacks(this.id);
	}
	
	
	
	
	public void update() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(this.id);
	}
	
	public void destroy() {
		GLFW.glfwDestroyWindow(this.id);
	}
	
	public boolean isCloseRequest() {
		return GLFW.glfwWindowShouldClose(this.id);
	}
	public static EngineWindow getWindow() {
		return instance;
	}

	
	
	
	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String window) {
		title = window;
	}
	
}
