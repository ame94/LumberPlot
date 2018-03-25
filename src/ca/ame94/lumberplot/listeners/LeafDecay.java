package ca.ame94.lumberplot.listeners;

import ca.ame94.lumberplot.LumberPlot;
import ca.ame94.lumberplot.util.Logger;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeafDecay implements Listener {

    /**
     * Prevent saplings and other items falling
     * @param event The leaf decay event
     */
    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event) {
        if (LumberPlot.isLocationUnderProtection(event.getBlock().getLocation())) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
        }
    }
}
