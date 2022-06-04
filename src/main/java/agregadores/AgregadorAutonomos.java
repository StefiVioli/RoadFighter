package agregadores;

import java.util.Random;

import config_valores.Config;
import interfaces.Updatable;
import javafx.scene.paint.Color;
import pista.Pista;
import roadFighterGame.GameObjectBuilder;
import vehiculo.Vehiculo;
import vehiculo.VehiculoAutonomo;

public class AgregadorAutonomos implements Updatable {

	private Pista p;

	private double posicionPrevia, posicionActual;
	private long tiempoEntreAutos = 2;
	private long tiempoGeneracion;

	public AgregadorAutonomos(Pista p) {
		this.p = p;
		this.posicionActual = this.posicionPrevia = 0;
	}

	public void update(double deltaT) { // no estoy usando deltaT.

		long currentNano = System.nanoTime();

		this.posicionActual = p.getPuntero().getY();

		if (currentNano / Config.NANOS_IN_SECOND - tiempoGeneracion > 0
				&& posicionActual > posicionPrevia + Config.baseHeight * 3) {

			posicionPrevia = posicionActual;

			tiempoGeneracion = currentNano / Config.NANOS_IN_SECOND + tiempoEntreAutos;

			Random x = new Random();
			agregarAuto(x.nextDouble(-Config.distAlCentro + Vehiculo.width, Config.distAlCentro - Vehiculo.width),
					p.getPuntero().getY() + Config.baseHeight);
		}
	}

	private void agregarAuto(double x, double y) {

		VehiculoAutonomo v = new VehiculoAutonomo(x, y, Color.BLACK);
		p.add(v);
		GameObjectBuilder.getInstance().add(v);
	}

}
