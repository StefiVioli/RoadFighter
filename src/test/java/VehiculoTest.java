import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import enums.Tecla;
import javafx.scene.paint.Color;
import objetos.PowerUp;
import pista.Pista;
import vehiculo.Vehiculo;
import vehiculo.VehiculoJugador;

public class VehiculoTest {

//	@Test
//	public void avanzar() {
//		VehiculoJugador auto = new VehiculoJugador(0, Color.RED);
//		auto.keyPressed(Tecla.ARRIBA);
//		auto.update(5);
//		System.out.println(auto.getY());
//	}

//	@Test
//	public void avanzarDosBloques() {
//		Vehiculo auto = new VehiculoJugador(Color.AZUL);
//		auto.avanzar();
//		auto.avanzar();
//		assertEquals(2, (int) auto.getY());
//
//	}
//
//	@Test
//	public void avanzarConVelocidadMaximaBloque() {
//		Vehiculo auto = new VehiculoJugador(Color.AZUL);
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		assertEquals(20, (int) auto.getY());
//
//	}
//
//	@Test
//	public void frenar() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		auto.avanzar(); // +1
//		auto.avanzar(); // +1
//		auto.frenar(); // +1
//		auto.frenar(); // +1
//		assertEquals(0, (int) auto.getVelocidad()); // avanzo y freno
//		assertEquals(4, (int) auto.getY()); // freno pero siguio avanzando
//	}
//
//	@Test
//	public void frenarNoBajaDe0KmH() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		auto.frenar();
//		auto.frenar(); // el calculo daria -100
//		assertEquals(0, (int) auto.getVelocidad());
//		assertEquals(0, (int) auto.getY()); // no se si se ponen 2 assert en un test.
//	}
//
//	@Test
//	public void noSuperaLaVelMax() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		for (int i = 0; i < 10; i++) {
//			auto.avanzar();
//		}
//		assertEquals(400, (int) auto.getVelocidad());
//	}
//
//	@Test
//	public void cambiarAModoMejorado() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		auto.cambiarModo();
//		assertEquals("ModoVehiculoJugadorMejorado", ((VehiculoJugador) auto).getModo().getClass().getSimpleName()); // revisar
//	}
//
//	@Test
//	public void cambiarAModoNormal() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		auto.cambiarModo(); // De normal a mejorado
//		auto.cambiarModo(); // De mejorado a normal
//		assertEquals("ModoVehiculoJugadorNormal", ((VehiculoJugador) auto).getModo().getClass().getSimpleName()); // revisar
//	}
//
//	@Test
//	public void chocar() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		auto.avanzar();
//		auto.avanzar();
//		auto.chocar(); // retrocede 1
//		assertEquals(1, (int) auto.getY());
//	}
//
//	@Test
//	public void chocarTresVecesYMorir() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.avanzar();
//		auto.explotar(); // retrocede 1
//		auto.explotar(); // retrocede 1
//		auto.explotar(); // retrocede 1
//		assertFalse(auto.isVivo());
//	}
//
//	@Test
//	public void tomaPowerUpCuandoSuperaPosicion() {
//		// 8 no sinifica q avance 8 posiciones avanza segun velocidad
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		PowerUp power = new PowerUp(5);
//		for (int i = 0; i < 8; i++) {
//			auto.avanzar();
//		}
//		((VehiculoJugador) auto).tomarPowerUp(power);
//		assertEquals(100, ((VehiculoJugador) auto).getModo().velocidadAvance());
//
//	}
//
//	@Test
//	public void tomaPowerUpCuandoNoSuperaPosicion() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		PowerUp power = new PowerUp(10);
//		for (int i = 0; i < 4; i++) {
//			auto.avanzar();
//		}
//		((VehiculoJugador) auto).tomarPowerUp(power);
//		assertEquals(50, ((VehiculoJugador) auto).getModo().velocidadAvance());
//	}
//
//	@Test
//	public void tomaPowerUpCuandoPosicionEsLaMisma() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		PowerUp power = new PowerUp(6);
//		for (int i = 0; i < 4; i++) {
//			auto.avanzar();
//		}
//		((VehiculoJugador) auto).tomarPowerUp(power);
//		assertEquals(100, ((VehiculoJugador) auto).getModo().velocidadAvance());
//
//	}
//
//	@Test
//	public void powerUpVerificarModo() {
//		VehiculoJugador auto = new VehiculoJugador(Color.NEGRO);
//
//		LinkedList<Vehiculo> autos = new LinkedList<Vehiculo>();
//		autos.add(auto);
//
//		auto.activarPowerUp();
//		auto.activarPowerUp();
//		assertEquals("ModoVehiculoJugadorMejorado", ((VehiculoJugador) auto).getModo().getClass().getSimpleName());
//		// activamos power up dos veces para chequear que se mantiene la mejora y no
//		// cambia el modo a normal
//	}
//
//	@Test
//	public void powerUpVelocidad() {// si avanzar me devuelve 100 tomo el power up
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);// activar power up directo en vehiculo o en vehiculojugador
//		((VehiculoJugador) auto).activarPowerUp();// conviene castear o definirlo como abstracto en la clase vehiculo??
//		auto.avanzar();
//		auto.avanzar();
//		assertEquals(200, (int) auto.getVelocidad());// verifico que tenga la velocidad de auto mejorado
//	}
//
//	@Test
//	public void powerUpVuelveAVelocidadNormal() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);// activar power up directo en vehiculo o en
//															// vehiculojugador
//		((VehiculoJugador) auto).activarPowerUp();
//		auto.avanzar();
//		auto.avanzar();// velocidad =200
//		auto.cambiarModo();// desactivo power up
//		auto.avanzar();// 250
//		assertEquals(250, (int) auto.getVelocidad());
//	}
//
//	/*
//	 * @Test public void tomaObstaculoCuandoSuperaPosicion() { //8 no sinifica q
//	 * avance 8 posiciones avanza segun velocidad Vehiculo auto = new
//	 * VehiculoJugador(Color.NEGRO); Obstaculo obstaculo = new Obstaculo(5); for
//	 * (int i = 0; i < 6; i++) { auto.avanzar(); } auto.managerObstaculo();
//	 * assertEquals(30, ((VehiculoJugador) auto).getModo().avanzar()); }
//	 */
//	// ver cuando supera los obstaculos, en el paso 4 el avance llega a 6 y el
//	// obstaculo esta en 5
//	// velocidad llega a 200 y resta 20 cuando llega a 6 pero deberia hacerlo en 5
//
//	@Test
//	public void tomarObstaculoCuandoSuperaPosicion() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		ObstaculoPowerUp obstaculo = new ObstaculoPowerUp(5);
//
//		for (int i = 0; i < 4; i++) {
//			auto.avanzar(); // termina avanzando 6 lugares.
//		}
//
//		((VehiculoJugador) auto).managerObstaculo(obstaculo);
//
//		assertEquals(180, (int) auto.getVelocidad());
//		// tenemos en cuenta que se está parado en la posicion 6 cuando la velocidad
//		// deberia reducirse
//		// en la posicion 5. Lo resolveremos en la proxima entrega.
//	}
//
//	@Test
//	public void tomaObstaculoCuandoNoSuperaPosicion() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		ObstaculoPowerUp obstaculo = new ObstaculoPowerUp(10);
//		for (int i = 0; i < 4; i++) {
//			auto.avanzar();
//		}
//		((VehiculoJugador) auto).managerObstaculo(obstaculo);
//		assertEquals(200, (int) auto.getVelocidad());
//
//	}
//
//	@Test
//	public void tomaObstaculoCuandoPosicionEsLaMisma() {
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		ObstaculoPowerUp obstaculo = new ObstaculoPowerUp(6);
//		for (int i = 0; i < 4; i++) {
//			auto.avanzar();
//		}
//		((VehiculoJugador) auto).managerObstaculo(obstaculo);
//		assertEquals(180, (int) auto.getVelocidad());
//	}
//
//	@Test
//	public void obstaculoVelocidadAutoMejorado() {// al toparme con un obstaculo deberia reducir la velocidad en
//													// -20 lo pruebo en auto mejorado
//		Vehiculo auto = new VehiculoJugador(Color.NEGRO);
//		((VehiculoJugador) auto).activarPowerUp();// pasa a auto mejorado
//		auto.avanzar();
//		auto.accionObstaculo();
//		assertEquals(80, (int) auto.getVelocidad());// velocidad de auto mejorado menos la reduccion del obstaculo
//	}
//
//	@Test
//	public void determinarLlegadaAMeta() {
//		VehiculoJugador auto1 = new VehiculoJugador(Color.ROJO);
//		VehiculoJugador auto2 = new VehiculoJugador(Color.NEGRO);
//
//		Pista pista = new Pista();
//
//		for (int i = 0; i < 100; i++)
//			auto1.avanzar(); // lo llevo al final. (Se va hasta 388).
//
//		for (int i = 0; i < 10; i++)
//			auto2.avanzar(); // lo llevo hasta 28. (para los primeros 10 suma 28).
//
//		assertEquals(true, pista.autoLlegoAFinal(auto1));
//		assertEquals(false, pista.autoLlegoAFinal(auto2));
//	}
//
//	@Test
//	public void noSuperarMeta() {
//		Vehiculo auto1 = new VehiculoJugador(Color.ROJO);
//
//		for (int i = 0; i < 500; i++)
//			auto1.avanzar();
//
//		assertEquals(100, (int) auto1.getY());
//	}
//
////	@Test
////	public void determinarPodio() {
////		ArrayList<VehiculoJugador> listaAutos = new ArrayList<VehiculoJugador>();
////		
////		VehiculoJugador auto1 = new VehiculoJugador(Color.ROJO);
////		VehiculoJugador auto2 = new VehiculoJugador(Color.NEGRO);
////		VehiculoJugador auto3 = new VehiculoJugador(Color.NEGRO);
////		
////		for (int i = 0; i < 100; i++)
////			auto1.avanzar(); // lo llevo al final. (Se va hasta 388).
////		
////		for (int i = 0; i < 15; i++)
////			auto2.avanzar();
////		
////		for (int i = 0; i < 5; i++)
////			auto3.avanzar();
////		
////		Pista pista = new Pista();
////		
////		listaAutos.add(auto3);
////		listaAutos.add(auto1);
////		listaAutos.add(auto2);
////			
//////		assertEquals(new ArrayList<VehiculoJugador>(Arrays.asList(auto1, auto2, auto3)), pista.getPodio(listaAutos));
////		assertArrayEquals(new ArrayList<VehiculoJugador>(Arrays.asList(auto1, auto2, auto3)), pista.getPodio(listaAutos));
////	
////		assertEquals(true, List.equals(new ArrayList<VehiculoJugador>(Arrays.asList(auto1, auto2, auto3), pista.getPodio(listaAutos))));
////	}

}
