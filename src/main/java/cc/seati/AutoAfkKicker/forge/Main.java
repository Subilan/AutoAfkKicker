package cc.seati.AutoAfkKicker.forge;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("autoafkkicker")
public class Main {
    public static final Logger LOGGER = LogManager.getLogger();
    public Main() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        Utils.registerListener(Events::onPlayerJoin);
        Utils.registerListener(Events::onPlayerQuit);
    }

    private void setup(final FMLCommonSetupEvent e) {
        LOGGER.info("Hello from AutoAfkKicker");
    }
}
