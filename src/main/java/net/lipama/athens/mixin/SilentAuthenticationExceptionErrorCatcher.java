package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;

import net.hyperj.*;

@Mixin(YggdrasilUserApiService.class)
public class SilentAuthenticationExceptionErrorCatcher {
    @SuppressWarnings("all")
    @Redirect(method = "<init>", at = @At(
        value = "INVOKE", target = "Lcom/mojang/authlib/yggdrasil/YggdrasilUserApiService;fetchProperties()V"
    )) private void onYggdrasilUserApiServiceConstruction(
        YggdrasilUserApiService self
    ) throws AuthenticationException {
        HyperJ.inject(self).call("fetchProperties");
    }
}
