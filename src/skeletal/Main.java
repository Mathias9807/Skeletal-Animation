package skeletal;

import org.lwjgl.Sys;

import skeletal.graphics.Graphics;
import skeletal.level.Level;

public class Main {
	
	public static boolean running = true;
	
	public static void main(String[] args) {
		begin();
		
		loop();
		
		end();
		
		System.exit(0);
	}
	
	private static void begin() {
		Graphics.init();
		Level.init();
	}
	
	private static void loop() {
		double now = (double) Sys.getTime() / Sys.getTimerResolution();
		double past = now;
		double delta;
		while (running) {
			now = (double) Sys.getTime() / Sys.getTimerResolution();
			delta = now - past;
			past = now;
			
			Level.tick(delta);
			Graphics.tick();
		}
	}
	
	private static void end() {
		Graphics.end();
	}

}
