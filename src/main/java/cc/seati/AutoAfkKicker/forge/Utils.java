package cc.seati.AutoAfkKicker.forge;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {
    public static @Nullable ServerPlayerEntity getServerPlayerByName(String playername) {
        List<ServerPlayerEntity> result = getServer().getPlayerList().getPlayers()
                .stream()
                .filter(p -> p.getGameProfile().getName().equalsIgnoreCase(playername))
                .collect(Collectors.toList());
        if (result.isEmpty()) return null;
        else return result.get(0);
    }

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static void broadcast(ITextComponent component) {
        getServer().getPlayerList().getPlayers().forEach(p -> p.sendMessage(component, UUID.randomUUID()));
    }

    public static long toTicks(long timeValue, String timeUnit) {
        int multiplier = 1;
        switch (timeUnit) {
            case "d": {
                multiplier = 60 * 60 * 24 * 20;
                break;
            }

            case "h": {
                multiplier = 60 * 60 * 20;
                break;
            }

            case "m": {
                multiplier = 60 * 20;
                break;
            }

            case "s": {
                multiplier = 20;
                break;
            }
        }
        return timeValue * multiplier;
    }
}
