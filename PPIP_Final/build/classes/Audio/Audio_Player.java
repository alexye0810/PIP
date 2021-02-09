/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Audio;
//import com.sun.scenario.Settings;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
/**
 *
 * @author Alex
 */
public class Audio_Player {
        private static HashMap<String, Clip> clips;
	private static int gap;
        private static float change= (float) 0.9;
	public static boolean mute = false;
        public static String playing;
	
	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}
	
	public static void load(String s, String n) {
		if(clips.get(n) != null) return;
		Clip clip;
		try {			
			AudioInputStream music =AudioSystem.getAudioInputStream(Audio_Player.class.getResourceAsStream(s));
			AudioFormat baseFormat = music.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream music_clip = AudioSystem.getAudioInputStream(decodeFormat, music);
			clip = AudioSystem.getClip();
			clip.open(music_clip);
			clips.put(n, clip);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void play(String s) {
		play(s, gap);
	}
	
	public static void play(String s, int i) {
		if(mute) return;
		Clip c = clips.get(s);
		if(c == null) return;
		if(c.isRunning()) c.stop();
		c.setFramePosition(i);
		while(!c.isRunning()) c.start();
	}
	
	public static void stop(String s) {
		if(clips.get(s) == null) return;
		if(clips.get(s).isRunning()) clips.get(s).stop();
	}
	
	public static void resume(String s) {
		if(mute) return;
		if(clips.get(s).isRunning()) return;
		clips.get(s).start();
	}
	
	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}
	
	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}
	
	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}
	
	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if(mute) return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}
	
	public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
	public static int getPosition(String s) { return clips.get(s).getFramePosition(); }
	
	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}
        
        public static void VolumeControl(String clip, String vol){
            Clip c = clips.get(clip);
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            if ((vol=="+")&&(change<0.81)){
            change+=(float)0.1;}
            else if ((vol=="-")&&(0.11<change)){
                change-=(float)0.1;
            }
            float gain = (range * change) + volume.getMinimum();
            volume.setValue(gain);
        }
        
        public static void VolumeControlNum(String clip, float vol){
            Clip c = clips.get(clip);
            FloatControl volume = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (range * vol) + volume.getMinimum();
            volume.setValue(gain);
        }
        
        
        
}
