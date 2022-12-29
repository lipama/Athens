package net.lipama.athens.modules;

import net.minecraft.client.gui.widget.ButtonWidget;

import net.lipama.athens.AthensClient;
import net.lipama.athens.utils.*;

import java.util.*;

public class Modules<M extends Module> {
    public Modules() {
        add(new FullBright());
        add(new AutoFish());
        add(new BoatFly());

        init();
    }







    public ArrayList<IModule<M>> MODULES = new ArrayList<>();
    @SuppressWarnings("all")
    public <Mod extends Module> void add(Mod module) {
        MODULES.add(new IModule(module));
    }
    public Optional<M> getByName(String name) {
        for(IModule<M> module : MODULES) {
            if(module.name.equals(name)) return Optional.of(module.module);
        }
        return Optional.empty();
    }
    public void toggle(String name) {
        Optional<M> module = getByName(name);
        module.ifPresent(Module::toggle);
    }
    public ArrayList<ButtonWidget> buildButtonWidgets() {
        ArrayList<ButtonWidget> res = new ArrayList<>();
        for(IModule<M> module : MODULES) {
            res.add(module.module.buildButtonWidget());
        }
        return res;
    }
    @SuppressWarnings("unchecked")
    private void init() {
        for(IModule<M> module : MODULES) {
            AthensClient.HUD.add(module.module);
        }
    }
    public void tick() {
        for(IModule<M> module : MODULES) {
            module.module.tick();
        }
    }
    public void shutdown() {
        SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
        for(IModule<M> module : MODULES) {
            save.addLine(module.module.name(), module.module.enabled);
        }
        SaveUtils.saveState(SaveUtils.SavableData.MODULE, save.build());
    }
    public static final class IModule<M extends Module> {
        public final String name;
        public final M module;
        public IModule(M module) {
            this.module = module;
            this.name = module.name();
        }
    }
}
