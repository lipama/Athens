package net.lipama.athens.systems.modules.modules.chams;

import net.lipama.athens.systems.modules.ModuleSettings;
import net.lipama.athens.utils.RainbowColor;
import net.lipama.athens.utils.SaveUtils;
import net.lipama.chams.api.ChamsSettings;
import net.lipama.chams.api.Color;

public final class ChamsModuleSettings extends ModuleSettings.Settings {
    public ChamsSettings settings = new ChamsSettings();

    @Override
    public void saveCurrentStateToStringViaBuilder(SaveUtils.SaveBuilder saveBuilder) {
        {// Crystal Settings
            ChamsSettings.CrystalSettings cs = settings.crystalSettings;
            saveBuilder.addLine("cs.enabled", cs.enabled);

            saveBuilder.addLine("cs.crystalScale", cs.crystalScale);
            saveBuilder.addLine("cs.crystalBounceHeight", cs.crystalBounceHeight);
            saveBuilder.addLine("cs.crystalRotateSpeed", cs.crystalRotateSpeed);
            saveBuilder.addLine("cs.textureCrystals", cs.textureCrystals);

            saveBuilder.addLine("cs.renderCrystalCore", cs.renderCrystalCore);
            saveBuilder.addLine$("cs.crystalCoreColor", cs.crystalCoreColor, this::parseColor);

            saveBuilder.addLine("cs.renderInnerFrame", cs.renderInnerFrame);
            saveBuilder.addLine$("cs.innerFrameColor", cs.innerFrameColor, this::parseColor);

            saveBuilder.addLine("cs.renderOuterFrame", cs.renderOuterFrame);
            saveBuilder.addLine$("cs.outerFrameColor", cs.outerFrameColor, this::parseColor);
        }
        {// Hand Settings
            ChamsSettings.HandSettings hs = settings.handSettings;
            saveBuilder.addLine("hs.enabled", hs.enabled);
            saveBuilder.addLine("hs.textureHand", hs.textureHand);
            saveBuilder.addLine$("hs.handColor", hs.handColor, this::parseColor);
        }
        {// Player Settings
            ChamsSettings.PlayerSettings ps = settings.playerSettings;
            saveBuilder.addLine("ps.enabled", ps.enabled);
            saveBuilder.addLine("ps.ignoreSelf", ps.ignoreSelf);
            saveBuilder.addLine("ps.texturePlayers", ps.texturePlayers);
            saveBuilder.addLine$("ps.playerModelColor", ps.playerModelColor, this::parseColor);
            saveBuilder.addLine("ps.playerModelScale", ps.playerModelScale);
        }
    }

    @Override
    public void updateInstanceSettingsViaLoader(SaveUtils.Loader loader) {
        {// Crystal Settings
            ChamsSettings.CrystalSettings cs = settings.crystalSettings;
            cs.enabled = loader.bool$("cs.enabled");

            cs.crystalScale = loader.double$("cs.crystalScale");
            cs.crystalBounceHeight = loader.double$("cs.crystalBounceHeight");
            cs.crystalRotateSpeed = loader.double$("cs.crystalRotateSpeed");
            cs.textureCrystals = loader.bool$("cs.textureCrystals");

            cs.renderCrystalCore = loader.bool$("cs.renderCrystalCore");
            cs.crystalCoreColor = loader.$("cs.crystalCoreColor", this::parseColor);

            cs.renderInnerFrame = loader.bool$("cs.renderInnerFrame");
            cs.innerFrameColor = loader.$("cs.innerFrameColor", this::parseColor);

            cs.renderOuterFrame = loader.bool$("cs.renderOuterFrame");
            cs.outerFrameColor = loader.$("cs.outerFrameColor", this::parseColor);
        }
        {// Hand Settings
            ChamsSettings.HandSettings hs = settings.handSettings;
            hs.enabled = loader.bool$("hs.enabled");
            hs.textureHand = loader.bool$("hs.textureHand");
            hs.handColor = loader.$("hs.handColor", this::parseColor);
        }
        {// Player Settings
            ChamsSettings.PlayerSettings ps = settings.playerSettings;
            ps.enabled = loader.bool$("ps.enabled");
            ps.ignoreSelf = loader.bool$("ps.ignoreSelf");
            ps.texturePlayers = loader.bool$("ps.texturePlayers");
            ps.playerModelColor = loader.$("ps.playerModelColor", this::parseColor);
            ps.playerModelScale = loader.double$("ps.playerModelScale");
        }
    }

    private Color parseColor(String string) {
        int packed = Integer.parseInt(string);
        return RainbowColor.fromPacked(packed);
    }

    private String parseColor(Color color) {
        return "" + RainbowColor.getPacked(color);
    }
}
