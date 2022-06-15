package me.towo.sculkmic.server.vibration;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.towo.sculkmic.common.init.ModGameEvent;
import me.towo.sculkmic.common.mixin.MixinSculkSensorBlock;
import net.minecraft.Util;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.gameevent.GameEvent;

public class ModVibrationFrequencies {


    /**
     * Calls a setter method in SculkSensorBlock that's been added there via Mixin. The given frequency will be set for {@link ModGameEvent#TALK} in {@link SculkSensorBlock#VIBRATION_FREQUENCY_FOR_EVENT}.
     */
    public static void setTalkFrequency(int frequency) {
        if (SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT.getInt(ModGameEvent.TALK) == frequency) {
            return;
        }

        Object2IntMap<GameEvent> newMap = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (p_238254_) -> {
            p_238254_.put(GameEvent.STEP, 1);
            p_238254_.put(GameEvent.FLAP, 2);
            p_238254_.put(GameEvent.SWIM, 3);
            p_238254_.put(GameEvent.ELYTRA_GLIDE, 4);
            p_238254_.put(GameEvent.HIT_GROUND, 5);
            p_238254_.put(GameEvent.TELEPORT, 5);
            p_238254_.put(GameEvent.SPLASH, 6);
            p_238254_.put(GameEvent.ENTITY_SHAKE, 6);
            p_238254_.put(GameEvent.BLOCK_CHANGE, 6);
            p_238254_.put(GameEvent.NOTE_BLOCK_PLAY, 6);
            p_238254_.put(GameEvent.PROJECTILE_SHOOT, 7);
            p_238254_.put(GameEvent.DRINK, 7);
            p_238254_.put(GameEvent.PRIME_FUSE, 7);
            p_238254_.put(GameEvent.PROJECTILE_LAND, 8);
            p_238254_.put(GameEvent.EAT, 8);
            p_238254_.put(GameEvent.ENTITY_INTERACT, 8);
            p_238254_.put(GameEvent.ENTITY_DAMAGE, 8);
            p_238254_.put(GameEvent.EQUIP, 9);
            p_238254_.put(GameEvent.SHEAR, 9);
            p_238254_.put(GameEvent.ENTITY_ROAR, 9);
            p_238254_.put(GameEvent.BLOCK_CLOSE, 10);
            p_238254_.put(GameEvent.BLOCK_DEACTIVATE, 10);
            p_238254_.put(GameEvent.BLOCK_DETACH, 10);
            p_238254_.put(GameEvent.DISPENSE_FAIL, 10);
            p_238254_.put(GameEvent.BLOCK_OPEN, 11);
            p_238254_.put(GameEvent.BLOCK_ACTIVATE, 11);
            p_238254_.put(GameEvent.BLOCK_ATTACH, 11);
            p_238254_.put(GameEvent.ENTITY_PLACE, 12);
            p_238254_.put(GameEvent.BLOCK_PLACE, 12);
            p_238254_.put(GameEvent.FLUID_PLACE, 12);
            p_238254_.put(GameEvent.ENTITY_DIE, 13);
            p_238254_.put(GameEvent.BLOCK_DESTROY, 13);
            p_238254_.put(GameEvent.FLUID_PICKUP, 13);
            p_238254_.put(GameEvent.ITEM_INTERACT_FINISH, 14);
            p_238254_.put(GameEvent.CONTAINER_CLOSE, 14);
            p_238254_.put(GameEvent.PISTON_CONTRACT, 14);
            p_238254_.put(GameEvent.PISTON_EXTEND, 15);
            p_238254_.put(GameEvent.CONTAINER_OPEN, 15);
            p_238254_.put(GameEvent.ITEM_INTERACT_START, 15);
            p_238254_.put(GameEvent.EXPLODE, 15);
            p_238254_.put(GameEvent.LIGHTNING_STRIKE, 15);
            p_238254_.put(GameEvent.INSTRUMENT_PLAY, 15);
            p_238254_.put(ModGameEvent.TALK, frequency);
        }));

        MixinSculkSensorBlock.setVibrationFrequenciesForEvents(newMap);
    }
}
