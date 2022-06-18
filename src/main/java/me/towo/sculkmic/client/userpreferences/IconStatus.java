package me.towo.sculkmic.client.userpreferences;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import me.towo.sculkmic.client.gui.components.Icon;
import net.minecraft.Util;
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

    private final static Object2ObjectMap<IconStatus, Icon.Position> POSITION_WITH_OPTION_STATUS = Object2ObjectMaps.unmodifiable(Util.make(new Object2ObjectArrayMap<>(), (map) -> {
        map.put(TOP_LEFT, Icon.Position.TOP_LEFT);
        map.put(MIDDLE_LEFT, Icon.Position.MIDDLE_LEFT);
        map.put(BOTTOM_LEFT, Icon.Position.BOTTOM_LEFT);
        map.put(TOP_RIGHT, Icon.Position.TOP_RIGHT);
        map.put(MIDDLE_RIGHT, Icon.Position.MIDDLE_RIGHT);
        map.put(BOTTOM_RIGHT, Icon.Position.BOTTOM_RIGHT);
        map.put(TOP_CENTER, Icon.Position.TOP_CENTER);
    }));

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

    public Icon.Position getCorrespondingPosition() {
        return POSITION_WITH_OPTION_STATUS.get(this);
    }
}
