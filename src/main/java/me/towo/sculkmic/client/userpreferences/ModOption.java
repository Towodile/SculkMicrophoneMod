package me.towo.sculkmic.client.userpreferences;

import com.mojang.serialization.Codec;
import me.towo.sculkmic.client.gui.screen.MicrophoneSettingsScreen;
import me.towo.sculkmic.client.sound.AudioManager;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Optional;


@OnlyIn(Dist.CLIENT) @Mod.EventBusSubscriber(Dist.CLIENT)
public class ModOption {

    public static final OptionInstance<Boolean> ENABLE_MIC_LISTENING = OptionInstance.createBoolean("options.mic.enable", SculkMicConfig.ENABLED.getDefault(),
            SculkMicConfig.ENABLED::set);

    public static final OptionInstance<String> INPUT_DEVICE = new OptionInstance<>("options.mic.device", OptionInstance.noTooltip(),
            (p_231919_, p_231920_) -> Component.literal(p_231920_),
            new OptionInstance.LazyEnum<>(AudioManager.Input::getAllDeviceNames,
                    (value) -> !AudioManager.Input.exists(value) ? Optional.empty() : Optional.of(value), Codec.STRING),
            SculkMicConfig.INPUT_DEVICE.getDefault(),
            SculkMicConfig::setInputDevice);

    public static final OptionInstance<Integer> SCULK_THRESHOLD = new OptionInstance<>("options.mic.threshold", OptionInstance.noTooltip(),
            (p_231962_, p_231963_) -> Options.genericValueLabel(p_231962_, Component.translatable("options.mic.thresholdValue", p_231963_)), new OptionInstance.IntRange(0, 120), (int)Math.round(SculkMicConfig.THRESHOLD.getDefault()),
            (value) -> SculkMicConfig.store(SculkMicConfig.THRESHOLD, (double)value)); // store instead of set, as rapidly trying to update the config will crash the game.

    public static final OptionInstance<IconStatus> ICON_POSITION = new OptionInstance<>("options.mic.icon", OptionInstance.noTooltip(), OptionInstance.forOptionEnum(),
            new OptionInstance.Enum<>(Arrays.asList(IconStatus.values()),
                    Codec.INT.xmap(IconStatus::byId, IconStatus::getId)), IconStatus.byId(SculkMicConfig.ICON.getDefault()),
            (value) -> SculkMicConfig.ICON.set(value.getId()));

    @SubscribeEvent
    public static void onMouseRelease(ScreenEvent.MouseButtonReleased e) {
        if (e.getScreen() instanceof MicrophoneSettingsScreen) {
            SculkMicConfig.saveStoredToConfig();
        }
    }

    public static void setInitialValues(){
        ENABLE_MIC_LISTENING.set(SculkMicConfig.ENABLED.get());
        INPUT_DEVICE.set(SculkMicConfig.INPUT_DEVICE.get());
        SCULK_THRESHOLD.set((int)Math.round(SculkMicConfig.THRESHOLD.get()));
        ICON_POSITION.set(IconStatus.byId(SculkMicConfig.ICON.get()));
    }
}
