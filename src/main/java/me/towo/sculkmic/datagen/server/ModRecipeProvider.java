package me.towo.sculkmic.datagen.server;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        // shaped
        /*
        ShapedRecipeBuilder.shaped(BlockINIT.BACKPACK.get().asItem(), 1)
                .define('l', Items.LEATHER)
                .pattern("lll").pattern("lll").pattern("lll")
                .unlockedBy("has_" + Items.LEATHER.getRegistryName().getPath(), has(Items.LEATHER))
                .save(consumer, new ResourceLocation(BackpacksMod.ID, BlockINIT.BACKPACK.get().getRegistryName().getPath()));
         */

        // shapeless
        /*
        ShapelessRecipeBuilder.shapeless(BlockINIT.BACKPACK.get().asItem(), 10)
                .requires(Items.RABBIT_HIDE).save(consumer,  new ResourceLocation(BackpacksMod.ID, BlockINIT.BACKPACK.get().getRegistryName().getPath()));
        */

        // cooking
        //SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.EGG), Items.FEATHER, 1, 20)
        //        .save(consumer, new ResourceLocation(BackpacksMod.ID, Items.FEATHER.getRegistryName().getPath() + "_smelt"));

    }
}
