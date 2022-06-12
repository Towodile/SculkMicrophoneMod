package me.towo.sculkmic.common.init;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.server.blockentity.ModVibrationFrequencies;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;

public class ModGameEvent {
    private static final ResourceKey<GameEvent> TALK_KEY = ResourceKey.create(Registry.GAME_EVENT_REGISTRY,
            new ResourceLocation(SculkMicMod.ID, "talk"));
    public static GameEvent TALK;


    public static void register() {
        SculkMicMod.LOGGER.info("Registering custom GameEvents");

        TALK = registerGameEvent(TALK_KEY.location().getNamespace() + ":" + TALK_KEY.location().getPath());

        new ModVibrationFrequencies().setTalkFrequency(15);
    }

    private static GameEvent registerGameEvent(String p_157823_) {
        return registerGameEvent(p_157823_, 16);
    }

    private static GameEvent registerGameEvent(String p_157825_, int p_157826_) {
        return Registry.register(Registry.GAME_EVENT, p_157825_, new GameEvent(p_157825_, p_157826_));
    }
}
