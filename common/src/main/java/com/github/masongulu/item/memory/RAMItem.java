package com.github.masongulu.item.memory;

import com.github.masongulu.uxn.MemoryRegion;
import net.minecraft.world.item.ItemStack;

public class RAMItem extends MemoryItem {
    @Override
    public MemoryRegion getMemory(ItemStack stack) {
        return new MemoryRegion();
    }
}
