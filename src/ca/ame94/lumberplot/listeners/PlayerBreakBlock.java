package ca.ame94.lumberplot.listeners;

import ca.ame94.lumberplot.LumberPlot;
import ca.ame94.lumberplot.util.PluginMgr;
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



    /**
     * Handle block break events. Allow only leaf and log blocks to be broken if
     * the player doesn't have creative or the required permission node.
     * @param event The BlockBreakEvent
     */
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
                    case LOG:
                    case LOG_2:
                        Material matBelow = block.getRelative(BlockFace.DOWN).getType();
                        if (matBelow == Material.DIRT || matBelow == Material.GRASS) {

                            //todo: Don't rely on this
                            byte data = block.getData();
                            if (block.getType() == Material.LOG_2) {
                                data -= 4;
                            }
                            byte finalData = data;
                            PluginMgr.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(PluginMgr.getPlugin(), new Runnable() {
                                public void run() {
                                    block.setType(Material.SAPLING);
                                    block.setData(finalData);
                                }
                            }, 1L);
                        }
                    case VINE:
                    case LEAVES:
                    case LEAVES_2:
                        // vines, leaves & logs are permitted
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
