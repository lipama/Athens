package net.lipama.athens.modules.interfaces;

import net.minecraft.client.util.Session;

@SuppressWarnings("all")
public interface IMinecraftClient {
    public void rightClick();
    public void setItemUseCooldown(int itemUseCooldown);
    public IClientPlayerInteractionManager getInteractionManager();
    public int getItemUseCooldown();
    public IClientPlayerEntity getPlayer();
    public IWorld getWorld();
    public void setSession(Session session);
}
