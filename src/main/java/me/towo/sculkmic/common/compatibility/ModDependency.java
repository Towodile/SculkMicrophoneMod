package me.towo.sculkmic.common.compatibility;

public abstract class ModDependency {

    public final String modid;
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
