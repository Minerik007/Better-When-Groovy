package jamdoggie.musicmod.mixininterface;

import net.minecraft.client.sound.SoundPoolEntry;
import paulscode.sound.SoundSystem;

public interface ISoundManager
{
	SoundSystem getSoundSystem();
	boolean isMusicPlaying();
	boolean isStreamableSoundPlaying();
	void playTitleScreenMusic();
	void stopBgMusic();
	void setTicksUntilMusic(int ticks);
	void playMusic(SoundPoolEntry song);
}
