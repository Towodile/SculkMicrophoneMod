package me.towo.sculkmic.common.compatibility;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;
import me.towo.sculkmic.common.init.GlobalEventHandler;
import net.minecraftforge.common.MinecraftForge;


/**
 * This class contains all mods Sculk Microphone will look for.
 */
public final class Dependencies {

    /** <a href="https://github.com/henkelmax/simple-voice-chat">Simple Voice Chat by henkelmax</a> **/
    public static final ModDependency SIMPLE_VOICE_CHAT = new ModDependency("voicechat") {
        @Override
        public void onFind() {
            // enable listener for Simple Voice Chat events
            MinecraftForge.EVENT_BUS.register(GlobalEventHandler.VOICE_CHAT_LISTENER);
            SculkMicMod.LOGGER.info("Registered listener for Simple Voice Chat.");

            // due to this mod taking control of the system's microphone,
            // Sculk Microphone's default microphone listener will be unregistered.
            GlobalEventHandler.MICROPHONE_LISTENER.kill();
            SculkMicMod.LOGGER.info("Disabled default microphone listener.");

            // disabling settings -> when microphone listener code is reworked, this shouldn't be necessary anymore.
            SculkMicConfig.editIfEnabled(false);
            SculkMicConfig.editIfInfoOnScreen(false);
            SculkMicMod.LOGGER.info("Edited config files: microphone and debug info have been disabled.");
        }
    };
}
