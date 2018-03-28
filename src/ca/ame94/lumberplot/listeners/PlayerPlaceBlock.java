package ca.ame94.lumberplot.listeners;

import ca.ame94.lumberplot.LumberPlot;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceBlock implements Listener {

    /**
     * Handle block place events. By-pass protections if the player has permission (or
     * is op) or if in creative mode.
     * @param event The event to handle
     */
    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (LumberPlot.isLocationUnderProtection(block.getLocation())) {
            Player player = event.getPlayer();
            boolean inCreative = player.getGameMode().equals(GameMode.CREATIVE);
            boolean hasPermission = player.hasPermission("lumberplot.modify");
            if (!(inCreative || hasPermission)) {
                event.setCancelled(true);
            }
        }
    }
}
