package net.lipama.athens.modules.settings;

import net.lipama.athens.screens.AthensOptionsScreen;
import net.lipama.athens.AthensClient;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

@SuppressWarnings("all")
public class SettingGroup<S extends Setting<?,?>> {
    private final ArrayList<S> settings;
    private final String screenName;
    private final int settingHeight;
    private SettingScreen settingScreen;
    public SettingGroup(String name, int height) {
        this.settings = new ArrayList<>();
        this.settingHeight = height;
        this.screenName = name;
        settingScreen = new SettingScreen<>(
            this.screenName,
            this.settings
        );
    }

    public <S2 extends S> SettingGroup<?> add(S2 setting) {
        this.settings.add(setting);
        this.settingScreen.addModuleSetting(setting);
        return this;
    }

    public SettingGroup<?> register() {
        this.settingScreen = new SettingScreen(this.screenName, this.settings);
        if(this.settingScreen == null) {
            AthensClient.LOG.error("Setting Screen Null");
        }
        AthensClient.LOG.error("Setting Screen:", settingScreen);
        ButtonWidget.Builder element = new ButtonWidget.Builder(Text.of("Config"),(btn)->{
            try {
                AthensClient.MC.setScreen(this.settingScreen);
            } catch (NullPointerException e) {
                AthensClient.LOG.error("Error: ", e);
            }
            AthensClient.LOG.info("Opening Screen");
        });
        element.dimensions(
            AthensOptionsScreen.WIDTH / 2 - 100 + 205,
            AthensOptionsScreen.HEIGHT / 2 - this.settingHeight,
            50,
            20
        );
        AthensOptionsScreen.settingButtons.add(
            element.build()
        );
        return this;
    }

    public S get(String name) {
        for(S setting : this.settings) {
            if(setting.name.equalsIgnoreCase(name)) return setting;
        }
        return (S) new DummySetting();
    }

    private static class DummySetting extends Setting<Boolean,ButtonWidget> {
        public DummySetting() { super("__DUMMY_SETTING__","__DUMMY_DESCRIPTION__",false); }
    }
    private static class SettingScreen<S extends Setting<?,?>> extends Screen {
        public static int WIDTH;
        public static int HEIGHT;
        private final ArrayList<S> moduleSettings;

        public SettingScreen(String moduleName, ArrayList<S> moduleSettings) {
            super(Text.of(moduleName + "Config"));
            this.moduleSettings = moduleSettings;
            SettingScreen.WIDTH = this.width;
            SettingScreen.HEIGHT = this.height;
        }

        public void addModuleSetting(S setting) {
            this.moduleSettings.add(setting);
        }

        protected void init() {
            SettingScreen.WIDTH = this.width;
            SettingScreen.HEIGHT = this.height;
            try {
                for(Setting<?,?> setting : this.moduleSettings) {
                    this.addDrawableChild(setting.buildWidget());
                }
            } catch (NullPointerException e){
                AthensClient.LOG.error("NULL PTR EXCEPTIONS");
            }
        }
    }
}
