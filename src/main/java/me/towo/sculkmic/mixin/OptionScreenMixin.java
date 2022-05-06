package me.towo.sculkmic.mixin;

import me.towo.sculkmic.gui.screen.MicrophoneSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionScreenMixin extends Screen {
    protected OptionScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(at = @At("RETURN"), method = "init")
    private void addButton(CallbackInfo ci) {
        this.addRenderableWidget(new Button(this.width / 2 - 155, this.height / 6 + 120 - 6 + 24, 150, 20, new TranslatableComponent("gui.microphone_settings"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new MicrophoneSettingsScreen(Minecraft.getInstance().screen, Minecraft.getInstance().options));
        }));
    }
}
