package agregadores;

import java.util.Random;

import config_valores.Config;
import interfaces.Updatable;
import objetos.Obstaculo;
import pista.Pista;
import roadFighterGame.GameObjectBuilder;

public class AgregadorObstaculos implements Updatable {

	private Pista p;

	private long tiempoEntreObstaculos = 10;
	private long tiempoGeneracion;

	private double posicionPrevia, posicionActual;

	public AgregadorObstaculos(Pista p) {
		this.p = p;
		this.posicionActual = this.posicionPrevia = 0;
		this.tiempoGeneracion = 0;
	}

	@Override
	public void update(double deltaT) {

		long currentNano = System.nanoTime();

		this.posicionActual = p.getPuntero().getY();

		if ((currentNano / Config.NANOS_IN_SECOND) - tiempoGeneracion > 0
				&& posicionActual > posicionPrevia + 500 * Config.modificadorResolucion) {

			posicionPrevia = posicionActual;

			tiempoGeneracion = currentNano / Config.NANOS_IN_SECOND + tiempoEntreObstaculos;
			Random x = new Random();
			agregarObstaculo(x.nextDouble(-Config.distAlCentro + Obstaculo.width, Config.distAlCentro - Obstaculo.width),
					p.getPuntero().getY() - Config.baseHeight);
		}
	}

	private void agregarObstaculo(double x, double y) {
		Obstaculo o = new Obstaculo(x, y);

		p.add(o);
		GameObjectBuilder.getInstance().add(o);
	}
}
