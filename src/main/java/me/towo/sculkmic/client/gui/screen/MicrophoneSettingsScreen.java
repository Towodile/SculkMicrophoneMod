package me.towo.sculkmic.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.towo.sculkmic.client.sound.microphone.MicrophoneHandler;
import me.towo.sculkmic.client.userpreferences.ModOption;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.init.GlobalEventHandler;
import me.towo.sculkmic.common.utils.ModColors;
import me.towo.sculkmic.common.utils.ModMath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(value = Dist.CLIENT)
public class MicrophoneSettingsScreen extends OptionsSubScreen {
    private final MicrophoneHandler microphone;
    private OptionsList list;
    private float lastLevel;
    public MicrophoneSettingsScreen(Screen lastScreen, Options options, MicrophoneHandler microphoneHandler) {
        super(lastScreen, options, Component.translatable("options.microphoneTitle"));
        microphone = microphoneHandler;
    }

    public static void setAsConfigScreen(){
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen)
                -> new MicrophoneSettingsScreen(screen, Minecraft.getInstance().options,
                GlobalEventHandler.MICROPHONE_LISTENER.getHandler())));
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);

        this.list.addSmall(new OptionInstance[] {ModOption.ENABLE_MIC_LISTENING, ModOption.ICON_POSITION});
        this.list.addBig(ModOption.INPUT_DEVICE);
        this.list.addBig(ModOption.SCULK_THRESHOLD);

        this.addWidget(this.list);

        // DONE
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (p_232810_) -> {
            this.minecraft.options.save();
            SculkMicConfig.saveStoredToConfig();
            this.minecraft.setScreen(this.lastScreen);
        }));
    }

    public void render(@NotNull PoseStack p_96813_, int p_96814_, int p_96815_, float p_96816_) {
        this.renderBackground(p_96813_);
        this.list.render(p_96813_, p_96814_, p_96815_, p_96816_);
        drawCenteredString(p_96813_, this.font, this.title, this.width / 2, 5, 16777215);
        super.render(p_96813_, p_96814_, p_96815_, p_96816_);
        List<FormattedCharSequence> list = tooltipAt(this.list, p_96814_, p_96815_);
        if (this.list != null) {
            this.renderTooltip(p_96813_, list, p_96814_, p_96815_);
        }

        drawMeter(ModOption.SCULK_THRESHOLD, p_96813_, 5, SoundEvents.SCULK_CLICKING);
    }

    private void drawMeter(OptionInstance<?> option, PoseStack pose, int height, SoundEvent soundOnThreshold) {
        AbstractWidget widget = list.findOption(option);
        assert widget != null;
        int width = (int) (ModMath.factor(0, 120, microphone.getCurrentLevel()) * widget.getWidth());
        fill(pose, widget.x, widget.y + widget.getHeight(),  widget.x + width, widget.y + widget.getHeight() + height, ModColors.SCULK);

        if (microphone.getCurrentLevel() > SculkMicConfig.THRESHOLD.get()) {
            widget.setFGColor(ModColors.SCULK);
            if (soundOnThreshold != null && lastLevel < SculkMicConfig.THRESHOLD.get()) {
                Minecraft.getInstance().getSoundManager()
                        .play(SimpleSoundInstance.forUI(soundOnThreshold, 1.0F));
            }
        } else if (microphone.isRunning()) {
            widget.setFGColor(ModColors.REGULAR);
        } else widget.setFGColor(ModColors.INACTIVE);

        lastLevel = microphone.getCurrentLevel();
    }

    @Override
    public void removed() {
        super.removed();
        SculkMicConfig.saveStoredToConfig();
    }
}
