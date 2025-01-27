package io.taraxacum.finaltech.core.item.machine.range.point;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.*;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.taraxacum.finaltech.FinalTech;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.core.menu.AbstractMachineMenu;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.finaltech.util.MachineUtil;
import io.taraxacum.finaltech.util.BlockTickerUtil;
import io.taraxacum.finaltech.util.ConfigUtil;
import io.taraxacum.finaltech.util.ConstantTableUtil;
import io.taraxacum.finaltech.util.RecipeUtil;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class EquivalentConcept extends AbstractPointMachine implements RecipeItem {
    public static final String KEY_LIFE = "l";
    public static final String KEY_RANGE = "r";
    private final double attenuationRate = ConfigUtil.getOrDefaultItemSetting(0.95, this, "attenuation-rate");
    private final double life = ConfigUtil.getOrDefaultItemSetting(4.0, this, "life");
    private final int range = ConfigUtil.getOrDefaultItemSetting(2, this, "range");

    public EquivalentConcept(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.addItemHandler(new ItemUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onRightClick(PlayerRightClickEvent e) {
                e.cancel();
            }
        });
        this.addItemHandler(new WeaponUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onHit(@Nonnull EntityDamageByEntityEvent e, @Nonnull Player player, @Nonnull ItemStack item) {
                e.setCancelled(true);
            }
        });
        this.addItemHandler(new EntityInteractHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onInteract(PlayerInteractEntityEvent e, ItemStack item, boolean offHand) {
                e.setCancelled(true);
            }
        });
        this.addItemHandler(new ToolUseHandler() {
            @Override
            @EventHandler(priority = EventPriority.LOWEST)
            public void onToolUse(BlockBreakEvent e, ItemStack tool, int fortune, List<ItemStack> drops) {
                e.setCancelled(true);
            }
        });
    }

    @Nonnull
    @Override
    protected BlockPlaceHandler onBlockPlace() {
        return MachineUtil.BLOCK_PLACE_HANDLER_DENY;
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return MachineUtil.simpleBlockBreakerHandler();
    }

    @Nonnull
    @Override
    protected AbstractMachineMenu setMachineMenu() {
        // this is the only
        return null;
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull SlimefunItem slimefunItem, @Nonnull Config config) {
        if (BlockTickerUtil.hasSleep(config)) {
            BlockTickerUtil.subSleep(config);
            return;
        }

        double life = config.contains(KEY_LIFE) ? Double.parseDouble(config.getString(KEY_LIFE)) : 0;
        if (life < 1) {
            Location location = block.getLocation();
            BlockStorage.addBlockInfo(location, KEY_LIFE, null);
            BlockStorage.addBlockInfo(location, KEY_RANGE, null);
            BlockTickerUtil.setSleep(config, null);
            BlockStorage.clearBlockInfo(location);
            JavaPlugin javaPlugin = this.getAddon().getJavaPlugin();
            javaPlugin.getServer().getScheduler().runTaskLaterAsynchronously(javaPlugin, () -> {
                if (!location.getBlock().getType().isAir() && BlockStorage.getLocationInfo(location, ConstantTableUtil.CONFIG_ID) == null) {
                    BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, FinalTechItems.JUSTIFIABILITY.getItemId(), true);
                }
            }, Slimefun.getTickerTask().getTickRate() + 1);
            return;
        }

        final int range = config.contains(KEY_RANGE) ? Integer.parseInt(config.getString(KEY_RANGE)) : this.range;

        while (life > 1) {
            final double finalLife = life--;
            this.function(block, range, location -> {
                FinalTech.getLocationRunnableFactory().waitThenRun(() -> {
                    Block targetBlock = location.getBlock();
                    if (!BlockStorage.hasBlockInfo(location)) {
                        if (targetBlock.getType().isAir()) {
                            BlockStorage.addBlockInfo(location, ConstantTableUtil.CONFIG_ID, EquivalentConcept.this.getId(), true);
                            BlockStorage.addBlockInfo(location, KEY_LIFE, String.valueOf(finalLife * attenuationRate));
                            BlockStorage.addBlockInfo(location, KEY_RANGE, String.valueOf(range + 1));
                            BlockTickerUtil.setSleep(location, String.valueOf(EquivalentConcept.this.life - finalLife));
                            JavaPlugin javaPlugin = EquivalentConcept.this.getAddon().getJavaPlugin();
                            javaPlugin.getServer().getScheduler().runTask(javaPlugin, () -> targetBlock.setType(EquivalentConcept.this.getItem().getType()));
                        }
                    }
                }, location);
                return 0;
            });
        }

        BlockStorage.addBlockInfo(block, KEY_LIFE, String.valueOf(0));
    }

    @Override
    protected boolean isSynchronized() {
        return false;
    }

    @Nonnull
    @Override
    protected Location getTargetLocation(@Nonnull Location location, int range) {
        int y = location.getBlockY() - range + FinalTech.getRandom().nextInt(range + range);
        y = Math.min(location.getWorld().getMaxHeight(), y);
        y = Math.max(location.getWorld().getMinHeight(), y);
        return new Location(location.getWorld(), location.getX() - range + FinalTech.getRandom().nextInt(range + range), y, location.getZ() - range + new Random().nextInt(range + range));
    }

    @Override
    public void registerDefaultRecipes() {
        RecipeUtil.registerDescriptiveRecipe(FinalTech.getLanguageManager(), this);
    }
}
