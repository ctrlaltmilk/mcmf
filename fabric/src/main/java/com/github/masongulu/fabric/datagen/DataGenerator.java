package com.github.masongulu.fabric.datagen;

import com.github.masongulu.block.ModBlocks;
import com.github.masongulu.item.memory.SROMItem;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import static com.github.masongulu.ComputerMod.MOD_ID;
import static com.github.masongulu.block.ModBlocks.*;
import static com.github.masongulu.item.ModItems.SROM_ITEM;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(TagGenerator::new);
        generator.addProvider(ItemModelGenerator::new);
    }

    private static class TagGenerator extends FabricTagProvider.BlockTagProvider {
        public TagGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(DEVICE_CABLE)
                    .add(CABLE_BLOCK.get());
            getOrCreateTagBuilder(SERIAL_CABLE)
                    .add(SERIAL_CABLE_BLOCK.get());
        }
    }

    private static class ItemModelGenerator extends FabricModelProvider {

        public ItemModelGenerator(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            itemModelGenerator.generateFlatItem(SROM_ITEM.get(), ModelTemplates.FLAT_ITEM);
        }
    }
}
