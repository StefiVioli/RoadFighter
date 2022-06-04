package agregadores;

import java.util.Random;

import config_valores.Config;
import interfaces.Updatable;
import objetos.PowerUp;
import pista.Pista;
import roadFighterGame.GameObjectBuilder;

public class AgregadorPowerUps implements Updatable {
	
	private Pista p;

	private long tiempoEntrePowerUps = 30;
	private long tiempoGeneracion;

	private double posicionPrevia, posicionActual;

	public AgregadorPowerUps(Pista p) {
		this.p = p;
		this.posicionActual = this.posicionPrevia = 0;
		this.tiempoGeneracion = 0;
	}

	@Override
	public void update(double deltaT) {

		long currentNano = System.nanoTime();

		this.posicionActual = p.getPuntero().getY();
		// ver si se deberian generar para el ultimo porque sino hay mucha desventaja una vez que uno empieza a agarrar powerUps.

		if ((currentNano / Config.NANOS_IN_SECOND) - tiempoGeneracion > 0
				&& posicionActual > posicionPrevia + 1000 * Config.modificadorResolucion) {

			posicionPrevia = posicionActual;

			tiempoGeneracion = currentNano / Config.NANOS_IN_SECOND + tiempoEntrePowerUps;
			Random x = new Random();
			agregarPowerUp(x.nextDouble(-Config.distAlCentro + PowerUp.width, Config.distAlCentro - PowerUp.width), 
					p.getPuntero().getY() + Config.baseHeight);
		}

	}

	private void agregarPowerUp(double x, double y) {
		PowerUp p = new PowerUp(x, y);

		this.p.add(p);
		GameObjectBuilder.getInstance().add(p);
	}
}