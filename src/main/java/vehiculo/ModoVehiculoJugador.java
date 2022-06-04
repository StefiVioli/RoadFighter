package vehiculo;

public abstract class ModoVehiculoJugador {
	
	protected abstract double velocidadAvance();
	protected abstract double velocidadFreno();
	protected abstract ModoVehiculoJugador cambiarModo();
	
	protected abstract double velocidadMaxima();
}
