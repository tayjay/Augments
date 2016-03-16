package com.tayjay.augments.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by tayjm_000 on 2016-01-29.
 */
public class DummyPlayer extends EntityPlayer
{
    public static final DummyPlayer PLAYER = new DummyPlayer(Minecraft.getMinecraft().theWorld,new GameProfile(new UUID(9,1),"AugmentDummy"));
    public DummyPlayer(World p_i45324_1_, GameProfile p_i45324_2_)
    {
        super(p_i45324_1_, p_i45324_2_);
    }

    @Override
    public void addChatMessage(IChatComponent p_145747_1_)
    {

    }

    @Override
    public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
    {
        return false;
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates()
    {
        return null;
    }
}
