// Package Declaration
package me.iffa.bspace.runnables;

// bSpace Imports
import me.iffa.bspace.listeners.misc.BlackHolePlayerListener;

// Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Location;

// Spout Imports
import org.getspout.spoutapi.block.SpoutBlock;

// Bukkit Imports
import org.bukkit.entity.Entity;

/**
 * Runnable that handles causing chaos with black holes.
 * 
 * @author iffamies
 * @author SwearWord (thanks for your awesome maths!)
 */
public class SpoutBlackHoleChaosRunnable implements Runnable {
    // Variables
    private SpoutBlock block;
    private Entity entity;
    private float angle;
    private double xDistance;
    private double zDistance;
    private double index;

    /**
     * Constructor of SpoutBlackHoleChaosRunnable.
     * 
     * @param entity Entity to suck
     * @param block Black hole block
     */
    public SpoutBlackHoleChaosRunnable(Entity entity, SpoutBlock block) {
        this.block = block;
        this.entity = entity;
        xDistance = block.getLocation().getX() - entity.getLocation().getX();
        zDistance = block.getLocation().getZ() - entity.getLocation().getZ();
        angle = (float) Math.atan(zDistance / xDistance);
    }

    /**
     * Magic happens here.
     */
    @Override
    public void run() {
        if (entity.isDead()) {
            Bukkit.getScheduler().cancelTask(BlackHolePlayerListener.getRunningTasks().get(entity));
            BlackHolePlayerListener.getRunningTasks().remove(entity);
            return;
        }
	double x = xDistance - index;
	double z = Math.tan(angle) * x;
	x += block.getLocation().getBlockX();
	z += block.getLocation().getBlockZ();
	index += 0.1;
        if (index > xDistance) {
            entity.remove(); // OH NO!
            Bukkit.getScheduler().cancelTask(BlackHolePlayerListener.getRunningTasks().get(entity));
            BlackHolePlayerListener.getRunningTasks().remove(entity);
            return;
        }
        entity.teleport(new Location(entity.getWorld(), x, block.getY(), z));
    }
}