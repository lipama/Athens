package net.lipama.athens.systems.modules.modules;

import net.lipama.athens.Athens;
import net.lipama.athens.events.system.ShutdownEvent;
import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.systems.modules.ModuleSettings;
import net.lipama.athens.utils.SaveUtils;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

public class OutLiner extends Module {
    private static final class OutLinerSettings extends ModuleSettings.Settings {
        public boolean onlyPlayers = false;

        @Override
        public void saveCurrentStateToStringViaBuilder(SaveUtils.SaveBuilder saveBuilder) {
            saveBuilder.addLine("op", onlyPlayers);
        }

        @Override
        public void updateInstanceSettingsViaLoader(SaveUtils.Loader loader) {
            this.onlyPlayers = loader.bool$("op");
        }
    }
    private final ModuleSettings<OutLinerSettings> settings =
        ModuleSettings.initLoaded(id(), OutLinerSettings.class);
    private static boolean active = false;
    public OutLiner() {
        super("OutLiner");
        this.position = Position.Left(4);
    }
    @Override
    public void onEnable() {
        active = true;
        this.settings.load();
    }
    @Override
    public void onDisable() {
        active = false;
        this.settings.save();
    }
    public static void outline(Entity entity, CallbackInfoReturnable<Boolean> ci) {
        if(!active) return;
        Optional<OutLiner> outLiner = getInstance();
        if(outLiner.isEmpty()) return;
        ci.setReturnValue(!(
            outLiner.get().settings.get().onlyPlayers &&
            !entity.isPlayer()
        ));
    }
    @SuppressWarnings("all")
    public static Optional<OutLiner> getInstance() {
        Optional<Module> moduleOptional = Athens.SYSTEMS.MODULES.MODULES.g("OutLiner");
        if(moduleOptional.isPresent()) {
            return Optional.ofNullable((OutLiner) moduleOptional.get());
        }
        return Optional.empty();
    }
}
