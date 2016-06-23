package com.tayjay.augments.item.BodyParts;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IHUDProvider;
import com.tayjay.augments.entity.EntityCyborg;
import com.tayjay.augments.handler.PlayerHandler;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.inventory.InventoryAugmentPlayer;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tayjay on 2016-06-20.
 */
public class ItemCyborgEyes extends ItemBodyPart implements IHUDProvider
{

    public ItemCyborgEyes()
    {
        super(Type.EYES,1, 1);
        ModItems.register(this);
    }

    public ModelBiped modelHead = new ModelBiped();


    @Override
    public String getName()
    {
        return this.getUnlocalizedName();
    }

    @Override
    public void render(ItemStack stack, RenderPlayerEvent e)
    {
        RenderUtil.renderModelOnPlayer(e,modelHead,new ResourceLocation("augments","/textures/models/eyesBiped.png"),Type.EYES.ordinal());//new ResourceLocation("augments","/textures/entity/EntityCyborgTier2.png")

        /*
        if(e instanceof RenderPlayerEvent.Specials.Post)
        {
            RenderPlayerEvent.Specials.Post event = (RenderPlayerEvent.Specials.Post) e;
            ModelBiped playerBiped = event.renderer.modelBipedMain;
            if(!event.entityPlayer.isInvisible())
            {
                GL11.glPushMatrix();

                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments:/textures/entity/EntityCyborgTier2.png"));

                //Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments:/textures/models/eyesBiped.png"));
                float s2 = 1.01F / 16F;
                GL11.glScalef(s2, s2, s2);

                this.modelHead.bipedHead.rotateAngleX = playerBiped.bipedHead.rotateAngleX;
                this.modelHead.bipedHead.rotateAngleY = playerBiped.bipedHead.rotateAngleY;
                this.modelHead.bipedHead.rotateAngleZ = playerBiped.bipedHead.rotateAngleZ;
                this.modelHead.bipedHead.offsetX = playerBiped.bipedHead.offsetX;
                this.modelHead.bipedHead.offsetY = playerBiped.bipedHead.offsetY;
                this.modelHead.bipedHead.offsetZ = playerBiped.bipedHead.offsetZ;
                if (event.entityPlayer.isSneaking())
                {
                    this.modelHead.bipedHead.offsetX = playerBiped.bipedHead.offsetX;
                    this.modelHead.bipedHead.offsetY = playerBiped.bipedHead.offsetY + 1;
                    this.modelHead.bipedHead.offsetZ = playerBiped.bipedHead.offsetZ;
                }

                this.modelHead.bipedHead.render(1F);
                GL11.glPopMatrix();
            }
        }
        */

    }

    @Override
    public boolean canAdd(ItemStack bodyStack, EntityLivingBase entity)
    {
        return true;
    }

    @Override
    public void displayGameOverlay(ItemStack stack, RenderGameOverlayEvent event)
    {
        if(event.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
        {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            GL11.glScaled(2, 2, 2);
            List<String> toRender = new ArrayList<String>(6);
            toRender.add("You are a Cyborg!");
            toRender.add("");

            //************
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            InventoryAugmentPlayer inv = PlayerHandler.getPlayerAugmentInventory(player);
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack s = inv.getStackInSlot(i);
                if(s != null) {
                    if(s.getItem() instanceof IAugment)
                    {
                        toRender.add(((IAugment) s.getItem()).getName());
                    }
                }
            }
            //***********************
            int mod = 1;
            for(String rendering : toRender)
            {
                fontRenderer.drawString(rendering, 5, 10*(mod++), Color.RED.getRGB());
            }
        }
    }

    @Override
    public void displayInWorldElements(ItemStack stack, RenderWorldLastEvent event)
    {
        GL11.glPushMatrix();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer p = mc.thePlayer;
        List<EntityCyborg> list = p.worldObj.getEntitiesWithinAABB(EntityCyborg.class, AxisAlignedBB.getBoundingBox(p.posX-30,p.posY-30,p.posZ-30,p.posX+30,p.posY+30,p.posZ+30));
        GL11.glTranslated(-mc.renderViewEntity.posX, -mc.renderViewEntity.posY, -mc.renderViewEntity.posZ);
        for(EntityCyborg e : list)
        {
            if(e!=null && e.getOwner()==p)
                RenderUtil.renderAABBWithColour(e.boundingBox,1,1,1,1, false, true);
        }
        GL11.glPopMatrix();
    }


}
