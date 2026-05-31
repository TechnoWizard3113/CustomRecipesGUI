package net.customrecipes.plugin.gui;

import net.customrecipes.plugin.CustomRecipesGUI;
import net.customrecipes.plugin.recipe.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    private final CustomRecipesGUI plugin;

    public GUIListener(CustomRecipesGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (e.getView().getTitle().equals(MainGUI.TITLE)) {

            e.setCancelled(true);

            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.BLUE_STAINED_GLASS_PANE) return;

            String name = e.getCurrentItem().getItemMeta().getDisplayName();

            for (CustomRecipe r : plugin.getRecipeManager().getRecipes()) {
                if (r.getDisplayName().equals(name)) {
                    PreviewGUI.open(p, r);
                    return;
                }
            }
        }

        if (e.getView().getTitle().equals(PreviewGUI.TITLE)) {

            e.setCancelled(true);

            if (e.getSlot() == 49) {
                MainGUI.open(p, plugin.getRecipeManager());
            }
        }
    }
}