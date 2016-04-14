package com.tayjay.augments.augment;

import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IAugmentRender;
import com.tayjay.augments.augment.interfaces.IHUDProvider;
import com.tayjay.augments.entity.EntityCyborg;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.item.ItemAugment;
import com.tayjay.augments.util.RenderUtil;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import scala.collection.parallel.ParIterableLike;

import java.util.List;

/**
 * Created by tayjm_000 on 2016-03-19.
 */
public class AugmentCyborgEyes extends ItemAugment implements IAugmentRender, IHUDProvider
{

    public ModelBiped modelHead = new ModelBiped();

    public AugmentCyborgEyes()
    {
        super();
        this.setUnlocalizedName("cyborgEyes");
        this.maxStackSize = 1;
        ModItems.register(this);
    }

    @Override
    public String getAugmentName()
    {
        return null;
    }

    @Override
    public IAugment getAugment()
    {
        return null;
    }

    @Override
    public byte getTier()
    {
        return 0;
    }

    @Override
    public void onAdd(ItemStack stack, EntityLivingBase entity)
    {

    }

    @Override
    public void onRemove(ItemStack stack, EntityLivingBase entity)
    {

    }

    @Override
    public void writeAugmentToNBT(NBTTagCompound tag)
    {

    }

    @Override
    public void readAugmentFromNBT(NBTTagCompound tag)
    {

    }

    @Override
    public boolean canAdd(ItemStack stack, EntityLivingBase addingTo)
    {
        return true;
    }

    @Override
    public void onEvent(ItemStack itemStack, Event event)
    {

    }

    @Override
    public void doRender(ItemStack s, Event e)
    {
        if(e instanceof RenderPlayerEvent.Specials.Post)
        {
            RenderPlayerEvent.Specials.Post event = (RenderPlayerEvent.Specials.Post) e;
            ModelBiped playerBiped = event.renderer.modelBipedMain;
            if(!event.entityPlayer.isInvisible())
            {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments:/textures/models/eyesBiped.png"));
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
    }

    @Override
    public void displayGameOverlay(ItemStack stack, RenderGameOverlayEvent event)
    {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        GL11.glPushMatrix();
        GL11.glScaled(2,2,2);
        fontRenderer.drawString("You are a Cyborg!",10,10,0);
        GL11.glPopMatrix();
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
