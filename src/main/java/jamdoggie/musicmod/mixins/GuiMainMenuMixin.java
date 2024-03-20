package jamdoggie.musicmod.mixins;

import jamdoggie.musicmod.mixininterface.ISoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiMainMenu.class, remap = false)
public class GuiMainMenuMixin
{
	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci)
	{
		Minecraft mc = Minecraft.getMinecraft(this);
		ISoundManager mixinSndManager = (ISoundManager) mc.sndManager;

		if (!mixinSndManager.isMusicPlaying() && !mixinSndManager.isStreamableSoundPlaying()
		&& mc.currentScreen instanceof GuiMainMenu)
		{
			mixinSndManager.playTitleScreenMusic();
		}
	}
}
