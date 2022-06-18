package me.towo.sculkmic.client.userpreferences;

import me.towo.sculkmic.client.gui.components.Icon;
import net.minecraft.client.ParticleStatus;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

import java.util.Arrays;
import java.util.Comparator;

public enum IconStatus implements OptionEnum {
    OFF(0, "options.mic.icon.off"),
    TOP_LEFT(1, "options.mic.icon.top_left"),
    MIDDLE_LEFT(2, "options.mic.icon.middle_left"),
    BOTTOM_LEFT(3, "options.mic.icon.bottom_left"),
    TOP_RIGHT(4, "options.mic.icon.top_right"),
    MIDDLE_RIGHT(5, "options.mic.icon.middle_right"),
    BOTTOM_RIGHT(6, "options.mic.icon.bottom_right"),
    TOP_CENTER(7, "options.mic.icon.top_center");

    private int id;
    private String key;
    private static final IconStatus[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(IconStatus::getId)).toArray(IconStatus[]::new);

    IconStatus(int id, String key) {
        this.id = id;
        this.key = key;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getKey() {
        return key;
    }

    public static IconStatus byId(int id) {
        return BY_ID[Mth.positiveModulo(id, BY_ID.length)];
    }

    public Icon.Position.PositionType toPositionType() {
        return Icon.Position.PositionType.values()[id - 1];
    }
}
