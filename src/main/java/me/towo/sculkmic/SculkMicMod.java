package me.towo.sculkmic;


import me.towo.sculkmic.config.SculkMicConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("sculkmic")
public class SculkMicMod
{
    public static final String ID = "sculkmic";
//    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(ID + "_accessories") {
//        @Override
//        @OnlyIn(Dist.CLIENT)
//        public ItemStack makeIcon() {
//            return new ItemStack(BlockINIT.BACKPACK.get());
//        }
//    };

    public SculkMicMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SculkMicConfig.SPEC, "sculkmicmod-client.toml");

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }
}
