package com.tayjay.augments.item;

import com.tayjay.augments.Augments;
import com.tayjay.augments.api.capabilities.IAugHolderProvider;
import com.tayjay.augments.api.item.IBodyPart;
import com.tayjay.augments.api.item.PartType;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.init.IItemModelProvider;
import com.tayjay.augments.util.CapHelper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by tayjay on 2016-06-24.
 */
public class ItemBodyPart extends ItemBase implements IBodyPart,IItemModelProvider
{
    protected int tier;
    protected PartType type;
    public ItemBodyPart(String name,int tier, PartType type)
    {
        super(name);
        this.tier = tier;
        this.type = type;
        setMaxStackSize(1);
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

    //TODO: Make this through capability or something else
    @Override
    public void setPartType(ItemStack stack, PartType type)
    {
        //Maybe not needed
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
        IAugHolderProvider augments = CapHelper.getAugHolderCap(stack);
        for(int i=0;i<augments.getSize();i++)
        {
            if(augments.getAugments().getStackInSlot(i)!=null)
                tooltip.add(augments.getAugments().getStackInSlot(i).getDisplayName());
        }

    }

    @Override
    public int getHolderSize(ItemStack stack)
    {
        return tier;//TODO: Temp implementation
    }

    @Override
    public IAugHolderProvider getProvider(ItemStack stack)
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
}
