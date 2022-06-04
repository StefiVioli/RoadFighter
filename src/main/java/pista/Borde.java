package pista;

import config_valores.Config;
import interfaces.Collidable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import objetos.GameObject;

public class Borde extends GameObject implements Collidable {

	public final static double ANCHO_BORDE = 40 * Config.modificadorResolucion;
	private Rectangle collider;

	public Borde(double x) {

		this.collider = new Rectangle(ANCHO_BORDE, Pista.LONGITUD_DE_PISTA);

		this.collider.relocate(x, 0);
		collider.setFill(null);
		collider.setStroke(Color.FUCHSIA);
	}

	public Shape getCollider() {
		return collider;
	}
}
