package vehiculo;

import audio.AudioUI;
import config_valores.Config;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class VehiculoAutonomo extends Vehiculo {

	private boolean activo;
	private double velocidadRelativa;

	private double yLogica;

	protected TranslateTransition desplRenderIzq;
	protected TranslateTransition desplRenderDer;
	protected TranslateTransition desplColliderIzq;
	protected TranslateTransition desplColliderDer;

	public VehiculoAutonomo(double x, double y, Color color) {
		super(x, y, color);

		this.setX(Config.baseCentro + x);
		this.setY(-height);

		this.velocidad = 175 * Config.modificadorResolucion;
		this.velocidadRelativa = velocidad;

		this.activo = true;

		this.yLogica = y;

		desplRenderIzq = initDesplRenderIzq();
		desplRenderDer = initDesplRenderDer();
		desplColliderIzq = initDesplColliderIzq();
		desplColliderDer = initDesplColliderDer();
	}

	private TranslateTransition initDesplRenderIzq() {
		TranslateTransition renderAIzq = new TranslateTransition(Duration.millis(1000), render);
		renderAIzq.setByX(-50);
		return renderAIzq;
	}

	private TranslateTransition initDesplRenderDer() {
		TranslateTransition renderADer = new TranslateTransition(Duration.millis(1000), render);
		renderADer.setByX(50);
		return renderADer;
	}

	private TranslateTransition initDesplColliderIzq() {
		TranslateTransition colliderAIzq = new TranslateTransition(Duration.millis(1000), collider);
		colliderAIzq.setByX(-50);
		return colliderAIzq;
	}

	private TranslateTransition initDesplColliderDer() {
		TranslateTransition colliderADer = new TranslateTransition(Duration.millis(1000), collider);
		colliderADer.setByX(50);
		return colliderADer;
	}

	protected void avanzar(double deltaT) {
		if (this.vivo)
			this.velocidad = 175 * Config.modificadorResolucion;

		this.setY(y - velocidadRelativa * deltaT);
		this.yLogica = yLogica + velocidad * deltaT;
	}

	@Override
	protected void setY(double y) {
		this.y = y;
		render.setY(y);
		collider.setY(y - height);
	}

	public void update(double deltaT) {

		if (this.activo) {
			this.avanzar(deltaT);
		}
		if (vivo != true) {
			tiempoMuerto += deltaT;
			if (tiempoMuerto > 1.6) {
				// aca el tiempo muerto es menor porque solo quiero la animacion de la
				// explosion, no la del parpadeo.
				this.activo = false;
			}
		}
	}

	public void setVelocidadRelativa(double v) {
		this.velocidadRelativa = v;
	}

	public void incrementarAudio() {
		choqueAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		explosionAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
	}

	public void reducirAudio() {
		choqueAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		explosionAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
	}

	public void anularAudio() {
		this.explosionAudio.setVolume(0);
		this.choqueAudio.setVolume(0);
	}

	public void activarAudio() {
		this.explosionAudio.setVolume(Math.pow(AudioUI.valorActual, 2));
		this.choqueAudio.setVolume(Math.pow(AudioUI.valorActual, 2));

	}

	@Override
	protected void detenerAnimaciones() {
		desplRenderIzq.stop();
		desplColliderIzq.stop();
		desplRenderDer.stop();
		desplColliderDer.stop();
	}

	public boolean isActivo() {
		return activo;
	}

	@Override
	public double getY() {
		return yLogica;
	}

	public Shape getCollider() {
		return collider;
	}

	public Node getRender() {
		return render;
	}
	
	public boolean tieneAudio() {
		return (this.explosionAudio.getVolume() == 0) ? false : true;
	}
}
