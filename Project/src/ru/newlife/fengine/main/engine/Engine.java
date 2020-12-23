package ru.newlife.fengine.main.engine;

public class Engine {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 360;
	public static final String TITLE = "NewLife`s Engine ";
	private EngineWindow engineWindow;
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
			//TODO: Make rendering
		}
		
		this.engineWindow.destroy();
	}
	

	
}
