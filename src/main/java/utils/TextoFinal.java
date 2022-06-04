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

public class TextoFinal extends GameObject implements Renderable, Updatable {

	private int puntaje;
	private Text puntajeText;
	private Text puntajePos;
	private Text puntajeColor;
	private Text numJugador;
	private VBox render;

	public TextoFinal(Puntaje puntaje, int pos, String color) {
		if (color.equals("Rojo")) {
			numJugador = new Text("\t  " + 1 + "\n");
		} else {
			numJugador = new Text("\t  " + 2 + "\n");
		}

		puntajeText = new Text("\n" + puntaje.toString() + "\n\n\n");
		puntajePos = new Text("POS " + Integer.toString(pos) + "\n\n");
		puntajeColor = new Text(color);

		render = new VBox(numJugador, puntajeText, puntajePos, puntajeColor);
		if (pos == 1) {
			render.setTranslateX(Config.baseCentro - 120);
			render.setTranslateY(238);
		} else {
			render.setTranslateX(Config.baseCentro + 162);
			render.setTranslateY(238);
		}

		Font font = Font.font("Malgun Gothic", FontWeight.BOLD, FontPosture.ITALIC, 18);
		Font font2 = Font.font("Malgun Gothic", FontWeight.BOLD, FontPosture.REGULAR, 32);

		numJugador.setTextAlignment(TextAlignment.CENTER);
		numJugador.setFont(font2);
		numJugador.setFill(Color.WHITE);

		puntajeText.setTextAlignment(TextAlignment.CENTER);
		puntajeText.setFont(font);
		puntajeText.setFill(Color.WHITE);

		puntajePos.setTextAlignment(TextAlignment.CENTER);
		puntajePos.setFont(font);
		puntajePos.setFill(Color.WHITE);

		puntajeColor.setTextAlignment(TextAlignment.CENTER);
		puntajeColor.setFont(font);
		puntajeColor.setFill(Color.WHITE);
		render.setViewOrder(-12);
	}

	@Override
	public void update(double deltaTime) {
	}

	@Override
	public Node getRender() {
		return render;
	}

	@Override
	public String toString() {
		return puntaje + "";
	}
}
