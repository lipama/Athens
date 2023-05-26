package net.lipama.athens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import net.lipama.athens.systems.interfaces.IMinecraftClient;
import net.lipama.athens.events.system.*;
import net.lipama.athens.systems.*;
import net.lipama.athens.utils.*;

import net.titanium.composer.*;
import org.slf4j.*;
import java.io.*;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class Athens implements ClientModInitializer {
	public static final String MOD_ID = Athens.class.getSimpleName().toLowerCase();
	private static final String PACKAGE = Athens.class.getPackageName();
	public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(MOD_ID)
		.get().getMetadata().getName();
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), MOD_NAME);
	public static MinecraftClient MC = MinecraftClient.getInstance();
	public static IMinecraftClient IMC = (IMinecraftClient) MC;
	public static final IEventBus COMPOSER = Composer.getDefault(PACKAGE);
	public static final Systems SYSTEMS = Systems.init();
	public static final class Config {
		public static final boolean SILENT_MODE = true;
		public static boolean useHud = true;
		public static boolean useBaritone = false;
	}
	@Override
	public void onInitializeClient() {
		LOG.info("Initializing " + MOD_NAME);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> COMPOSER.post(ShutdownEvent.get()), MOD_NAME));
		COMPOSER.subscribe(this);
		COMPOSER.post(PreInitEvent.get());
	}
	@EventHandler
	public void onPreInit(PreInitEvent event) {
		Config.useHud = SaveUtils.getLoader(SaveUtils.SavableData.GLOBAL).LoadB("Hud");
//		KeyBinds.registerKeybinds();
//		AthensOptionsScreen.preInit();
		SYSTEMS.COLOR.setSpeed(0.01);
		if(!FOLDER.exists()) {
			FOLDER.getParentFile().mkdirs();
			FOLDER.mkdir();
			LOG.info("Welcome, new user");
		} else {
			LOG.info("Welcome back, user");
		}
	}

//	@EventHandler
//	@SuppressWarnings("ResultOfMethodCallIgnored")
//	public void onPostInit(PostInitEvent event) {
//
//	}
//	public static void onPostTick() {
//		SYSTEMS.COLOR.getNext();
//	}
	@EventHandler
	private void onShutdown(ShutdownEvent event) {
		LOG.info("Shutting Down Athens");
		SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
		save.addLine("Hud", Config.useHud);
		save.addLine("Baritone", Config.useBaritone);
		SaveUtils.saveState(SaveUtils.SavableData.GLOBAL, save.build());
		LOG.error("ALL GOOD");
	}
}
