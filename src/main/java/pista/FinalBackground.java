package pista;

import config_valores.Config;
import interfaces.Renderable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objetos.GameObject;

public class FinalBackground extends GameObject implements Renderable{
	private ImageView render;

	private final double finalWidth = 256 * Config.modificadorResolucion;
	private final double finalHeight = 240 * Config.modificadorResolucion;
	
	public FinalBackground() {
		Image finalBack = new Image("file:src/main/resources/img/Final.png", finalWidth, finalHeight, false, false);
		
		render = new ImageView(finalBack);
		render.setViewOrder(0);
		render.relocate(finalWidth, 0);
		render.relocate(0, 0);
	}

	@Override
	public Node getRender() {
		return render;
	}
}