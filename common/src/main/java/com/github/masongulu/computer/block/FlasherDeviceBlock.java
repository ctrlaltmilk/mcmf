package com.github.masongulu.computer.block;

import com.github.masongulu.ModBlockEntities;
import com.github.masongulu.computer.block.entity.FlasherDeviceBlockEntity;
import com.github.masongulu.computer.block.entity.RedstoneDeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FlasherDeviceBlock extends GenericDeviceBlock {

    public FlasherDeviceBlock() {
        super(Properties.of(Material.STONE));
    }

    public BlockEntity newBlockEntity(BlockPos arg, BlockState arg2) {
        return new RedstoneDeviceBlockEntity(arg, arg2);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.FLASHER_DEVICE_BLOCK_ENTITY.get(), FlasherDeviceBlockEntity::tick);
    }

}