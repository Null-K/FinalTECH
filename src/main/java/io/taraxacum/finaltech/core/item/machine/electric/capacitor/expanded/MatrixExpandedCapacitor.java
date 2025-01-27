package io.taraxacum.finaltech.core.item.machine.electric.capacitor.expanded;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.unusable.ItemPhony;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusL2Menu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class MatrixExpandedCapacitor extends AbstractExpandedElectricCapacitor {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE - 1, this, "capacity");
    private final String stack = ConfigUtil.getOrDefaultItemSetting(StringNumberUtil.VALUE_INFINITY, this, "stack");

    public MatrixExpandedCapacitor(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusL2Menu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        BlockMenu blockMenu = BlockStorage.getInventory(location);
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (!ItemStackUtil.isItemNull(item) && ItemPhony.isValid(item)) {
                String energyStack = String.valueOf(config.getValue(KEY));
                energyStack = StringNumberUtil.add(energyStack, energyStack);
                BlockStorage.addBlockInfo(location, KEY, energyStack);
                item.setAmount(item.getAmount() - 1);
            }
        }
        super.tick(block, slimefunItem, config);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Nonnull
    @Override
    public String getMaxStack() {
        return this.stack;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf((this.capacity / 2)),
                this.stack,
                String.format("%.2f", Slimefun.getTickerTask().getTickRate() / 20.0));
    }
}
