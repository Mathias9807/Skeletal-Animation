package skeletal.level;

import static org.lwjgl.input.Keyboard.*;

public class Player {
	
	public double speed;

	public Player() {
		speed = 1;
	}
	
	public void tick(double delta) {
		if (isKeyDown(KEY_W)) 		Level.camPos.z -= speed * delta;
		if (isKeyDown(KEY_A)) 		Level.camPos.x -= speed * delta;
		if (isKeyDown(KEY_S)) 		Level.camPos.z += speed * delta;
		if (isKeyDown(KEY_D)) 		Level.camPos.x += speed * delta;
		if (isKeyDown(KEY_SPACE)) 	Level.camPos.y += speed * delta;
		if (isKeyDown(KEY_LSHIFT)) 	Level.camPos.y -= speed * delta;
	}

}
