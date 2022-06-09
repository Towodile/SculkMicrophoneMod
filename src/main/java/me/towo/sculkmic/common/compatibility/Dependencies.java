package me.towo.sculkmic.common.compatibility;

import me.towo.sculkmic.SculkMicMod;
import me.towo.sculkmic.client.userpreferences.SculkMicConfig;

public final class Dependencies {
    public static final ModDependency SIMPLE_VOICE_CHAT = new ModDependency("voicechat") {
        @Override
        public void onFind() {
            SculkMicMod.LOGGER.info("Found Simple Voice Chat mod!");
            SculkMicConfig.editIfEnabled(false);
            SculkMicConfig.editIfInfoOnScreen(false);
        }
    };
}
