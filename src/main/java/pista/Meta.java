package pista;

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

public class Meta extends ElementoDePista implements Updatable, Renderable, Collidable {
	private ImageView render;

	private final double metaHeight = (32 * Config.modificadorResolucion);
	private final double metaWidth = (Config.baseCentro)/1.48; //para reproporcionar

	private Rectangle collider;

	private double y;

	public Meta() {
		super(0, Pista.LONGITUD_DE_PISTA);
		Image meta = new Image("file:src/main/resources/img/Meta.png", metaWidth, metaHeight, false, false);//190
		render = new ImageView(meta);
		render.setViewOrder(2);
				
		collider  = new Rectangle(metaWidth, metaHeight);
		render.relocate(metaWidth, -Pista.LONGITUD_DE_PISTA);
		collider.relocate(metaWidth, -Pista.LONGITUD_DE_PISTA);

		collider.setFill(null);
		collider.setStroke(Color.DARKTURQUOISE);
		collider.setStrokeWidth(3);
	}

	@Override
	public Node getRender() {
		return render;
	}

	@Override
	public void update(double variacion) {
		setY(this.y + variacion);
	}

	protected void setY(double y) {
		this.y = y;
		render.setY(y);
		collider.setY(y);
	}

	@Override
	public Shape getCollider() {
		return collider;
	}

}
