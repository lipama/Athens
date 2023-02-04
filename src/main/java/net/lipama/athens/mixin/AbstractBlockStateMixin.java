package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.util.shape.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

import net.lipama.athens.modules.modules.XRay;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract Block getBlock();

    @Inject(at = @At("HEAD"), method = "isSideInvisible", cancellable = true)
    public void isSideInvisible(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> ci) {
        XRay.isSideInvisible(this.getBlock(), ci);
    }

    @Inject(at = @At("HEAD"), method = "isSideSolid", cancellable = true)
    public void isSideSolid(BlockView world, BlockPos pos, Direction direction, SideShapeType shapeType, CallbackInfoReturnable<Boolean> ci) {
        XRay.isSideSolid(this.getBlock(), ci);
    }

    @Inject(at = @At("HEAD"), method = "getLuminance", cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> ci) {
        XRay.getLuminance(this.getBlock(), ci);
    }

    @Inject(at = @At("HEAD"), method = "getAmbientOcclusionLightLevel", cancellable = true)
    public void getAmbientOcclusionLightLevel(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ci) {
        XRay.getAmbientOcclusionLightLevel(ci);
    }

    @Inject(at = @At("HEAD"), method = "getCullingFace", cancellable = true)
    public void getCullingFace(CallbackInfoReturnable<VoxelShape> ci) {
        XRay.getCullingFace(this.getBlock(),ci);
    }
}
