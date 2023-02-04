package net.lipama.athens.modules.modules;

import net.lipama.athens.events.TickEvent;

import net.lipama.athens.modules.interfaces.IClientPlayerInteractionManager;
import net.lipama.athens.utils.RotationUtils.*;
import net.lipama.athens.modules.settings.*;
import net.lipama.athens.modules.Module;
import net.lipama.athens.AthensClient;
import net.lipama.athens.utils.*;

import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.entity.decoration.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.hit.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.function.*;
import java.util.stream.*;
import java.util.*;

@SuppressWarnings("all")
public class CrystalAura extends Module {
    public static SettingGroup settingGroup = new SettingGroup("Crystal Aura", 105)
        .add(
            new SliderSetting("Range","Maximum target distance",0D,16D,8D)
        )
        .add(
            new ToggleableSetting("Auto Place","Automatically place crystals when a target is nearby", true)
        )
        .add(
            new EnumSetting<FaceBlocks>("Face Blocks Mode","Mode to use when detonating crystals", FaceBlocks.OFF)
        )
        .add(
            new ToggleableSetting("Check LOS", "Check line of sight for obstacles, enable for servers with AntiCheat!!!", true)
        )
        .add(
            new EnumSetting<TakeItemsFrom>("Take Items From", "Where to take crystals and obi from", TakeItemsFrom.HOTBAR)
        )
    .register();
//    public static Setting<Double> range = new Setting<>("Range", 8D);
//    private final double range = 8;// Max target range
//    private final boolean autoPlace = true;// Automatically place crystals
//    private final FaceBlocks faceBlocks = FaceBlocks.OFF;// face blocks (ENUM)
//    private final boolean checkLOS = false;// Check line of sight (true for ANTICHEAT)
//    private final TakeItemsFrom takeItemsFrom = TakeItemsFrom.HOTBAR;// Where to take items from


    public CrystalAura() {
        super("CrystalAura");
        this.height = 105;
    }

    @Override
    public void onEnable() {
        AthensClient.LOG.info("CrystalAura Enabled");
    }

    @Override
    public void onDisable() {
        AthensClient.LOG.info("CrystalAura Disabled");
    }

    public void onPostTick(){
        if(!this.enabled) return;

        ArrayList<Entity> crystals = getNearbyCrystals();

        if(!crystals.isEmpty()) {
            detonate(crystals);
            return;
        }

        boolean autoPlace = (boolean) settingGroup.get("Auto Place").getValue();

        if(!autoPlace || !hasItem(item -> item == Items.END_CRYSTAL)) return;

        ArrayList<Entity> targets = getNearbyTargets();
        placeCrystalsNear(targets);
    }

    private ArrayList<BlockPos> placeCrystalsNear(ArrayList<Entity> targets) {
        ArrayList<BlockPos> newCrystals = new ArrayList<>();

        boolean shouldSwing = false;
        for(Entity target : targets) {
            ArrayList<BlockPos> freeBlocks = getFreeBlocksNear(target);

            for(BlockPos pos : freeBlocks)
                if(placeCrystal(pos)) {
                    shouldSwing = true;
                    newCrystals.add(pos);

                    break;
                }
        }

        if(shouldSwing) AthensClient.MC.player.swingHand(Hand.MAIN_HAND);

        return newCrystals;
    }

    private void detonate(ArrayList<Entity> crystals) {
        for (Entity e : crystals) {
            FaceBlocks faceBlocks = (FaceBlocks) settingGroup.get("Face Blocks Mode").getValue();
            faceBlocks.face(e.getBoundingBox().getCenter());
            AthensClient.MC.interactionManager.attackEntity(AthensClient.MC.player, e);
        }

        if (!crystals.isEmpty()) AthensClient.MC.player.swingHand(Hand.MAIN_HAND);
    }

