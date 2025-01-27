package io.taraxacum.finaltech.core.item.machine;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.item.unusable.digital.AbstractDigitalNumber;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.core.menu.machine.DigitAdderMenu;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.MachineUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class DigitAdder extends AbstractMachine implements RecipeItem {
    public DigitAdder(@Nonnull ItemGroup itemGroup, @Nonnull SlimefunItemStack item, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_PLACER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler(this);
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        return new DigitAdderMenu(this);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        BlockMenu blockMenu = BlockStorage.getInventory(block);
        Inventory inventory = blockMenu.toInventory();
        if (MachineUtil.slotCount(inventory, this.getOutputSlot()) == this.getOutputSlot().length) {
            return;
        }
        ItemStack itemStack;
        int digit = 0;
        for (int slot : this.getInputSlot()) {
            itemStack = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(itemStack)) {
                return;
            }
            SlimefunItem digitalItem = SlimefunItem.getByItem(itemStack);
            if (digitalItem instanceof DigitalItem) {
                digit += ((DigitalItem) digitalItem).getDigit();
            } else {
                return;
            }
        }
        if (digit > 15) {
            itemStack = AbstractDigitalNumber.INTEGER_ITEM_STACK_MAP.get(digit / 16);
            if (itemStack != null) {
                inventory.setItem(this.getOutputSlot()[0], itemStack);
            }
        }
        itemStack = AbstractDigitalNumber.INTEGER_ITEM_STACK_MAP.get(digit % 16);
        if (itemStack != null) {
            inventory.setItem(this.getOutputSlot()[1], itemStack);
        }
        for (int slot : this.getInputSlot()) {
            itemStack = inventory.getItem(slot);
            if (ItemStackUtil.isItemNull(itemStack)) {
                continue;
            }
            itemStack.setAmount(itemStack.getAmount() - 1);
        }
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Override
    public void registerDefaultRecipes() {
        for (ItemStack itemStack : AbstractDigitalNumber.INTEGER_ITEM_STACK_MAP.values()) {
            this.registerRecipe(itemStack, ItemStackUtil.AIR);
        }
    }
}
