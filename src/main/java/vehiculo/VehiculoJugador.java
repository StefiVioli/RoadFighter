package vehiculo;

import animaciones.IndividualSpriteAnimation;
import audio.AudioUI;
import config_valores.Config;
import enums.Tecla;
import interfaces.Collidable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import objetos.PowerUp;
import pista.Meta;
import roadFighterGame.GameObjectBuilder;
import utils.Puntaje;
import utils.Velocidad;

public class VehiculoJugador extends Vehiculo{

	private double VELOCIDAD_MAX;

	private final double VELOCIDAD_HORIZONTAL = 30 * Config.modificadorResolucion;

	public final double renderIniY = Config.baseHeight - height; // BORRABLE

	private int puntajeValor;

	private Puntaje puntaje;
	private AudioUI audio;
	private Velocidad velocidadScreen;

	protected IndividualSpriteAnimation powerUpAnimation;
	protected IndividualSpriteAnimation choqueAnimationIzq;
	protected IndividualSpriteAnimation choqueAnimationDer;

	private ModoVehiculoJugador modo = new ModoVehiculoJugadorNormal();

	private boolean directionRight = false;
	private boolean directionLeft = false;
	private boolean directionUp = false;
	private boolean directionDown = false;
	private boolean manejable;
	private boolean ganador = false;
	private boolean modoMejorado = false;

	private double tiempoNoManejable;
	private double tiempoModoMejorado = 10;

	public VehiculoJugador(double x, Color c) {
		super(x, 10, c);

		if (c == Color.RED) {
			puntaje = new Puntaje(70 * Config.modificadorResolucion);
			audio = new AudioUI(1);
			// ver como hacer para que solo un jugador maneje el audio o al menos que se vea
			// para uno solo.
			velocidadScreen = new Velocidad(100 * Config.modificadorResolucion);
		} else {
			puntaje = new Puntaje(165 * Config.modificadorResolucion);
			audio = new AudioUI(0); // aca podria ir otra cosa.
			velocidadScreen = new Velocidad(195 * Config.modificadorResolucion);
		}

		this.setX(Config.baseCentro + x);
		this.setY(Config.baseHeight); // se cambian aca ya que en autonomo o en jugador se posicionan distinto.
		render.setY(Config.baseHeight - height);
		collider.setY(Config.baseHeight - 2 * height);

		VELOCIDAD_MAX = this.modo.velocidadMaxima();

		manejable = true;

		initAnimaciones();

		GameObjectBuilder.getInstance().add(puntaje, audio, velocidadScreen);
	}

	private void initAnimaciones() {
		powerUpAnimation = initPowerUpAnimation();
		choqueAnimationIzq = initChoqueAnimationIzq();
		choqueAnimationDer = initChoqueAnimationDer();
	}

	private IndividualSpriteAnimation initPowerUpAnimation() {

		Image cuadro1, cuadro2;

		if (color == Color.RED) {
			cuadro1 = new Image("img/AutoRojoT.png", width, height, false, false);
			cuadro2 = new Image("img/AutoAzulT.png", width, height, false, false);
		} else {
			cuadro1 = new Image("img/AutoAzulT.png", width, height, false, false);
			cuadro2 = new Image("img/AutoRojoT.png", width, height, false, false);
		}

		Image cuadro3 = new Image("img/AutoVerdeT.png", width, height, false, false);
		Image cuadro4 = new Image("img/AutoAmarilloT.png", width, height, false, false);
		Image cuadro5 = new Image("img/AutoNaranjaT.png", width, height, false, false);

		IndividualSpriteAnimation powerUpAnimation = new IndividualSpriteAnimation(
				new Image[] { cuadro1, cuadro2, cuadro3, cuadro4, cuadro5 }, render, Duration.millis(500));

		powerUpAnimation.setCustomFrames(new int[] { 0, 1, 2, 3, 4, 0 });

		powerUpAnimation.setCycleCount(10 * 2); // 10 segundos de duracion * 2 ya que cada ciclo dura 0.5 segundos.
		return powerUpAnimation;
	}

