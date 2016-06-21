package com.tayjay.augments.augment.handlers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by tayjay on 2016-06-20.<br/>
 * Static access to models of augments/body parts
 */
public class AugmentModels
{
    public static final ModelBiped MODEL_BIPED = new ModelBiped();
    public static final ModelRenderer TORSO = MODEL_BIPED.bipedBody;
    public static final ModelRenderer HEAD = MODEL_BIPED.bipedHead;
    public static final ModelRenderer ARM_LEFT = MODEL_BIPED.bipedLeftArm;
    public static final ModelRenderer ARM_RIGHT = MODEL_BIPED.bipedRightArm;
    public static final ModelRenderer LEG_LEFT = MODEL_BIPED.bipedLeftLeg;
    public static final ModelRenderer LEG_RIGHT = MODEL_BIPED.bipedRightLeg;
}
