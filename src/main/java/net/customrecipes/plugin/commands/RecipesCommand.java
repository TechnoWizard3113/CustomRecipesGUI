package net.customrecipes.plugin.commands;

import net.customrecipes.plugin.CustomRecipesGUI;
import net.customrecipes.plugin.gui.MainGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecipesCommand implements CommandExecutor {

    private final CustomRecipesGUI plugin;

    public RecipesCommand(CustomRecipesGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        MainGUI.open(player, plugin.getRecipeManager());
        return true;
    }
}