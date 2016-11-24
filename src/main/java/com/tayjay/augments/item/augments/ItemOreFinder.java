package com.tayjay.augments.item.augments;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.item.PartType;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by tayjay on 2016-11-22.
 */
public class ItemOreFinder extends ItemAugment
{
    public ItemOreFinder()
    {
        super("oreFinder", 4, PartType.HEAD, 4, "See what ores are around you.");
    }

    @Override
    public boolean activeWhenCreated(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean validate(ItemStack augment, EntityPlayer player)
    {
        return true;
    }

    @Override
    public void activate(ItemStack augment, EntityPlayer player)
    {
        World world = player.worldObj;
        if(world.isRemote)
        {
            int playerX = ((int) player.posX);
            int playerY = ((int) player.posY);
            int playerZ = ((int) player.posZ);
            int rangeX = 20,rangeY = 70,rangeZ = 20;
            BlockPos blockChecking;
            Augments.proxy.getClientEventHandler().clearBlockRender();
            for (int x = playerX - rangeX; x < playerX + rangeX; x++)
            {
                for (int z = playerZ - rangeZ; z < playerZ + rangeZ; z++)
                {
                    for (int y = playerY - rangeY; y < playerY + rangeY; y++)
                    {
                        blockChecking = new BlockPos(x,y,z);
                        if (world != null && world.getBlockState(blockChecking).getBlock().getUnlocalizedName().contains("ore"))
                        {
                            /*int currentMeta = world.getBlockMetadata(x, y, z);
                            if(meta==-1 || currentMeta == meta)
                            {
                                TayJayUtil.proxy.getEventHandler().addBlockPos(Vec3.createVectorHelper(x, y, z));
                                System.out.println("Found block at " + x + "," + y + "," + z);
                            }*/
                            Augments.proxy.getClientEventHandler().addBlockToRender(blockChecking);
                        }
                    }
                }
            }
        }
    }

}
