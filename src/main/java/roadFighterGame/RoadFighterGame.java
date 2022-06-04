package roadFighterGame;

import java.util.List;

import agregadores.AgregadorAutonomos;
import agregadores.AgregadorObstaculos;
import agregadores.AgregadorPowerUps;
import config_valores.Config;
import enums.Tecla;
import interfaces.Collidable;
import interfaces.Collidator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import pista.Background;
import pista.FinalBackground;
import pista.Meta;
import pista.Pista;
import utils.TablaScore;
import utils.TextoFinal;
import vehiculo.VehiculoJugador;

public class RoadFighterGame extends Application {

	private Scene currentScene;

	protected final long NANOS_IN_SECOND = 1000000000;
	protected final double NANOS_IN_SECOND_D = 1000000000.0;

	private VehiculoJugador jugador;
	private VehiculoJugador jugador2;

	private Pista pista;
	private Background background;

	private Meta meta;

	private FinalBackground finalBackground;

	private long previousNanoFrame;

	AnimationTimer gameTimer;

	AgregadorAutonomos agregadorAutonomos;
	AgregadorObstaculos agregadorObstaculos;
	AgregadorPowerUps agregadorPowerups;

	private boolean endGame = false;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) {

		Group root = new Group();
		currentScene = new Scene(root, Config.baseWidth, Config.baseHeight);

		background = new Background();
		jugador = new VehiculoJugador(20, Color.RED);
		jugador2 = new VehiculoJugador(-20, Color.BLUE);
		pista = new Pista();
		meta = new Meta();
		agregadorAutonomos = new AgregadorAutonomos(pista);
		agregadorObstaculos = new AgregadorObstaculos(pista);
		agregadorPowerups = new AgregadorPowerUps(pista);
		TablaScore t = new TablaScore();
		
		addUpdateEachFrame();
		agregarEventHandlers();

		stage.setScene(currentScene);
		stage.setTitle("RoadFighters | Grupo 4");

		pista.addJugador(jugador);
		pista.addJugador2(jugador2);
		
		GameObjectBuilder.getInstance().setRoot(root);		
		GameObjectBuilder.getInstance().add(jugador, jugador2, background, t, meta);

		stage.show();
	}

	private void agregarEventHandlers() {

		currentScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case UP:
				case W:
					jugador.keyPressed(Tecla.ARRIBA);
					break;

				case I:
					jugador2.keyPressed(Tecla.ARRIBA);
					break;

				case DOWN:
				case S:
					jugador.keyPressed(Tecla.ABAJO);
					break;

				case K:
					jugador2.keyPressed(Tecla.ABAJO);
					break;

				case LEFT:
				case A:
					jugador.keyPressed(Tecla.IZQUIERDA);
					break;

				case J:
					jugador2.keyPressed(Tecla.IZQUIERDA);
					break;

				case RIGHT:
				case D:
					jugador.keyPressed(Tecla.DERECHA);
					break;

				case L:
					jugador2.keyPressed(Tecla.DERECHA);
					break;

				case ESCAPE:
					System.exit(0);
					break;

				case ADD:
				case E:
					pista.incrementarAudio();
					break;

				case SUBTRACT:
				case Q:
					pista.reducirAudio();
					break;

				default:
					break;
				}
			}
		});

		currentScene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			public void handle(KeyEvent e) {
				switch (e.getCode()) {
				case UP:
				case W:
					jugador.keyReleased(Tecla.ARRIBA);
					break;

				case I:
					jugador2.keyReleased(Tecla.ARRIBA);
					break;

				case DOWN:
				case S:
					jugador.keyReleased(Tecla.ABAJO);
					break;

				case K:
					jugador2.keyReleased(Tecla.ABAJO);
					break;

				case LEFT:
				case A:
					jugador.keyReleased(Tecla.IZQUIERDA);
					break;

				case J:
					jugador2.keyReleased(Tecla.IZQUIERDA);
					break;

				case RIGHT:
				case D:
					jugador.keyReleased(Tecla.DERECHA);
					break;

				case L:
					jugador2.keyReleased(Tecla.DERECHA);
					break;

				default:
					break;
				}
			}
		});
	}

	private void addUpdateEachFrame() {

		previousNanoFrame = System.nanoTime();
		AnimationTimer gameTimer = new AnimationTimer() {

			@Override
			public void handle(long currentNano) {
				update((currentNano - previousNanoFrame) / NANOS_IN_SECOND_D);
				previousNanoFrame = currentNano;
			}
		};

		gameTimer.start();
	}

	public void update(double deltaT) {

		if (!endGame) {

			pista.update(deltaT);
			background.update(deltaT * pista.getPuntero().getVelocidad());
			meta.update(deltaT * pista.getPuntero().getVelocidad());
			agregadorAutonomos.update(deltaT);
			agregadorObstaculos.update(deltaT);
			agregadorPowerups.update(deltaT);

			checkColliders();

			if (pista.getPuntero().isGanador()) {

				endGame = true;

				Group root = new Group();
                finalBackground = new FinalBackground();
                
                TextoFinal ganadorTxt = new TextoFinal(pista.getPuntero().getPuntaje(),1,pista.getPuntero().getColor());
                TextoFinal perdedorTxt = new TextoFinal(pista.getUltimo().getPuntaje(),2,pista.getUltimo().getColor());
                
                GameObjectBuilder.getInstance().setRoot(root);
                GameObjectBuilder.getInstance().add(ganadorTxt, perdedorTxt ,finalBackground);
			}
		}
	}

	private void checkColliders() {

		List<Collidator> collidators = GameObjectBuilder.getInstance().getCollidators();

		List<Collidable> collidables = GameObjectBuilder.getInstance().getCollidables();

		for (int i = 0; i < collidators.size(); i++) {

			Collidator collidator = collidators.get(i);

			for (int j = i + 1; j < collidators.size(); j++) {

				Collidator otherCollidator = collidators.get(j);

				Shape intersect = Shape.intersect(collidator.getCollider(), otherCollidator.getCollider());

				if (intersect.getBoundsInLocal().getWidth() != -1) {

					collidator.collide(otherCollidator);

					otherCollidator.collide(collidator);
				}
			}

			for (int j = 0; j < collidables.size(); j++) {
				Collidable collidable = collidables.get(j);
				Shape intersect = Shape.intersect(collidator.getCollider(), collidable.getCollider());

				if (intersect.getBoundsInLocal().getWidth() != -1) {
					collidator.collide(collidable);
				} else {
					Bounds collidableBounds = collidable.getCollider().getBoundsInLocal();
					Bounds collidatorBounds = collidator.getCollider().getBoundsInLocal();
					if (collidableBounds.contains(collidatorBounds.getCenterX(), collidatorBounds.getCenterY())) {
						collidator.collide(collidable);
					}
				}
			}
		}
	}

}