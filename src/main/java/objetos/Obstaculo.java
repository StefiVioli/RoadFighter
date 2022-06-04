package objetos;

import config_valores.Config;
import interfaces.Collidable;
import interfaces.Renderable;
import interfaces.Updatable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import pista.ElementoDePista;

public class Obstaculo extends ElementoDePista implements Updatable, Renderable, Collidable{
	public static final double width = 18 * Config.modificadorResolucion;
	public static final double height = 18 * Config.modificadorResolucion;
	
	private ImageView render;
	private Rectangle collider;
	
	
	public Obstaculo(double x, double y) {
		super(x, 0);
		
		Image obstaculo;
		
		obstaculo = new Image("file:src/main/resources/img/ObstaculoV.png", width, height, false, false);
		
		render = new ImageView(obstaculo);
		
		render.setViewOrder(2);

		collider = new Rectangle(width, height);
		collider.relocate(Config.baseCentro + x, - Config.baseHeight); //decia -0.5
		render.relocate(Config.baseCentro + x,  - Config.baseHeight);
		collider.setFill(null);
		collider.setStroke(Color.FUCHSIA);
	}

	@Override
	public Node getRender() {
		return render;
	}
	
	@Override
	public Shape getCollider() {
		return collider;
	}
	
	@Override
	public void update(double variacion) {
		setY(this.y + variacion);
		
	}
	
	@Override
	protected void setY(double y) {
		this.y = y;
		render.setY(y);
		collider.setY(y);
	}



}