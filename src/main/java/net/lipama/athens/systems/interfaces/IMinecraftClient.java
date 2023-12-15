package net.lipama.athens.systems.interfaces;

import net.minecraft.client.session.Session;

@SuppressWarnings("all")
public interface IMinecraftClient {
    public void rightClick();
    public void setItemUseCooldown(int itemUseCooldown);
    public IClientPlayerInteractionManager getIInteractionManager();
    public int getItemUseCooldown();
    public IClientPlayerEntity getPlayer();
    public IWorld getWorld();
    public void setSession(Session session);
}
