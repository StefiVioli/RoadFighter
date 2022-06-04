package audio;

import javafx.scene.media.AudioClip;

public final class AudioResources {

	private static AudioClip create(String name) {
		return new AudioClip(ClassLoader.getSystemResource(name).toString());
	}

	public static AudioClip getExplosionAudio() {
		return create("sfx/Explosion2.wav");
	}

	public static AudioClip getChoqueAudio() {
		return create("sfx/Choque.wav");
	}
	
	public static AudioClip getPowerUpAudio() {
		return create("sfx/Powerup.wav");
	}
}