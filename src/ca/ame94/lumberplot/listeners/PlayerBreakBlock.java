package ca.ame94.lumberplot.listeners;

import ca.ame94.lumberplot.LumberPlot;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        // If the affected block is under protection
        if (LumberPlot.isLocationUnderProtection(block.getLocation())) {
            Player player = event.getPlayer();
            boolean inCreative = player.getGameMode().equals(GameMode.CREATIVE);
            boolean hasPermission = player.hasPermission("lumberplot.modify");
            if (!(inCreative || hasPermission)) {
                switch (block.getType()) {
                    case SAPLING:
                    case DIRT:
                    case GRASS:
                        // specifically deny changes to saplings, dirt and grass
                        event.setCancelled(true);
                        break;
                    case LEAVES:
                    case LEAVES_2:
                    case LOG:
                    case LOG_2:
                        // leaves & logs are permitted
                        break;
                    default:
                        // deny everything else
                        event.setCancelled(true);
                        break;
                }
            }
        }
    }
}
