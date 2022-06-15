package me.towo.sculkmic.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.towo.sculkmic.client.userpreferences.ModOption;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.client.voice.microphone.MicrophoneHandler;
import me.towo.sculkmic.common.utils.ModColors;
import me.towo.sculkmic.common.utils.ModMath;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class MicrophoneSettingsScreen extends OptionsSubScreen {

    // UGLY CODE AHEAD -- YOU HAVE BEEN WARNED

    private static final OptionInstance<?>[] BIG_OPTIONS = new OptionInstance[] {
            ModOption.ENABLE_MIC_LISTENING,
            ModOption.SCULK_THRESHOLD
    };

    private static final OptionInstance<?>[] SMALL_OPTIONS = new OptionInstance[] {
            ModOption.SENSITIVITY,
            ModOption.DO_ICON
    };
    private final MicrophoneHandler microphone;
    private OptionsList list;
    private float lastVolume;
    public MicrophoneSettingsScreen(Screen lastScreen, Options options, MicrophoneHandler microphoneHandler) {
        super(lastScreen, options, Component.translatable("options.microphoneTitle"));
        GpuWarnlistManager gpuWarnlistManager = lastScreen.getMinecraft().getGpuWarnlistManager();
        gpuWarnlistManager.resetWarnings();
        if (options.graphicsMode().get() == GraphicsStatus.FABULOUS) {
            gpuWarnlistManager.dismissWarning();
        }

        microphone = microphoneHandler;
    }

    @Override
    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);

        this.list.addBig(BIG_OPTIONS[0]);
        this.list.addBig(BIG_OPTIONS[1]);

        this.addWidget(this.list);
        this.addRenderableWidget(new Button(this.width / 2, this.height - 27, 100, 20, CommonComponents.GUI_DONE, (p_96827_) -> {
            this.minecraft.options.save();
            this.minecraft.setScreen(this.lastScreen);
            SculkMicConfig.saveStoredToConfig();
        }));

        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 100, 20, Component.translatable("options.mic.apply"), (p_96827_) -> {
            this.minecraft.options.save();
            SculkMicConfig.saveStoredToConfig();
        }));
    }

    public void render(PoseStack p_96813_, int p_96814_, int p_96815_, float p_96816_) {
        this.renderBackground(p_96813_);
        this.list.render(p_96813_, p_96814_, p_96815_, p_96816_);
        drawCenteredString(p_96813_, this.font, this.title, this.width / 2, 5, 16777215);
        super.render(p_96813_, p_96814_, p_96815_, p_96816_);
        List<FormattedCharSequence> list = tooltipAt(this.list, p_96814_, p_96815_);
        if (list != null) {
            this.renderTooltip(p_96813_, list, p_96814_, p_96815_);
        }
        drawText(p_96813_);
        drawMeter(BIG_OPTIONS[1], p_96813_, 5);
    }

    private void drawText(PoseStack matrix) {
        float level = microphone.getCurrentLevel();
        String displayedVolume = level + "";
        int volumeColor = ModColors.REGULAR;
        int statusColor = ModColors.REGULAR;
        if (level > SculkMicConfig.THRESHOLD.get()) {
            volumeColor = ModColors.SCULK;
            if (lastVolume < SculkMicConfig.THRESHOLD.get()) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.SCULK_CLICKING, 1.0F));
            }
        }

        if (!microphone.isRunning()) {
            volumeColor = ModColors.INACTIVE;
            statusColor = ModColors.ERROR;
        }

        int x = (this.width/4) + 30;
        int y = this.height/2;
        Minecraft.getInstance().font.drawWordWrap(Component.literal(Component.translatable("options.mic.test.current_level").getString() + displayedVolume), 20, y, x*5, volumeColor);
        if (!microphone.isRunning()) Minecraft.getInstance().font.drawWordWrap(Component.translatable("options.mic.test.no_mic"), 20, y + 20, x*5, statusColor);
        lastVolume = microphone.getCurrentLevel();

        if (SculkMicConfig.hasStoredChanges()) {
            Minecraft.getInstance().font.drawWordWrap(Component.translatable("options.mic.unsaved_changes"), width/2 - 100, y+ 40, 200, ModColors.REGULAR);;
        }
    }

    // UNFINISHED
    private void drawMeter(OptionInstance<?> option, PoseStack pose, int height) {
        AbstractWidget widget = list.findOption(option);
        assert widget != null;
        int width = (int) (ModMath.factor(0, 120, microphone.getCurrentLevel()) * widget.getWidth());
        fill(pose, widget.x, widget.y + widget.getHeight(),  widget.x + width, widget.y + widget.getHeight() + height, ModColors.SCULK);
    }

    @Override
    public void removed() {
        super.removed();
        SculkMicConfig.saveStoredToConfig();
    }
}
