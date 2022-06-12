package me.towo.sculkmic.common.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.towo.sculkmic.common.init.ModGameEvent;
import net.minecraft.Util;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SculkSensorBlock.class)
public interface MixinSculkSensorBlock {
    @Mutable
    @Accessor("VIBRATION_FREQUENCY_FOR_EVENT")
    static void setVibrationFrequenciesForEvents(Object2IntMap<GameEvent> map) {}

}
