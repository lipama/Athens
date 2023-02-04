package net.lipama.athens.modules.interfaces;

import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.*;

@SuppressWarnings("all")
public interface IClientPlayerInteractionManager {
    public float getCurrentBreakingProgress();
    public void setBreakingBlock(boolean breakingBlock);
    public void windowClick_PICKUP(int slot);
    public void windowClick_QUICK_MOVE(int slot);
    public void windowClick_THROW(int slot);
    public void rightClickItem();
    public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec);
    public void sendPlayerActionC2SPacket(PlayerActionC2SPacket.Action action, BlockPos blockPos, Direction direction);
    public void sendPlayerInteractBlockPacket(Hand hand, BlockHitResult blockHitResult);
    public void setBlockHitDelay(int delay);
}
