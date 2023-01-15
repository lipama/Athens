package net.lipama.athens.modules;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.shape.*;
import net.minecraft.block.*;

import net.lipama.athens.utils.*;
import net.lipama.athens.*;

public class XRay extends Module {
    private static final BlockConfig BLOCKS = BlockConfig.Templates.XRAY.getBlocks();

    public XRay() {
        super("XRay");
        this.height = -45;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("XRay Enabled");
        AthensClient.MC.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("XRay Disabled");
        AthensClient.MC.worldRenderer.reload();
    }

    public static void isSideInvisible(Block block, CallbackInfoReturnable<Boolean> ci) {
        if(isActive()) {
            if(isBlockInteresting(block)) {
                ci.setReturnValue(false);
                return;
            }
            ci.setReturnValue(true);
        }
    }

    public static void isSideSolid(Block block, CallbackInfoReturnable<Boolean> ci) {
        if(isActive()) {
            if(isBlockInteresting(block)) {
                ci.setReturnValue(true);
                return;
            }
            ci.setReturnValue(false);
        }
    }

    public static void getCullingFace(Block block, CallbackInfoReturnable<VoxelShape> ci) {
        if(isActive()) {
            if(isBlockInteresting(block)){
                ci.setReturnValue(VoxelShapes.fullCube());
                return;
            }
            ci.setReturnValue(VoxelShapes.empty());
        }
    }

    public static void getLuminance(Block block, CallbackInfoReturnable<Integer> ci) {
        if(isActive() && isBlockInteresting(block)) ci.setReturnValue(12);
    }

    public static void getAmbientOcclusionLightLevel(CallbackInfoReturnable<Float> ci){
        if(isActive()) ci.setReturnValue(1.0f);
    }

    public static boolean isBlockInteresting(Block block) { return BLOCKS.includes(block); }

    public static boolean isActive() {
        if(Athens.MODULES.getByName("XRay")==null) return false;
        return Athens.MODULES.getByName("XRay").enabled;
    }
}
