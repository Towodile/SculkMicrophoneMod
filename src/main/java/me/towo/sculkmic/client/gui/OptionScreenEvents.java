package me.towo.sculkmic.client.gui;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.gui.screen.MicrophoneSettingsScreen;
import me.towo.sculkmic.common.compatibility.VoiceChatCompatibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button ;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OptionScreenEvents {
    private static Button btn;
    private static boolean showTooltip;
    @SubscribeEvent
    public static void onOptionsScreen(ScreenEvent.InitScreenEvent.Post e) {
        btn = new Button(e.getScreen().width / 2 - 155, e.getScreen().height / 6 + 120 - 6 + 24, 150, 20, new TranslatableComponent("gui.microphone_settings"), (p_96335_) -> {
        Minecraft.getInstance().setScreen(new MicrophoneSettingsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options));
    });

        if (e.getScreen() instanceof OptionsScreen) {
            e.addListener(btn);
            if (VoiceChatCompatibility.present) {
                btn.active = false;
            }
        }
    }
    @SubscribeEvent
    public static void renderToolTip(ScreenEvent.DrawScreenEvent e) {
        if (e.getScreen() instanceof  OptionsScreen) {
            ArrayList<Component> text = new ArrayList<>();
            for (int i = 1; i <= 5 ; i++) {
                text.add(new TranslatableComponent("options.mic.info.voicechat." + i));
            }

            if (btn != null && VoiceChatCompatibility.present && btn.isHoveredOrFocused())
                e.getScreen().renderTooltip(e.getPoseStack(), text, Optional.empty(), e.getMouseX(), e.getMouseY());

        }

    }
}
