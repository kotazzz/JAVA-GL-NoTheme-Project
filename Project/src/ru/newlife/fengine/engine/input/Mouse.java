package ru.newlife.fengine.engine.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import ru.newlife.fengine.engine.EngineWindow;

public class Mouse {
	private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
	
	public static float mouseX;
	public static float mouseY;
	public static boolean isEntered;
	
	public static boolean buttonDown(int buttonId) {
		return GLFW.glfwGetMouseButton(EngineWindow.getWindow().id, buttonId) == GLFW.GLFW_TRUE;
	}
	
	public static void setMouseCallbacks(long id) {
		setCursorPositionCallback(id);
		setCursorEnterCallback(id);
	}
	
	public static void setCursorPositionCallback(long id) {
		GLFW.glfwSetCursorPosCallback(id, new GLFWCursorPosCallbackI() {
			
			@Override
			public void invoke(long id, double x, double y) {
				// TODO Auto-generated method stub
				mouseX = (float) x;
				mouseY = (float) y;
			}
		});
	}
	public static void setCursorEnterCallback(long id) {
		GLFW.glfwSetCursorEnterCallback(id, new GLFWCursorEnterCallbackI() {
			
			@Override
			public void invoke(long arg0, boolean enter) {
				isEntered = enter;
			}
		});
	}
	public static boolean buttonPressed(int buttonId) {
		return buttonDown(buttonId) && !buttons[buttonId];
	}
	public static boolean buttonReleased(int buttonId) {
		return !buttonDown(buttonId) && buttons[buttonId];
	}
	
	public static void handleMouseInput() {
		for(int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST;i++) {
			buttons[i] = buttonDown(i);
		}
	}
}
