package me.towo.sculkmic.client.gui.components;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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

    public void setPosition(Position.PositionType positionType) {
        Position pos = Position.getForIcon(this, positionType);
        if (pos == null)
            return;

        this.x = pos.x;
        this.y = pos.y;
    }

    @SubscribeEvent
    static void draw(RenderGameOverlayEvent.Pre e) {
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

    public static class Position {
        public enum PositionType {
            TOP_LEFT,
            MIDDLE_LEFT,
            BOTTOM_LEFT,
            TOP_RIGHT,
            MIDDLE_RIGHT,
            BOTTOM_RIGHT,
            TOP_CENTER
        }

        private static final int margin = 20;
        public final int x;
        public final int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Position getForIcon(Icon icon, PositionType positionType) {
            Window window = Minecraft.getInstance().getWindow();
            int screenWidth = window.getGuiScaledWidth();
            int screenHeight = window.getGuiScaledHeight();
            if (positionType == PositionType.TOP_LEFT) return new Icon.Position(margin, margin);
            if (positionType == PositionType.MIDDLE_LEFT) return new Icon.Position(margin,(screenHeight/2) - (icon.height/2));
            if (positionType == PositionType.BOTTOM_LEFT) return new Icon.Position(margin,screenHeight - margin - icon.height);
            if (positionType == PositionType.TOP_RIGHT) return new Icon.Position(screenWidth - margin - icon.width, margin);
            if (positionType == PositionType.MIDDLE_RIGHT) return new Icon.Position(screenWidth - margin- icon.width,screenHeight/2- (icon.height/2));
            if (positionType == PositionType.BOTTOM_RIGHT) return new Icon.Position(screenWidth - margin- icon.width,screenHeight - margin - icon.height);
            if (positionType == PositionType.TOP_CENTER) return new Icon.Position(screenWidth/2- (icon.width/2), margin);
            return null;
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
}
