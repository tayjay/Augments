package com.tayjay.augments.augment;

import com.tayjay.augments.augment.interfaces.IAddon;
import com.tayjay.augments.augment.interfaces.IAugment;
import com.tayjay.augments.augment.interfaces.IComponent;
import com.tayjay.augments.init.AugmentRegistry;
import com.tayjay.augments.item.ItemAugment;
import com.tayjay.augments.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by tayjm_000 on 2016-04-03.
 * Store information about an augment.<br>
 * This is a holder that can be used in conjunction with NBT on an itemstack to store data.<br>
 */
public class AugData
{
    public String augName;
    public IComponent[] components;
    public IAddon[] addons;

    public AugData(String nameIn)
    {
        this(nameIn,new IComponent[5],new IAddon[5]);
    }

    public AugData(String nameIn,IComponent[] compsIn, IAddon[] addonsIn)
    {
        augName = nameIn;
        components = compsIn;
        addons = addonsIn;
    }

    public void addComponents(IComponent com)
    {

    }

    public void writeToStack(ItemStack stack)
    {
        if(!(stack.getItem() instanceof IAugment))
            return;
        NBTTagCompound augData = new NBTTagCompound();
        NBTTagCompound componentsTag = new NBTTagCompound();
        NBTTagCompound addonsTag = new NBTTagCompound();
        NBTTagCompound[] comDatas = new NBTTagCompound[5];
        int comCount = 0;
        NBTTagCompound[] addonDatas = new NBTTagCompound[5];
        int addonCount = 0;

        augData.setString("augName",augName);
        //Write component tags to the array
        for(int i = 0; i<components.length;i++)
        {
            if(components[i]!=null)
            {
                comDatas[i] = components[i].writeToNBT(stack);
            }
            else
            {
                comCount = i;
            }
        }
        //Write addon tags to the array
        for(int i = 0;i<addons.length;i++)
        {
            if(addons[i]!=null)
            {
                addonDatas[i] = addons[i].writeToNBT(stack);
            }
            else
            {
                addonCount=i;
            }
        }

        //Populate componentsTag
        componentsTag.setInteger("count", comCount);
        for(int j = 0; j<comCount;j++)
        {
            componentsTag.setTag("component"+j,comDatas[j]);
        }
        augData.setTag("components",componentsTag);
        //Populate addonsTag
        addonsTag.setInteger("count", addonCount);
        for(int j = 0; j<addonCount;j++)
        {
            addonsTag.setTag("addon"+j,addonDatas[j]);
        }
        augData.setTag("addons",addonsTag);

        //Write final tag
        NBTHelper.setTagCompound(stack,"AugData",augData);
    }

    public static AugData readFromStack(ItemStack stack)
    {
        if(!(stack.getItem() instanceof ItemAugment))
            return null;
        IComponent[] componentsArray = new IComponent[5];
        IAddon[] addonsArray = new IAddon[5];
        NBTTagCompound tag = NBTHelper.getTagCompound(stack,"AugData");
        NBTTagCompound componentsTag = tag.getCompoundTag("components");
        NBTTagCompound addonsTag = tag.getCompoundTag("addons");
        int compCount = componentsTag.getInteger("count");
        int addonsCount = addonsTag.getInteger("count");

        for(int i=0;i<compCount;i++)
        {
            NBTTagCompound current = componentsTag.getCompoundTag("component"+i);
            componentsArray[i] = AugmentRegistry.Component.getComponent(current.getString("name"));
        }

        for(int j=0;j<addonsCount;j++)
        {
            NBTTagCompound current = addonsTag.getCompoundTag("addon"+j);
            addonsArray[j] = AugmentRegistry.Addon.getAddon(current.getString("name"));
        }

        return new AugData(tag.getString("augName"),componentsArray,addonsArray);
    }
}
