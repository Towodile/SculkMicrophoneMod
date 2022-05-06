package me.towo.sculkmic.ui;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.config.SculkMicConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.swing.event.MenuEvent;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModSettings {
    @SubscribeEvent
    public static void onPauseMenu(ScreenEvent.InitScreenEvent.Post e) {
        if (e.getScreen() instanceof PauseScreen) {
            Button button = new Button(43, 43, Minecraft.getInstance().screen.width / 4,
                    Minecraft.getInstance().screen.height / 4, new TextComponent("Microphone Options"), btn -> {
                SculkMicConfig.editIfInfoOnScreen(!SculkMicConfig.SHOW_INFO_ON_SCREEN.get());
            });
            e.addListener(button);
        }
    }

}
