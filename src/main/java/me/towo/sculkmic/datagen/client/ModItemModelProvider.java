package me.towo.sculkmic.datagen.client;

import me.towo.sculkmic.SculkMicMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, SculkMicMod.ID, existingFileHelper);
    }

    protected void simpleBlockItem(Item item) {
        getBuilder(item.getRegistryName().toString())
                .parent(getExistingFile(modLoc("block/" + item.getRegistryName().getPath())));
    }

    protected  void oneLayerItem(Item item, ResourceLocation texture) {
        ResourceLocation itemTexture = new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath());
        if (existingFileHelper.exists(itemTexture, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(item.getRegistryName().getPath())
                    .parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", itemTexture);
        } else {
            System.out.println("TEXTURE FOR " + item.getRegistryName().toString() + " NOT PRESENT AT " + itemTexture.toString());
        }
    }

    protected void oneLayerItem(Item item) {
        oneLayerItem(item, item.getRegistryName());
    }

    @Override
    protected void registerModels() {
        // Block Items
        //simpleBlockItem(BlockINIT.BACKPACK.get().asItem());

        // Simple Items
        //oneLayerItem(BlockINIT.BACKPACK.get().asItem());
    }
}
