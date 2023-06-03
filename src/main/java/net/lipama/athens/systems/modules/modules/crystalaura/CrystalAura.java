package net.lipama.athens.systems.modules.modules.crystalaura;

import net.lipama.athens.Athens;
import net.lipama.athens.events.TickEvent;
import net.lipama.athens.systems.interfaces.IClientPlayerInteractionManager;
import net.lipama.athens.systems.modules.Module;
import net.lipama.athens.systems.modules.ModuleSettings;
import net.lipama.athens.utils.BlockUtils;
import net.lipama.athens.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.titanium.composer.EventHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("ConstantConditions")
public class CrystalAura extends Module {
    public final ModuleSettings<CrystalAuraSettings> settings =
            ModuleSettings.initLoaded(id(), CrystalAuraSettings.class);

    public CrystalAura() {
        super("CrystalAura");
        this.position = Position.Left(0);
    }

    @Override
    public void onEnable() {
        this.settings.load();
        Athens.LOG.info("CrystalAura Enabled");
    }

    @Override
    public void onDisable() {
        this.settings.save();
        Athens.LOG.info("CrystalAura Disabled");
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPostTick(TickEvent.Post event) {
        if (!this.enabled) return;

        ArrayList<Entity> crystals = getNearbyCrystals();

        if (!crystals.isEmpty()) {
            detonate(crystals);
        }

        if (!settings.isLoaded()) return;
        if (settings.get() == null) return;
        if (!hasItem(item -> item == Items.END_CRYSTAL)) return;
        if (!settings.get().autoPlace) return;

        ArrayList<Entity> targets = getNearbyTargets();
        placeCrystalsNear(targets);
    }

    @SuppressWarnings("UnusedReturnValue")
    private ArrayList<BlockPos> placeCrystalsNear(ArrayList<Entity> targets) {
        ArrayList<BlockPos> newCrystals = new ArrayList<>();

        boolean shouldSwing = false;
        for (Entity target : targets) {
            ArrayList<BlockPos> freeBlocks = getFreeBlocksNear(target);

            for (BlockPos pos : freeBlocks) {
                if (placeCrystal(pos)) {
                    shouldSwing = true;
                    newCrystals.add(pos);

                    break;
                }
            }
        }

        if (shouldSwing) Athens.MC.player.swingHand(Hand.MAIN_HAND);

        return newCrystals;
    }

    private void detonate(ArrayList<Entity> crystals) {
        for (Entity e : crystals) {
            CrystalAuraSettings.FaceBlocks faceBlocks = settings.get().faceBlocksMode;
            faceBlocks.face(e.getBoundingBox().getCenter());
            Athens.MC.interactionManager.attackEntity(Athens.MC.player, e);
        }

        if (!crystals.isEmpty()) Athens.MC.player.swingHand(Hand.MAIN_HAND);
    }

    private boolean selectItem(Predicate<Item> item) {
        PlayerInventory inventory = Athens.MC.player.getInventory();
        IClientPlayerInteractionManager im = Athens.IMC.getInteractionManager();

        if (!settings.isLoaded()) return false;
        if (settings.get() == null) return false;

        for (int slot = 0; slot < settings.get().takeItemsFrom.maxInvSlot; slot++) {
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
        if (Athens.MC.player == null) return false;
        PlayerInventory inventory = Athens.MC.player.getInventory();

        if (!settings.isLoaded()) return false;
        if (settings.get() == null) return false;

        for (int slot = 0; slot < settings.get().takeItemsFrom.maxInvSlot; slot++) {
            ItemStack stack = inventory.getStack(slot);
            if (!item.test(stack.getItem())) continue;

            return true;
        }

        return false;
    }

    private boolean placeCrystal(BlockPos pos) {
        if (!settings.isLoaded()) return false;
        if (settings.get() == null) return false;
        Vec3d eyesPos = RotationUtils.getEyesPos();
        double rangeSq = Math.pow(settings.get().range, 2);
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

            if (
                    settings.get().checkLineOfSight && Athens.MC.world.raycast(
                            new RaycastContext(
                                    eyesPos,
                                    hitVec,
                                    RaycastContext.ShapeType.COLLIDER,
                                    RaycastContext.FluidHandling.NONE,
                                    Athens.MC.player
                            )
                    ).getType() != HitResult.Type.MISS
            ) continue;

            if (!selectItem(item -> item == Items.END_CRYSTAL)) return false;

            settings.get().faceBlocksMode.face(hitVec);

            // place blocK
            Athens.IMC.getInteractionManager().rightClickBlock(neighbor, side.getOpposite(), hitVec);

            return true;
        }

        return false;
    }

    private ArrayList<Entity> getNearbyCrystals() {
        ClientPlayerEntity player = Athens.MC.player;
        if (!settings.isLoaded()) return new ArrayList<>();
        if (settings.get() == null) return new ArrayList<>();
        if (player == null || Athens.MC.world == null) return new ArrayList<>();
        double rangeSq = Math.pow(settings.get().range, 2);

        Comparator<Entity> furthestFromPlayer = Comparator.<Entity>comparingDouble(
                e -> Athens.MC.player.squaredDistanceTo(e)
        ).reversed();

        return StreamSupport.stream(Athens.MC.world.getEntities().spliterator(), true)
            .filter(e -> e instanceof EndCrystalEntity)
            .filter(e -> !e.isRemoved())
            .filter(e -> player.squaredDistanceTo(e) <= rangeSq)
            .sorted(furthestFromPlayer)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Entity> getNearbyTargets() {
        if (!settings.isLoaded()) return new ArrayList<>();
        if (settings.get() == null) return new ArrayList<>();
        double rangeSq = Math.pow(settings.get().range, 2);

        Comparator<Entity> furthestFromPlayer =
            Comparator.<Entity>comparingDouble(e -> Athens.MC.player.squaredDistanceTo(e)).reversed();

        Stream<Entity> stream = StreamSupport.stream(
                Athens.MC.world.getEntities().spliterator(), false
            )
            .filter(e -> !e.isRemoved())
            .filter(e -> e instanceof LivingEntity && ((LivingEntity) e).getHealth() > 0)
            .filter(e -> e != Athens.MC.player)
            .filter(e -> Athens.MC.player.squaredDistanceTo(e) <= rangeSq);

        return stream.sorted(furthestFromPlayer).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<BlockPos> getFreeBlocksNear(Entity target) {
        if (!settings.isLoaded()) return new ArrayList<>();
        if (settings.get() == null) return new ArrayList<>();
        Vec3d eyesVec = RotationUtils.getEyesPos().subtract(0.5, 0.5, 0.5);
        double rangeSq = Math.pow(settings.get().range + 0.5, 2);
        int rangeI = 2;

        BlockPos center = target.getBlockPos();
        BlockPos min = center.add(-rangeI, -rangeI, -rangeI);
        BlockPos max = center.add(rangeI, rangeI, rangeI);
        Box targetBB = target.getBoundingBox();

        Vec3d targetEyesVec = target.getPos().add(0, target.getEyeHeight(target.getPose()), 0);

        Comparator<BlockPos> closestToTarget = Comparator.comparingDouble(
            pos -> targetEyesVec.squaredDistanceTo(Vec3d.ofCenter(pos))
        );

        return BlockUtils.getAllInBoxStream(min, max).get()
            .filter(pos -> eyesVec.squaredDistanceTo(Vec3d.of(pos)) <= rangeSq)
            .filter(this::isReplaceable)
            .filter(this::hasCrystalBase)
            .filter(
                    pos -> !targetBB.intersects(new Box(pos))
            ).sorted(closestToTarget)
            .collect(Collectors.toCollection(ArrayList::new));
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
}
