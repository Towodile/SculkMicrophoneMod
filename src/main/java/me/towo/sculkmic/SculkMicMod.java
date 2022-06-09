package me.towo.sculkmic;


import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.compatibility.CompatibilityRegister;
import me.towo.sculkmic.common.compatibility.VoiceChatCompatibility;
import me.towo.sculkmic.server.network.packet.PacketHandler;
import me.towo.sculkmic.server.userpreferences.ServerSculkMicConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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

        bus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SculkMicConfig.SPEC, "sculkmicmod-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerSculkMicConfig.SPEC, "sculkmicmod-server.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
        new CompatibilityRegister().registerAll();

    }
}
