package io.taraxacum.finaltech.core.item.machine.logic;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.RecipeUtil;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class LogicAmountComparator extends AbstractLogicComparator implements RecipeItem {
    public LogicAmountComparator(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected boolean preCompare(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        return !ItemStackUtil.isItemNull(item1) && !ItemStackUtil.isItemNull(item2);
    }

    @Override
    protected boolean compare(@Nullable ItemStack item1, @Nullable ItemStack item2) {
        return item1.getAmount() == item2.getAmount();
    }

    @Nonnull
    @Override
    protected ItemStack resultTrue() {
        return ItemStackUtil.cloneItem(FinalTechItems.LOGIC_TRUE);
    }

    @Nonnull
    @Override
    protected ItemStack resultFalse() {
        return ItemStackUtil.cloneItem(FinalTechItems.LOGIC_FALSE);
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
