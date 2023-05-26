package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.Athens;
import net.lipama.athens.events.ShutdownEvent;
import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.systems.modules.ModuleSettings;
import net.lipama.athens.utils.SaveUtils;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

public class OutLiner extends Module implements ShutdownEvent.Event {
    private static final class OutLinerSettings extends ModuleSettings.Settings {
        public boolean onlyPlayers = false;

        @Override
        public void saveCurrentStateToStringViaBuilder(SaveUtils.SaveBuilder saveBuilder) {
            saveBuilder.addLine("op", onlyPlayers);
        }

        @Override
        public void updateInstanceSettingsViaLoader(SaveUtils.Loader loader) {
            this.onlyPlayers = loader.LoadB("op");
        }
    }
    private final ModuleSettings<OutLinerSettings> settings =
        ModuleSettings.initLoaded(id(), OutLinerSettings.class);
    private static boolean active = false;
    public OutLiner() {
        super("OutLiner");
        ShutdownEvent.subscribe(this);
        this.position = Position.Left(4);
    }
    @Override
    public void onEnable() {
        active = true;
    }
    @Override
    public void onDisable() {
        active = false;
        this.settings.save();
    }
    @Override
    public void onShutdown() {
        this.settings.save();
    }
    public static void outline(Entity entity, CallbackInfoReturnable<Boolean> ci) {
        if(!active) return;
        Optional<OutLiner> outLiner = getInstance();
        if(outLiner.isEmpty()) return;
        if(outLiner.get().settings.get().onlyPlayers) {
            if(entity.isPlayer()) ci.setReturnValue(true);
            else ci.setReturnValue(false);
        } else {
            ci.setReturnValue(true);
        }
//        if(!entity.isPlayer() && outLiner.get().settings.get().onlyPlayers) return;
    }
    @SuppressWarnings("all")
    public static Optional<OutLiner> getInstance() {
        Optional<Module> moduleOptional = Athens.SYSTEMS.MODULES.MODULES.get("OutLiner");
        if(moduleOptional.isPresent()) {
            return Optional.ofNullable((OutLiner) moduleOptional.get());
        }
        return Optional.empty();
    }
}