    private boolean selectItem(Predicate<Item> item) {
        PlayerInventory inventory = AthensClient.MC.player.getInventory();
        IClientPlayerInteractionManager im = AthensClient.IMC.getInteractionManager();
        int maxInvSlot = ((TakeItemsFrom) settingGroup.get("Take Items From").getValue()).maxInvSlot;

        for (int slot = 0; slot < maxInvSlot; slot++) {
            ItemStack stack = inventory.getStack(slot);
            if (!item.test(stack.getItem())) continue;

            if (slot < 9) inventory.selectedSlot = slot;
            else if (inventory.getEmptySlot() < 9) im.windowClick_QUICK_MOVE(slot);
            else if (inventory.getEmptySlot() != -1) {
                im.windowClick_QUICK_MOVE(inventory.selectedSlot + 36);
                im.windowClick_QUICK_MOVE(slot);
            } else {
                im.windowClick_PICKUP(inventory.selectedSlot + 36);
                im.windowClick_PICKUP(slot);
                im.windowClick_PICKUP(inventory.selectedSlot + 36);
            }

            return true;
        }

        return false;
    }

    private boolean hasItem(Predicate<Item> item) {
        if(AthensClient.MC.player == null) return false;
        PlayerInventory inventory = AthensClient.MC.player.getInventory();
        int maxInvSlot = ((TakeItemsFrom) settingGroup.get("Take Items From").getValue()).maxInvSlot;

        for (int slot = 0; slot < maxInvSlot; slot++) {
            ItemStack stack = inventory.getStack(slot);
            if (!item.test(stack.getItem())) continue;

            return true;
        }

        return false;
    }

    private boolean placeCrystal(BlockPos pos) {
        Vec3d eyesPos = RotationUtils.getEyesPos();
        double rangeSq = Math.pow(((Double) settingGroup.get("Range").getValue()), 2);
        Vec3d posVec = Vec3d.ofCenter(pos);
        double distanceSqPosVec = eyesPos.squaredDistanceTo(posVec);

        for (Direction side : Direction.values()) {
            BlockPos neighbor = pos.offset(side);

            // check if neighbor can be right-clicked
            if (!isClickableNeighbor(neighbor)) continue;

            Vec3d dirVec = Vec3d.of(side.getVector());
            Vec3d hitVec = posVec.add(dirVec.multiply(0.5));

            // check if hitVec is within range
            if (eyesPos.squaredDistanceTo(hitVec) > rangeSq) continue;

            // check if side is visible (facing away from player)
            if (distanceSqPosVec > eyesPos.squaredDistanceTo(posVec.add(dirVec))) continue;

            if(
                ((Boolean) settingGroup.get("Check LOS").getValue()) && AthensClient.MC.world.raycast(
                    new RaycastContext(
                        eyesPos,
                        hitVec,
                        RaycastContext.ShapeType.COLLIDER,
                        RaycastContext.FluidHandling.NONE,
                        AthensClient.MC.player
                    )
                ).getType() != HitResult.Type.MISS
            ) continue;

            if (!selectItem(item -> item == Items.END_CRYSTAL)) return false;

            ((FaceBlocks) settingGroup.get("Face Blocks Mode").getValue()).face(hitVec);

            // place blocK
            AthensClient.IMC.getInteractionManager().rightClickBlock(neighbor, side.getOpposite(), hitVec);

            return true;
        }

        return false;
    }

