package me.towo.sculkmic.client.gui;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.gui.components.ImageButton;
import me.towo.sculkmic.client.gui.screen.MicrophoneSettingsScreen;
import me.towo.sculkmic.common.init.GlobalEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OptionScreenExtensions {

    @SubscribeEvent
    public static void init(ScreenEvent.InitScreenEvent.Post e) {
        if (e.getScreen() instanceof OptionsScreen) {

            Button musicSoundsBtn = null;
            for (GuiEventListener listener : e.getListenersList()) {
                if (listener instanceof Button) {
                    if(((Button) listener).getMessage().equals(Component.translatable("options.sounds"))){
                        musicSoundsBtn = (Button) listener;
                        break;
                    }
                }
            }

            assert musicSoundsBtn != null;
            int btnSize = musicSoundsBtn.getHeight();
            musicSoundsBtn.setWidth(musicSoundsBtn.getWidth() - btnSize);
            ResourceLocation icon = new ResourceLocation(SculkMicMod.ID, "textures/gui/settings.png");
            ResourceLocation hovered = new ResourceLocation(SculkMicMod.ID, "textures/gui/settings_hover.png");
            ImageButton btn = new ImageButton(icon, hovered,
                    musicSoundsBtn.x + musicSoundsBtn.getWidth(), musicSoundsBtn.y, btnSize,
                    (button) -> Minecraft.getInstance().setScreen(new MicrophoneSettingsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options, GlobalEventHandler.MICROPHONE_LISTENER.getHandler())));

            e.addListener(btn);
        }
    }
}
