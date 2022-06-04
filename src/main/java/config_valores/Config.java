package config_valores;

public class Config {
	
	public final static double modificadorResolucion = 2.5; 

	public final static double baseHeight = 240 * modificadorResolucion; 
	public final static double baseWidth = 256 * modificadorResolucion; 
	public final static double baseCentro = 112 * modificadorResolucion; // el centro de la carretera.

	public final static double bordeIzquierdo = 79 * modificadorResolucion;
	public final static double bordeDerecho = 151 * modificadorResolucion;
	
	public final static double distAlCentro = 36 * Config.modificadorResolucion;
	
	public final static int NANOS_IN_SECOND = 1000000000;
	public final static int MILIS_IN_SECOND = 1000;
}
