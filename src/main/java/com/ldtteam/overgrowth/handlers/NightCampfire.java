package com.ldtteam.overgrowth.handlers;

import com.ldtteam.overgrowth.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Torch die.
 */
public class NightCampfire implements ITransformationHandler
{
    @Override
    public boolean transforms(final BlockState state)
    {
        return state.getBlock() == Blocks.CAMPFIRE;
    }
    @Override
    public boolean ready(final long worldTick)
    {
        return worldTick % 11 == 0;
    }

    @Override
    public void transformBlock(final BlockPos relativePos, final LevelChunk chunk, final int chunkSection, final BlockState input)
    {
        final BlockState relativeState = Utils.getBlockState(chunk, relativePos.below(), chunkSection);
        if (relativeState.getBlock() == Blocks.HAY_BLOCK)
        {
            return;
        }
        final BlockPos worldPos = Utils.getWorldPos(chunk, chunk.getSections()[chunkSection], relativePos);
        chunk.getLevel().setBlock(worldPos, Blocks.CAMPFIRE.defaultBlockState().setValue(CampfireBlock.LIT, false), 0x03);
    }
}
