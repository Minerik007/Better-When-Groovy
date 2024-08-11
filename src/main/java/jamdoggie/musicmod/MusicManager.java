package jamdoggie.musicmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.sound.SoundPool;
import turniplabs.halplibe.HalpLibe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Hashtable;

public class MusicManager
{
	public static final File appDirectory;
	public static final File exMusicDirectory;
	static {
		if (!HalpLibe.isClient){
			appDirectory = null;
			exMusicDirectory = null;
		} else {
			appDirectory = Minecraft.getAppDir("minecraft-bta");
			exMusicDirectory = new File(appDirectory.getAbsolutePath() + "/resources/mod/ex-music");
		}
	}
	
	public static SoundPool music = new SoundPool();
	public static SoundPool notFittingMusic = new SoundPool();
	
	public SoundPool titleScreenMusic = new SoundPool();
	public SoundPool netherMusic = new SoundPool();

	public SoundPool fallMusic = new SoundPool();
	public SoundPool winterMusic = new SoundPool();
	public SoundPool winterNight = new SoundPool();
	public SoundPool creativeMusic = new SoundPool();

	private static final Hashtable<String, String> fileCache = new Hashtable<>();

	public void init()
	{
		alphaMusic();
		betaMusic();
		extraMusic();

		creativeMusic();
		titleScreenMusic();
		netherMusic();
		fallMusic();
		winterMusic();
		winterNight();
	}

	private void alphaMusic()
	{
		extractMusic("musicalpha/","ex_beginning.ogg", music);
		extractMusic("musicalpha/","ex_cat.ogg", notFittingMusic);
		extractMusic("musicalpha/","ex_chris.ogg", music);
		extractMusic("musicalpha/","ex_clark.ogg", music);
		extractMusic("musicalpha/","ex_danny.ogg", music);
		extractMusic("musicalpha/","ex_death.ogg", notFittingMusic);
		extractMusic("musicalpha/","ex_dog.ogg", notFittingMusic);
		extractMusic("musicalpha/","ex_door.ogg", music);
		extractMusic("musicalpha/","ex_droopy_likes_ricochet.ogg", music);
		extractMusic("musicalpha/","ex_droopy_likes_your_face.ogg", music);
		extractMusic("musicalpha/","ex_dry_hands.ogg", music);
		extractMusic("musicalpha/","ex_equinoxe.ogg", music);
		extractMusic("musicalpha/","ex_excuse.ogg", music);
		extractMusic("musicalpha/","ex_haggstorm.ogg", music);
		extractMusic("musicalpha/","ex_key.ogg", music);
		extractMusic("musicalpha/","ex_living_mice.ogg", music);
		extractMusic("musicalpha/","ex_mice_on_venus.ogg", music);
		extractMusic("musicalpha/","ex_minecraft.ogg", music);
		extractMusic("musicalpha/","ex_moog_city.ogg", music);
		extractMusic("musicalpha/","ex_oxygene.ogg", music);
		extractMusic("musicalpha/","ex_subwoofer_lullaby.ogg", music);
		extractMusic("musicalpha/","ex_sweden.ogg", music);
		extractMusic("musicalpha/","ex_thirteen.ogg", notFittingMusic);
		extractMusic("musicalpha/","ex_wet_hands.ogg", music);

	}

	private void betaMusic()
	{
		extractMusic("musicbeta/","ex_alpha.ogg", music);
		extractMusic("musicbeta/","ex_aria_math.ogg", music);
		extractMusic("musicbeta/","ex_ballad_of_the_cats.ogg", music);
		extractMusic("musicbeta/","ex_beginning_2.ogg", music);
		extractMusic("musicbeta/","ex_biome_fest.ogg", music);
		extractMusic("musicbeta/","ex_blind_spots.ogg", music);
		extractMusic("musicbeta/","ex_blocks.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_chirp.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_concrete_halls.ogg", music);
		extractMusic("musicbeta/","ex_dead_voxel.ogg", music);
		extractMusic("musicbeta/","ex_dreiton.ogg", music);
		extractMusic("musicbeta/","ex_eleven.ogg", music);
		extractMusic("musicbeta/","ex_far.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_flake.ogg", music);
		extractMusic("musicbeta/","ex_floating_trees.ogg", music);
		extractMusic("musicbeta/","ex_haunt_muskie.ogg", music);
		extractMusic("musicbeta/","ex_intro.ogg", music);
		extractMusic("musicbeta/","ex_ki.ogg", music);
		extractMusic("musicbeta/","ex_kyoto.ogg", music);
		extractMusic("musicbeta/","ex_mall.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_mellohi.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_moog_city_2.ogg", music);
		extractMusic("musicbeta/","ex_mutation.ogg", music);
		extractMusic("musicbeta/","ex_stal.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_strad.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_taswell.ogg", music);
		extractMusic("musicbeta/","ex_the_end.ogg", music);
		extractMusic("musicbeta/","ex_wait.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_ward.ogg", notFittingMusic);
		extractMusic("musicbeta/","ex_warmth.ogg", music);
	}
	
