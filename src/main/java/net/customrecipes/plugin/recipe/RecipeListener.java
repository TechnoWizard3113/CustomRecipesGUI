package net.customrecipes.plugin.recipe;

import net.customrecipes.plugin.CustomRecipesGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RecipeListener implements Listener {

    private final CustomRecipesGUI plugin;

    public RecipeListener(CustomRecipesGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepare(PrepareItemCraftEvent e) {

        CraftingInventory inv = e.getInventory();

        for (CustomRecipe recipe : plugin.getRecipeManager().getRecipes()) {

            if (matches(inv, recipe)) {
                inv.setResult(recipe.getResult());
                return;
            }
        }

        inv.setResult(null);
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {

        CraftingInventory inv = (CraftingInventory) e.getInventory();

        for (CustomRecipe recipe : plugin.getRecipeManager().getRecipes()) {

            if (!matches(inv, recipe)) continue;

            consume(inv, recipe);
            return;
        }
    }

    private boolean matches(CraftingInventory inv, CustomRecipe recipe) {

        ItemStack[] matrix = inv.getMatrix();
        String[] shape = recipe.getShape();

        for (int i = 0; i < 9; i++) {

            int r = i / 3;
            int c = i % 3;

            char key = shape[r].charAt(c);
            CustomRecipe.Ingredient ing = recipe.getIngredients().get(key);

            ItemStack item = matrix[i];

            if (ing == null || ing.material == Material.AIR) {
                if (item != null && item.getType() != Material.AIR) return false;
                continue;
            }

            if (item == null || item.getType() != ing.material) return false;

            if (item.getAmount() < ing.amount) return false;
        }

        return true;
    }

    private void consume(CraftingInventory inv, CustomRecipe recipe) {

        ItemStack[] matrix = inv.getMatrix();
        String[] shape = recipe.getShape();

        for (int i = 0; i < 9; i++) {

            int r = i / 3;
            int c = i % 3;

            char key = shape[r].charAt(c);
            CustomRecipe.Ingredient ing = recipe.getIngredients().get(key);

            ItemStack item = matrix[i];

            if (ing == null || ing.material == Material.AIR) continue;

            int newAmount = item.getAmount() - ing.amount;

            if (newAmount <= 0) {
                matrix[i] = null;
            } else {
                item.setAmount(newAmount);
                matrix[i] = item;
            }
        }

        inv.setMatrix(matrix);
    }
}