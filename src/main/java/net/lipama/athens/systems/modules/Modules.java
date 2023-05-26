package net.lipama.athens.systems.modules;

import net.lipama.athens.systems.modules.modules.*;
import net.lipama.athens.systems.System;
import net.lipama.athens.events.*;
import net.lipama.athens.utils.*;

import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("all")
public class Modules implements System {
    public static final JMap<String, Module> MODULES = JMap.init();
//    public final AutoRespawn autoRespawn;
//    public final CrystalAura crystalAura;
//    public final FullBright fullBright;
//    public final AutoFish autoFish;
//    public final AutoFarm autoFarm;
//    public final BoatFly boatFly;
//    public final Flight flight;
//    public final XRay xRay;

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
        ShutdownEvent.subscribe(this);
        initModules();
//        autoRespawn = new AutoRespawn();
//        crystalAura = new CrystalAura();
//        fullBright = new FullBright();
//        autoFish = new AutoFish();
//        autoFarm = new AutoFarm();
//        boatFly = new BoatFly();
//        flight = new Flight();
//        xRay = new XRay();
        load();
    }
    private void load() {
        SaveUtils.Loader loader = SaveUtils.getLoader(SaveUtils.SavableData.MODULES);
        MODULES.iter((name, module) -> {
            module.enabled = loader.LoadB(name);
        });
//        autoRespawn.enabled = loader.LoadB(autoRespawn.name());
//        crystalAura.enabled = loader.LoadB(crystalAura.name());
//        fullBright.enabled = loader.LoadB(fullBright.name());
//        autoFish.enabled = loader.LoadB(autoFish.name());
//        autoFarm.enabled = loader.LoadB(autoFarm.name());
//        boatFly.enabled = loader.LoadB(boatFly.name());
//        flight.enabled = loader.LoadB(flight.name());
//        xRay.enabled = loader.LoadB(xRay.name());
    }
//    public void tick() {
//        int drawInt = 5;
//        if(autoRespawn.enabled) {
//            autoRespawn.hudHeight = drawInt;
//            Athens.HUD.add(autoRespawn);
//            drawInt += 10;
//        }
//        if(crystalAura.enabled) {
//            crystalAura.hudHeight = drawInt;
//            Athens.HUD.add(crystalAura);
//            drawInt += 10;
//        }
//        if(autoFarm.enabled) {
//            autoFarm.hudHeight = drawInt;
//            Athens.HUD.add(autoFarm);
//            drawInt += 10;
//        }
//        if(fullBright.enabled) {
//            fullBright.hudHeight = drawInt;
//            Athens.HUD.add(fullBright);
//            drawInt += 10;
//        }
//        if(autoFish.enabled) {
//            autoFish.hudHeight = drawInt;
//            Athens.HUD.add(autoFish);
//            drawInt += 10;
//        }
//        if(boatFly.enabled) {
//            boatFly.hudHeight = drawInt;
//            Athens.HUD.add(boatFly);
//            drawInt += 10;
//        }
//        if(flight.enabled) {
//            flight.hudHeight = drawInt;
//            Athens.HUD.add(flight);
//            drawInt += 10;
//        }
//        if(xRay.enabled) {
//            xRay.hudHeight = drawInt;
//            Athens.HUD.add(xRay);
//            drawInt += 10;
//        }
//    }
    public ArrayList<ButtonWidget> buildButtonWidgets() {
        ArrayList<ButtonWidget> res = new ArrayList<>();
        MODULES.iter((_name,module) -> {
            res.add(module.buildButtonWidget());
        });
//        res.add(autoRespawn.buildButtonWidget());
//        res.add(crystalAura.buildButtonWidget());
//        res.add(fullBright.buildButtonWidget());
//        res.add(autoFish.buildButtonWidget());
//        res.add(autoFarm.buildButtonWidget());
//        res.add(boatFly.buildButtonWidget());
//        res.add(flight.buildButtonWidget());
//        res.add(xRay.buildButtonWidget());

        return res;
    }
    @Override
    public void onShutdown() {
        SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
        MODULES.iter((name,module) -> {
            save.addLine(name, module.enabled);
        });
//        save.addLine(autoRespawn.name(), autoRespawn.enabled);
//        save.addLine(crystalAura.name(), crystalAura.enabled);
//        save.addLine(fullBright.name(), fullBright.enabled);
//        save.addLine(autoFish.name(), autoFish.enabled);
//        save.addLine(autoFarm.name(), autoFarm.enabled);
//        save.addLine(boatFly.name(), boatFly.enabled);
//        save.addLine(flight.name(), flight.enabled);
//        save.addLine(xRay.name(), xRay.enabled);
        SaveUtils.saveState(SaveUtils.SavableData.MODULES, save.build());
    }
//    private static final class IModule<M extends Module> {
//        public final String name;
//        public final M module;
//        public IModule(M module) {
//            this.module = module;
//            this.name = module.name();
//        }
//    }
//    private static final class DummyModule extends Module {
//        public DummyModule(){ super("DUMMY_MODULE"); }
//        @Override public void onEnable() { }
//        @Override public void onDisable() { }
//    }
}
