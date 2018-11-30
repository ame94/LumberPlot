package ca.ame94.lumberplot.listeners;

import ca.ame94.lumberplot.LumberPlot;
import ca.ame94.lumberplot.util.PluginMgr;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBreakBlock implements Listener {
    /**
     * Handle block break events. Allow only leaf and log blocks to be broken if
     * the player doesn't have creative or the required permission node.
     * @param event The BlockBreakEvent
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        // If the affected block is under protection
        if (LumberPlot.isLocationUnderProtection(block.getLocation())) {
            Player player = event.getPlayer();
            boolean inCreative = player.getGameMode().equals(GameMode.CREATIVE);
            boolean hasPermission = player.hasPermission("lumberplot.admin") | player.hasPermission("lumberplot.modify");

            if (!(inCreative || hasPermission)) {
                Material newSaplingMaterial = null;

                switch (block.getType()) {
                    case OAK_LOG:
                        newSaplingMaterial = Material.OAK_SAPLING;
                        break;
                    case BIRCH_LOG:
                        newSaplingMaterial = Material.BIRCH_SAPLING;
                        break;
                    case SPRUCE_LOG:
                        newSaplingMaterial = Material.SPRUCE_SAPLING;
                        break;
                    case JUNGLE_LOG:
                        newSaplingMaterial = Material.JUNGLE_SAPLING;
                        break;
                    case ACACIA_LOG:
                        newSaplingMaterial = Material.ACACIA_SAPLING;
                        break;
                    case DARK_OAK_LOG:
                        newSaplingMaterial = Material.DARK_OAK_SAPLING;
                        break;
                }

                switch (block.getType()) {
                    case OAK_SAPLING:
                    case BIRCH_SAPLING:
                    case SPRUCE_SAPLING:
                    case JUNGLE_SAPLING:
                    case ACACIA_SAPLING:
                    case DARK_OAK_SAPLING:
                    case DIRT:
                    case GRASS:
                        // specifically deny changes to saplings, dirt and grass
                        event.setCancelled(true);
                        break;

                    case OAK_LOG:
                    case BIRCH_LOG:
                    case SPRUCE_LOG:
                    case JUNGLE_LOG:
                    case ACACIA_LOG:
                    case DARK_OAK_LOG:
                        Material matBelow = block.getRelative(BlockFace.DOWN).getType();
                        if (matBelow == Material.DIRT || matBelow == Material.GRASS | matBelow == Material.PODZOL) {
                            BlockData data = block.getBlockData();
                            Material finalNewSapling = newSaplingMaterial;
                            PluginMgr.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(PluginMgr.getPlugin(), new Runnable() {
                                public void run() {
                                    block.setType(finalNewSapling);
                                }
                            }, 2L);
                        }
                        break;

                    case VINE:
                    case OAK_LEAVES:
                    case BIRCH_LEAVES:
                    case SPRUCE_LEAVES:
                    case JUNGLE_LEAVES:
                    case ACACIA_LEAVES:
                    case DARK_OAK_LEAVES:
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
