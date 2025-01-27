package io.taraxacum.finaltech.core.operation;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.item.machine.operation.ItemSerializationConstructor;
import io.taraxacum.finaltech.core.item.unusable.CopyCard;
import io.taraxacum.finaltech.core.item.unusable.ItemPhony;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.plugin.dto.ItemWrapper;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Final_ROOT
 * @since 1.0
 */
public class ItemCopyCardOperation implements ItemSerializationConstructorOperation {
    private double count;
    private final int difficulty;
    private final ItemStack matchItem;
    private final ItemWrapper matchItemWrapper;
    private final ItemStack copyCardItem;
    private final ItemStack showItem;

    protected ItemCopyCardOperation(@Nonnull ItemStack item) {
        this.count = item.getAmount();
        this.difficulty = ConstantTableUtil.ITEM_COPY_CARD_AMOUNT;
        this.matchItem = item.clone();
        this.matchItem.setAmount(1);
        this.matchItemWrapper = new ItemWrapper(this.matchItem);
        this.copyCardItem = CopyCard.newItem(this.matchItem, "1");
        this.showItem = new CustomItemStack(item.getType(), FinalTech.getLanguageString("items", FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId(), "copy-card", "name"));
        this.updateShowItem();
    }

    public double getCount() {
        return this.count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Nonnull
    public ItemStack getMatchItem() {
        return this.matchItem;
    }

    @Override
    public int getType() {
        return ItemSerializationConstructorOperation.COPY_CARD;
    }

    @Nonnull
    @Override
    public ItemStack getShowItem() {
        return this.showItem;
    }

    @Override
    public void updateShowItem() {
        ItemStackUtil.setLore(this.showItem, FinalTech.getLanguageManager().replaceStringArray(FinalTech.getLanguageStringArray("items", FinalTechItems.ITEM_SERIALIZATION_CONSTRUCTOR.getItemId(), "copy-card", "lore"),
                ItemStackUtil.getItemName(this.matchItem),
                String.format("%.8f", this.count),
                String.valueOf(this.difficulty),
                String.valueOf(ItemSerializationConstructor.EFFICIENCY > 0 ? 1 / ItemSerializationConstructor.EFFICIENCY : "INFINITY")));
    }

    @Override
    public int addItem(@Nullable ItemStack item) {
        if (!this.isFinished()) {
            if (ItemStackUtil.isItemSimilar(item, this.matchItemWrapper)) {
                double efficiency = ItemSerializationConstructor.EFFICIENCY;
                efficiency = Math.min(efficiency, 1);
                if(efficiency <= 0) {
                    return 0;
                }
                if (item.getAmount() * efficiency + this.count < this.difficulty) {
                    int amount = item.getAmount();
                    item.setAmount(item.getAmount() - amount);
                    this.count += amount * efficiency;
                    return amount;
                } else {
                    int amount = (int) Math.ceil((this.difficulty - this.count) / efficiency);
                    item.setAmount(item.getAmount() - amount);
                    this.count = this.difficulty;
                    return amount;
                }
            } else if (ItemPhony.isValid(item)) {
                double amount = Math.min(item.getAmount(), this.difficulty - this.count);
                item.setAmount(item.getAmount() - (int) Math.ceil(amount));
                this.count += amount;
                return (int) Math.ceil(amount);
            }
        }
        return 0;
    }

    @Override
    public boolean isFinished() {
        return this.count >= this.difficulty;
    }

    @Nonnull
    @Override
    public ItemStack getResult() {
        return this.copyCardItem;
    }

    @Deprecated
    @Override
    public void addProgress(int i) {
    }

    @Deprecated
    @Override
    public int getProgress() {
        return (int) Math.floor(this.count);
    }

    @Deprecated
    @Override
    public int getTotalTicks() {
        return this.difficulty;
    }
}
