package vehiculo;

import java.util.Random;

import animaciones.IndividualSpriteAnimation;
import audio.AudioResources;
import audio.AudioUI;
import config_valores.Config;
import interfaces.Collidable;
import interfaces.Collidator;
import interfaces.Renderable;
import interfaces.Updatable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import objetos.Obstaculo;
import pista.Borde;
import pista.ElementoDePista;

public abstract class Vehiculo extends ElementoDePista implements Renderable, Updatable, Collidator {

	protected double velocidad = 0 * Config.modificadorResolucion;
	protected Color color;
	protected boolean vivo = true;

	protected double tiempoMuerto = 0;

	public static final double width = 14 * Config.modificadorResolucion;
	public static final double height = 16 * Config.modificadorResolucion;
	protected Rectangle collider;

	protected Image imageBase;

	protected AudioClip explosionAudio;
	protected AudioClip choqueAudio;
	protected AudioClip powerUpAudio;

	protected boolean explotado = false;

	protected ImageView render;

	protected IndividualSpriteAnimation explosionAnimation;

	public Vehiculo(double x, double y, Color c) {
		super(x, y);
		color = c;

		initImage();
		initAudio();

		render = new ImageView(imageBase);

		render.relocate(-width / 2, -height);

		render.setViewOrder(0);

		collider = new Rectangle(width - 6 * Config.modificadorResolucion, height);

		collider.setFill(null);
		collider.setStroke(Color.FUCHSIA);

		explosionAnimation = initExplosionAnimation();

	}

	private IndividualSpriteAnimation initExplosionAnimation() {
		Image explosion1 = new Image("file:src/main/resources/img/Explosion1T.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false);
		Image explosion2 = new Image("file:src/main/resources/img/Explosion2T.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false);
		Image explosion3 = new Image("file:src/main/resources/img/Explosion3T.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false);

		Image imageB = imageBase;

		IndividualSpriteAnimation explosionSpriteAnimation = new IndividualSpriteAnimation(
				new Image[] { explosion1, explosion2, explosion3, imageB, null }, render, Duration.millis(4500));
		explosionSpriteAnimation.setCustomFrames(new int[] { 0, 1, 2, 0, 1, 2, 4, 4, 4, 4, 3, 4, 3, 4, 3, 4, 3 });
		explosionSpriteAnimation.setCycleCount(1);

		return explosionSpriteAnimation;
	}

	private void initImage() {

		String[] colores = new String[5];

		colores[0] = "file:src/main/resources/img/AutoRojoT.png";
		colores[1] = "file:src/main/resources/img/AutoAzulT.png";
		colores[2] = "file:src/main/resources/img/AutoAmarilloT.png";
		colores[3] = "file:src/main/resources/img/AutoNaranjaT.png";
		colores[4] = "file:src/main/resources/img/AutoVerdeT.png";

		if (color == Color.RED) {
			imageBase = new Image(colores[0], width, height, false, false);
		} else if (color == Color.BLUE) {
			imageBase = new Image(colores[1], width, height, false, false);
		} else {
			Random rand = new Random();
			imageBase = new Image(colores[rand.nextInt(2, 5)], width, height, false, false);
		}

	}

	private void initAudio() {
		choqueAudio = AudioResources.getChoqueAudio();
		explosionAudio = AudioResources.getExplosionAudio();
		powerUpAudio = AudioResources.getPowerUpAudio();

		choqueAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		explosionAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		powerUpAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
	}

	@Override
	protected void setX(double x) {
		this.x = x;
		render.setX(x);
		this.collider.setX(x - 4 * Config.modificadorResolucion);
		// el 4 es porque la imagen del vehiculo es mas grande que el collider.
	}

	public double getVelocidad() {
		return velocidad;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void collide(Collidable collidable) {

		if (vivo == true) {

			if (collidable.getClass() == VehiculoAutonomo.class) {
				VehiculoAutonomo chocado = (VehiculoAutonomo) collidable;
				if (chocado.getX() > this.getX()) {
					chocado.desplRenderDer.play();
					chocado.desplColliderDer.play();
				} else {
					chocado.desplRenderIzq.play();
					chocado.desplColliderIzq.play();
				}
			}
			if (collidable.getClass() == Obstaculo.class && this.getClass() == VehiculoJugador.class) {
				this.velocidad = 0;
			}

			if (collidable.getClass() == Borde.class && explotado == false) {

				this.velocidad = 0;

				explosionAudio.play();

				explotado = true;

				this.detenerAnimaciones();

				vivo = false;

				if (this.getClass() == VehiculoJugador.class) {
					VehiculoJugador v = (VehiculoJugador) this;
					v.powerUpAnimation.pause();
					v.bajarPuntaje(1000);
				}

				explosionAnimation.play();
			}
		}
	}

	protected abstract void avanzar(double deltaT);

	protected abstract void detenerAnimaciones();
}
