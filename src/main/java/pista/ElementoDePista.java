package pista;

import objetos.GameObject;

public class ElementoDePista extends GameObject{
	protected double y;
	protected double x;
	
	public ElementoDePista(double x, double y) {
		this.x = x;
		this.y = y;
	}

	protected void setY(double y) {
		this.y = y;
	}

	public double getY() {
		return y;
	}
	
	protected void setX(double x) {
		this.x = x;
	}
	
	public double getX() {
		return x;
	}
}