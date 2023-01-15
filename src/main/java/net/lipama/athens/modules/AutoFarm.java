package net.lipama.athens.modules;

import com.linkrbot.projects.orbit.EventHandler;
import net.lipama.athens.AthensClient;
import net.lipama.athens.events.TickEvent;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

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

    @EventHandler
    public void onTick(TickEvent.Post event) {
        MinecraftClient client = AthensClient.MC;
        if(client.player != null){
            for (int y = -1; y <= 0; y++){
                for (int x = -2; x <= 2; x++){
                    for (int z = -2; z <= 2;) {
                        tryPlant(client, client.player.getBlockPos().add(x,y,z));
                    }
                }
            }
        }
    }

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