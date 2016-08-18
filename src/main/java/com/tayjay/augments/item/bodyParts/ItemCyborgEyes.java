package com.tayjay.augments.item.bodyParts;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.events.IActivate;
import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IEnergySupply;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemCyborgEyes extends ItemEyesBase
{
    public ItemCyborgEyes(String name,int tier)
    {
        super(name,tier,3,name);
    }

}
