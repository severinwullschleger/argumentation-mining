package Main.Enums;

import Main.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 16.12.17.
 */
public enum Stance {
    CON("con"),
    PRO("pro"),
    UNCLEAR("unclear"),
    UNDEFINED("undefined");

    private String text;

    Stance(String text) {
        this.text = text;
    }

    public String getStanceToString() {
        return this.text;
    }

}



