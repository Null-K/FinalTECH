package io.taraxacum.finaltech.core.item.machine.electric;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.MenuUpdater;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.unit.StatusMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.libs.slimefun.util.EnergyUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class VariableWireResistance extends AbstractElectricMachine implements RecipeItem, MenuUpdater {
    private final int capacity = ConfigUtil.getOrDefaultItemSetting(65536, this, "capacity");
    private final String capacityString = String.valueOf(this.capacity);

    public VariableWireResistance(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new StatusMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        Location location = block.getLocation();
        String charge = EnergyUtil.getCharge(location);
        if (this.capacityString.equals(charge)) {
            BlockStorage.clearBlockInfo(location);
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, () -> {
                if(location.getBlock().getType().equals(VariableWireResistance.this.getItem().getType()) && BlockStorage.getLocationInfo(location, ConstantTableUtil.CONFIG_ID) == null) {
                    block.setType(FinalTechItems.VARIABLE_WIRE_CAPACITOR.getType());
                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItems.VARIABLE_WIRE_CAPACITOR.getItemId(), true);
                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_CHARGE, String.valueOf(this.getCapacity()));
//                    Slimefun.getBlockDataService().setBlockData(block, FinalTechItems.VARIABLE_WIRE_CAPACITOR.getItemId());
                }
            }, Slimefun.getTickerTask().getTickRate() + 1);
        } else {
            BlockMenu blockMenu = BlockStorage.getInventory(location);
            if (blockMenu.hasViewer()) {
                this.updateMenu(blockMenu, StatusMenu.STATUS_SLOT, this, charge);
            }
        }
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this,
                String.valueOf(this.capacity));
    }
}
