package me.towo.sculkmic.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//@Mod.EventBusSubscriber(modid = SculkMicMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(at = @At("RETURN"), method = "createPauseMenu")
    private void addButton(CallbackInfo ci) {
        this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16 + 24, 204, 20, new TranslatableComponent("gui.microphone_settings"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancements()));
        }));
    }
//    @SubscribeEvent
//    public static void onPauseMenu(ScreenEvent.InitScreenEvent.Post e) {
//        if (e.getScreen() instanceof PauseScreen) {
//            PauseScreen scr = (PauseScreen)e.getScreen();
//            Button button = new Button(43, 43, Minecraft.getInstance().screen.width / 4,
//                    Minecraft.getInstance().screen.height / 4, new TextComponent("Microphone Options"), btn -> {
//                SculkMicConfig.editIfInfoOnScreen(!SculkMicConfig.SHOW_INFO_ON_SCREEN.get());
//            });
//            e.addListener(button);
//            scr.
//        }
//    }

}
