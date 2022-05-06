package me.towo.sculkmic.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.userpreferences.ModOption;
import me.towo.sculkmic.userpreferences.SculkMicConfig;
import net.minecraft.client.FullscreenResolutionProgressOption;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class MicrophoneSettingsScreen extends OptionsSubScreen {
    private static final Option[] OPTIONS = new Option[]{ModOption.ENABLE_MIC_LISTENING, ModOption.MICROPHONE_SENSITIVITY, ModOption.SHOW_ON_SCREEN_INFO, ModOption.SCULK_THRESHOLD};
    private final GpuWarnlistManager gpuWarnlistManager;
    private final Object oldMipmaps;
    private OptionsList list;

    public MicrophoneSettingsScreen(Screen lastScreen, Options options) {
        super(lastScreen, options, new TranslatableComponent("options.microphoneTitle"));
        this.gpuWarnlistManager = lastScreen.getMinecraft().getGpuWarnlistManager();
        this.gpuWarnlistManager.resetWarnings();
        if (options.graphicsMode == GraphicsStatus.FABULOUS) {
            this.gpuWarnlistManager.dismissWarning();
        }

        this.oldMipmaps = options.mipmapLevels;
    }

    @Override
    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        this.list.addSmall(OPTIONS);
        this.addWidget(this.list);
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (p_96827_) -> {
            this.minecraft.options.save();
            this.minecraft.setScreen(this.lastScreen);
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

    }

    @Override
    public void removed() {
        super.removed();
    }
}
