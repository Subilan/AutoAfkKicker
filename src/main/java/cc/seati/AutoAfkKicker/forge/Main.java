package cc.seati.AutoAfkKicker.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("autoafkkicker")
public class Main {
    public static final String MOD_ID = "autoafkkicker";
    public static final Logger LOGGER = LogManager.getLogger();
    public Main() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.GENERAL_SPEC, "autoafkkicker.toml"));
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup));
    }

    private void setup(final FMLCommonSetupEvent e) {
        LOGGER.info("Hello from AutoAfkKicker");
    }
}
