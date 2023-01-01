package net.lipama.athens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.MinecraftClient;

import net.lipama.athens.modules.*;
import net.lipama.athens.utils.*;

import org.slf4j.*;

@SuppressWarnings("rawtypes")
public class AthensClient implements ClientModInitializer {
	public static final String MOD_ID = Athens.class.getSimpleName().toLowerCase();
	public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getName();
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static MinecraftClient MC = MinecraftClient.getInstance();
	public static final java.io.File FOLDER = new java.io.File(FabricLoader.getInstance().getGameDir().toString(),MOD_NAME);
	public static final HudUtils HUD = new HudUtils<>();
	public static final Modules MODULES = new Modules<>();
	public static RainbowColor COLOR = new RainbowColor();
	@Override
	public void onInitializeClient() {
		AthensClient.COLOR.setSpeed(0.01);
		Athens.preInit();
		BusLoop.onTick(mc->{
			MODULES.tick();
			AthensClient.COLOR = COLOR.getNext();
		});
		Runtime.getRuntime().addShutdownHook(new Thread(Athens::shutdown));
	}
	public static void init() {
		Athens.init();
		MC = MinecraftClient.getInstance();
	}
}
