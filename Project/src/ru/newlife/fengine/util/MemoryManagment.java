package ru.newlife.fengine.util;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import static org.lwjgl.system.MemoryStack.*;
public class MemoryManagment {
	public static IntBuffer putData(int[] data) {
		return (IntBuffer) stackGet().mallocInt(data.length*4).put(data).flip();
	}
	public static FloatBuffer putData(float[] data) {
		return (FloatBuffer) stackGet().mallocFloat(data.length*4).put(data).flip();
	}
	public static DoubleBuffer putData(double[] data) {
		return (DoubleBuffer) stackGet().mallocDouble(data.length*8).put(data).flip();
	}
	public static LongBuffer putData(long[] data) {
		return (LongBuffer) stackGet().mallocLong(data.length*8).put(data).flip();
	}
}
