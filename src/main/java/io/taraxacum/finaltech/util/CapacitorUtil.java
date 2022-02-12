package io.taraxacum.finaltech.util;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Final_ROOT
 */
public class CapacitorUtil {
    public static final ItemStack CAPACITOR_ICON = new CustomItemStack(Material.YELLOW_STAINED_GLASS_PANE, "&e存电量",
            "",
            "&7当前存电量 &60J");

    public static final void setIcon(ItemStack item, int capacitor) {
        ItemStackUtil.setLastLore(item, "§7当前存电量 §6" + capacitor + "J");
    }

    public static final void setIcon(ItemStack item, String capacitor) {
        ItemStackUtil.setLastLore(item, "§7当前存电量 §6" + capacitor + "J");
    }
}