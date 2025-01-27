package io.taraxacum.finaltech.core.item.usable;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.taraxacum.finaltech.core.item.AbstractMySlimefunItem;
import io.taraxacum.finaltech.util.MachineUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * An {@link SlimefunItem} that can be used by player to do some #function.
 * @author Final_ROOT
 * @since 2.0
 */
// TODO: Optimization
public abstract class UsableSlimefunItem extends AbstractMySlimefunItem {
    public UsableSlimefunItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(MachineUtil.BLOCK_PLACE_HANDLER_DENY);
        this.addItemHandler((ItemUseHandler) UsableSlimefunItem.this::function);
    }

    /**
     * The function the item will do
     * while a player hold the item and right click.
     * @param playerRightClickEvent
     */
    protected abstract void function(@Nonnull PlayerRightClickEvent playerRightClickEvent);
}
