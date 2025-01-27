package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.core.machines.MachineOperation;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class DustFactoryOperation implements MachineOperation {
    private int amountCount = 0;
    private int typeCount = 0;
    private int amountDifficulty;
    private int typeDifficulty;
    private final ItemWrapper[] matchItemList;

    public DustFactoryOperation(int amountDifficulty, int typeDifficulty) {
        this.amountDifficulty = amountDifficulty;
        this.typeDifficulty = typeDifficulty;
        this.matchItemList = new ItemWrapper[typeDifficulty + 1];
    }

    public void addItem(@Nullable ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return;
        }

        this.amountCount += item.getAmount();
        if (this.amountCount > this.amountDifficulty + 1) {
            this.amountCount = this.amountDifficulty + 1;
        }

        if (this.typeCount <= this.typeDifficulty) {
            boolean newItem = true;
            for (int i = 0; i < this.typeCount; i++) {
                ItemWrapper existedItem = this.matchItemList[i];
                if (ItemStackUtil.isItemSimilar(item, existedItem)) {
                    newItem = false;
                    break;
                }
            }
            if (newItem) {
                this.matchItemList[this.typeCount++] = new ItemWrapper(ItemStackUtil.cloneItem(item));
            }
        }
    }

    public int getAmountCount() {
        return this.amountCount;
    }

    public int getTypeCount() {
        return this.typeCount;
    }

    public int getAmountDifficulty() {
        return this.amountDifficulty;
    }

    public int getTypeDifficulty() {
        return this.typeDifficulty;
    }

    @Override
    public boolean isFinished() {
        return this.amountCount >= this.amountDifficulty && this.typeCount >= this.typeDifficulty;
    }

    @Nullable
    public ItemStack getResult() {
        if (this.amountCount == this.amountDifficulty && this.typeCount == this.typeDifficulty) {
            return ItemStackUtil.cloneItem(FinalTechItems.ORDERED_DUST);
        } else if (this.isFinished()) {
            return ItemStackUtil.cloneItem(FinalTechItems.UNORDERED_DUST);
        } else {
            return null;
        }
    }

    @Deprecated
    @Override
    public void addProgress(int i) {

    }

    @Deprecated
    @Override
    public int getProgress() {
        return 0;
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return 0;
    }
}
