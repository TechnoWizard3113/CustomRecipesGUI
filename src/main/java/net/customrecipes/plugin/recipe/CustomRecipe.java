package net.customrecipes.plugin.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomRecipe {

    private final String id;
    private final String displayName;
    private final String[] shape;
    private final Map<Character, Ingredient> ingredients;
    private final ItemStack result;

    public CustomRecipe(String id,
                        String displayName,
                        String[] shape,
                        Map<Character, Ingredient> ingredients,
                        ItemStack result) {

        this.id = id;
        this.displayName = displayName;
        this.shape = shape;
        this.ingredients = ingredients;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getShape() {
        return shape;
    }

    public Map<Character, Ingredient> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public static class Ingredient {
        public final Material material;
        public final int amount;

        public Ingredient(Material material, int amount) {
            this.material = material;
            this.amount = amount;
        }
    }
}