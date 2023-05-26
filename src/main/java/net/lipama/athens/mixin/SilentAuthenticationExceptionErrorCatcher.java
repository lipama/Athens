package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.Mixin;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;

import net.lipama.athens.*;

import java.lang.reflect.*;

@Mixin(YggdrasilUserApiService.class)
public class SilentAuthenticationExceptionErrorCatcher {
    @SuppressWarnings("all")
    @Redirect(method = "<init>", at = @At(
        value = "INVOKE", target = "Lcom/mojang/authlib/yggdrasil/YggdrasilUserApiService;fetchProperties()V"
    )) private void onYggdrasilUserApiServiceConstruction(
        YggdrasilUserApiService injectedThis
    ) throws AuthenticationException {
        if(Athens.Config.SILENT_MODE) {
            try {
                initYggdrasilUserApiService(injectedThis);
            } catch (Exception e) {
                Athens.LOG.debug("Invalid Session ID");
            }
        } else {
            try {
                initYggdrasilUserApiService(injectedThis);
            } catch (Exception e) {
                throw new AuthenticationException(e);
            }
        }
    }

    private static void initYggdrasilUserApiService(
        YggdrasilUserApiService yggdrasilUserApiService
    ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = yggdrasilUserApiService.getClass().getDeclaredMethod(
            "fetchProperties"
        );
        method.setAccessible(true);
        method.invoke(yggdrasilUserApiService);
        method.setAccessible(false);
    }
}