	private IndividualSpriteAnimation initChoqueAnimationIzq() {
		Image azul_izq = new Image("file:src/main/resources/img/Azul_Izq.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false);
		Image rojo_izq = new Image("file:src/main/resources/img/Rojo_Izq.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false); // esta foto tiene puntitos blancos, revisar.

		Image imageB = imageBase;

		IndividualSpriteAnimation choqueSpriteAnimation = new IndividualSpriteAnimation(
				new Image[] { azul_izq, rojo_izq, imageB }, render, Duration.millis(333));

		if (color == Color.BLUE)
			choqueSpriteAnimation.setCustomFrames(new int[] { 0, 2 });
		else
			choqueSpriteAnimation.setCustomFrames(new int[] { 1, 2 });

		choqueSpriteAnimation.setCycleCount(3);

		return choqueSpriteAnimation;
	}

	private IndividualSpriteAnimation initChoqueAnimationDer() {
		Image azul_der = new Image("file:src/main/resources/img/Azul_Der.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false);
		Image rojo_der = new Image("file:src/main/resources/img/Rojo_Der.png", 17 * Config.modificadorResolucion,
				17 * Config.modificadorResolucion, false, false);

		Image imageB = imageBase;

		IndividualSpriteAnimation choqueSpriteAnimation = new IndividualSpriteAnimation(
				new Image[] { azul_der, rojo_der, imageB }, render, Duration.millis(333));

		if (color == Color.BLUE)
			choqueSpriteAnimation.setCustomFrames(new int[] { 0, 2 });
		else
			choqueSpriteAnimation.setCustomFrames(new int[] { 1, 2 });
		choqueSpriteAnimation.setCycleCount(3);

		return choqueSpriteAnimation;
	}

	@Override
	protected void avanzar(double deltaT) {

		this.velocidad += this.modo.velocidadAvance();

		if (this.velocidad > this.VELOCIDAD_MAX) {
			this.velocidad = this.VELOCIDAD_MAX;
		}

		double nuevoY = y + velocidad * deltaT;

		this.setY(nuevoY);
	}

	private void frenar(double deltaT) {

		this.velocidad += this.modo.velocidadFreno();
		if (this.velocidad < 0) {
			this.velocidad = 0;
		} else {
			double nuevoY = y + velocidad * deltaT;
			this.setY(nuevoY);
		}
	}

	private void ralentizarse(double deltaT) {
		this.velocidad += (this.modo.velocidadFreno() / 5);
		if (this.velocidad < 0)
			this.velocidad = 0;
		else {
			double nuevoY = y + velocidad * deltaT;
			this.setY(nuevoY);
		}
	}

	private void cambiarModo() {
		this.modo = this.modo.cambiarModo();
	}

	public void update(double deltaTime) {

		velocidadScreen.asignar((int) this.velocidad);
		velocidadScreen.update((int) velocidad);

		puntajeValor += deltaTime * this.velocidad;
		puntaje.asignar(puntajeValor);
		puntaje.update(deltaTime);
		audio.update(deltaTime);

		if (modoMejorado) {
			tiempoModoMejorado -= deltaTime;

			if (tiempoModoMejorado < 0) {
				this.cambiarModo();
				this.modoMejorado = false;
				this.VELOCIDAD_MAX = this.modo.velocidadMaxima();
				tiempoModoMejorado = 10;
			}
		}

		if (!manejable) {

			tiempoNoManejable -= deltaTime;

			if (tiempoNoManejable < 0) {
				manejable = true;

				directionLeft = directionRight = false;
			}
		}

		if (vivo == true) {

			if (directionUp) {
				this.avanzar(deltaTime);
			} else if (directionDown)
				this.frenar(deltaTime);
			else
				this.ralentizarse(deltaTime);

			int directionHorizontal = directionRight ? 1 : (directionLeft ? -1 : 0);

			setX(x + directionHorizontal * VELOCIDAD_HORIZONTAL * deltaTime);

		} else {
			tiempoMuerto += deltaTime;

			if (tiempoMuerto > 2.1 && explotado == true) {

				if (this.x < Config.baseCentro)
					setX(Config.baseCentro - 20 * Config.modificadorResolucion);
				else
					setX(Config.baseCentro + 20 * Config.modificadorResolucion);
				explotado = false;
			}

			if (tiempoMuerto > 4.2) {
				vivo = true;
				tiempoMuerto = 0;

				if (modoMejorado) {
					powerUpAnimation.playFrom(new Duration(powerUpAnimation.getTotalDuration().toMillis()
							- tiempoModoMejorado * Config.MILIS_IN_SECOND));
				}
			}
		}
	}

	@Override
	public void collide(Collidable collidable) {

		super.collide(collidable);

		if (vivo) {

			if (collidable.getClass() == VehiculoAutonomo.class || collidable.getClass() == VehiculoJugador.class) {

				Vehiculo v = (Vehiculo) collidable;

				if (!modoMejorado && v.isVivo()) {

					manejable = false;

					choqueAudio.play();

					tiempoNoManejable = 1;

					if (this.getX() > v.getX()) {
						this.choqueAnimationDer.play();

						directionLeft = false;
						directionRight = true;

					} else {
						this.choqueAnimationIzq.play();

						directionRight = false;
						directionLeft = true;
					}
				}

			} else if (collidable.getClass() == PowerUp.class) {

				puntajeValor += 500;
				puntaje.asignar(puntajeValor);

				if (modoMejorado == false) {
					powerUpAnimation.play();
					this.modoMejorado = true;
					this.cambiarModo();
					this.VELOCIDAD_MAX = this.modo.velocidadMaxima();
				} else {
					tiempoModoMejorado = 10; // igual aparecen cada 30 segundos.
				}

				powerUpAudio.play();
				GameObjectBuilder.getInstance().remove((PowerUp) collidable);

			} else if (collidable.getClass() == Meta.class) {
				this.velocidad = 0;
				this.manejable = false;
				ganador = true;
			}
		}
	}

	public void keyPressed(Tecla t) {

		// aca no quiero que se cambie nada mientras se choca, en el otro si quiero
		// poder dejar de avanzar.
		if (manejable) {

			switch (t) {
			case ARRIBA:
				this.directionUp = true;
				break;

			case ABAJO:
				this.directionDown = true;
				break;

			case IZQUIERDA:
				this.directionLeft = true;
				break;

			case DERECHA:
				this.directionRight = true;
				break;
			}

		}
	}

	public void keyReleased(Tecla t) {

		switch (t) {
		case ARRIBA:
			this.directionUp = false;
			break;

		case ABAJO:
			if (manejable) {
				this.directionDown = false;
			}
			break;

		case IZQUIERDA:
			if (manejable) {
				this.directionLeft = false;
			}
			break;

		case DERECHA:
			if (manejable) {
				this.directionRight = false;
			}
			break;

		}
	}

	protected void bajarPuntaje(int cant) {
		this.puntajeValor -= cant;

		if (this.puntajeValor < 0) {
			this.puntajeValor = 0;
		}
	}

	public void reducirAudio() {

		AudioUI.valorActual -= 0.1;

		if (AudioUI.valorActual < 0.1)
			AudioUI.valorActual = 0;

		choqueAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		explosionAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		powerUpAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
	}

	public void incrementarAudio() {

		AudioUI.valorActual += 0.1;

		if (AudioUI.valorActual > 1)
			AudioUI.valorActual = 1;

		choqueAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		explosionAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		powerUpAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
	}

	public void resetVision() { // por ahora no hace nada, la idea era corregir el render y collider del auto
								// cuando parece que se va quedando atras en la escena, esto pasa cuando los 2
								// jugadores se superan muchas veces.

		render.setY(Config.baseHeight - height);
		collider.setY(Config.baseHeight - 2 * height);
	}

	@Override
	protected void detenerAnimaciones() {
		this.choqueAnimationDer.stop();
		this.choqueAnimationIzq.stop();
	}

	public boolean isGanador() {
		return ganador;
	}

	public Puntaje getPuntaje() {
		return this.puntaje;
	}

	public ModoVehiculoJugador getModo() {
		return modo;
	}

	public String getColor() {
		if (this.color == Color.RED)
			return "Rojo";
		else
			return "Azul";
	}

	public Node getRender() {
		return render;
	}
	
	public Shape getCollider() {
		return collider;
	}
	
	public boolean isMayorQueInicialEnY() {
		return render.getY() > renderIniY;
	}
}
