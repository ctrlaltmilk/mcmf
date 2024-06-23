package com.github.masongulu.block;

import com.github.masongulu.block.entity.CableBlockEntity;
import com.github.masongulu.block.entity.ComputerBlockEntity;
import com.github.masongulu.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CableBlock extends BaseEntityBlock {
    public CableBlock() {
        super(Properties.of(Material.STONE));
    }

    public BlockEntity newBlockEntity(BlockPos arg, BlockState arg2) {
        return new CableBlockEntity(arg, arg2);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof CableBlockEntity) {
            ((CableBlockEntity) blockEntity).invalidate(null);
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (blockEntityType == ModBlockEntities.CABLE_BLOCK_ENTITY) {
            return CableBlockEntity::tick;
        }
        return null;
    }
}
