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

public class Velocidad extends GameObject implements Renderable, Updatable  {

	private int velocidad;
	private Text velocidadText;
	
	private VBox render;
	
	public Velocidad(double y) {
		this.velocidad = 0;
		velocidadText = new Text(Integer.toString(velocidad));
		render = new VBox(velocidadText);
		
		render.setTranslateX(Config.baseWidth - 105);
		render.setTranslateY(y);
		
		Font font = Font.font("Malgun Gothic", FontWeight.BOLD, FontPosture.REGULAR, 18);
		velocidadText.setTextAlignment(TextAlignment.CENTER);
		velocidadText.setFont(font);
		velocidadText.setFill(Color.WHITE);

		render.setViewOrder(2);
	}

	@Override
	public void update(double deltaTime) {
		velocidadText.setText(Integer.toString(velocidad));

	}

	@Override
	public Node getRender() {
		return render;
	}

	public void asignar(int valor) {
		this.velocidad = (int) (valor * 0.2);
	}
}
