package Main.Enums;

import Main.Main;

/**
 * Created by LuckyP on 16.12.17.
 */
public enum ArgumentType {
    OPP("opp"),
    PRO("pro"),
    UNDEFINED("undefined");

    private String text;

    ArgumentType(String text) {
        this.text = text;
    }

    public String getArgumentTypeToString() {
        return this.text;
    }
}
