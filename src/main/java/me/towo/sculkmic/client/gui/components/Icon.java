package me.towo.sculkmic.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Icon {
    private final ResourceLocation texture;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private boolean visible;

    public Icon(ResourceLocation resource, int width, int height) {
        this.texture = resource;
        this.width = width;
        this.height = height;
        this.x = 0;
        this.y = 0;
        this.visible = false;
    }
    public void create() {
        IconList.add(this);
    }

    public void show() {
        this.visible = true;
    }

    public void hide() {
        this.visible = false;
    }

    public void kill() {
        IconList.remove(this);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Position position) {
        Position.XYValue xy = position.getValues(this);
        this.x = xy.x;
        this.y =  xy.y;
    }

    @SubscribeEvent
    static void draw(RenderGuiOverlayEvent.Pre e) {
        if (Minecraft.getInstance().level == null)
            return;

        for (Icon icon : IconList.get()) {
            if (!icon.visible)
                continue;

            PoseStack matrix = e.getPoseStack();
            RenderSystem.setShaderTexture(0, icon.texture);
            int x = icon.x;
            int y = icon.y;
            int width = icon.width;
            int height = icon.height;
            Gui.blit(matrix, x, y, 0, 0, width, height, width, height);
        }
    }
    private static class IconList {
        private static final ArrayList<Icon> list = new ArrayList<>();

        static void add(Icon icon) {
            list.add(icon);
        }

        static void remove(Icon icon) {
            list.remove(icon);
        }

        static Icon[] get() {
            return list.toArray(new Icon[0]);
        }
    }

    public interface Position {
        int margin = 20;
        private static int screenWidth() { return Minecraft.getInstance().getWindow().getGuiScaledWidth(); }
        private static int screenHeight() { return Minecraft.getInstance().getWindow().getGuiScaledHeight(); }

        Position TOP_LEFT = (icon) -> new XYValue(margin, margin);
        Position MIDDLE_LEFT = (icon) -> new XYValue(margin, (screenHeight()/2) - (icon.height/2));
        Position BOTTOM_LEFT = (icon) -> new XYValue(margin, screenHeight() - margin - icon.height);
        Position TOP_RIGHT = (icon) -> new XYValue(screenWidth() - margin - icon.width, margin);
        Position MIDDLE_RIGHT = (icon) -> new XYValue(screenWidth() - margin - icon.width, screenHeight()/2 - (icon.height/2));
        Position BOTTOM_RIGHT = (icon) -> new XYValue(screenWidth() - margin - icon.width, screenHeight() - margin - icon.height);
        Position TOP_CENTER = (icon) -> new XYValue(screenWidth()/2 - (icon.width/2), margin);

        XYValue getValues(Icon icon);

        record XYValue(int x, int y) { }
    }
}
