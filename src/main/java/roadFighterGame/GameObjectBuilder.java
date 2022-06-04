package roadFighterGame;

import java.util.ArrayList;
import java.util.List;
import interfaces.Collidable;
import interfaces.Collidator;
import interfaces.Renderable;
import interfaces.Updatable;
import javafx.scene.Group;
import objetos.GameObject;

public class GameObjectBuilder {

	private static GameObjectBuilder instance = null;

	private Group root = null;

	private List<GameObject> allObjects = new ArrayList<GameObject>();
	private List<Collidable> collidables = new ArrayList<Collidable>();
	private List<Collidator> collidators = new ArrayList<Collidator>();
	private List<Renderable> renderables = new ArrayList<Renderable>();
	private List<Updatable> updatables = new ArrayList<Updatable>();

	private Group objectsGroup = new Group();
	private Group collidersGroup = new Group();

	private GameObjectBuilder() {

	}

	public static GameObjectBuilder getInstance() {
		if (instance == null) {
			instance = new GameObjectBuilder();
		}

		return instance;
	}

	public void setRoot(Group root) {
		if (this.root == null) {
			this.root = root;
			root.getChildren().add(objectsGroup);
//			root.getChildren().add(collidersGroup); // muestra los colliders
		}
	}

	public void add(GameObject... elementos) {

		for (GameObject elemento : elementos) {
			allObjects.add(elemento);

			if (Updatable.class.isAssignableFrom(elemento.getClass())) {
				updatables.add((Updatable) elemento);
			}

			if (Renderable.class.isAssignableFrom(elemento.getClass())) {
				renderables.add((Renderable) elemento);

				Renderable elementoRenderable = (Renderable) elemento;

				objectsGroup.getChildren().add(elementoRenderable.getRender());
			}

			if (Collidator.class.isAssignableFrom(elemento.getClass())) {
				collidators.add((Collidator) elemento);

				Collidator elementoCollidator = (Collidator) elemento;

				collidersGroup.getChildren().add(elementoCollidator.getCollider());
			}
			
			else if (Collidable.class.isAssignableFrom(elemento.getClass())) {
				collidables.add((Collidable) elemento);

				collidersGroup.getChildren().add(((Collidable) elemento).getCollider());
			}
		}
	}

	public List<Updatable> getUpdatables() {
		return new ArrayList<Updatable>(updatables);
	}

	public List<Collidable> getCollidables() {
		return new ArrayList<Collidable>(collidables);
	}

	public List<Collidator> getCollidators() {
		return new ArrayList<Collidator>(collidators);
	}

	public void remove(GameObject... elementos) {

		for (GameObject elemento : elementos) {
			allObjects.remove(elemento);

			if (Updatable.class.isAssignableFrom(elemento.getClass())) {
				updatables.remove((Updatable) elemento);
			}

			if (Renderable.class.isAssignableFrom(elemento.getClass())) {
				Renderable renderableGameObject = (Renderable) elemento;
				renderables.remove(renderableGameObject);

				objectsGroup.getChildren().remove(renderableGameObject.getRender());
			}

			if (Collidator.class.isAssignableFrom(elemento.getClass())) {
				Collidator collidatorGameObject = (Collidator) elemento;
				collidators.remove(collidatorGameObject);

				collidersGroup.getChildren().remove(collidatorGameObject.getCollider());
			} else if (Collidable.class.isAssignableFrom(elemento.getClass())) {
				Collidable collideableGameObject = (Collidable) elemento;
				collidables.remove(collideableGameObject);

				collidersGroup.getChildren().remove(collideableGameObject.getCollider());
			}
		}
	}
}
