package com.tayjay.augments.item;

import com.tayjay.augments.api.events.IHUDProvider;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.util.CapHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.opengl.GL11;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemCyborgEyes extends ItemBodyPart implements IHUDProvider
{
    public ItemCyborgEyes(String name,int tier)
    {
        super(name,tier, PartType.EYES);
        this.setMaxStackSize(1);
    }

    ModelBiped model = new ModelBiped();

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments", "textures/models/eyesBiped.png"));

        alignModels(renderPlayer.getMainModel().bipedHead,model.bipedHead,playerIn.isSneaking());
        //GL11.glScalef(1.04f, 1.04f, 1.04f);
        model.bipedHead.render(0.0625f);
        GL11.glPopMatrix();
    }



    @Override
    public void drawWorldElements(ItemStack stack, RenderWorldLastEvent event)
    {

    }

    @Override
    public void drawHudElements(ItemStack stack, RenderGameOverlayEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if(event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS)
        {
            IItemHandler parts = CapHelper.getPlayerPartsCap(mc.thePlayer).getPartsInv();
            String rendering = "";
            for(int i = 0 ; i<parts.getSlots();i++)
            {
                if(parts.getStackInSlot(i)== null)
                    rendering = "null";
                else
                    rendering = parts.getStackInSlot(i).getDisplayName();
                mc.fontRendererObj.drawString(rendering,10,12*i,0);
            }
        }
    }
}
