package net.lipama.athens.systems.modules.modules.crystalaura;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import java.util.function.Consumer;

import net.lipama.athens.systems.modules.ModuleSettings;
import net.lipama.athens.utils.*;
import net.lipama.athens.Athens;

@SuppressWarnings("SpellCheckingInspection")
public final class CrystalAuraSettings extends ModuleSettings.Settings {
    public int range = 8;
    public boolean autoPlace = true;
    public FaceBlocks faceBlocksMode = FaceBlocks.OFF;
    public boolean checkLineOfSight = true;
    public TakeItemsFrom takeItemsFrom = TakeItemsFrom.HOTBAR;
    @Override
    public void saveCurrentStateToStringViaBuilder(SaveUtils.SaveBuilder saveBuilder) {
        saveBuilder.addLine("range", range);
        saveBuilder.addLine("autoplace", autoPlace);
        saveBuilder.addParsableLine("fbm", faceBlocksMode);
        saveBuilder.addLine("checklos", checkLineOfSight);
        saveBuilder.addParsableLine("tio", takeItemsFrom);
    }
    @Override
    public void updateInstanceSettingsViaLoader(SaveUtils.Loader loader) {
        this.range = loader.int$("range");
        this.autoPlace = loader.bool$("autoplace");
        this.faceBlocksMode = faceBlocksMode.fromString(loader.String$("fbm"));
        this.checkLineOfSight = loader.bool$("checklos");
        this.takeItemsFrom = takeItemsFrom.fromString(loader.String$("tio"));
    }

    public enum FaceBlocks implements ModuleSettings.EnumSetting {
        OFF("Off", v -> {}),
        SPAM("Packet spam", v -> {
            RotationUtils.Rotation rotation = RotationUtils.getNeededRotations(v);
            assert Athens.MC.player != null;
            PlayerMoveC2SPacket.LookAndOnGround packet = new PlayerMoveC2SPacket.LookAndOnGround(
                rotation.yaw(), rotation.pitch(), Athens.MC.player.isOnGround()
            );
            Athens.MC.player.networkHandler.sendPacket(packet);
        });
        public final String name;
        public final Consumer<Vec3d> face;
        FaceBlocks(String name, Consumer<Vec3d> face) {
            this.name = name;
            this.face = face;
        }
        public void face(Vec3d v) {
            face.accept(v);
        }
        @Override
        public String toString() {
            return name;
        }
        @Override
        public String asString() {
            return this.equals(SPAM) ? "SPAM" : "OFF";
        }
        @Override
        public FaceBlocks fromString(String string) {
            return string.equalsIgnoreCase("SPAM") ? SPAM : OFF;
        }
    }

    public enum TakeItemsFrom implements ModuleSettings.EnumSetting {
        HOTBAR("Hotbar", 9),
        INVENTORY("Inventory", 36);
        public final String name;
        public final int maxInvSlot;
        TakeItemsFrom(String name, int maxInvSlot) {
            this.name = name;
            this.maxInvSlot = maxInvSlot;
        }
        @Override
        public String toString() {
            return name;
        }
        @Override
        public String asString() {
            return this.equals(INVENTORY) ? "INVENTORY" : "HOTBAR";
        }
        @Override
        public TakeItemsFrom fromString(String string) {
            return string.equalsIgnoreCase("INVENTORY") ? INVENTORY : HOTBAR;
        }
    }
}
