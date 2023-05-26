package net.lipama.athens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.lipama.athens.systems.Systems;
import net.lipama.athens.systems.screens.AthensOptionsScreen;
import net.lipama.athens.utils.KeyBinds;
import net.lipama.athens.utils.SaveUtils;
import net.minecraft.client.MinecraftClient;

import net.lipama.athens.systems.interfaces.IMinecraftClient;
import net.lipama.athens.events.*;

import java.io.File;
import org.slf4j.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class Athens implements ClientModInitializer {
	public static final String MOD_ID = Athens.class.getSimpleName().toLowerCase();
	public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getName();
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), MOD_NAME);
	public static MinecraftClient MC = MinecraftClient.getInstance();
	public static IMinecraftClient IMC = (IMinecraftClient) MC;
	public static final Systems SYSTEMS = Systems.init();
	public static final class Config {
		public static final boolean SILENT_MODE = true;
		public static boolean useHud = true;
		public static boolean useBaritone = false;
	}
	@Override
	public void onInitializeClient() {
		LOG.info("Initializing Athens");
		Runtime.getRuntime().addShutdownHook(new Thread(Athens::onShutdown, MOD_NAME));
		Config.useHud = SaveUtils.getLoader(SaveUtils.SavableData.GLOBAL).LoadB("Hud");
		KeyBinds.registerKeybinds();
		AthensOptionsScreen.preInit();
	}
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static void postInit() {
		SYSTEMS.COLOR.setSpeed(0.01);
		if(!FOLDER.exists()) {
			FOLDER.getParentFile().mkdirs();
			FOLDER.mkdir();
			LOG.info("Welcome, new user");
		} else {
			LOG.info("Welcome back, user");
		}
	}
	public static void onPostTick() {
		SYSTEMS.COLOR.getNext();
	}
	public static void onShutdown() {
		LOG.info("Shutting Down Athens");
		ShutdownEvent.call();
		SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
		save.addLine("Hud", Config.useHud);
		save.addLine("Baritone", Config.useBaritone);
		SaveUtils.saveState(SaveUtils.SavableData.GLOBAL, save.build());
		LOG.error("ALL GOOD");
	}
}
