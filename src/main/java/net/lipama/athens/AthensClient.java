package net.lipama.athens;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.MinecraftClient;

import net.lipama.athens.modules.interfaces.IMinecraftClient;
import net.lipama.athens.events.*;

import java.io.File;
import org.slf4j.*;

@SuppressWarnings("rawtypes")
public class AthensClient implements ClientModInitializer {
	public static final String MOD_ID = Athens.class.getSimpleName().toLowerCase();
	public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getName();
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static MinecraftClient MC = MinecraftClient.getInstance();
	public static IMinecraftClient IMC = (IMinecraftClient) MC;
	public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(),MOD_NAME);
	@Override
	public void onInitializeClient() {
		AthensClient.LOG.info("Initialize Client");
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			ShutdownEvent.call();
		}));
		Athens.preInit();
	}
}
