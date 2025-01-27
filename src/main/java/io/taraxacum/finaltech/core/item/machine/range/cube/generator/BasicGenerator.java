package io.taraxacum.finaltech.core.item.machine.range.cube.generator;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class BasicGenerator extends AbstractCubeElectricGenerator {
    private final String electricity = ConfigUtil.getOrDefaultItemSetting("1", this, "electricity");
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    public BasicGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected String getElectricity() {
        return this.electricity;
    }

    @Override
    protected int getRange() {
        return this.range;
    }
}
