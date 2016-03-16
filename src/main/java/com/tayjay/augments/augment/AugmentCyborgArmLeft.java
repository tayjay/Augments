package com.tayjay.augments.augment;

import com.tayjay.augments.AugmentsCore;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IAugmentRender;
import com.tayjay.augments.init.ModItems;
import com.tayjay.augments.item.ItemAugment;
import com.tayjay.augments.proxy.ClientProxy;
import com.tayjay.augments.util.LogHelper;
import com.tayjay.augments.util.NBTHelper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.realms.RendererUtility;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by tayjm_000 on 2016-01-31.
 */
public class AugmentCyborgArmLeft extends ItemAugment implements IAugment, IAugmentRender
{
    public ModelBiped modelArm = new ModelSkeleton();

    public AugmentCyborgArmLeft()
    {
        super();
        this.setUnlocalizedName("cyborgArmLeft");
        this.maxStackSize = 1;
        ModItems.register(this);
    }

    @Override
    public String getAugmentName()
    {
        return "cyborgArmLeft";
    }

    @Override
    public IAugment getAugment()
    {
        return this;
    }

    @Override
    public byte getTier()
    {
        return 3;
    }

    @Override
    public void onAdd(ItemStack stack, EntityLivingBase entity)
    {
        NBTHelper.setString(stack, "entityName", entity.getCommandSenderName());
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
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean p_77624_4_)
    {
        super.addInformation(itemStack, player, list, p_77624_4_);
        if(NBTHelper.hasTag(itemStack,"entityName"))
            list.add("EntityId: "+NBTHelper.getString(itemStack, "entityName"));
        list.add("CurrentPlayer: "+player.getCommandSenderName());

    }

    /**
     * Call in Player Render Post Event
     * @param stack containing stack
     * @param e     Event to render in
     */
    @Override
    public void doRender(ItemStack stack,Event e)
    {
        RenderPlayerEvent event=null;
        RenderLivingEvent livingRender = null;
        EntityPlayer player = null;
        try
        {
            if(e instanceof RenderPlayerEvent)
            {
                event = (RenderPlayerEvent) e;
                player = event.entityPlayer;
            }
            else if(e instanceof RenderLivingEvent)
            {
                livingRender = (RenderLivingEvent) e;
                player = (EntityPlayer) livingRender.entity;
            }

        }catch(ClassCastException ex)
        {
            LogHelper.error("Attempted to render with non-render event!");
            return;
        }
        if(event!=null&& stack!=null)
        {
            ModelBiped playerBiped = event.renderer.modelBipedMain;
            if(event instanceof RenderPlayerEvent.Specials.Post )
            {
                GL11.glPushMatrix();
                Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/entity/skeleton/skeleton.png"));
                float s5 = 1.01F / 16F;
                GL11.glScalef(s5, s5, s5);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(GL11.GL_BLEND);

                this.modelArm.bipedLeftArm.rotateAngleX = playerBiped.bipedLeftArm.rotateAngleX;
                this.modelArm.bipedLeftArm.rotateAngleY = playerBiped.bipedLeftArm.rotateAngleY;
                this.modelArm.bipedLeftArm.rotateAngleZ = playerBiped.bipedLeftArm.rotateAngleZ;

                this.modelArm.bipedLeftArm.offsetX = playerBiped.bipedLeftArm.offsetX;
                this.modelArm.bipedLeftArm.offsetY = playerBiped.bipedLeftArm.offsetY;
                this.modelArm.bipedLeftArm.offsetZ = playerBiped.bipedLeftArm.offsetZ;

                //playerBiped.bipedLeftArm.isHidden = true;

                this.modelArm.bipedLeftArm.render(1F);

                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
            else if(event instanceof RenderPlayerEvent.Pre)
            {
                playerBiped.bipedLeftArm.render(0f);
            }

        }
        if(livingRender!=null && stack!=null)
        {
            Field modelField = ReflectionHelper.findField(RendererLivingEntity.class, "mainModel", "field_77045_g");
            modelField.setAccessible(true);
            ModelBase modelBase=null;

            try
            {
                modelBase = (ModelBase) modelField.get(livingRender.renderer);
            } catch (IllegalAccessException e1)
            {
                e1.printStackTrace();
            }

            if(modelBase!=null&&modelBase instanceof ModelBiped)
            {
                ModelBiped biped = (ModelBiped) modelBase;
                biped.bipedLeftArm.isHidden = true;
            }

        }
    }
}
