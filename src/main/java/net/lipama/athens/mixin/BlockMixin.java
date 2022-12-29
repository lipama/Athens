package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.BlockView;
import net.minecraft.util.math.*;
import net.minecraft.block.*;

import net.lipama.athens.modules.XRay;

@Mixin(value = Block.class)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public abstract class BlockMixin {
    @Inject(at = @At("RETURN"), method = "shouldDrawSide(" +
            "Lnet/minecraft/block/BlockState;" + // state
            "Lnet/minecraft/world/BlockView;" + // reader
            "Lnet/minecraft/util/math/BlockPos;" + // pos
            "Lnet/minecraft/util/math/Direction;" + // face
            "Lnet/minecraft/util/math/BlockPos;" + // blockPosaaa
            ")Z", // ci
            cancellable = true
    )
    private static void shouldDrawSide(
            BlockState state,
            BlockView reader,
            BlockPos pos,
            Direction face,
            BlockPos blockPos,
            CallbackInfoReturnable<Boolean> ci
    ) {
        XRay.shouldSideBeRendered(state, reader, pos, face, ci);
    }
}