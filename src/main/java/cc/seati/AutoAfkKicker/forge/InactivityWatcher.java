package cc.seati.AutoAfkKicker.forge;

import cc.seati.AutoAfkKicker.forge.Timers.PlayerTickTimer;
import cc.seati.AutoAfkKicker.forge.Timers.PlayerTickTimerStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class InactivityWatcher {
    public static Map<PlayerEntity, Pair<Triple<Double, Double, Double>, Vector3d>> playerActions = new HashMap<>();
    public static Map<PlayerEntity, Boolean> lock = new HashMap<>();

    private static void delayUnlockFor(PlayerEntity p) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                lock.put(p, false);
            }
        }, 200L);
    }

    public static void judge(PlayerEntity p) {
        // Lock: judge per 200ms (realtime)
        if (!lock.containsKey(p)) {
            lock.put(p, true);
            delayUnlockFor(p);
        } else {
            if (lock.get(p)) return;
        }

        if (playerActions.containsKey(p)) {
            // Get previous values (0.2s before)
            Pair<Triple<Double, Double, Double>, Vector3d> previousAction = playerActions.get(p);
            Triple<Double, Double, Double> prevPosition = previousAction.getLeft();
            Vector3d prevLookAngle = previousAction.getRight();
            // Comparing with current values
            if (prevPosition.getLeft() == p.getX() || prevPosition.getMiddle() == p.getY() || prevPosition.getRight() == p.getZ()) return;
            if (prevLookAngle.equals(p.getLookAngle())) return;
            // For now, the X, Y, Z coordinates and look angle of the player are all changing, meaning the player is active now. Reset the AFK Timer.
            PlayerTickTimer timer = PlayerTickTimerStorage.getTimerByName(p.getGameProfile().getName());
            if (timer != null) {
                timer.reset();
            }
        }

        // Put or update values in map.
        playerActions.put(p, Pair.of(Triple.of(p.getX(), p.getY(), p.getZ()), p.getLookAngle()));
    }
}
