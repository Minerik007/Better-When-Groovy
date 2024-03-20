package jamdoggie.musicmod.mixins;

import jamdoggie.musicmod.MusicManager;
import jamdoggie.musicmod.MusicMod;
import jamdoggie.musicmod.mixininterface.ISoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundPool;
import net.minecraft.client.sound.SoundPoolEntry;
import net.minecraft.client.sound.SoundTypeHelper;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.season.Seasons;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

import java.io.File;
import java.util.Random;

@Mixin(value = SoundManager.class, remap = false)
public class SoundManagerMixin implements ISoundManager
{
	@Shadow
	private static boolean loaded;

	@Shadow
	private GameSettings options;

	@Shadow
	private int ticksBeforeMusic;

	@Shadow
	@Final
	private Random rand;

	@Shadow
	private Minecraft mc;

	@Shadow
	@Final
	private SoundPool cave;
	@Shadow
	@Final
	private SoundPool soundPoolMusic;
	@Shadow
	private static SoundSystem sndSystem;
	@Unique
	private int minTicksBetweenSongs = 6000;

	private SoundPoolEntry lastSong = null;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(CallbackInfo ci)
	{
		ticksBeforeMusic = 0;
	}

	@Inject(method = "playRandomMusicIfReady", at = @At("HEAD"), cancellable = true)
	private void playRandomMusicIfReady(CallbackInfo ci)
	{
		if (!loaded || sndSystem == null || SoundTypeHelper.getEffectiveVolume(SoundType.MUSIC, this.options) == 0.0f) {
			ci.cancel();
			return;
		}

		Minecraft minecraft = Minecraft.getMinecraft(this);

		if (minecraft == null || minecraft.thePlayer == null || minecraft.theWorld == null)
		{
			ci.cancel();
			return;
		}

		SoundPool musicPool = this.soundPoolMusic;

		// Play a cave sound if we're in the overworld & underground
		if (!minecraft.thePlayer.world.canBlockSeeTheSky(
			MathHelper.floor_double(minecraft.thePlayer.x),
			MathHelper.floor_double(minecraft.thePlayer.y),
			MathHelper.floor_double(minecraft.thePlayer.z)))
		{
			musicPool = this.cave;
		}

		// Play nether music if we're in the nether
		if (minecraft.thePlayer.world.dimension == Dimension.nether)
		{
			musicPool = MusicMod.musicManager.netherMusic;
		}
		else
		// Overworld music
		{
			if (minecraft.thePlayer.getGamemode() == Gamemode.creative)
			{
				musicPool = MusicMod.musicManager.creativeMusic;
			}
			else
			{
				// 50/50 chance of playing a seasonal song
				if (rand.nextInt(2) == 1)
				{
					// Fall
					if (minecraft.theWorld.seasonManager.getCurrentSeason() == Seasons.OVERWORLD_FALL)
						musicPool = MusicMod.musicManager.fallMusic;

					// Winter
					if (minecraft.theWorld.seasonManager.getCurrentSeason() == Seasons.OVERWORLD_WINTER)
					{
						musicPool = MusicMod.musicManager.winterMusic;

						// 50/50 chance of playing a night song if night
						if (!minecraft.theWorld.isDaytime() &&
							rand.nextInt(2) == 1)
						{
							musicPool = MusicMod.musicManager.winterNight;
						}
					}
				}
			}
		}

		if (!sndSystem.playing("BgMusic") && !sndSystem.playing("streaming"))
		{
			if (this.ticksBeforeMusic > 0)
			{
				--this.ticksBeforeMusic;
				ci.cancel();
				return;
			}

			SoundPoolEntry soundpoolentry = musicPool.getRandomSound();

			if (soundpoolentry != null)
			{
				this.ticksBeforeMusic = this.rand.nextInt(minTicksBetweenSongs) + minTicksBetweenSongs;

				playMusic(soundpoolentry);
			}
		}

		ci.cancel();
	}

	@Inject(method = "addMusic", at = @At("HEAD"))
	private void addMusic(String s, File file, CallbackInfo ci)
	{
		System.out.println("Adding music: " + s + " from " + file.getAbsolutePath());
	}

	@Override
	@Unique
	public SoundSystem getSoundSystem()
	{
		return sndSystem;
	}

	@Override
	@Unique
	public boolean isMusicPlaying()
	{
		return sndSystem.playing("BgMusic");
	}

	@Override
	@Unique
	public boolean isStreamableSoundPlaying()
	{
		return sndSystem.playing("streaming");
	}

	@Override
	public void playTitleScreenMusic()
	{
		if (!loaded || sndSystem == null || SoundTypeHelper.getEffectiveVolume(SoundType.MUSIC, this.options) == 0.0f)
		{
			return;
		}

		SoundPoolEntry soundpoolentry = MusicMod.musicManager.titleScreenMusic.getRandomSound();

		if (soundpoolentry != null)
		{
			System.out.println("Playing title screen music");

			playMusic(soundpoolentry);
		}
	}

	@Override
	public void stopBgMusic()
	{
		sndSystem.fadeOut("BgMusic", null, 2000);
	}

	@Override
	public void setTicksUntilMusic(int ticks)
	{
		ticksBeforeMusic = ticks;
	}

	@Override
	public void playMusic(SoundPoolEntry song)
	{
		if (sndSystem.playing("BgMusic"))
			sndSystem.stop("BgMusic");

		lastSong = song;
		sndSystem.backgroundMusic("BgMusic", song.soundUrl, song.soundName, false);
		sndSystem.setVolume("BgMusic", SoundTypeHelper.getEffectiveVolume(SoundType.MUSIC, this.options));
		sndSystem.play("BgMusic");
	}
}
