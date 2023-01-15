package net.lipama.athens.modules;

import com.linkrbot.projects.orbit.EventHandler;

import net.minecraft.client.gui.widget.ButtonWidget;

import net.lipama.athens.events.*;
import net.lipama.athens.utils.*;
import net.lipama.athens.*;

import java.util.*;

@SuppressWarnings("all")
public class Modules<M extends Module> {
    public Modules() {
        // Register Modules Here
        add(new FullBright());
        add(new AutoFish());
        add(new BoatFly());
        add(new Flight());
        add(new XRay());
        add(new AutoFarm());
    }


    public ArrayList<IModule<M>> MODULES = new ArrayList<>();
    public <Mod extends Module> void add(Mod module) {
        IModule mod = new IModule<>(module);
        mod.module.enabled = SaveUtils.loadState(SaveUtils.SavableData.MODULE, mod.name);
        MODULES.add(mod);
        AthensClient.EVENT_BUS.subscribe(module);
    }
    public M getByName(String name) {
        for (IModule<M> module : MODULES) {
            if (module.name.equals(name)) return module.module;
        }
        return (M) new DummyModule();
    }
    public ArrayList<ButtonWidget> buildButtonWidgets() {
        ArrayList<ButtonWidget> res = new ArrayList<>();
        for (IModule<M> module : MODULES) {
            res.add(module.module.buildButtonWidget());
        }
        return res;
    }
    public void tick() {
        int drawInt = 5;
        for(IModule<M> module : MODULES) {
            if(module.module.enabled) {
                module.module.hudHeight = drawInt;
                Athens.HUD.add(module.module);
                drawInt += 10;
            }
        }
    }
    @EventHandler
    private void onShutdown(ShutdownEvent event) {
        SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
        for(IModule<M> module : MODULES) {
            save.addLine(module.module.name(), module.module.enabled);
        }
        SaveUtils.saveState(SaveUtils.SavableData.MODULE, save.build());
    }
    private static final class IModule<M extends Module> {
        public final String name;
        public final M module;
        public IModule(M module) {
            this.module = module;
            this.name = module.name();
        }
    }
    private static final class DummyModule extends Module {
        public DummyModule(){ super("DUMMY_MODULE"); }
        @Override public void onEnable() { }
        @Override public void onDisable() { }
    }
}
