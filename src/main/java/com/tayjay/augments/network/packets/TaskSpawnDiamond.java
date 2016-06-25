package com.tayjay.augments.network.packets;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by tayjay on 2016-06-23.
 */
public class TaskSpawnDiamond implements Runnable
{
    public double x,y,z;
    public int amount;
    public EntityPlayer player;

    public World world;

    public TaskSpawnDiamond(PacketSpawnDiamond message, MessageContext ctx)
    {
        this.x = (double)message.x;
        this.y = (double)message.y;
        this.z = (double)message.z;
        this.amount = message.amount;
        this.player = ctx.getServerHandler().playerEntity;
        this.world = ctx.getServerHandler().playerEntity.getServerWorld();
    }

    @Override
    public void run()
    {
        player.addChatMessage(new TextComponentString("Spawning Diamonds!"));


        double x1=player.posX,y1=player.posY,z1 = player.posZ;
        System.out.println("Attempting to spawn Diamonds on server!\n"+x+" : "+x1+"\n"+y+" : "+y1+"\n"+z+" : "+z1);
        player.worldObj.spawnEntityInWorld(new EntityItem(world,x,y,z,new ItemStack(Items.DIAMOND,amount,0)));
    }
}
