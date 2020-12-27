package ru.newlife.fengine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import com.kenny.engine.EngineWindow;

public class Mouse 
{
	private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public static float mouseX;
	public static float mouseY;
	public static boolean isEntered;
	
	public static boolean buttonDown(int keyId)
	{
		return GLFW.glfwGetMouseButton(EngineWindow.getWindow().id, keyId) == 1;
	}
	
	public static void setMouseCallbacks(long id)
	{
		setCursorPositionCallback(id);
		setCursorEnterCallback(id);
	}
	
	public static void setCursorPositionCallback(long id)
	{
		GLFW.glfwSetCursorPosCallback(id, new GLFWCursorPosCallbackI() 
		{
			@Override
			public void invoke(long arg0, double arg1, double arg2) 
			{
				mouseX = (float) arg1;
				mouseY = (float) arg2;
			}
		});
	}
	
	public static void setCursorEnterCallback(long id)
	{
		GLFW.glfwSetCursorEnterCallback(id, new GLFWCursorEnterCallbackI() 
		{
			@Override
			public void invoke(long arg0, boolean arg1) 
			{
				isEntered = arg1;
			}
		});
	}
	
	public static boolean buttonPreesed(int keyId)
	{
		return buttonDown(keyId) && !buttons[keyId];
	}
	
	public static boolean buttonReleased(int keyId)
	{
		return !buttonDown(keyId) && buttons[keyId];
	}
	
	public static void handleMouseInput()
	{
		for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) 
		{
			buttons[i] = buttonDown(i);
		}
	}
}
