package me.roundaround.slothighlights.client;

import me.roundaround.allay.api.Entrypoint;
import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint(Entrypoint.CLIENT)
public class SlotHighlightsClientMod implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    SlotHighlightsConfig.getInstance().init();
  }
}
