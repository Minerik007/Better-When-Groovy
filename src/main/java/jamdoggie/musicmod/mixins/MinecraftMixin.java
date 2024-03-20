package jamdoggie.musicmod.mixins;

import jamdoggie.musicmod.mixininterface.ISoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin
{
	@Shadow
	public SoundManager sndManager;

	/*@Inject(method = "startWorld", at = @At("HEAD"))
	private void startWorld(String worldDirName, String worldName, long seed, CallbackInfo ci)
	{
		((ISoundManager)sndManager).stopBgMusic();
	}*/

	@Inject(method = "changeWorld(Lnet/minecraft/core/world/World;Ljava/lang/String;Lnet/minecraft/core/entity/player/EntityPlayer;)V"
		, at = @At("HEAD"))
	private void changeWorld(World world, String loadingTitle, EntityPlayer player, CallbackInfo ci)
	{
		((ISoundManager)sndManager).stopBgMusic();
		((ISoundManager)sndManager).setTicksUntilMusic(600);
	}
}
