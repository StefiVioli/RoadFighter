package vehiculo;

import config_valores.Config;

public class ModoVehiculoJugadorMejorado extends ModoVehiculoJugador {
	
	@Override
	protected double velocidadAvance() {
		return 10 * Config.modificadorResolucion;
	}
	
	@Override
	protected double velocidadFreno() {
		return -15 * Config.modificadorResolucion;
	}
	
	@Override
	protected ModoVehiculoJugador cambiarModo() {
		return new ModoVehiculoJugadorNormal();
	}

	@Override
	protected double velocidadMaxima() {
		return 500 * Config.modificadorResolucion;
	}
	
	
}
