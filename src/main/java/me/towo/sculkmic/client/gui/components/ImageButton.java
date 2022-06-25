package me.towo.sculkmic.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ImageButton extends Button {
    private final ResourceLocation image;
    private final @Nullable ResourceLocation hoveredImage;
    private final double offsetX;
    private final double offsetY;

    public ImageButton(ResourceLocation normalImage, @Nullable ResourceLocation hoveredImage, int x, int y, int size, OnPress onPress, double offsetX, double offsetY) {
        super(x, y, size, size, Component.empty(), onPress);
        this.image = normalImage;
        this.hoveredImage = hoveredImage;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public ImageButton(ResourceLocation normalImage, @Nullable ResourceLocation hoveredImage, int x, int y, int size, OnPress onPress) {
        this(normalImage, hoveredImage, x, y, size, onPress, 0, 0);
    }

    @Override
    public void render(PoseStack matrix, int p_93658_, int p_93659_, float p_93660_) {
        super.render(matrix, p_93658_, p_93659_, p_93660_);

        ResourceLocation img;
        if (isHoveredOrFocused() && hoveredImage != null)
            img = hoveredImage;
        else
            img = image;

        RenderSystem.setShaderTexture(0, img);
        Minecraft.getInstance().textureManager.bindForSetup(img);
        int iWidth = (int)(width / 1.5);
        int iHeight = (int)(height / 1.5);

        Gui.blit(matrix, (int) ((width - iWidth)/2 + offsetX + x), (int) ((height - iHeight)/2 + offsetY + y), 0, 0, iWidth, iHeight, iWidth, iHeight);
    }
}
