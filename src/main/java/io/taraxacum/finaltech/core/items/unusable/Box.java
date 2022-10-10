package io.taraxacum.finaltech.core.items.unusable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.api.interfaces.RecipeItem;
import io.taraxacum.finaltech.util.slimefun.ConfigUtil;
import io.taraxacum.finaltech.util.slimefun.RecipeUtil;
import io.taraxacum.finaltech.util.slimefun.SfItemUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class Box extends UnusableSlimefunItem implements RecipeItem {
    public static final double HEIGHT = ConfigUtil.getOrDefaultItemSetting(64, SfItemUtil.getIdFormatName(Box.class), "height");

    public Box(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
