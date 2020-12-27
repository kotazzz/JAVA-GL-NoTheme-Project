package ru.newlife.fengine.renderer;

import static org.lwjgl.opengl.GL46C.*;
import static com.kenny.engine.util.MemoryManagment.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Shader 
{
	public int programId;
	
	public Shader(String vsSrc, String fsSrc) 
	{
		Map<Integer, String> shaderSources = new HashMap<Integer, String>(2);
		shaderSources.put(1, this.readFile(vsSrc));
		shaderSources.put(2, this.readFile(fsSrc));
		this.compile(shaderSources);
	}
	
	public void compile(Map<Integer, String> shaderSources)
	{
		int program = glCreateProgram();
		
		List<Integer> shaderIds = new ArrayList<Integer>();
		int shaderIdIdxs = 1;
		
		for(int i = 0; i < shaderSources.size(); i++)
		{
			int type = i == 0 ? GL_VERTEX_SHADER : i == 1 ? GL_FRAGMENT_SHADER : - 1;
			String source = shaderSources.get(shaderIdIdxs);
			
			int shader = glCreateShader(type);
			glShaderSource(shader, source);
			glCompileShader(shader);
			
			int isCompiled = 0;
			isCompiled = glGetShaderi(shader, GL_COMPILE_STATUS);
			if(isCompiled == GL_FALSE)
			{
				int maxLength = 0;
				maxLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
				
				String infoLog = "";
				infoLog = glGetShaderInfoLog(shader, maxLength);
				glDeleteShader(shader);
				
				String st = type == 0 ? "Vertex Shader" : "Fragment Shader";
				System.out.println("Cannot compile " + st + ": " + infoLog);
				System.exit(-1);
			}
			
			glAttachShader(program, shader);
			shaderIdIdxs++;
		}
		
		glLinkProgram(program);
		
		int isLinked = 0;
		isLinked = glGetProgrami(program, GL_LINK_STATUS);
		if(isLinked == GL_FALSE)
		{
			int maxLength = 0;
			maxLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
			
			String infoLog = "";
			infoLog = glGetProgramInfoLog(program, maxLength);
			
			for(int shaderId : shaderIds)
			{
				glDetachShader(program, shaderId);
			}
			
			for(int shaderId : shaderIds)
			{
				glDeleteShader(shaderId);
			}
		
			System.out.println("Cannot link shader program! ");
			System.out.println(infoLog);
			System.exit(-1);

		}
		
		for(int shaderId : shaderIds)
		{
			glDetachShader(program, shaderId);
		}
		
		this.programId = program;
	}
	
	
	public String readFile(String file)
	{
		boolean appendSlashes = false;
		boolean returnInOneLine = false;
		StringBuilder shaderSource = new StringBuilder();
        try
        {
        	InputStream in = Class.class.getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null)
            {
                shaderSource.append(line);
            
                if(appendSlashes) shaderSource.append("//");
                if(!returnInOneLine) shaderSource.append("\n");
            }
            reader.close();
            
            return shaderSource.toString();
        }
        catch(IOException e)
        {
        	System.out.println("This file '" + file + "' cound be read!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }
        
        return "[Reading Error]: This file '" + file + "' cound be read!";
	}
	
	public void bind()
	{
		glUseProgram(this.programId);
	}
	
	public void unBind()
	{
		glUseProgram(0);
	}
	
	public void setUniform1f(String name, float v)
	{
		glUniform1f(glGetUniformLocation(this.programId, name), v);
	}
	
	public void setUniform2f(String name, Vector2f v)
	{
		glUniform2f(glGetUniformLocation(this.programId, name), v.x, v.y);
	}
	
	public void setUniform2f(String name, float v1, float v2)
	{
		glUniform2f(glGetUniformLocation(this.programId, name), v1, v2);
	}
	
	public void setUniform3f(String name, Vector3f v)
	{
		glUniform3f(glGetUniformLocation(this.programId, name), v.x, v.y, v.z);
	}
	
	public void setUniform2f(String name, float v1, float v2, float v3)
	{
		glUniform3f(glGetUniformLocation(this.programId, name), v1, v2, v3);
	}
	
	public void setUniform4f(String name, Vector4f v)
	{
		glUniform4f(glGetUniformLocation(this.programId, name), v.x, v.y, v.z, v.w);
	}
	
	public void setUniform4f(String name, float v1, float v2, float v3, float v4)
	{
		glUniform4f(glGetUniformLocation(this.programId, name), v1, v2, v3, v4);
	}
	
	public void setUniformMat2f(String name, Matrix2f mat)
	{
		glUniformMatrix2fv(glGetUniformLocation(this.programId, name), false, putData(mat));
	}
	
	public void setUniformMat3f(String name, Matrix3f mat)
	{
		glUniformMatrix2fv(glGetUniformLocation(this.programId, name), false, putData(mat));
	}
	
	public void setUniformMat4f(String name, Matrix4f mat)
	{
		glUniformMatrix2fv(glGetUniformLocation(this.programId, name), false, putData(mat));
	}
	
	public void setUniformBool(String name, boolean v)
	{
		glUniform1i(glGetUniformLocation(this.programId, name), v == true ? 1 : 0);
	}
}
