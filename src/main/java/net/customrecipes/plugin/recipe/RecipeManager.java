package net.customrecipes.plugin.recipe;

import net.customrecipes.plugin.CustomRecipesGUI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class RecipeManager {

    private final CustomRecipesGUI plugin;
    private final Map<String, CustomRecipe> recipes = new LinkedHashMap<>();

    public RecipeManager(CustomRecipesGUI plugin) {
        this.plugin = plugin;
    }

    public void loadRecipes() {
        recipes.clear();

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("recipes");
        if (section == null) return;

        for (String id : section.getKeys(false)) {

            ConfigurationSection r = section.getConfigurationSection(id);
            if (r == null) continue;

            String display = r.getString("display", id);

            List<String> shapeList = r.getStringList("shape");
            String[] shape = shapeList.toArray(new String[0]);

            // result
            ConfigurationSection res = r.getConfigurationSection("result");
            Material mat = Material.valueOf(res.getString("material", "STONE"));

            ItemStack result = new ItemStack(mat);
            ItemMeta meta = result.getItemMeta();
            meta.setDisplayName(display);
            result.setItemMeta(meta);

            // ingredients
            Map<Character, CustomRecipe.Ingredient> ing = new HashMap<>();

            ConfigurationSection ingSec = r.getConfigurationSection("ingredients");
            for (String key : ingSec.getKeys(false)) {

                ConfigurationSection i = ingSec.getConfigurationSection(key);

                Material m = Material.valueOf(i.getString("material", "AIR"));
                int amount = i.getInt("amount", 1);

                ing.put(key.charAt(0), new CustomRecipe.Ingredient(m, amount));
            }

            recipes.put(id, new CustomRecipe(id, display, shape, ing, result));
        }
    }

    public Collection<CustomRecipe> getRecipes() {
        return recipes.values();
    }

    public CustomRecipe getRecipe(String id) {
        return recipes.get(id);
    }
}