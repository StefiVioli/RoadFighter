package pista;

import java.util.ArrayList;

import config_valores.Config;
import interfaces.Updatable;
import objetos.Obstaculo;
import objetos.PowerUp;
import roadFighterGame.GameObjectBuilder;
import vehiculo.VehiculoAutonomo;
import vehiculo.VehiculoJugador;

public class Pista implements Updatable {

	public static final double LONGITUD_DE_PISTA = Config.baseHeight * 100;
	private final double MAX_OFF_SCREEN = 3 * Config.baseHeight;

	private ArrayList<VehiculoAutonomo> autos;

	private ArrayList<Obstaculo> obstaculos;

	private ArrayList<PowerUp> powerUps;

	private VehiculoJugador jugador;

	private VehiculoJugador jugador2;

	private VehiculoJugador jugadorPuntero;

	private VehiculoJugador jugadorUltimo;

	private Borde bordeIzq, bordeDer;

	public Pista() {

		this.autos = new ArrayList<VehiculoAutonomo>();
		this.obstaculos = new ArrayList<Obstaculo>();
		this.powerUps = new ArrayList<PowerUp>();

		this.bordeIzq = new Borde(Config.bordeIzquierdo - Borde.ANCHO_BORDE);
		this.bordeDer = new Borde(Config.bordeDerecho);

		GameObjectBuilder.getInstance().add(bordeIzq, bordeDer);
	}

	public void add(VehiculoAutonomo v) {
		autos.add(v);
	}

	public void add(Obstaculo o) {
		obstaculos.add(o);
	}

	public void add(PowerUp p) {
		powerUps.add(p);
	}

	public void addJugador(VehiculoJugador j) {
		jugador = j;
	}

	public void addJugador2(VehiculoJugador j2) {
		jugador2 = j2;
	}

	public VehiculoJugador getPuntero() {
		return jugadorPuntero;
	}

	public VehiculoJugador getUltimo() {
		return jugadorUltimo;
	}

	public void update(double deltaTime) {

		jugador.update(deltaTime);
		jugador2.update(deltaTime);

		double velocidadRelativa;

		if (jugador.getY() > jugador2.getY()) {

			jugadorPuntero = jugador;
			jugadorUltimo = jugador2;

			velocidadRelativa = jugadorPuntero.getVelocidad() - jugador2.getVelocidad();

			jugador2.getCollider()
					.setTranslateY(jugador2.getCollider().getTranslateY() + velocidadRelativa * deltaTime);
			jugador2.getRender().setTranslateY(jugador2.getRender().getTranslateY() + velocidadRelativa * deltaTime);

		} else {

			jugadorPuntero = jugador2;
			jugadorUltimo = jugador;

			velocidadRelativa = jugadorPuntero.getVelocidad() - jugador.getVelocidad();

			jugador.getCollider().setTranslateY(jugador.getCollider().getTranslateY() + velocidadRelativa * deltaTime);
			jugador.getRender().setTranslateY(jugador.getRender().getTranslateY() + velocidadRelativa * deltaTime);

		}

		// no resuelve el problema de que se van quedando atras los autos.
		if (jugadorPuntero.isMayorQueInicialEnY() == true) {

			jugadorPuntero.resetVision();
		}

		ArrayList<VehiculoAutonomo> removibles = new ArrayList<VehiculoAutonomo>();

		for (VehiculoAutonomo vehiculo : autos) {

			vehiculo.setVelocidadRelativa(vehiculo.getVelocidad() - jugadorPuntero.getVelocidad());

			vehiculo.update(deltaTime);

			if (Math.abs(jugadorPuntero.getY() - vehiculo.getY()) > MAX_OFF_SCREEN && vehiculo.tieneAudio()) {

				vehiculo.anularAudio();

			} else if (Math.abs(jugadorPuntero.getY() - vehiculo.getY()) <= MAX_OFF_SCREEN && !vehiculo.tieneAudio()) {

				vehiculo.activarAudio();
			}

			if (vehiculo.isActivo() == false || vehiculo.getY() < (jugadorPuntero.getY() - Config.baseHeight)) {
				GameObjectBuilder.getInstance().remove(vehiculo);
				removibles.add(vehiculo);
			}
		}

		for (Obstaculo obstaculo : obstaculos) {
			obstaculo.update(jugadorPuntero.getVelocidad() * deltaTime);
		}

		for (PowerUp powerUp : powerUps) {
			powerUp.update(jugadorPuntero.getVelocidad() * deltaTime);
		}

		autos.removeAll(removibles);
	}

	public void incrementarAudio() {
		for (VehiculoAutonomo auto : autos) {
			auto.incrementarAudio();
		}

		jugador.incrementarAudio(); //ver como anular el del jugador2.
	}

	public void reducirAudio() {
		for (VehiculoAutonomo auto : autos) {
			auto.reducirAudio();
		}

		jugador.reducirAudio();
	}
}
