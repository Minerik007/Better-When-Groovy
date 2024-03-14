package jamdoggie.musicmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class MusicMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint, ClientModInitializer
{
    public static final String MOD_ID = "musicmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MusicManager musicManager;

    @Override
    public void onInitialize() {
        LOGGER.info("Better when Groovy initialized.");

    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void onInitializeClient()
	{
		musicManager = new MusicManager();
		musicManager.init();
	}
}
