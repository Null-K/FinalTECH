package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.taraxacum.common.util.MathUtil;
import io.taraxacum.common.util.StringNumberUtil;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.item.unusable.ItemPhony;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.DustGeneratorMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustGenerator extends AbstractMachine implements RecipeItem, EnergyNetProvider {
    private final String KEY_COUNT = "count";
    private final int CAPACITY = ConfigUtil.getOrDefaultItemSetting(Integer.MAX_VALUE / 4, this, "capacity");
    // default = 144115188344291328
    // long.max= 9223372036854775808
    private final long COUNT_LIMIT = (long) this.CAPACITY * (this.CAPACITY + 1) / 2;

    public DustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent blockPlaceEvent) {
                BlockStorage.addBlockInfo(blockPlaceEvent.getBlock().getLocation(), KEY_COUNT, StringNumberUtil.ZERO);
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new DustGeneratorMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Location location = block.getLocation();

        long count = Long.parseLong(config.getString(KEY_COUNT));
        boolean work = false;
        for (int slot : this.getInputSlot()) {
            ItemStack item = blockMenu.getItemInSlot(slot);
            if (ItemStackUtil.isItemSimilar(item, FinalTechItems.UNORDERED_DUST)) {
                item.setAmount(item.getAmount() - 1);
                count = Math.min(++count, this.COUNT_LIMIT);
                work = true;
                break;
            } else if (ItemPhony.isValid(item)) {
                item.setAmount(item.getAmount() - 1);
                count *= 2;
                count = Math.min(count, this.COUNT_LIMIT);
                work = true;
                break;
            }
        }
        if (!work) {
            count /= 2;
        }
        int charge = (int) MathUtil.getBig(1, 1, -2 * count);

        BlockStorage.addBlockInfo(location, KEY_COUNT, String.valueOf(count));
        if (count > 0) {
            this.addCharge(location, charge);
        }

        if(blockMenu.hasViewer()) {
            this.updateMenu(blockMenu, count, charge, this.getCharge(location));
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.GENERATOR;
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location location, @Nonnull Config config) {
        int charge = this.getCharge(location);
        this.setCharge(location, 0);
        return charge;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    private void updateMenu(@Nonnull BlockMenu blockMenu, long count, int charge, int energy) {
        ItemStack item = blockMenu.getItemInSlot(DustGeneratorMenu.STATUS_SLOT);
        ItemStackUtil.setLore(item, ConfigUtil.getStatusMenuLore(FinalTech.getLanguageManager(), this,
                String.valueOf(count),
                String.valueOf(charge),
                String.valueOf(energy)));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.CAPACITY));
    }
}
