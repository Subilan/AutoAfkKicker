package cc.seati.AutoAfkKicker.forge;

import cc.seati.AutoAfkKicker.forge.Timers.PlayerTickTimer;
import cc.seati.AutoAfkKicker.forge.Timers.PlayerTickTimerStorage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class Events {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {
        String playername = e.getPlayer().getGameProfile().getName();
        PlayerTickTimer timer  = PlayerTickTimer.createFor(playername);
        timer.setWorking(true);
        PlayerTickTimerStorage.addTimerForName(timer, playername);
    }

    @SubscribeEvent
    public static void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent e) {
        PlayerTickTimerStorage.removeTimerForName(e.getPlayer().getGameProfile().getName());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END) InactivityWatcher.judge(e.player);
    }
}
