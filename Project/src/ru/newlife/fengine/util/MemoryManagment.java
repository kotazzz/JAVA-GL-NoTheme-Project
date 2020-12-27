package ru.newlife.fengine.util;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static org.lwjgl.system.MemoryStack.*;

public class MemoryManagment 
{
	public static FloatBuffer putData(Matrix4f matrix)
	{
		FloatBuffer b = stackGet().mallocFloat(4 * 16);
		b.put(matrix.m00()).put(matrix.m01()).put(matrix.m02()).put(matrix.m03());
		b.put(matrix.m10()).put(matrix.m11()).put(matrix.m12()).put(matrix.m13());
		b.put(matrix.m20()).put(matrix.m21()).put(matrix.m22()).put(matrix.m23());
		b.put(matrix.m30()).put(matrix.m31()).put(matrix.m32()).put(matrix.m33());
		return (FloatBuffer) b.flip();
	}
	
	public static FloatBuffer putData(Matrix3f matrix)
	{
		FloatBuffer b = stackGet().mallocFloat(4 * 9);
		b.put(matrix.m00()).put(matrix.m01()).put(matrix.m02());
		b.put(matrix.m10()).put(matrix.m11()).put(matrix.m12());
		b.put(matrix.m20()).put(matrix.m21()).put(matrix.m22());
		return (FloatBuffer) b.flip();
	}
	
	public static FloatBuffer putData(Matrix2f matrix)
	{
		FloatBuffer b = stackGet().mallocFloat(4 * 4);
		b.put(matrix.m00()).put(matrix.m01());
		b.put(matrix.m10()).put(matrix.m11());
		return (FloatBuffer) b.flip();
	}
	
	public static IntBuffer putData(int[] data)
	{
		return (IntBuffer) stackGet().mallocInt(4 * data.length).put(data).flip();
	}
	
	public static FloatBuffer putData(float[] data)
	{
		return (FloatBuffer) stackGet().mallocFloat(4 * data.length).put(data).flip();
	}
	
	public static DoubleBuffer putData(double[] data)
	{
		return (DoubleBuffer) stackGet().mallocDouble(8 * data.length).put(data).flip();
	}
	
	public static LongBuffer putData(long[] data)
	{
		return (LongBuffer) stackGet().mallocLong(8 * data.length).put(data).flip();
	}
	
}
