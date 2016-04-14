package com.tayjay.augments.block;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.ModBlocks;
import com.tayjay.augments.lib.Names;
import com.tayjay.augments.tileentity.TileEntityAugmentBuilder;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by tayjm_000 on 2016-04-05.
 */
public class BlockAugmentBuilder extends BlockA implements ITileEntityProvider
{
    public BlockAugmentBuilder()
    {
        super();
        this.setBlockName(Names.Blocks.AUGMENT_BUILDER);
        this.setBlockTextureName(Names.Blocks.AUGMENT_BUILDER);
        ModBlocks.register(this);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityAugmentBuilder();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            if(!player.isSneaking())
            {

            }
        }
        return true;
    }
}
