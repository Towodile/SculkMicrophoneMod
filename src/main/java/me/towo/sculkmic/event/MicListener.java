package me.towo.sculkmic.event;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.userpreferences.SculkMicConfig;
import me.towo.sculkmic.mic.Microphone;
import me.towo.sculkmic.mic.MicrophoneHandler;
import me.towo.sculkmic.utils.BlockEntityFinder;
import me.towo.sculkmic.utils.Chat;
import me.towo.sculkmic.utils.SculkVibrationGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MicListener {

    private final static MicrophoneHandler handler = new MicrophoneHandler();


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e) {
        Player p = Minecraft.getInstance().player;
        if (!SculkMicConfig.ENABLED.get()) {
            if (handler.isRunning()) {
                handler.stopCurrentThread();

                SculkMicMod.LOGGER.info("Microphone has been activated.");
                if (p != null)
                    Chat.sendMessage("Microphone has been closed.", p);
            }
            return;
        }

        if (SculkMicConfig.ENABLED.get() && !handler.isRunning()) {
            if (handler.startNewThread()) {
                SculkMicMod.LOGGER.info("Microphone has been activated.");
                if (p != null)
                    Chat.sendMessage("Microphone has been activated.", p);
            } else {
                SculkMicMod.LOGGER.error("Microphone is unavailable! Microphone has been disabled.");
                if (p != null)
                    Chat.sendMessage("§4ERROR: §cMicrophone is unavailable.", p);
                SculkMicConfig.editIfEnabled(false);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (!SculkMicConfig.ENABLED.get() || !handler.isRunning())
            return;

        int noiseLevel = handler.getCurrentVolumeLevel();
        int threshold = SculkMicConfig.THRESHOLD.get();
        int range = 6;
        boolean playerIsLoud = noiseLevel > threshold;

        if (playerIsLoud) {
            SculkVibrationGenerator.generate(e.player, range, noiseLevel);
        }
    }

}
