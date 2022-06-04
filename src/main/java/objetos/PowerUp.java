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

public class PowerUp extends ElementoDePista implements Updatable, Renderable, Collidable {

	public static final int width = (int) (12 * Config.modificadorResolucion);
	public static final int height = (int) (12 * Config.modificadorResolucion);

	private ImageView render;
	private Rectangle collider;

	public PowerUp(double x, double y) {
		super(x, 0);
		
		Image power;

		power = new Image("file:src/main/resources/img/PowerUp.png", width, height, false, false);

		render = new ImageView(power);

		render.setViewOrder(2);

		collider = new Rectangle(width, height);
		collider.relocate(Config.baseCentro + x, -0.5 * Config.baseHeight); // para que no se vea en pantalla.
		render.relocate(Config.baseCentro + x, -0.5 * Config.baseHeight);
		collider.setFill(null);
		collider.setStroke(Color.VIOLET);

	}

	@Override
	public Shape getCollider() {
		return collider;
	}

	@Override
	public Node getRender() {
		return render;
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