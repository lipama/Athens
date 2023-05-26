package net.lipama.athens.systems.modules;

import net.lipama.athens.utils.SaveUtils;
import net.lipama.athens.Athens;

import java.lang.reflect.Constructor;

public class ModuleSettings<S extends ModuleSettings.Settings> {
//    public static <S extends ModuleSettings.Settings> ModuleSettings<S> initLoaded(String id) {
//        Class<S> clazz = (Class<S>) (new Object()).getClass();
//        return initLoaded(id, clazz);
//    }
//    public static <S extends ModuleSettings.Settings> ModuleSettings<S> initUnloaded(String id) {
//        Class<S> clazz = (Class<S>) (new Object()).getClass();
//        return initUnloaded(id, clazz);
//    }
    public static <S extends ModuleSettings.Settings> ModuleSettings<S> initLoaded(String id, Class<S> clazz) {
        ModuleSettings<S> moduleSettings = initUnloaded(id, clazz);
        moduleSettings.load();
        return moduleSettings;
    }
    public static <S extends ModuleSettings.Settings> ModuleSettings<S> initUnloaded(String id, Class<S> clazz) {
        return new ModuleSettings<>(id, clazz);
    }
    private final SaveUtils.SavableData SAVE;
    private S moduleSettings;
    private boolean loaded = false;
    @SuppressWarnings("all")
    private ModuleSettings(String id, Class<S> clazz) {
        this.SAVE = SaveUtils.SavableData.MODULE(id);
        try {
            Constructor<S> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            moduleSettings = constructor.newInstance();
            constructor.setAccessible(false);
        } catch (Exception e) {
            Athens.LOG.error("MODULE SETTING INIT ERROR", e);
        }
        Athens.COMPOSER.subscribe(this);
    }
    public boolean isLoaded() {
        return this.loaded && this.moduleSettings != null;
    }
    public void load() {
        SaveUtils.Loader loader = SaveUtils.getLoader(this.SAVE);
        try {
            moduleSettings.updateInstanceSettingsViaLoader(loader);
        } catch (Exception e) {
            Athens.LOG.warn("Failed to Load module settings");
        }
        this.loaded = true;
    }
    public void save() {
        if(!this.isLoaded()) {
            Athens.LOG.error("NEVER LOADED");
            return;
        }
        Athens.LOG.error("SAVING");
        {
            SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
            this.moduleSettings.saveCurrentStateToStringViaBuilder(save);
            SaveUtils.saveState(this.SAVE, save.build());
        }
//        S backup = this.moduleSettings;
//        SaveUtils.SaveBuilder backupSave = new SaveUtils.SaveBuilder();
//        backup.saveCurrentStateToStringViaBuilder(backupSave);
//        byte[] backupBytes = backupSave.build();
//        {
//            this.load();
//            SaveUtils.SaveBuilder save = new SaveUtils.SaveBuilder();
//            moduleSettings.saveCurrentStateToStringViaBuilder(save);
//            byte[] output = save.build();
//            if(output != backupBytes) {
//                this.moduleSettings = backup;
//                this.save();
//            } else {
//                Athens.LOG.info("Saved successfully");
//            }
//        }
    }
    public static abstract class Settings implements SaveUtils.StateParsable {}
    public interface EnumSetting extends SaveUtils.StringParsable {}
    public S get() {
        if(!isLoaded()) return null;
        return moduleSettings;
    }
}
