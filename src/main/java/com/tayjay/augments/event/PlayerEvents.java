package com.tayjay.augments.event;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IPlayerDataProvider;
import com.tayjay.augments.api.capabilities.IPlayerBodyProvider;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.capability.PlayerDataImpl;
import com.tayjay.augments.capability.PlayerBodyImpl;
import com.tayjay.augments.client.renderer.CustomModel;
import com.tayjay.augments.inventory.SlotBodyPart;
import com.tayjay.augments.network.NetworkHandler;
import com.tayjay.augments.network.packets.PacketREQSyncParts;
import com.tayjay.augments.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by tayjay on 2016-06-24.<br/>
 * Events that are run on the player. Registering properties and such.
 */
public class PlayerEvents
{
    public static final int SYNC_TICKS = 20;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void playerSyncClient(TickEvent.PlayerTickEvent event)
    {
        if(!event.player.worldObj.isRemote)
            return;
        long time = (event.player.ticksExisted);
        if(time % SYNC_TICKS == 0)
        {
            NetworkHandler.sendToServer(new PacketREQSyncParts(event.player));
            /*if(CapHelper.getPlayerBodyCap(event.player).getStackByPart(PartType.TORSO)!=null)
                ReflectHelper.setCapeLocation((AbstractClientPlayer) event.player,new ResourceLocation("items/diamond.png"));
            else
            {
                //ReflectHelper.setCapeLocation((AbstractClientPlayer) event.player, null);
                ReflectHelper.resetTextures((AbstractClientPlayer) event.player);
            }*/
        }
    }

    ArrayList<String> hasCape = new ArrayList<String>();
    int frame = 0;
    int ticksSinceFrame = 0;
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void supporterCheck(TickEvent.PlayerTickEvent event)
    {
        //Augments.supporterList.add("Player689");
        if(event.player.worldObj.isRemote && event.player !=null /*&& !hasCape.contains(event.player.getDisplayNameString())*/ /*&& Augments.supporterList.contains(event.player.getDisplayNameString())*/)
        {
            ReflectHelper.setCapeLocation((AbstractClientPlayer) event.player,new ResourceLocation("augments","textures/models/capeAugmentsTest.png"));
            /*//hasCape.add(event.player.getDisplayNameString());
            //ticksSinceFrame++;
            if(((AbstractClientPlayer) event.player).worldObj.getTotalWorldTime()%5==0&& event.phase == TickEvent.Phase.START)
            {
                if(frame==0)
                {
                    ReflectHelper.setCapeLocation((AbstractClientPlayer) event.player,new ResourceLocation("augments","textures/models/capeAugmentsTest.png"));
                }
                else if(frame >0)
                {
                    ReflectHelper.setCapeLocation((AbstractClientPlayer) event.player,new ResourceLocation("augments","textures/models/capeTest3.png"));
                    frame = -1;
                }
                frame++;
                //ticksSinceFrame = 0;


            }*/
        }
    }


    @SubscribeEvent
    public void cloneEvent(PlayerEvent.Clone event)
    {

        NBTTagCompound data = CapHelper.getPlayerDataCap(event.getOriginal()).serializeNBT();
        NBTTagCompound parts = CapHelper.getPlayerBodyCap(event.getOriginal()).serializeNBT();
        CapHelper.getPlayerDataCap(event.getEntityPlayer()).deserializeNBT(data);
        CapHelper.getPlayerDataCap(event.getEntityPlayer()).reboot();
        CapHelper.getPlayerBodyCap(event.getEntityPlayer()).deserializeNBT(parts);

        LogHelper.debug("Cloned Body Parts for "+event.getEntityPlayer().getName());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(PlayerDataImpl.Provider.NAME,new PlayerDataImpl.Provider((EntityPlayer) event.getObject()));
            event.addCapability(PlayerBodyImpl.Provider.NAME,new PlayerBodyImpl.Provider((EntityPlayer)event.getObject()));
            LogHelper.debug("Player Capabilities added to player");
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if(!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = ((EntityPlayerMP) event.getEntity());
            IPlayerBodyProvider body = CapHelper.getPlayerBodyCap(player);
            IPlayerDataProvider data = CapHelper.getPlayerDataCap(player);
            body.sync((EntityPlayerMP)event.getEntity());
            data.sync((EntityPlayerMP)event.getEntity());
            LogHelper.info("Synced own Capabilities to client");
            //todo: Not including this yet. Hopefully "StartTracking" event will handle this.
            //NetworkHandler.INSTANCE.sendToAllAround(new PacketSyncPlayerBody(parts.serializeNBT(),player),new NetworkRegistry.TargetPoint(player.dimension,player.posX,player.posY,player.posZ,40));
        }

    }

