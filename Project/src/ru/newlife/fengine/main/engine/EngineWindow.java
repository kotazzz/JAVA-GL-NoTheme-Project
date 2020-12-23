package ru.newlife.fengine.main.engine;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class EngineWindow {


	private int width;
	private int height;
	public IntBuffer bufferWidth;
	public IntBuffer bufferHeight;
	private GLFWVidMode videoMode;
	private String title;
	public long id;
	
	public EngineWindow(int width, int height, String window) {
		super();
		this.width = width;
		this.height = height;
		title = window;
	}
	
	
	public void create() {
		if (!GLFW.glfwInit()) {
			System.err.println("Unable to init GLFW");
			System.exit(-1);
		}
		this.id = GLFW.glfwCreateWindow(this.width,this.height, this.title, 0, 0);
		
		if(this.id == 0) {
			System.err.println("Unable to create Window");
			System.exit(-1);
		}
		
		try (MemoryStack mem = MemoryStack.stackPush()){
			this.bufferWidth =  BufferUtils.createIntBuffer(1);
		    this.bufferHeight =  BufferUtils.createIntBuffer(1);
		    GLFW.glfwGetWindowSize(this.id, this.bufferWidth, this.bufferHeight);
		} catch (Exception e) {
			// TODO: handle exception
		}
		GLFW.glfwSetWindowTitle(this.id, this.title);
		GLFW.glfwSetWindowSize(this.id, this.width, this.height);
		GLFW.glfwSetWindowAspectRatio(this.id, this.width, this.height);
		GLFW.glfwSetWindowPos(this.id, ((this.videoMode.width() - this.bufferWidth.get(0))/2), 
				((this.videoMode.height() - this.bufferHeight.get(0))/2));
		GLFW.glfwSetWindowSizeLimits(this.id, this.width, this.height, 1600, 900);
		
		GLFW.glfwMakeContextCurrent(this.id);
		
		GL.createCapabilities();
		
		GL11.glViewport(0,0,this.bufferWidth.get(0),this.bufferHeight.get(0));
		
		
		
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


	public String getWindow() {
		return title;
	}


	public void setWindow(String window) {
		title = window;
	}
	
}
