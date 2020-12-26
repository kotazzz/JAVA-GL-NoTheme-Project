package ru.newlife.fengine.engine;

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
	
	public  void update() {
		while (!this.engineWindow.isCloseRequest()) {
			this.engineWindow.update();
		}
		
		this.engineWindow.destroy();
	}
	

	
}
