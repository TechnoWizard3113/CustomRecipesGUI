package net.customrecipes.plugin;

import net.customrecipes.plugin.commands.RecipesCommand;
import net.customrecipes.plugin.recipe.RecipeManager;
import net.customrecipes.plugin.recipe.RecipeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomRecipesGUI extends JavaPlugin {

    private RecipeManager recipeManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.recipeManager = new RecipeManager(this);
        this.recipeManager.loadRecipes();

        getCommand("recipes").setExecutor(new RecipesCommand(this));

        getServer().getPluginManager().registerEvents(new RecipeListener(this), this);
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }
}