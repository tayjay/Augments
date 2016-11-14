package com.tayjay.augments.client.renderer;

import com.google.common.primitives.Ints;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tayjay on 2016-10-30.
 */
public class CustomModel implements IBakedModel
{
    private final TextureAtlasSprite base, overlay;
    private boolean hasStateSet = false;
    private final IExtendedBlockState state;

    private static int cubeSize = 2;
    public static final IUnlistedProperty<Integer>[] properties = new IUnlistedProperty[6];

    static
    {
        for(EnumFacing f : EnumFacing.values())
        {
            properties[f.ordinal()] = Properties.toUnlisted(PropertyInteger.create(f.getName(), 0, (1 << (cubeSize * cubeSize)) - 1));
        }
    }

    public CustomModel(TextureAtlasSprite base, TextureAtlasSprite overlay)
    {
        this(base, overlay, null);
    }

    public CustomModel(TextureAtlasSprite base, TextureAtlasSprite overlay, IExtendedBlockState state)
    {
        this.base = base;
        this.overlay = overlay;
        this.state = state;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
    {
        int cubeSize = 1;
        int len = cubeSize * 5 + 1;
        List<BakedQuad> ret = new ArrayList<BakedQuad>();
        for(EnumFacing f : EnumFacing.values())
        {
            ret.add(createSidedBakedQuad(0, 1, 0, 1, 1, base, f));
            for(int i = 0; i < cubeSize; i++)
            {
                for(int j = 0; j < cubeSize; j++)
                {
                    if(state != null)
                    {
                        Integer value = state.getValue((IProperty<Integer>) properties[f.ordinal()]);
                        if(value != null && (value & (1 << (i * cubeSize + j))) != 0)
                        {
                            ret.add(createSidedBakedQuad((float)(1 + i * 5) / len, (float)(5 + i * 5) / len, (float)(1 + j * 5) / len, (float)(5 + j * 5) / len, 1.0001f, overlay, f));
                        }
                    }
                }
            }
        }
        return ret;
    }

    private int[] vertexToInts(float x, float y, float z, int color, TextureAtlasSprite texture, float u, float v)
    {
        return new int[]{
                Float.floatToRawIntBits(x),
                Float.floatToRawIntBits(y),
                Float.floatToRawIntBits(z),
                color,
                Float.floatToRawIntBits(texture.getInterpolatedU(u)),
                Float.floatToRawIntBits(texture.getInterpolatedV(v)),
                0
        };
    }

    private BakedQuad createSidedBakedQuad(float x1, float x2, float z1, float z2, float y, TextureAtlasSprite texture, EnumFacing side)
    {
        Vec3d v1 = rotate(new Vec3d(x1 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
        Vec3d v2 = rotate(new Vec3d(x1 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
        Vec3d v3 = rotate(new Vec3d(x2 - .5, y - .5, z2 - .5), side).addVector(.5, .5, .5);
        Vec3d v4 = rotate(new Vec3d(x2 - .5, y - .5, z1 - .5), side).addVector(.5, .5, .5);
        return new BakedQuad(Ints.concat(
                vertexToInts((float) v1.xCoord, (float) v1.yCoord, (float) v1.zCoord, -1, texture, 0, 0),
                vertexToInts((float) v2.xCoord, (float) v2.yCoord, (float) v2.zCoord, -1, texture, 0, 16),
                vertexToInts((float) v3.xCoord, (float) v3.yCoord, (float) v3.zCoord, -1, texture, 16, 16),
                vertexToInts((float) v4.xCoord, (float) v4.yCoord, (float) v4.zCoord, -1, texture, 16, 0)
        ), -1, side,texture);
    }

    @Override
    public boolean isGui3d()
    {
        return true;
    }

    @Override
    public boolean isAmbientOcclusion()
    {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer()
    {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture()
    {
        return this.base;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms()
    {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides()
    {
        return null;
    }

    private static Vec3d rotate(Vec3d vec, EnumFacing side)
    {
        switch(side)
        {
            case DOWN:  return new Vec3d( vec.xCoord, -vec.yCoord, -vec.zCoord);
            case UP:    return new Vec3d( vec.xCoord,  vec.yCoord,  vec.zCoord);
            case NORTH: return new Vec3d( vec.xCoord,  vec.zCoord, -vec.yCoord);
            case SOUTH: return new Vec3d( vec.xCoord, -vec.zCoord,  vec.yCoord);
            case WEST:  return new Vec3d(-vec.yCoord,  vec.xCoord,  vec.zCoord);
            case EAST:  return new Vec3d( vec.yCoord, -vec.xCoord,  vec.zCoord);
        }
        return null;
    }

}
