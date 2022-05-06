package me.towo.sculkmic.event;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.config.SculkMicConfig;
import me.towo.sculkmic.mic.Microphone;
import me.towo.sculkmic.mic.MicrophoneHandler;
import me.towo.sculkmic.utils.BlockEntityFinder;
import me.towo.sculkmic.utils.Chat;
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

    private final static Microphone mic = MicrophoneHandler.getMic();

    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggedInEvent e) {
        if (e.getPlayer() != null) {
            mic.start();

            Chat.sendMessage("Microphone has opened!", e.getPlayer());
        } else {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggedOutEvent e) {
        mic.tryStop();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        int noiseLevel = mic.getLevelAfterCalculate();
        int treshold = SculkMicConfig.THRESHOLD.get();
        boolean playerIsLoud = noiseLevel > treshold;

        if (playerIsLoud) {
            BlockEntityFinder<SculkSensorBlockEntity> finder =
                    new BlockEntityFinder<>(SculkSensorBlockEntity.class,6, e.player.blockPosition(), e.player.level);

            for (SculkSensorBlockEntity sculk : finder.find()) {
                VibrationListener listener = sculk.getListener();
                GameEvent event = getGameEventByLoudness(noiseLevel);
                //GameEvent event = GameEvent.EAT;
                listener.handleGameEvent(e.player.level, event, e.player, e.player.blockPosition());
            }
        }
    }

    private static GameEvent getGameEventByLoudness(int loudness) {
        int eventInt = loudness - (loudness / 3);

        Object2IntMap vibrationMap = SculkSensorBlock.VIBRATION_STRENGTH_FOR_EVENT;
        int[] values = vibrationMap.values().toIntArray();
        Object[] keys = vibrationMap.keySet().toArray();

        int distance = Math.abs(values[0] - eventInt);
        int index = 0;
        for (int i = 1; i < vibrationMap.size(); i++) {
            int idistance = Math.abs(values[i] - eventInt);

            if(idistance < distance){
                index = i;
                distance = idistance;
            }
        }
        return (GameEvent)keys[index];
    }




}
