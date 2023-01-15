package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.option.SimpleOption;

import net.lipama.athens.modules.FullBright;

import java.util.Optional;

@Mixin(SimpleOption.DoubleSliderCallbacks.class)
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class SimpleOptionMixin {
    @Inject(method = "validate(Ljava/lang/Double;)Ljava/util/Optional;", at = @At("RETURN"), cancellable = true)
    public void removeValidation(Double value, CallbackInfoReturnable<Optional<Double>> cir) {
        if(FullBright.active && value == 69420.0) cir.setReturnValue(Optional.of(value));
    }
}