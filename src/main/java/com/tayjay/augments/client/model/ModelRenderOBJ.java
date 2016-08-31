package com.tayjay.augments.client.model;

import com.google.common.base.Function;
import com.tayjay.augments.util.LogHelper;
import com.tayjay.augments.util.ModelUtils;
import com.tayjay.augments.util.TransformUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJLoader;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

/**
 * Created by tayjay on 2016-08-28.
 * Source: DraconicEvolution
 */
public class ModelRenderOBJ extends ModelRenderer
{
    private ResourceLocation customModel;
    public ResourceLocation texture;
    private int displayList;
    private boolean compiled = false;
    private IBakedModel objModel;
    public float scale = 0;

    private static Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter = new Function<ResourceLocation, TextureAtlasSprite>()
    {
        @Nullable
        @Override
        public TextureAtlasSprite apply(@Nullable ResourceLocation input)
        {
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(input.toString());
        }
    };

    public ModelRenderOBJ(ModelBase baseModel, ResourceLocation customModel, ResourceLocation texture)
    {
        super(baseModel);
        this.customModel = customModel;
        try
        {
            objModel = OBJLoader.INSTANCE.loadModel(customModel).bake(TransformUtils.DEFAULT_TOOL, DefaultVertexFormats.ITEM, bakedTextureGetter);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        this.texture = texture;
    }

    @Override
    public void render(float scale)
    {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(scale);
            }

            GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);

            if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                    GlStateManager.callList(this.displayList);
                } else {
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                    GlStateManager.callList(this.displayList);
                    GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
                }
            } else {
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                if (this.rotateAngleZ != 0.0F) {
                    GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
                }

                if (this.rotateAngleY != 0.0F) {
                    GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
                }

                if (this.rotateAngleX != 0.0F) {
                    GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
                }

                GlStateManager.callList(this.displayList);
                GlStateManager.popMatrix();
            }

            GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
        }
    }

    private void compileDisplayList(float scale)
    {
        if (this.scale == 0)
        {
            this.scale = scale;
        }

        if (objModel == null)
        {
            compiled = true;
            LogHelper.error("Broken Model!");
            return;
        }

        scale = this.scale;
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, GL11.GL_COMPILE);

        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(180, -1, 0, 1);

        GlStateManager.bindTexture(Minecraft.getMinecraft().getTextureMapBlocks().getGlTextureId());
        ModelUtils.renderQuads(objModel.getQuads(null, null, 0));

        GlStateManager.popMatrix();

        GlStateManager.glEndList();
        this.compiled = true;
    }

    @Override
    public void renderWithRotation(float scale)
    {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(scale);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
            if (this.rotateAngleY != 0.0F) {
                GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
                GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            if (this.rotateAngleZ != 0.0F) {
                GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.callList(this.displayList);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void postRender(float scale)
    {
        if (!this.isHidden && this.showModel) {

            if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                }
            } else {
                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                if (this.rotateAngleZ != 0.0F) {
                    GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
                }

                if (this.rotateAngleY != 0.0F) {
                    GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
                }

                if (this.rotateAngleX != 0.0F) {
                    GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
                }
            }
        }
    }
}
