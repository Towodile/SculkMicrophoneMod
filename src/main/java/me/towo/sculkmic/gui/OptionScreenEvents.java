package me.towo.sculkmic.gui;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.gui.screen.MicrophoneSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button ;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OptionScreenEvents {
    @SubscribeEvent
    public static void onOptionsScreen(ScreenEvent.InitScreenEvent.Post e) {
        if (e.getScreen() instanceof OptionsScreen) {
            e.addListener(new Button(e.getScreen().width / 2 - 155, e.getScreen().height / 6 + 120 - 6 + 24, 150, 20, new TranslatableComponent("gui.microphone_settings"), (p_96335_) -> {
                Minecraft.getInstance().setScreen(new MicrophoneSettingsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options));
            }));
        }
    }
}
