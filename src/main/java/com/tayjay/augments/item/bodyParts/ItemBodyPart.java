package com.tayjay.augments.item.bodyParts;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;import com.tayjay.augments.api.item.IAugment;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.IItemModelProvider;
import com.tayjay.augments.item.ItemBase;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemBodyPart extends ItemBase implements IBodyPart,IItemModelProvider
{
    protected int tier;
    protected PartType type;
    protected int armourValue;
    protected int storageSize;
    public ResourceLocation textureSteve = new ResourceLocation("augments", "textures/models/steve.png");
    public ResourceLocation textureAlex = new ResourceLocation("augments", "textures/models/alex.png");
    ModelPlayer modelSteve = new ModelPlayer(0f,false);
    ModelPlayer modelAlex = new ModelPlayer(0f,true);
    public ItemBodyPart(String name,int tier,int armourValue, int storageSize,String textureSteve, PartType type)
    {
        this(name,tier,armourValue,storageSize,textureSteve,textureSteve+"_slim",type);
    }

    public ItemBodyPart(String name,int tier,int armourValue, int storageSize, String texture,String textureS, PartType type)
    {
        super(name);
        this.tier = tier;
        this.type = type;
        this.armourValue = armourValue;
        this.storageSize = storageSize;
        textureSteve = new ResourceLocation("augments","textures/models/"+texture+".png");
        textureAlex = new ResourceLocation("augments","textures/models/"+textureS+".png");
        setMaxStackSize(1);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack,boolean hasSmallArms)
    {
        return hasSmallArms ? textureAlex : textureSteve;
    }

    @Override
    public void tickAllAugments(ItemStack bodyPartStack, TickEvent.PlayerTickEvent event)
    {
        IItemHandler augments = CapHelper.getAugHolderCap(bodyPartStack).getAugments();
        for (int j = 0; j < augments.getSlots(); j++)
        {
            if (augments.getStackInSlot(j) != null)
            {
                ((IAugment) augments.getStackInSlot(j).getItem()).tickAugment(augments.getStackInSlot(j), bodyPartStack, event);
            }
        }
    }

    @Override
    public boolean hasAugment(ItemStack bodyPartStack, IAugment augment)
    {
        IItemHandler augments = CapHelper.getAugHolderCap(bodyPartStack).getAugments();
        for (int j = 0; j < augments.getSlots(); j++)
        {
            if (augments.getStackInSlot(j) != null && augments.getStackInSlot(j).getItem() == augment)
            {
                return true;
            }
        }
        return false;
    }

    public void setTextureName(String name)
    {
        textureSteve = new ResourceLocation("augments","textures/models/"+name+".png");
        textureAlex = new ResourceLocation("augments","textures/models/"+name+"_slim.png");
    }

    @Override
    public int getTier(ItemStack stack)
    {
        return tier;
    }

    @Override
    public void renderOnPlayer(ItemStack stack, EntityPlayer playerIn, RenderPlayer renderPlayer)
    {

    }

    @Override
    public PartType getPartType(ItemStack stack)
    {
        return type;
    }

    @Override
    public int getArmourValue(ItemStack stack)
    {
        return armourValue;
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

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add("Tier: "+this.tier);
        tooltip.add("PartType: "+this.getPartType(stack));
        tooltip.add(ChatFormatting.BOLD+"Hold Shift for Augments");
        if(GuiScreen.isShiftKeyDown())
        {
            tooltip.remove(tooltip.size()-1);
            tooltip.add(ChatFormatting.GOLD+"Augments:");
            IAugHolderProvider augments = CapHelper.getAugHolderCap(stack);
            for (int i = 0; i < augments.getSize(); i++)
            {
                if (augments.getAugments().getStackInSlot(i) != null)
                    tooltip.add(augments.getAugments().getStackInSlot(i).getDisplayName());
            }
        }

    }

    @Override
    public int getHolderSize(ItemStack stack)
    {
        return storageSize;//TODO: Temp implementation
    }

    @Override
    public IAugHolderProvider getAugmentHolder(ItemStack stack)
    {
        return CapHelper.getAugHolderCap(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(!worldIn.isRemote)
        {
            playerIn.openGui(Augments.instance, GuiHandler.GuiIDs.BODY_PART_AUGS.ordinal(),worldIn,0,0,0);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void registerItemModel(Item item)
    {
        super.registerItemModel(item);
    }
}
