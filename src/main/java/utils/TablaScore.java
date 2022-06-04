package utils;

import config_valores.Config;
import interfaces.Renderable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import objetos.GameObject;

public class TablaScore extends GameObject implements Renderable{
	
	private ImageView tablaPuntajes;
	
	private VBox render;

	public TablaScore() {
		
		Image tabla = new Image("img/tablaScore.png", 150, Config.baseHeight, false, false);
		
		tablaPuntajes = new ImageView(tabla);
				
		render = new VBox(tablaPuntajes);
		
		render.setTranslateX(Config.baseWidth - 150);
		render.setTranslateY(0);
		render.setViewOrder(5);
	}

	@Override
	public Node getRender() {
		return render;
	}
}
