package cc.seati.AutoAfkKicker.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public class Utils {
    public static <T extends Event> void registerListener(Consumer<T> listener) {
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(listener));
    }
}
