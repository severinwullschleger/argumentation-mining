package Main.Enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyP on 16.12.17.
 */
public class EnumsManager {

    public static List<String> getStancesToString() {
        List<String> stances = new ArrayList<>();
        for (Stance stance : Stance.values()) {
            stances.add(stance.getStanceToString().trim().toUpperCase());
        }
        return stances;
    }

    public static List<String> getArgumentTypesToString() {
        List<String> types = new ArrayList<>();
        for (ArgumentType argumentType : ArgumentType.values()) {
            types.add(argumentType.getArgumentTypeToString().toUpperCase());
        }
        return types;
    }
}