    @SubscribeEvent
    public void startTracking(PlayerEvent.StartTracking event)
    {
        if(!event.getEntityPlayer().worldObj.isRemote && event.getTarget() instanceof EntityPlayerMP && event.getEntityPlayer() instanceof EntityPlayerMP)
        {
            CapHelper.getPlayerBodyCap(event.getEntityPlayer()).syncToOther((EntityPlayerMP) event.getTarget(), (EntityPlayerMP) event.getEntityPlayer());
            LogHelper.info("Synced Capabilities from "+event.getTarget().getName()+" to "+event.getEntityPlayer().getName());
        }
    }

    @SubscribeEvent
    public void onConstruct(EntityEvent.EntityConstructing event)
    {
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer))
        {
            //Clear any offline data
        }
    }

    int rotate = 0;
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void draw3dModelInGui(GuiScreenEvent.DrawScreenEvent.Post event)
    {
        if(!Augments.renderBodyPartInGUI)
            return;

        GuiScreen guiScreen = event.getGui();
        if(guiScreen instanceof GuiContainer)
        {
            GuiContainer guiinv = ((GuiContainer) guiScreen);
            if(guiinv.getSlotUnderMouse() !=null && guiinv.getSlotUnderMouse().getHasStack() && GuiContainer.isShiftKeyDown())
            {
                ItemStack stack = guiinv.getSlotUnderMouse().getStack();
                if(stack.getItem() instanceof IBodyPart)
                {
                    GlStateManager.enableColorMaterial();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((float)event.getMouseX(), (float)event.getMouseY(), 500.0F);
                    GlStateManager.scale((float)(-30), (float)30, (float)30);
                    GlStateManager.rotate(rotate++, 0.0F, 1.0F, 0.0F);
                    //GlStateManager.rotate(20,1,0,1);
                    GlStateManager.enableAlpha();
                    GL11.glColor4d(1,1,1,0.4);
                    renderBodyPart(stack,((IBodyPart) stack.getItem()).getPartType(stack),new RenderPlayer(Minecraft.getMinecraft().getRenderManager()));
                    GlStateManager.popMatrix();
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.disableRescaleNormal();
                    GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
                    GlStateManager.disableTexture2D();
                    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
                }

            }
        }
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color.
     */
    public static void drawRect(int left, int top, int right, int bottom, double zLevel, int color)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos((double)left, (double)bottom, zLevel).endVertex();
        vertexbuffer.pos((double)right, (double)bottom, zLevel).endVertex();
        vertexbuffer.pos((double)right, (double)top, zLevel).endVertex();
        vertexbuffer.pos((double)left, (double)top, zLevel).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    static int rotation = 0;


    public void renderBodyPart(ItemStack stack,PartType type,RenderPlayer renderPlayer)
    {
        ModelRenderer model;
        ModelPlayer modelSteve = renderPlayer.getMainModel();
        boolean smallArms = RenderUtil.hasSmallArms(modelSteve); //Todo: Utilize this and the alternate arm width
        //Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("augments", "textures/models/capeAugmentsTest.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
        GlStateManager.pushMatrix();
        GL11.glPushMatrix();
        switch (type)
        {
            case HEAD:
                GlStateManager.translate(0,0.5,0);
                model = modelSteve.bipedHead;
                break;
            case EYES:
                Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
                GlStateManager.translate(0,0.5,0);
                rotate = 180;
                model = modelSteve.bipedHead;
                //System.out.println(ItemNBTHelper.getFloat(stack,"offsetY",0));
                break;
            case TORSO:

                model = modelSteve.bipedBody;
                break;
            case ARM:
                ItemStack leftArm = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getStackByPartSided(PartType.ARM,0);
                if(leftArm!=null && leftArm.equals(stack))//If this stack is the one in the left arm slot
                {

                    model = modelSteve.bipedLeftArm;
                }
                else//This stack is in the right arm slot by elimination
                {

                    model = modelSteve.bipedRightArm;
                }
                break;
            case LEG:
                ItemStack leftLeg = CapHelper.getPlayerBodyCap(Minecraft.getMinecraft().thePlayer).getStackByPartSided(PartType.LEG,0);
                if(leftLeg !=null && leftLeg.equals(stack))
                {

                    model = modelSteve.bipedLeftLeg;
                }
                else
                {

                    model = modelSteve.bipedRightLeg;
                }
                break;
            default:

                model = modelSteve.bipedHeadwear;
                break;
        }

        /*if(CapHelper.getPlayerBodyCap(playerIn).getStackByPart(PartType.HEAD)!=null)
            renderPlayer.getMainModel().bipedRightArm = new ModelSkeleton().bipedRightArm;*/


        //GL11.glPushMatrix();


        GlStateManager.scale(1.1,1.1,1.1);
        //GlStateManager.enableAlpha();
        //GlStateManager.color(0,0,0,0.2f);

        //Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().thePlayer.getLocationSkin());
        alignModels(renderPlayer.getMainModel().bipedBody,model,false);
        model.render(0.0625f);
        //GlStateManager.disableAlpha();
        //GlStateManager.color(1,1,1,0.2f);
        Minecraft.getMinecraft().renderEngine.bindTexture(((IBodyPart)stack.getItem()).getTexture(stack,false));
        if(type==PartType.EYES)
        {
            model = new ModelRenderer(modelSteve,0, (int) ItemNBTHelper.getFloat(stack,"offsetY",0));
            model.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25f);
            alignModels(renderPlayer.getMainModel().bipedBody,model,false);
        }
        model.render(0.0625f);
        //LayerAugments.renderEnchantedGlint(renderPlayer,playerIn, model);
        GL11.glPopMatrix();
        GlStateManager.popMatrix();
        //GL11.glPopMatrix();
    }

    protected static void alignModels(ModelRenderer original, ModelRenderer moving, boolean isSneaking)
    {
        moving.rotateAngleX =   original.rotateAngleX;
        moving.rotateAngleY =   original.rotateAngleY;
        moving.rotateAngleZ =   original.rotateAngleZ;
        moving.offsetX =        original.offsetX;
        moving.offsetY =        isSneaking? original.offsetY+0.2f : original.offsetY;
        moving.offsetZ =        original.offsetZ;
        moving.rotationPointX = original.rotationPointX;
        moving.rotationPointY = original.rotationPointY;
        moving.rotationPointZ = original.rotationPointZ;
        moving.isHidden = original.isHidden;
        moving.mirror = original.mirror;
    }


    /*
    @SubscribeEvent
    public void renderHand(RenderHandEvent event)
    {
        EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;
        ItemStack rightArm = CapHelper.getPlayerBodyCap(clientPlayer).getStackByPart(PartType.ARM_RIGHT);

        if(rightArm!=null)
        {
            GL11.glColor4d(1,1,1,1);
            if(clientPlayer.isSneaking())
                GL11.glTranslated(0,-0.2,0);
            RenderPlayer renderPlayer = ReflectionHelper.getPrivateValue(RenderManager.class,Minecraft.getMinecraft().getRenderManager(),"playerRenderer");
            ((IBodyPart) rightArm.getItem()).renderOnPlayer(rightArm,clientPlayer, renderPlayer);
        }
    }
    */

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event)
    {
        for(ModelResourceLocation loc : event.getModelRegistry().getKeys())
        {
            if(loc.getVariant().equals("custom"))
            {

                //TextureAtlasSprite base = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/slime");
                //TextureAtlasSprite overlay = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/redstone_block");
                //IBakedModel customModel = new CustomModel(base, overlay);
/*
                event.getModelRegistry().putObject(loc,event.getModelManager().getModel(new ModelResourceLocation("redstone_block#normal")));
*/
            }
        }
    }

    /*@SubscribeEvent
    public void renderWorldLast(RenderWorldLastEvent event)
    {
        if(Minecraft.getMinecraft().thePlayer!=null)
        {
            GlStateManager.pushAttrib();
            GlStateManager.pushMatrix();
                //See through walls
                GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);


            GL11.glEnable(GL11.GL_BLEND);
            GL11.glLineWidth(3.0f);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_CULL_FACE);

            BlockPos block = new BlockPos(117,73,266);
            *//*
            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
             *//*
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            double x = block.getX()-(player.lastTickPosX + (player.posX-player.lastTickPosX)*event.getPartialTicks());
            double y = block.getY()-(player.lastTickPosY + (player.posY-player.lastTickPosY)*event.getPartialTicks());
            double z = block.getZ()-(player.lastTickPosZ + (player.posZ-player.lastTickPosZ)*event.getPartialTicks());

            RenderGlobal.drawBoundingBox(x,y,z,x+1,y+1,z+1,1,1,1,1);

            RenderGlobal.renderFilledBox(x,y,z,x+1,y+1,z+1,0,0,1,0.5f);

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);

            GlStateManager.popMatrix();
            GlStateManager.popAttrib();
        }
    }*/

}
