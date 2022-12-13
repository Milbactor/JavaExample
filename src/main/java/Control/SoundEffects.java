package Control;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * Enum fields with path values
 */
public enum SoundEffects {
    // sound paths in Windows environment
    Shoot1("src\\main\\sounds\\shoot1.wav"),
    Shoot2("src\\main\\sounds\\shoot2.wav"),
    Explosion("src\\main\\sounds\\explosion.wav"),
    Applause("src\\main\\sounds\\applause.wav"),
    // sound paths in UNIX environment
    Shoot1UNIX("src/main/sounds/shoot1.wav"),
    Shoot2UNIX("src/main/sounds/shoot2.wav"),
    ExplosionUNIX("src/main/sounds/explosion.wav"),
    ApplauseUNIX("src/main/sounds/applause.wav");

    String soundString;


    /**
    * This is the constructor, which handles all sounds or effects we have
    * @param soundFileName this is the sound path
    */
    SoundEffects(String soundFileName) {
        soundString = soundFileName;
    }

    /**
     * Play the sound effect from the beginning.
     */
    public void play() {
        try {
            AudioPlayer p = AudioPlayer.player;
            AudioStream as = new AudioStream(new FileInputStream(soundString));
            p.start(as);
        } catch (IOException e) {
        }
    }
}
