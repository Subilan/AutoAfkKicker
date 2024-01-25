package cc.seati.AutoAfkKicker.forge;

import net.minecraft.util.text.*;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec GENERAL_SPEC;
    public static ForgeConfigSpec.LongValue MAX_AFK_TIME_V;
    public static ForgeConfigSpec.ConfigValue<String> MAX_AFK_TIME_U;
    public static ForgeConfigSpec.LongValue MAX_INACTIVE_TIME_V;
    public static ForgeConfigSpec.ConfigValue<String> MAX_INACTIVE_TIME_U;
    public static ForgeConfigSpec.ConfigValue<String> ENTERING_AFK_MSG;
    public static ForgeConfigSpec.ConfigValue<String> LEAVING_AFK_MSG;


    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        MAX_AFK_TIME_V = builder.comment("Decide how long can players stay in AFK status without being kicked. This is the time value without unit. Must be a positive integer. Default: 20.")
                .defineInRange("max_afk_time_value", 20, 0, Long.MAX_VALUE);
        MAX_AFK_TIME_U = builder.comment("The time unit of max_afk_time.")
                .define("max_afk_time_unit",  "m");
        MAX_INACTIVE_TIME_V = builder.comment("Decide how long can players stay inactive and are considered not in AFK. This is the time value without unit. Must be a positive integer. Default: 10.")
                .defineInRange("max_inactive_time_value", 10, 0, Long.MAX_VALUE);
        MAX_INACTIVE_TIME_U = builder.comment("The time unit of max_inactive_time_unit")
                .define("max_inactive_time_unit", "m");
        ENTERING_AFK_MSG = builder.comment("The message to show at the time players enter AFK status.")
                .define("msg_entering_afk", "You're now in AFK.");
        LEAVING_AFK_MSG = builder.comment("The message to show at the time players leave AFK status.")
                .define("msg_leaving_afk", "You're no longer in AFK.");
    }

    /**
     * 从配置文件中获取允许玩家 AFK 最长时间
     * @return AFK 最长时间的刻数
     */
    public static long getMaxAFKTimeTicks() {
        return Utils.toTicks(MAX_AFK_TIME_V.get(), MAX_AFK_TIME_U.get());
    }

    /**
     * 从配置文件中获取 AFK 之前的最大不活跃时间
     * @return AFK 之前的最大不活跃时间的刻数
     */
    public static long getMaxInactiveTimeTicks() {
        return Utils.toTicks(MAX_INACTIVE_TIME_V.get(), MAX_INACTIVE_TIME_U.get());
    }

    public static ITextComponent getEnteringAFKComp() {
        return new StringTextComponent(ENTERING_AFK_MSG.get()).withStyle(TextFormatting.GRAY);
    }

    public static ITextComponent getLeavingAFKComp() {
        return new StringTextComponent(LEAVING_AFK_MSG.get()).withStyle(TextFormatting.GRAY);
    }
}
