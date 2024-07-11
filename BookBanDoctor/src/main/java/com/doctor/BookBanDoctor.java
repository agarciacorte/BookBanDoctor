package com.doctor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.ShulkerBox;

public class BookBanDoctor extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("BookBanDoctor is being enabled!");
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("BookBanDoctor has been enabled and events are registered!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BookBanDoctor is being disabled!");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        getLogger().info("PlayerQuitEvent triggered for player: " + player.getName());

        // Iterate through the player's inventory
        for (ItemStack item : player.getInventory().getContents()) {
            getLogger().info("" + item.getType().toString());
            if (item != null && item.getType().toString().endsWith("SHULKER_BOX")) {
                getLogger().info("tiene shulkers");
                BlockStateMeta blockStateMeta = (BlockStateMeta) item.getItemMeta();
                if (blockStateMeta != null && blockStateMeta.getBlockState() instanceof ShulkerBox) {
                    ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
                    Inventory shulkerInventory = shulkerBox.getInventory();

                    boolean containsBooks = false;
                    for (ItemStack shulkerItem : shulkerInventory.getContents()) {
                        if (shulkerItem != null && shulkerItem.getType() == Material.WRITTEN_BOOK) {
                            containsBooks = true;
                            break;
                        }
                    }

                    if (containsBooks) {
                        shulkerInventory.clear();
                        blockStateMeta.setBlockState(shulkerBox);
                        item.setItemMeta(blockStateMeta);
                        getLogger().info("Cleared Shulker Box containing books in " + player.getName() + "'s inventory.");
                    }
                }
            }
        }
    }
}
