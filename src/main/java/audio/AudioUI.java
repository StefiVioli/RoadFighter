package audio;

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

public class AudioUI extends GameObject implements Renderable, Updatable{

	public final static double offScreenTolerance = Config.baseHeight * 5;
	private Text audioText;
	private VBox render;
	
	public static double valorActual = 1;
	
	public AudioUI(int primero) {
		audioText = new Text("Audio: " + valorActual * 100 + "%");
		render = new VBox(audioText);
		
		if(primero == 1) {
			render.setTranslateX(Config.baseWidth - 82);
			render.setTranslateY(Config.baseHeight - 8 * Config.modificadorResolucion);
		}else {
			render.setTranslateX(-100); //esto no deberia ser asi.
			render.setTranslateY(-100);
		}
		
		
		Font font = Font.font("Aharoni", FontWeight.LIGHT, FontPosture.ITALIC, 14);
		audioText.setTextAlignment(TextAlignment.RIGHT);
		audioText.setFont(font);
		audioText.setFill(Color.FUCHSIA);
		
		render.setViewOrder(2);
	}
	
	@Override
	public void update(double deltaTime) {
		audioText.setText("Audio: " + (int)(valorActual * 100) + "%");
	}

	@Override
	public Node getRender() {
		return render;
	}
}