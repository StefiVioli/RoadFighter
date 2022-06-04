package utils;

import config_valores.Config;
import interfaces.Renderable;
import interfaces.Updatable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import objetos.GameObject;

public class Puntaje extends GameObject implements Renderable, Updatable {
	private int puntaje;

	private Text puntajeText;

	private VBox render;

	public Puntaje(double y) {
		
		this.puntaje = 0;
		puntajeText = new Text(Integer.toString(puntaje));

		render = new VBox(puntajeText);

		render.setTranslateX(Config.baseWidth - 105);
		render.setTranslateY(y);

		Font font = Font.font("Malgun Gothic", FontWeight.BOLD, FontPosture.REGULAR, 18);
		puntajeText.setTextAlignment(TextAlignment.CENTER);
		puntajeText.setFont(font);
		puntajeText.setFill(Color.WHITE);

		render.setViewOrder(2);

	}

	@Override
	public void update(double deltaTime) {
		puntajeText.setText(Integer.toString(puntaje));

	}

	@Override
	public Node getRender() {
		return render;
	}

	public void asignar(int valor) {
		this.puntaje = (int) (valor * 0.05);
	}

	@Override
	public String toString() {
		return puntaje + " Pts";
	}
}
