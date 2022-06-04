package pista;

import config_valores.Config;
import interfaces.Renderable;
import interfaces.Updatable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import objetos.GameObject;

public class Background extends GameObject implements Updatable, Renderable {
	private HBox render;

	private final int bordeIzqWidth = (int) (72 * Config.modificadorResolucion); // del limite izquierdo a la pista.
																					// 30.64 sin el mapita de la
																					// izquierda.
	private final int carreteraWidth = (int) (82 * Config.modificadorResolucion); // toda la parte de la pista en la que
																					// se mueve el auto.
	private final int bordeDerWidth = (int) (102 * Config.modificadorResolucion); // de la pista al limite derecho de la
																					// imagen.

	private final int carreteraHeight = (int) (96 * Config.modificadorResolucion);

	private double y;

	public Background() {

		Image fondo = new Image("file:src/main/resources/img/PistaNeon.png", Config.baseWidth, carreteraHeight, false,
				false);

		ImagePattern imagePattern = new ImagePattern(fondo, Config.baseWidth, carreteraHeight, Config.baseWidth,
				carreteraHeight, false);

		Rectangle pista = new Rectangle(carreteraWidth + bordeIzqWidth + bordeDerWidth,
				Config.baseHeight + carreteraHeight);

		pista.setFill(imagePattern);

		render = new HBox(pista);
		render.relocate(0, -carreteraHeight);

		render.setViewOrder(10);
	}

	public Node getRender() {
		return render;
	}

	public void update(double variacionY) { // el deltaTime tiene el deltaT * velocidadJugador.

		y += variacionY;
		render.setTranslateY(y % (carreteraHeight));
	}

}