    private ArrayList<Entity> getNearbyCrystals() {
        ClientPlayerEntity player = AthensClient.MC.player;
        if(player == null || AthensClient.MC.world == null) return new ArrayList<>();
        double rangeSq = Math.pow(((Double) settingGroup.get("Range").getValue()), 2);

        Comparator<Entity> furthestFromPlayer = Comparator.<Entity>comparingDouble(e -> AthensClient.MC.player.squaredDistanceTo(e)).reversed();

        return StreamSupport.stream(AthensClient.MC.world.getEntities().spliterator(), true).filter(e -> e instanceof EndCrystalEntity).filter(e -> !e.isRemoved()).filter(e -> player.squaredDistanceTo(e) <= rangeSq).sorted(furthestFromPlayer).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Entity> getNearbyTargets() {
        double rangeSq = Math.pow(((Double) settingGroup.get("Range").getValue()), 2);

        Comparator<Entity> furthestFromPlayer = Comparator.<Entity>comparingDouble(e -> AthensClient.MC.player.squaredDistanceTo(e)).reversed();

        Stream<Entity> stream = StreamSupport.stream(AthensClient.MC.world.getEntities().spliterator(), false)
                .filter(e -> !e.isRemoved())
                .filter(e -> e instanceof LivingEntity && ((LivingEntity) e).getHealth() > 0)
                .filter(e -> e != AthensClient.MC.player)
                .filter(e -> AthensClient.MC.player.squaredDistanceTo(e) <= rangeSq);

        return stream.sorted(furthestFromPlayer).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<BlockPos> getFreeBlocksNear(Entity target) {
        Vec3d eyesVec = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
        double rangeD = (Double) settingGroup.get("Range").getValue();
        double rangeSq = Math.pow(rangeD + 0.5, 2);
        int rangeI = 2;

        BlockPos center = target.getBlockPos();
        BlockPos min = center.add(-rangeI, -rangeI, -rangeI);
        BlockPos max = center.add(rangeI, rangeI, rangeI);
        Box targetBB = target.getBoundingBox();

        Vec3d targetEyesVec = target.getPos().add(0, target.getEyeHeight(target.getPose()), 0);

        Comparator<BlockPos> closestToTarget = Comparator.<BlockPos>comparingDouble(pos -> targetEyesVec.squaredDistanceTo(Vec3d.ofCenter(pos)));

        return BlockUtils.getAllInBoxStream(min, max).filter(pos -> eyesVec.squaredDistanceTo(Vec3d.of(pos)) <= rangeSq).filter(this::isReplaceable).filter(this::hasCrystalBase).filter(pos -> !targetBB.intersects(new Box(pos))).sorted(closestToTarget).collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isReplaceable(BlockPos pos) {
        return BlockUtils.getState(pos).getMaterial().isReplaceable();
    }

    private boolean hasCrystalBase(BlockPos pos) {
        Block block = BlockUtils.getBlock(pos.down());
        return block == Blocks.BEDROCK || block == Blocks.OBSIDIAN;
    }

    private boolean isClickableNeighbor(BlockPos pos) {
        return BlockUtils.canBeClicked(pos) && !BlockUtils.getState(pos).getMaterial().isReplaceable();
    }

    private enum FaceBlocks implements EnumSetting.EnumSettingType {
        OFF("Off", v -> {
        }),

        SPAM("Packet spam", v -> {
            Rotation rotation = RotationUtils.getNeededRotations(v);
            PlayerMoveC2SPacket.LookAndOnGround packet = new PlayerMoveC2SPacket.LookAndOnGround(rotation.getYaw(), rotation.getPitch(), AthensClient.MC.player.isOnGround());
            AthensClient.MC.player.networkHandler.sendPacket(packet);
        });

        private String name;
        private Consumer<Vec3d> face;

        private FaceBlocks(String name, Consumer<Vec3d> face) {
            this.name = name;
            this.face = face;
        }

        public void face(Vec3d v) {
            face.accept(v);
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getNext() {
            FaceBlocks setting;
            if(this.equals(FaceBlocks.OFF)) setting = FaceBlocks.SPAM;
            else setting = FaceBlocks.OFF;

            CrystalAura.settingGroup.get("Face Blocks Mode").setValue(setting);

            return setting.name;
        }
    }

    private enum TakeItemsFrom implements EnumSetting.EnumSettingType {
        HOTBAR("Hotbar", 9),

        INVENTORY("Inventory", 36);

        private final String name;
        private final int maxInvSlot;

        private TakeItemsFrom(String name, int maxInvSlot) {
            this.name = name;
            this.maxInvSlot = maxInvSlot;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getNext() {
            TakeItemsFrom setting;
            if(this.equals(TakeItemsFrom.HOTBAR)) setting = TakeItemsFrom.INVENTORY;
            else setting = TakeItemsFrom.HOTBAR;

            CrystalAura.settingGroup.get("Take Items From").setValue(setting);

            return setting.name;
        }
    }
}
