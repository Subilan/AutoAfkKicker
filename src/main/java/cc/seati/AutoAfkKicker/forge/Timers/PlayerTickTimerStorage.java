package cc.seati.AutoAfkKicker.forge.Timers;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PlayerTickTimerStorage {
    public static Map<String, PlayerTickTimer> map = new HashMap<>();

    public static @Nullable PlayerTickTimer getTimerByName(String name) {
        return map.get(name);
    }

    public static void addTimerForName(PlayerTickTimer timer, String name) {
        map.put(name, timer);
    }

    public static void removeTimerForName(String name) {
        map.get(name).setWorking(false);
        map.remove(name);
    }
}
