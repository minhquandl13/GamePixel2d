package view;

import java.awt.Graphics2D;
import controller.GamePanel;
import view.Lighting;

public class EnvironmentManager {
	public GamePanel gp;
	public Lighting lighting;
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		lighting = new Lighting(gp);
	}

	public void update() {
		lighting.update();
	}
	public void draw(Graphics2D g2) {
		lighting.draw(g2);
	}
}
