package io.taraxacum.finaltech.core.item.machine.template.extraction;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.taraxacum.finaltech.core.interfaces.RecipeItem;
import io.taraxacum.finaltech.setup.FinalTechItems;
import io.taraxacum.libs.slimefun.dto.RandomMachineRecipe;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Final_ROOT
 * @since 2.2
 */
public class DigitalExtraction extends AbstractExtractionMachine implements RecipeItem {
    public DigitalExtraction(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    public void registerDefaultRecipes() {
        List<RandomMachineRecipe.RandomOutput> randomOutputList1 = new ArrayList<>();
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_ZERO, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_ONE, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_TWO, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_THREE, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_FOUR, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_FIVE, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_SIX, 1));
        randomOutputList1.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_SEVEN, 1));
        RandomMachineRecipe randomMachineRecipe1 = new RandomMachineRecipe(FinalTechItems.LOGIC_FALSE, randomOutputList1);
        this.registerRecipe(randomMachineRecipe1);

        List<RandomMachineRecipe.RandomOutput> randomOutputList2 = new ArrayList<>();
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_EIGHT, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_NINE, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_TEN, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_ELEVEN, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_TWELVE, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_THIRTEEN, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_FOURTEEN, 1));
        randomOutputList2.add(new RandomMachineRecipe.RandomOutput(FinalTechItems.DIGITAL_FIFTEEN, 1));
        RandomMachineRecipe randomMachineRecipe2 = new RandomMachineRecipe(FinalTechItems.LOGIC_TRUE, randomOutputList2);
        this.registerRecipe(randomMachineRecipe2);
    }
}
