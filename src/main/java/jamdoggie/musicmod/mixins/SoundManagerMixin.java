package jamdoggie.musicmod.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundPool;
import net.minecraft.client.sound.SoundPoolEntry;
import net.minecraft.client.sound.SoundTypeHelper;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = SoundManager.class, remap = false)
public class SoundManagerMixin
{
	@Shadow
	private static boolean loaded;

	@Shadow
	private static SoundSystem sndSystem;

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
	private int minTicksBetweenSongs = 0; // should be 6000, 0 for testing purposes.

	@Inject(method = "playRandomMusicIfReady", at = @At("HEAD"), cancellable = true)
	private void playRandomMusicIfReady(CallbackInfo ci)
	{
		if (!loaded || sndSystem == null || SoundTypeHelper.getEffectiveVolume(SoundType.MUSIC, this.options) == 0.0f) {
			ci.cancel();
			return;
		}
		if (!sndSystem.playing("BgMusic") && !sndSystem.playing("streaming")) {
			if (this.ticksBeforeMusic > 0) {
				--this.ticksBeforeMusic;
				ci.cancel();
				return;
			}
			SoundPoolEntry soundpoolentry = this.mc != null && this.mc.thePlayer != null && !this.mc.thePlayer.world.canBlockSeeTheSky(MathHelper.floor_double(this.mc.thePlayer.x), MathHelper.floor_double(this.mc.thePlayer.y), MathHelper.floor_double(this.mc.thePlayer.z)) ? this.cave.getRandomSound() : this.soundPoolMusic.getRandomSound();
			if (soundpoolentry != null) {
				this.ticksBeforeMusic = this.rand.nextInt(minTicksBetweenSongs) + minTicksBetweenSongs;
				sndSystem.backgroundMusic("BgMusic", soundpoolentry.soundUrl, soundpoolentry.soundName, false);
				sndSystem.setVolume("BgMusic", SoundTypeHelper.getEffectiveVolume(SoundType.MUSIC, this.options));
				sndSystem.play("BgMusic");
			}
		}

		ci.cancel();
	}
}
