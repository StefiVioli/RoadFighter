package vehiculo;

import config_valores.Config;

public class ModoVehiculoJugadorNormal extends ModoVehiculoJugador {
	
	@Override
	public double velocidadAvance() {
		return 5 * Config.modificadorResolucion;
	}

	@Override
	public double velocidadFreno() {
		return -15 * Config.modificadorResolucion;
	}

	@Override
	public ModoVehiculoJugador cambiarModo() {
		return new ModoVehiculoJugadorMejorado();
	}

	@Override
	public double velocidadMaxima() {
		return 250 * Config.modificadorResolucion;
	}
}
