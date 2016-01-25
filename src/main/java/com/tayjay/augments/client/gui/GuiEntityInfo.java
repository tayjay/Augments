package com.tayjay.augments.client.gui;

import com.tayjay.augments.inventory.ContainerEmpty;
import com.tayjay.augments.lib.Reference;
import com.tayjay.augments.util.ClientUtil;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-17.
 */
public class GuiEntityInfo extends GuiContainer
{
    private ResourceLocation guiTexture;
    private EntityPlayer player;
    private EntityLivingBase target;

    public GuiEntityInfo(EntityPlayer player, EntityLivingBase target, String guiTextureName)
    {
        super(new ContainerEmpty());
        this.guiTexture = new ResourceLocation(Reference.MOD_ID_LOWER + ":textures/gui/" + guiTextureName + ".png");
        xSize = 256;
        ySize = 128;
        this.player = player;
        this.target =(EntityLivingBase) ClientUtil.getEntityLookingAt(player.worldObj, player);
        if(this.target==null)
            this.target = target;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        if(ClientUtil.getEntityLookingAt(player.worldObj,player)!=null)
            this.target =(EntityLivingBase) ClientUtil.getEntityLookingAt(player.worldObj,player);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);

        fontRendererObj.drawString(target.getCommandSenderName(), 8, 4, 0);

        List<String> infoList = getEntityInfo(target);
        int currentLine = 18;
        for(String info : infoList)
        {
            fontRendererObj.drawString(info,120,currentLine+=10,0);
        }
    }

    protected List<String> getEntityInfo(EntityLivingBase e)
    {
        List<String> infoList = new ArrayList<String>();
        infoList.add("Health: "+(int)e.getHealth()+" / "+(int)e.getMaxHealth());
        if(e.getHeldItem()!=null)
            infoList.add("Current Item: "+e.getHeldItem().getDisplayName());
        infoList.add("Age: "+e.getAge());
        if(e.getEntityAttribute(SharedMonsterAttributes.attackDamage)!=null)
            infoList.add("Attack Damage: "+e.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue());
        infoList.add("Potion Effects: "+e.getActivePotionEffects());
        if(e instanceof EntityCreeper)
        {
            EntityCreeper creeper = (EntityCreeper) e;
            List<EntityItem> drops = creeper.capturedDrops;
            for(EntityItem item : drops)
            {
                infoList.add(item.getCommandSenderName());
            }
        }
        if(e instanceof EntitySheep)
        {
            EntitySheep sheep = (EntitySheep) e;
            ItemStack woolStack = new ItemStack(Blocks.wool);
            woolStack.setItemDamage(sheep.getFleeceColor());
            String sheepColour = woolStack.getDisplayName().replace("Wool","");
            if(sheepColour.equals("")) sheepColour = "White";
            infoList.add("Sheep Colour: "+sheepColour);
            RenderUtil.drawItemStack(woolStack,xSize-40,8,2f,"");
        }

        return infoList;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        mc.getTextureManager().bindTexture(guiTexture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        RenderUtil.drawEntityLiving(guiLeft + xSize / 4 - 10, guiTop + ySize / 3 + 32, 30, 80, -20, target);
    }
}
