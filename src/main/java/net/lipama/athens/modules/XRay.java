package net.lipama.athens.modules;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.*;

@SuppressWarnings({"unused", "ReassignedVariable"})
public class XRay {
    public static void shouldSideBeRendered(
        BlockState adjacentState,
        BlockView blockState,
        BlockPos blockAccess,
        Direction pos,
        CallbackInfoReturnable<Boolean> ci
    ) {
        if (ci == null) ci = new CallbackInfoReturnable<>("shouldSideBeRendered", true);
    }
}
