package net.customrecipes.plugin.gui;

import net.customrecipes.plugin.recipe.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PreviewGUI {

    public static final String TITLE = "Recipe Preview";

    public static void open(Player player, CustomRecipe recipe) {

        Inventory inv = Bukkit.createInventory(null, 54, TITLE);

        fillBorder(inv);

        String[] shape = recipe.getShape();

        int startRow = 1;
        int startCol = 1;

        // 3x3 grid
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {

                char key = shape[r].charAt(c);
                CustomRecipe.Ingredient ing = recipe.getIngredients().get(key);

                ItemStack item = new ItemStack(ing == null ? Material.AIR : ing.material);

                if (ing != null) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(key + " x" + ing.amount);
                    item.setItemMeta(meta);
                }

                int slot = (startRow + r) * 9 + (startCol + c);
                inv.setItem(slot, item);
            }
        }

        // output
        inv.setItem(22, recipe.getResult());

        // back button
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName("Back");
        back.setItemMeta(meta);

        inv.setItem(49, back);

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