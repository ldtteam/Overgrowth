package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import static net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE;

/**
 * Mud dries up
 */
public class MudDry implements ITransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.MUD;
    }

    @Override
    public boolean ready(final long worldTick, final LevelChunk chunk)
    {
        return !chunk.getLevel().isRaining() && worldTick % 17 == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        int waterCount = 0;
        for (final Direction direction : Direction.values())
        {
            final BlockState relativeState = Utils.getBlockState(chunk, relativePos.relative(direction), chunkSection);
            if (relativeState.getBlock() == Blocks.WATER || relativeState.getBlock() == Blocks.MUD)
            {
                waterCount++;
            }
        }

        if (waterCount < 3)
        {
            final LevelChunkSection section = chunk.getSections()[chunkSection];
            final BlockPos worldPos = Utils.getWorldPos(chunk, section, relativePos);

            chunk.getLevel().setBlock(worldPos, Blocks.DIRT.defaultBlockState(), UPDATE_ALL_IMMEDIATE);
        }
    }
}
