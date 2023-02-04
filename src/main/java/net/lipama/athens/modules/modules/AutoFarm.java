package net.lipama.athens.modules.modules;

import net.lipama.athens.modules.Module;
import net.lipama.athens.AthensClient;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.*;
import net.minecraft.util.Hand;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class AutoFarm extends Module {
    public AutoFarm() {
        super("AutoFarm");
        this.height = 80;
    }
    @Override
    public void onEnable() {
        AthensClient.LOG.info("AutoFarm Enabled");
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("AutoFarm Disabled");
    }

//    @EventHandler
//    public void onTick(TickEvent.Post event) {
//        MinecraftClient client = AthensClient.MC;
//        if(client.player != null){
//            for (int y = -1; y <= 0; y++){
//                for (int x = -2; x <= 2; x++){
//                    for (int z = -2; z <= 2;) {
//                        tryPlant(client, client.player.getBlockPos().add(x,y,z));
//                    }
//                }
//            }
//        }
//    }

    public void tryPlant(MinecraftClient client, BlockPos pos) {
        BlockState blockState = client.world.getBlockState(pos);
        if (blockState.getBlock() instanceof FarmlandBlock) {
            BlockState blockStateUp = client.world.getBlockState(pos.up());
            if(blockStateUp.getBlock() instanceof AirBlock) {
                if (tryUseSeed(client, pos, Hand.MAIN_HAND) || tryUseSeed(client, pos, Hand.OFF_HAND)) {
                    // we planted
                }
            }
        }
    }

  public boolean tryUseSeed(MinecraftClient client, BlockPos pos, Hand hand) {
        Item item = client.player.getStackInHand(hand) .getItem();
        if (item == Items.WHEAT_SEEDS || item == Items.BEETROOT_SEEDS || item == Items.POTATO || item == Items.CARROT || item == Items.MELON_SEEDS || item == Items.PUMPKIN_SEEDS) {
            Vec3d blockPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            BlockHitResult hit = new BlockHitResult(blockPos, Direction.UP, pos, false);
            client.interactionManager.interactBlock(client.player, hand, hit);
            return true;
        }
        return false;
    }
}