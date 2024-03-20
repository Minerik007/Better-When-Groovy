package jamdoggie.musicmod.mixins;

import jamdoggie.musicmod.mixininterface.ISoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiMainMenu.class, remap = false)
public abstract class GuiMainMenuMixin extends GuiScreen
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

	@Inject(method = "drawScreen", at = @At("TAIL"))
	private void drawScreen(int mouseX, int mouseY, float partialTick, CallbackInfo ci)
	{
		I18n i18n = I18n.getInstance();

		String c418Disclaimer = i18n.translateKey("musicmod.titledisclaimer");
		this.drawString(this.fontRenderer, c418Disclaimer, this.width - this.fontRenderer.getStringWidth(c418Disclaimer) - 2, this.height - 30, 0xFFFFFF);
	}
}
