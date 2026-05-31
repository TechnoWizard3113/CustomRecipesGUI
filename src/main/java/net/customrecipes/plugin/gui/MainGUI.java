package net.customrecipes.plugin.gui;

import net.customrecipes.plugin.recipe.CustomRecipe;
import net.customrecipes.plugin.recipe.RecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainGUI {

    public static final String TITLE = "Recipes";

    public static void open(Player player, RecipeManager manager) {

        Inventory inv = Bukkit.createInventory(null, 54, TITLE);

        // border
        fillBorder(inv);

        int slot = 10;

        for (CustomRecipe recipe : manager.getRecipes()) {

            if (slot % 9 == 8) slot += 2;

            ItemStack item = recipe.getResult().clone();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(recipe.getDisplayName());
            item.setItemMeta(meta);

            inv.setItem(slot, item);
            slot++;
        }

        player.openInventory(inv);
    }

    private static void fillBorder(Inventory inv) {

        ItemStack pane = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {
            int row = i / 9;
            int col = i % 9;

            if (row == 0 || row == 5 || col == 0 || col == 8) {
                inv.setItem(i, pane);
            }
        }
    }
}