	private void extraMusic()
	{
		extractMusic("othermusic/","ex_sunrise.ogg", music);
		extractMusic("othermusic/","ex_far_lands.ogg", music);
	}

	private void creativeMusic()
	{
		creativeMusic.addSound("exmusic.ex_aria_math.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_aria_math.ogg"));
		creativeMusic.addSound("exmusic.ex_biome_fest.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_biome_fest.ogg"));
		creativeMusic.addSound("exmusic.ex_blind_spots.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_blind_spots.ogg"));
		creativeMusic.addSound("exmusic.ex_dreiton.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_dreiton.ogg"));
		creativeMusic.addSound("exmusic.ex_haunt_muskie.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_haunt_muskie.ogg"));
	}

	private void titleScreenMusic()
	{
		titleScreenMusic.addSound("exmusic.ex_sunrise.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/othermusic/ex_sunrise.ogg"));
		titleScreenMusic.addSound("exmusic.ex_beginning_2.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_beginning_2.ogg"));
		titleScreenMusic.addSound("exmusic.ex_beginning.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicalpha/ex_beginning.ogg"));
		titleScreenMusic.addSound("exmusic.ex_door.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicalpha/ex_door.ogg"));
		titleScreenMusic.addSound("exmusic.ex_floating_trees.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_floating_trees.ogg"));
		titleScreenMusic.addSound("exmusic.ex_moog_city_2.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_moog_city_2.ogg"));
		titleScreenMusic.addSound("exmusic.ex_mutation.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_mutation.ogg"));
	}

	private void netherMusic()
	{
		netherMusic.addSound("exmusic.ex_concrete_halls.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_concrete_halls.ogg"));
		netherMusic.addSound("exmusic.ex_dead_voxel.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_dead_voxel.ogg"));
		netherMusic.addSound("exmusic.ex_warmth.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_warmth.ogg"));
		netherMusic.addSound("exmusic.ex_excuse.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicalpha/ex_excuse.ogg"));
	}

	private void fallMusic()
	{
		fallMusic.addSound("exmusic.ex_blind_spots.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_blind_spots.ogg"));
	}

	private void winterMusic()
	{
		winterMusic.addSound("exmusic.ex_biome_fest.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_biome_fest.ogg"));
		winterMusic.addSound("exmusic.ex_flake.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_flake.ogg"));
	}

	private void winterNight()
	{
		winterNight.addSound("exmusic.ex_kyoto.ogg",  new File(exMusicDirectory.getAbsolutePath() + "/musicbeta/ex_kyoto.ogg"));
	}

	// Modification of the method from HalpLibe
	public static void extractMusic(String soundLocation, String soundFile, SoundPool music){
		if (appDirectory == null) {
			HalpLibe.LOGGER.warn("Resource directory cannot be found, function is only intended to be ran on the Client!");
			return;
		}
		String destination = exMusicDirectory.getPath();
		String source = (getMusicDirectory() + soundLocation + soundFile).replace("//", "/").trim();
		HalpLibe.LOGGER.info("File source: " + source);
		HalpLibe.LOGGER.info("File destination: " + destination);

		HalpLibe.LOGGER.info(extract(source, destination, soundLocation + soundFile) + " Added to sound directory");
		
		music.addSound("exmusic." + soundFile,  new File(exMusicDirectory.getAbsolutePath() + "/" + soundLocation + soundFile));
	}

	public static String getMusicDirectory()
	{
		return "/assets/" + MusicMod.MOD_ID + "/music/";
	}

	private static String extract(String jarFilePath, String destination, String soundSource){

		if(jarFilePath == null)
			return null;

		// See if we already have the file
		if(fileCache.contains(jarFilePath))
			return fileCache.get(jarFilePath);

		// Alright, we don't have the file, let's extract it
		try {
			// Read the file we're looking for
			InputStream fileStream = MusicManager.class.getResourceAsStream(jarFilePath);

			// Was the resource found?
			if(fileStream == null)
				return null;

			File tempFile = new File(new File(destination), soundSource);
			tempFile.getParentFile().mkdirs();
			tempFile.delete();
			Files.createFile(tempFile.toPath());

			// Set this file to be deleted on VM exit
			tempFile.deleteOnExit();

			// Create an output stream to barf to the temp file
			OutputStream out = Files.newOutputStream(tempFile.toPath());

			// Write the file to the temp file
			byte[] buffer = new byte[1024];
			int len = fileStream.read(buffer);
			while (len != -1) {
				out.write(buffer, 0, len);
				len = fileStream.read(buffer);
			}

			// Store this file in the cache list
			fileCache.put(jarFilePath, tempFile.getAbsolutePath());

			// Close the streams
			fileStream.close();
			out.close();

			// Return the path of this sweet new file
			return tempFile.getAbsolutePath();

		} catch (IOException e) {
			HalpLibe.LOGGER.warn(e.toString());
			for (StackTraceElement element :e.getStackTrace()){
				HalpLibe.LOGGER.debug(element.toString());
			}
			return null;
		}
	}
}
