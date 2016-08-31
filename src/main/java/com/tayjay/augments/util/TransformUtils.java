package com.tayjay.augments.util;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;

import javax.vecmath.Vector3f;

/**
 * Created by tayjay on 2016-08-28.
 */
public class TransformUtils
{
    private static final TRSRTransformation flipX = new TRSRTransformation(null, null, new Vector3f(-1, 1, 1), null);

    public static final SimpleModelState DEFAULT_BLOCK;
    public static final SimpleModelState DEFAULT_ITEM;
    public static final SimpleModelState DEFAULT_TOOL;

    static {
        TRSRTransformation thirdPerson = get(0, 2.5f, 0, 75, 45, 0, 0.375f);
        TRSRTransformation firstPerson;

        ImmutableMap.Builder<TransformType, TRSRTransformation> defaultBlockBuilder = ImmutableMap.builder();
        defaultBlockBuilder.put(TransformType.GUI, get(0, 0, 0, 30, 225, 0, 0.625f));
        defaultBlockBuilder.put(TransformType.GROUND, get(0, 3, 0, 0, 0, 0, 0.25f));
        defaultBlockBuilder.put(TransformType.FIXED, get(0, 0, 0, 0, 0, 0, 0.5f));
        defaultBlockBuilder.put(TransformType.THIRD_PERSON_RIGHT_HAND, thirdPerson);
        defaultBlockBuilder.put(TransformType.THIRD_PERSON_LEFT_HAND, leftify(thirdPerson));
        defaultBlockBuilder.put(TransformType.FIRST_PERSON_RIGHT_HAND, get(0, 0, 0, 0, 45, 0, 0.4f));
        defaultBlockBuilder.put(TransformType.FIRST_PERSON_LEFT_HAND, get(0, 0, 0, 0, 225, 0, 0.4f));
        DEFAULT_BLOCK = new SimpleModelState(defaultBlockBuilder.build());

        thirdPerson = get(0, 3, 1, 0, 0, 0, 0.55f);
        firstPerson = get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f);
        ImmutableMap.Builder<TransformType, TRSRTransformation> defaultItemBuilder = ImmutableMap.builder();
        defaultItemBuilder.put(TransformType.GROUND, get(0, 2, 0, 0, 0, 0, 0.5f));
        defaultItemBuilder.put(TransformType.HEAD, get(0, 13, 7, 0, 180, 0, 1));
        defaultItemBuilder.put(TransformType.THIRD_PERSON_RIGHT_HAND, thirdPerson);
        defaultItemBuilder.put(TransformType.THIRD_PERSON_LEFT_HAND, leftify(thirdPerson));
        defaultItemBuilder.put(TransformType.FIRST_PERSON_RIGHT_HAND, firstPerson);
        defaultItemBuilder.put(TransformType.FIRST_PERSON_LEFT_HAND, leftify(firstPerson));
        DEFAULT_ITEM = new SimpleModelState(defaultItemBuilder.build());

        ImmutableMap.Builder<TransformType, TRSRTransformation> defaultToolBuilder = ImmutableMap.builder();
        defaultToolBuilder.put(TransformType.THIRD_PERSON_RIGHT_HAND, get(0, 4, 0.5F, 0, -90, 55, 0.85F));
        defaultToolBuilder.put(TransformType.THIRD_PERSON_LEFT_HAND, get(0, 4, 0.5f, 0, 90, -55, 0.85f));
        defaultToolBuilder.put(TransformType.FIRST_PERSON_RIGHT_HAND, get(1.13f, 3.2f, 1.13f, 0, -90, 25, 0.68f));
        defaultToolBuilder.put(TransformType.FIRST_PERSON_LEFT_HAND, get(1.13f, 3.2f, 1.13f, 0, 90, -25, 0.68f));
        DEFAULT_TOOL = new SimpleModelState(defaultToolBuilder.build());
    }

    private static TRSRTransformation get(float tx, float ty, float tz, float ax, float ay, float az, float s) {
        return TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(tx / 16, ty / 16, tz / 16), TRSRTransformation.quatFromXYZDegrees(new Vector3f(ax, ay, az)), new Vector3f(s, s, s), null));
    }

    private static TRSRTransformation leftify(TRSRTransformation transform) {
        return TRSRTransformation.blockCenterToCorner(flipX.compose(TRSRTransformation.blockCornerToCenter(transform)).compose(flipX));
    }
}
