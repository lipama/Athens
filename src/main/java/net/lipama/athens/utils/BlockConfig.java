package net.lipama.athens.utils;

import net.minecraft.block.*;

import java.util.*;

public class BlockConfig {
    private final ArrayList<Block> blocks;
    public enum Templates {
        EMPTY(new BlockConfig()),
        INFESTED(new BlockConfig(
            Blocks.INFESTED_STONE, Blocks.INFESTED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS,
            Blocks.INFESTED_COBBLESTONE, Blocks.INFESTED_CHISELED_STONE_BRICKS,
            Blocks.INFESTED_MOSSY_STONE_BRICKS
        )),
        DIRT(new BlockConfig(
           Blocks.DIRT
        )),
        XRAY(new BlockConfig(
            /* Ores */
            Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.GOLD_ORE, Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE, Blocks.REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.NETHER_GOLD_ORE,
            Blocks.ANCIENT_DEBRIS, Blocks.NETHER_QUARTZ_ORE,

            // 1.17
            Blocks.COPPER_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,

            Blocks.RAW_COPPER_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.CRYING_OBSIDIAN,

            // 1.18
            Blocks.COPPER_BLOCK,

            /* Ore Blocks */
            Blocks.COAL_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK,
            Blocks.EMERALD_BLOCK, Blocks.REDSTONE_BLOCK, Blocks.LAPIS_BLOCK, Blocks.NETHERITE_BLOCK,

            /* Blocks */
            Blocks.OBSIDIAN, Blocks.BLUE_ICE, Blocks.CLAY, Blocks.BOOKSHELF,
            Blocks.SPONGE, Blocks.WET_SPONGE,

            /* Other */
            Blocks.NETHER_WART, Blocks.SPAWNER, Blocks.LAVA, Blocks.WATER,
            Blocks.TNT, Blocks.CONDUIT,

            /* Portals */
            Blocks.END_PORTAL_FRAME, Blocks.END_PORTAL, Blocks.NETHER_PORTAL,

            /* Interactive */
            Blocks.BEACON, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST,
            Blocks.DISPENSER, Blocks.DROPPER,

            /* Useless */
            Blocks.DRAGON_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_EGG
        ));
        private final BlockConfig cfg;
        Templates(BlockConfig blockConfig) {
            this.cfg = blockConfig;
        }
        public BlockConfig getBlocks() {
            return this.cfg;
        }
    }
    public BlockConfig() {
        this.blocks = new ArrayList<>();
    }
    public BlockConfig(Block... blocks) {
        this.blocks = new ArrayList<>();
        Collections.addAll(this.blocks, blocks);
    }
    public void add(Block... blocks) {
        Collections.addAll(this.blocks, blocks);
    }
    public void add(Block block) {
        this.blocks.add(block);
    }
    public boolean remove(Block block) {
        return this.blocks.remove(block);
    }
    public boolean includes(Block block) {
        return this.blocks.contains(block);
    }
}
