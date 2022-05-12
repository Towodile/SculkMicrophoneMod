package me.towo.sculkmic.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.mic.MicrophoneHandler;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.utils.ModColors;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OnScreenInfo {

    private final static MicrophoneHandler handler = new MicrophoneHandler();

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent.Pre e) {
        if (!SculkMicConfig.SHOW_INFO_ON_SCREEN.get())
            return;

        int level = handler.getCurrentVolumeLevel();
        PoseStack matrix = e.getMatrixStack();
        String displayedVolume = String.valueOf(level);
        String displayedStatus = String.valueOf(handler.isRunning());
        int volumeColor = ModColors.REGULAR;
        int statusColor = ModColors.REGULAR;
        if (level < 0) displayedVolume = "0";
        if (level > SculkMicConfig.THRESHOLD.get()) volumeColor = ModColors.SCULK;
        if (!handler.isRunning()) statusColor = ModColors.ERROR;

        Minecraft.getInstance().font.draw(matrix, "Mic volume: " + displayedVolume, 10, 10, volumeColor);
        Minecraft.getInstance().font.draw(matrix, "Is running? " + displayedStatus, 10, 20, statusColor);

    }
}
