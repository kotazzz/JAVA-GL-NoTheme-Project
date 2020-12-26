package ru.newlife.fengine.engine.input;

import org.lwjgl.glfw.GLFW;

import ru.newlife.fengine.engine.EngineWindow;

public class Keyboard {
	
	private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
	
	public static boolean keyDown(int keyId) {
		return GLFW.glfwGetKey(EngineWindow.getWindow().id, keyId) == GLFW.GLFW_TRUE;
	}
	public static boolean keyPressed(int keyId) {
		return keyDown(keyId) && !keys[keyId];
	}
	public static boolean keyReleased(int keyId) {
		return !keyDown(keyId) && keys[keyId];
	}
	
	public static void handleKeyboardInput() {
		for(int i = 0; i < GLFW.GLFW_KEY_LAST;i++) {
			keys[i] = keyDown(i);
		}
	}
}
