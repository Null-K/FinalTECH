package io.taraxacum.finaltech.core.menu.clicker;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.taraxacum.finaltech.core.interfaces.DigitalItem;
import io.taraxacum.finaltech.core.item.machine.AbstractMachine;
import io.taraxacum.finaltech.core.item.machine.clicker.RemoteAccessor;
import io.taraxacum.libs.plugin.util.ItemStackUtil;
import io.taraxacum.libs.plugin.util.ParticleUtil;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class ConfigurableRemoteAccessorMenu extends AbstractAccessorMenu {
    private static final int[] BORDER = new int[] {0, 1, 2, 3, 5, 6, 7, 8};
    private static final int[] INPUT_BORDER = new int[0];
    private static final int[] OUTPUT_BORDER = new int[0];
    private static final int[] INPUT_SLOT = new int[] {4};
    private static final int[] OUTPUT_SLOT = new int[] {4};

    public ConfigurableRemoteAccessorMenu(@Nonnull AbstractMachine machine) {
        super(machine);
    }

    @Override
    protected void doFunction(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Player player) {
        // TODO async
        ItemStack item = blockMenu.getItemInSlot(INPUT_SLOT[0]);
        if(!ItemStackUtil.isItemNull(item)) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
            if (slimefunItem instanceof DigitalItem digitalItem) {
                blockMenu.close();

                int digit = digitalItem.getDigit();

                BlockData blockData = block.getState().getBlockData();
                List<Block> blockList = new ArrayList<>();
                if (blockData instanceof Directional) {
                    BlockFace blockFace = ((Directional) blockData).getFacing();
                    Block targetBlock = block;

                    if(digit > 0) {
                        for(int i = 0; i < digit; i++) {
                            targetBlock = targetBlock.getRelative(blockFace);
                        }
                        blockList.add(targetBlock);

                        if(BlockStorage.hasInventory(targetBlock)) {
                            BlockMenu targetBlockMenu = BlockStorage.getInventory(targetBlock);
                            if(targetBlockMenu.canOpen(targetBlock, player)) {
                                JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
                                javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.COMPOSTER, 0, blockList));
                                targetBlockMenu.open(player);
                            }
                        }
                    } else if(digit == 0) {
                        for (int i = 0; i < RemoteAccessor.RANGE; i++) {
                            targetBlock = targetBlock.getRelative(blockFace);
                            blockList.add(targetBlock);
                            if (BlockStorage.hasInventory(targetBlock)) {
                                BlockMenu targetBlockMenu = BlockStorage.getInventory(targetBlock);
                                if (targetBlockMenu.canOpen(targetBlock, player)) {
                                    JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
                                    javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.COMPOSTER, 0, blockList));
                                    targetBlockMenu.open(player);
                                    break;
                                }
                            }
                        }
                    }
                }
                return;
            }
        }

        JavaPlugin javaPlugin = this.getSlimefunItem().getAddon().getJavaPlugin();
        javaPlugin.getServer().getScheduler().runTaskAsynchronously(javaPlugin, () -> ParticleUtil.drawCubeByBlock(javaPlugin, Particle.COMPOSTER, 0, block));
    }

    @Override
    protected int[] getBorder() {
        return BORDER;
    }

    @Override
    protected int[] getInputBorder() {
        return INPUT_BORDER;
    }

    @Override
    protected int[] getOutputBorder() {
        return OUTPUT_BORDER;
    }

    @Override
    public int[] getInputSlot() {
        return INPUT_SLOT;
    }

    @Override
    public int[] getOutputSlot() {
        return OUTPUT_SLOT;
    }

    @Override
    protected void updateInventory(@Nonnull Inventory inventory, @Nonnull Location location) {

    }
}
