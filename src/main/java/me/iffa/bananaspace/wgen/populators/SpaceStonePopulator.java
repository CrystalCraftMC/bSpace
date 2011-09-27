// Package Declaration
package me.iffa.bananaspace.wgen.populators;

// Java Imports
import java.util.Random;

// BananaSpace Imports
import me.iffa.bananaspace.api.SpaceConfigHandler;

// Bukkit Imports
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

/**
 * SpaceStonePopulator, populates a world with Notch's original stone (glowstone) patches.
 *
 * @author Markus 'Notch' Persson
 * @author iffa
 * @author Nightgunner5
 */
public class SpaceStonePopulator extends BlockPopulator {
    // Variables
    private static final BlockFace[] faces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST};

    /**
     * Populates a world with stone patches.
     * 
     * @param world World
     * @param random Random
     * @param source Source chunk
     */
    @Override
    public void populate(World world, Random random, Chunk source) {
        if (!SpaceConfigHandler.getAsteroidsEnabled(world)) {
            return;
        }
        for (int i = 0; i < 2; i++) {
            int x = random.nextInt(16);
            int y = random.nextInt(128);
            int z = random.nextInt(16);
            Block block = source.getBlock(x, y, z);
            if (block.getTypeId() != 0) {
                return;
            }
            if (random.nextInt(200) <= SpaceConfigHandler.getStoneChance(world)) {
                block.setTypeId(1);

                for (int j = 0; j < 1500; j++) {
                    Block current = block.getRelative(random.nextInt(8) - random.nextInt(8),
                            random.nextInt(12),
                            random.nextInt(8) - random.nextInt(8));
                    if (current.getTypeId() != 0) {
                        continue;
                    }
                    int count = 0;
                    for (BlockFace face : faces) {
                        if (current.getRelative(face).getTypeId() == 1) {
                            count++;
                        }
                    }
                    if (count == 1) {
                        current.setTypeId(1);
                    }
                }
            }
        }
    }
}