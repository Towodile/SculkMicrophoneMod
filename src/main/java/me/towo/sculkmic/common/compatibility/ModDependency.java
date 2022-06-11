package me.towo.sculkmic.common.compatibility;

/**
 * A dependency for another mod Sculk Microphone can look for.
 */
public abstract class ModDependency {
    /**
     * The Mod ID associated with the mod that's needed to be found.
     */
    public final String modid;

    /**
     * Represents whether this dependency has successfully been located or not.
     */
    private boolean present;

    public ModDependency(String modid) {
        this.modid = modid;
        present = false;
    }


    /**
     * This method gets called as soon as a mod matching {@link #modid} has been found.
     */
    public abstract void onFind();

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean isPresent) {
        present = isPresent;
    }
}
