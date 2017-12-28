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

    public static List<String> getRolesToString() {
        List<String> types = new ArrayList<>();
        for (ArgumentType role : ArgumentType.values()) {
            types.add(role.getArgumentTypeToString().toUpperCase());
        }
        return types;
    }

    public static Stance convertToStanceEnum(String attribute) {
        List<String> stancesStrings = getStancesToString();
        if (!stancesStrings.contains(attribute.toUpperCase())) {
            return Stance.UNDEFINED;
        }
        return Stance.valueOf(attribute.toUpperCase());
    }

    public static ArgumentType convertToRoleEnum(String attribute) {
        List<String> typesStrings = getRolesToString();
        if (!typesStrings.contains(attribute.toUpperCase())) {
            return ArgumentType.UNDEFINED;
        }
        return ArgumentType.valueOf(attribute.toUpperCase());
    }
}
