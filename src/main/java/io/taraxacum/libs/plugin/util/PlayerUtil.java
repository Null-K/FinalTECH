package io.taraxacum.libs.plugin.util;

import io.taraxacum.finaltech.FinalTech;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class PlayerUtil {
    public static final NamespacedKey KEY_UUID = new NamespacedKey(FinalTech.getInstance(), "UUID");
    public static final NamespacedKey KEY_IGNORE_PERMISSION = new NamespacedKey(FinalTech.getInstance(), "IP");
    public static final String IGNORE_PERMISSION_VALUE_FALSE = "f";
    public static final String IGNORE_PERMISSION_VALUE_TRUE = "t";

    public static String parseIdInItem(@Nonnull ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        return PlayerUtil.parseIdInItem(itemMeta);
    }
    public static String parseIdInItem(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(KEY_UUID, PersistentDataType.STRING)) {
            return persistentDataContainer.get(KEY_UUID, PersistentDataType.STRING);
        } else {
            return null;
        }
    }

    public static Boolean parseIgnorePermissionInItem(@Nonnull ItemStack item) {
        if (ItemStackUtil.isItemNull(item)) {
            return null;
        }
        ItemMeta itemMeta = item.getItemMeta();
        return PlayerUtil.parseIgnorePermissionInItem(itemMeta);
    }
    public static Boolean parseIgnorePermissionInItem(@Nonnull ItemMeta itemMeta) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        if (persistentDataContainer.has(KEY_IGNORE_PERMISSION, PersistentDataType.STRING)) {
            return IGNORE_PERMISSION_VALUE_TRUE.equals(persistentDataContainer.get(KEY_IGNORE_PERMISSION, PersistentDataType.STRING));
        }
        return false;
    }

    public static boolean updateIdInItem(@Nonnull ItemStack item, @Nonnull Player player, boolean ignoreExisted) {
        if (ItemStackUtil.isItemNull(item)) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (PlayerUtil.updateIdInItem(itemMeta, player, ignoreExisted)) {
            item.setItemMeta(itemMeta);
            return true;
        }
        return false;
    }
    public static boolean updateIdInItem(@Nonnull ItemMeta itemMeta, @Nonnull Player player, boolean ignoreExisted) {
        String newId = player.getUniqueId().toString();
        String oldId = PlayerUtil.parseIdInItem(itemMeta);
        if (ignoreExisted || oldId == null) {
            itemMeta.getPersistentDataContainer().set(KEY_UUID, PersistentDataType.STRING, newId);
            return true;
        }
        return false;
    }

    public static boolean updateIgnorePermissionInItem(@Nonnull ItemStack item, @Nonnull Boolean ignorePermission) {
        if (ItemStackUtil.isItemNull(item)) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        if (PlayerUtil.updateIgnorePermissionInItem(itemMeta, ignorePermission)) {
            item.setItemMeta(itemMeta);
            return true;
        }
        return false;
    }
    public static boolean updateIgnorePermissionInItem(@Nonnull ItemMeta itemMeta, @Nonnull Boolean ignorePermission) {
        itemMeta.getPersistentDataContainer().set(KEY_IGNORE_PERMISSION, PersistentDataType.STRING, ignorePermission ? IGNORE_PERMISSION_VALUE_TRUE : IGNORE_PERMISSION_VALUE_FALSE);
        return true;
    }
}
