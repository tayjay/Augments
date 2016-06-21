package com.tayjay.augments.item.BodyParts;

import com.tayjay.augments.AugmentsMod;
import com.tayjay.augments.augment.interfaces.IBodyPart;
import com.tayjay.augments.creativetab.CreativeTabA;
import com.tayjay.augments.handler.GuiHandler;
import com.tayjay.augments.lib.Tags;
import com.tayjay.augments.util.NBTHelper;
import com.tayjay.augments.util.RenderUtil;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.util.Constants;

/**
 * Created by tayjay on 2016-05-27.
 */
public abstract class ItemBodyPart extends Item implements IBodyPart
{
    protected int augmentCapacity;
    protected float maxEnergy;
    protected byte type;
    protected byte tier;
    protected ModelRenderer model;
    protected ResourceLocation texture;

    public ItemBodyPart(Type type,int tier, int augmentCapacity)
    {
        this.type = (byte)type.ordinal();
        this.tier = (byte)tier;
        this.augmentCapacity = augmentCapacity;
        setCreativeTab(CreativeTabA.AUGMENT_TAB);
    }

    public ItemBodyPart setModel(ModelRenderer model)
    {
        this.model = model;
        return this;
    }

    public ItemBodyPart setModelTexture(ResourceLocation texture)
    {
        this.texture = texture;
        return this;
    }

    public ItemBodyPart setTextureName(String name)
    {
        setModelTexture(new ResourceLocation("augments","textures/model/"+name+".png"));
        return this;
    }

    @Override
    public boolean addAugment(ItemStack bodyPart, ItemStack augment)
    {
        return false;
    }

    @Override
    public boolean removeAugment(ItemStack bodyPart, ItemStack augment)
    {
        return false;
    }

    @Override
    public int augmentCapacity()
    {
        return augmentCapacity;
    }

    @Override
    public int getType()
    {
        return type;
    }

    @Override
    public float getEnergy()
    {
        return maxEnergy;
    }

    @Override
    public void render(ItemStack stack, RenderPlayerEvent event)
    {
        if(!event.entity.isInvisible())
            RenderUtil.renderModelOnPlayer(event,this.model,this.texture,this.type);
    }

    public String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }


    @Override
    public ModelRenderer getModel()
    {
        return model;
    }

    @Override
    public boolean canAdd(ItemStack bodyStack, EntityLivingBase entity)
    {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(!world.isRemote)
            player.openGui(AugmentsMod.instance, GuiHandler.GuiIDs.INVENTORY_BODY_PART.ordinal(),world,0,0,0);
        return super.onItemRightClick(itemStack, world, player);
    }
}
