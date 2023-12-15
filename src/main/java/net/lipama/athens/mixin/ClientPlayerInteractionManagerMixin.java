package net.lipama.athens.mixin;

import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

import net.lipama.athens.systems.interfaces.IClientPlayerInteractionManager;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager {
    @Shadow @Final private MinecraftClient client;
    @Shadow private float currentBreakingProgress;
    @Shadow private boolean breakingBlock;

    /**
     * blockHitDelay
     */
    @Shadow
    private int blockBreakingCooldown;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getId()I", ordinal = 0), method = "updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z")
    private void onPlayerDamageBlock(BlockPos blockPos_1, Direction direction_1, CallbackInfoReturnable<Boolean> ci) {
//        AthensClient.EVENT_BUS.post(new BlockBreakingProgressEvent(blockPos_1, direction_1));
    }

    @Inject(at = @At("HEAD"), method = "hasExtendedReach()Z", cancellable = true)
    private void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
    @Override
    public float getCurrentBreakingProgress() {
        return currentBreakingProgress;
    }
    @Override
    public void setBreakingBlock(boolean breakingBlock) {
        this.breakingBlock = breakingBlock;
    }
    @Override
    public void windowClick_PICKUP(int slot) {
        clickSlot(0, slot, 0, SlotActionType.PICKUP, client.player);
    }
    @Override
    public void windowClick_QUICK_MOVE(int slot) {
        clickSlot(0, slot, 0, SlotActionType.QUICK_MOVE, client.player);
    }
    @Override
    public void windowClick_THROW(int slot) {
        clickSlot(0, slot, 1, SlotActionType.THROW, client.player);
    }
    @Override
    public void rightClickItem() {
        interactItem(client.player, Hand.MAIN_HAND);
    }
    @Override
    public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec) {
        interactBlock(client.player, Hand.MAIN_HAND, new BlockHitResult(hitVec, side, pos, false));
        interactItem(client.player, Hand.MAIN_HAND);
    }
    @Override
    public void sendPlayerActionC2SPacket(Action action, BlockPos blockPos, Direction direction) {
        sendSequencedPacket(client.world, i -> new PlayerActionC2SPacket(action, blockPos, direction, i));
    }
    @Override
    public void sendPlayerInteractBlockPacket(Hand hand, BlockHitResult blockHitResult) {
        sendSequencedPacket(client.world, i -> new PlayerInteractBlockC2SPacket(hand, blockHitResult, i));
    }
    @Override
    public void setBlockHitDelay(int delay) {
        blockBreakingCooldown = delay;
    }
    @Shadow
    private void sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator) { }
    @Shadow
    public abstract ActionResult interactBlock(ClientPlayerEntity clientPlayerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1);
    @Shadow
    public abstract ActionResult interactItem(PlayerEntity playerEntity_1, Hand hand_1);
    @Shadow
    public abstract void clickSlot(int syncId, int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity);
}
