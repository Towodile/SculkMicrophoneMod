package me.towo.sculkmic.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.config.SculkMicConfig;
import me.towo.sculkmic.mic.Microphone;
import me.towo.sculkmic.mic.MicrophoneHandler;
import me.towo.sculkmic.utils.ModColors;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OnScreenInfo {

    private static Microphone mic = MicrophoneHandler.getMic();

    @SubscribeEvent
    public static void onScreenGUI(RenderGameOverlayEvent.Pre e) {
        if (!SculkMicConfig.SHOW_INFO_ON_SCREEN.get())
            return;

        int level = mic.getLevelAfterCalculate();
        PoseStack matrix = e.getMatrixStack();
        String displayedVolume = String.valueOf(level);
        String displayedStatus = String.valueOf(mic.getStatus());
        int volumeColor = ModColors.REGULAR;
        int statusColor = ModColors.REGULAR;
        if (level < 0) displayedVolume = "0";
        if (level > SculkMicConfig.THRESHOLD.get()) volumeColor = ModColors.SCULK;
        if (mic.getStatus() != Microphone.Status.OK) statusColor = ModColors.ERROR;

        Minecraft.getInstance().font.draw(matrix, "Mic volume: " + displayedVolume, 10, 10, volumeColor);
        Minecraft.getInstance().font.draw(matrix, "Status: " + displayedStatus, 10, 20, statusColor);

    }
}
