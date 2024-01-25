package cc.seati.AutoAfkKicker.forge.Timers;

import cc.seati.AutoAfkKicker.forge.Config;
import cc.seati.AutoAfkKicker.forge.Utils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;

public class PlayerTickTimer {
    // 该计时器计时对象玩家名称
    public String playername;
    // 计时器是否工作。为 false 时暂停工作
    public boolean working = false;
    // 当前计时器的 tick 数量
    public Integer tickCount = 0;
    // 是否已经送达进入 AFK 的提示
    public boolean afkMessageSent = false;

    public static PlayerTickTimer createFor(String playername) {
        PlayerTickTimer inst = new PlayerTickTimer();
        inst.playername = playername;
        MinecraftForge.EVENT_BUS.addListener(inst::onTick);
        return inst;
    }

    public void setWorking(boolean work) {
        this.working = work;
    }

    public void onTick(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) this.onTickStart();
        if (e.phase == TickEvent.Phase.END) this.onTickEnd();
    }

    private void onTickStart() {
        if (!this.working) return;
        tickCount++;
    }

    private void onTickEnd() {
        if (!this.working) return;
        ServerPlayerEntity find = Utils.getServerPlayerByName(playername);
        if (find == null) return;

        if (tickCount > Config.getMaxAFKTimeTicks() + Config.getMaxInactiveTimeTicks()) {
            find.disconnect();
        } else if (tickCount > Config.getMaxInactiveTimeTicks() && !this.afkMessageSent) {
            find.sendMessage(Config.getEnteringAFKComp(), UUID.randomUUID());
            this.afkMessageSent = true;
        }
    }

    public void reset() {
        this.tickCount = 0;
        this.afkMessageSent = false;
    }
}
