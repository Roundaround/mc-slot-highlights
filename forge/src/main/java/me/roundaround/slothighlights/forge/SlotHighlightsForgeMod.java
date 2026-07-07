package me.roundaround.slothighlights.forge;

import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import me.roundaround.slothighlights.generated.Constants;
import me.roundaround.trove.client.gui.screen.ConfigScreen;
import me.roundaround.trove.forge.TroveForge;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("slothighlights")
public final class SlotHighlightsForgeMod {
  public SlotHighlightsForgeMod(FMLJavaModLoadingContext context) {
    TroveForge.bootstrap(context);

    SlotHighlightsConfig.getInstance().init();

    context.getContainer().registerExtensionPoint(
        ConfigScreenHandler.ConfigScreenFactory.class,
        () -> new ConfigScreenHandler.ConfigScreenFactory((_, parent) -> new ConfigScreen(
            parent,
            Constants.MOD_ID,
            SlotHighlightsConfig.getInstance()
        ))
    );
  }
}
