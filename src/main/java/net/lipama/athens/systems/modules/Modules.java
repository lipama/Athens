package net.lipama.athens.systems.modules;

import net.lipama.athens.events.system.ShutdownEvent;
import net.lipama.athens.systems.modules.modules.*;
import net.lipama.athens.systems.System;
import net.lipama.athens.systems.modules.modules.chams.ChamsModule;
import net.lipama.athens.systems.modules.modules.crystalaura.CrystalAura;
import net.lipama.athens.utils.*;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.titanium.composer.EventHandler;

import java.util.concurrent.atomic.AtomicReference;
import java.util.*;

@SuppressWarnings("all")
public class Modules implements System {
    public static final JMap<String, Module> MODULES = JMap.init();
    private void initModules() {
        add(new AutoRespawn());
        add(new CrystalAura());
        add(new FullBright());
        add(new AutoFish());
        add(new AutoFarm());
        add(new BoatFly());
        add(new Flight());
        add(new XRay());
        add(new OutLiner());
        add(new ChamsModule());
    }
    private void add(Module module) {
        MODULES.add(module.name(), module);
    }
    public Optional<Module> get(String id) {
        AtomicReference<Module> res = null;
        MODULES.iter((name,module) -> {
            if(id.equalsIgnoreCase(name)){
                res.set(module);
            }
        });
        return Optional.ofNullable(res.get());
    }

    public Modules() {
        initModules();
        load();
    }
    private void load() {
        SaveUtils.Loader loader = SaveUtils.getLoader(SaveUtils.SavableData.MODULES);
        MODULES.iter((name, module) -> {
            module.enabled = loader.bool$(name);
        });
    }
    public ArrayList<ButtonWidget> buildButtonWidgets() {
        ArrayList<ButtonWidget> res = new ArrayList<>();
        MODULES.iter((_name,module) -> {
            res.add(module.buildButtonWidget());
        });
        return res;
    }
    @EventHandler
    public void onShutdown(ShutdownEvent event) {
        SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
        MODULES.iter((name,module) -> {
            save.addLine(name, module.enabled);
        });
        SaveUtils.saveState(SaveUtils.SavableData.MODULES, save.build());
    }
}
