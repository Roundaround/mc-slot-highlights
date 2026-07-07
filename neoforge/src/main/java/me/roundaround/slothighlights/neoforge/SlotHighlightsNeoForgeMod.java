package me.roundaround.slothighlights.neoforge;

import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import me.roundaround.slothighlights.generated.Constants;
import me.roundaround.trove.client.gui.screen.ConfigScreen;
import me.roundaround.trove.neoforge.TroveNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod("slothighlights")
public final class SlotHighlightsNeoForgeMod {
  public SlotHighlightsNeoForgeMod(IEventBus modBus, ModContainer container) {
    TroveNeoForge.bootstrap(modBus, container);

    SlotHighlightsConfig.getInstance().init();

    container.registerExtensionPoint(
        IConfigScreenFactory.class,
        (_, parent) -> new ConfigScreen(parent, Constants.MOD_ID, SlotHighlightsConfig.getInstance())
    );
  }
}
