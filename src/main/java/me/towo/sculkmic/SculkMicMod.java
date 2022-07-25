package me.towo.sculkmic;


import me.towo.sculkmic.client.gui.screen.MicrophoneSettingsScreen;
import me.towo.sculkmic.client.sound.microphone.MicrophoneListener;
import me.towo.sculkmic.client.userpreferences.ModOption;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.init.ModGameEvent;
import me.towo.sculkmic.server.network.packet.PacketHandler;
import me.towo.sculkmic.server.userpreferences.ServerSculkMicConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("sculkmic")
public class SculkMicMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String ID = "sculkmic";

    public SculkMicMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SculkMicConfig.SPEC, SculkMicConfig.FILE_NAME);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerSculkMicConfig.SPEC, "sculkmicmod-server.toml");

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModOption.setInitialValues();
        MinecraftForge.EVENT_BUS.register(new MicrophoneListener(40));
        event.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MicrophoneSettingsScreen::setAsConfigScreen));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
        ModGameEvent.register();
    }
}
