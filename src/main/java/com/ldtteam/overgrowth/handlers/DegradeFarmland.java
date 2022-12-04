package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

/**
 * Degrades farmland.
 */
public class DegradeFarmland implements ITransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.FARMLAND;
    }

    @Override
    public boolean ready(final long worldTick)
    {
        return worldTick % 96 == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection)
    {
        final BlockState upState = Utils.getBlockState(chunk, relativePos.above(), chunkSection);
        if (upState.isAir() || chunk.getLevel().getRandom().nextInt(100) < 1)
        {
            final BlockState hereState = Utils.getBlockState(chunk, relativePos, chunkSection);

            final LevelChunkSection section = chunk.getSections()[chunkSection];
            final BlockPos worldPos = Utils.getWorldPos(chunk, section, relativePos);
            FarmBlock.turnToDirt(hereState, chunk.getLevel(), worldPos);
        }
        else if (upState.getBlock() instanceof CropBlock)
        {
            final LevelChunkSection section = chunk.getSections()[chunkSection];
            final BlockPos worldPos = Utils.getWorldPos(chunk, section, relativePos.above());

            if (chunk.getLevel().getRandom().nextInt(100) < 10)
            {
                chunk.getLevel().destroyBlock(worldPos, true);
            }
            else
            {
                chunk.getLevel().setBlock(worldPos, upState.getBlock().defaultBlockState(), 3);
            }
        }
    }
}