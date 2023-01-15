package net.lipama.athens;

import com.linkrbot.projects.orbit.*;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.MinecraftClient;

import net.lipama.athens.events.*;

import java.lang.invoke.MethodHandles;
import java.io.File;
import org.slf4j.*;

@SuppressWarnings("rawtypes")
public class AthensClient implements ClientModInitializer {
	public static final String MOD_ID = Athens.class.getSimpleName().toLowerCase();
	public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getName();
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static MinecraftClient MC = MinecraftClient.getInstance();
	public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(),MOD_NAME);
	public static final IEventBus EVENT_BUS = new EventBus();
	@Override
	public void onInitializeClient() {
		AthensClient.LOG.info("Initialize Client");
		EVENT_BUS.registerLambdaFactory(
			"net.lipama." + MOD_ID,
			(lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup())
		);
		EVENT_BUS.subscribe(this);
		Runtime.getRuntime().addShutdownHook(new Thread(()->{
			EVENT_BUS.post(ShutdownEvent.get());
		}));
		Athens.preInit();
	}
}
