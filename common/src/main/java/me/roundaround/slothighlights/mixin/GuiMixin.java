package me.roundaround.slothighlights.mixin;

import me.roundaround.slothighlights.client.HighlightRenderer;
import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
  @Inject(method = "extractSlot", at = @At("HEAD"))
  private void slothighlights$beforeHotbarSlot(
      GuiGraphicsExtractor graphics,
      int x,
      int y,
      DeltaTracker deltaTracker,
      Player player,
      ItemStack itemStack,
      int seed,
      CallbackInfo ci
  ) {
    if (slothighlights$isHotbarEnabled() && !slothighlights$isOverItems()) {
      HighlightRenderer.render(graphics, itemStack, x, y);
    }
  }

  @Inject(method = "extractSlot", at = @At("TAIL"))
  private void slothighlights$afterHotbarSlot(
      GuiGraphicsExtractor graphics,
      int x,
      int y,
      DeltaTracker deltaTracker,
      Player player,
      ItemStack itemStack,
      int seed,
      CallbackInfo ci
  ) {
    if (slothighlights$isHotbarEnabled() && slothighlights$isOverItems()) {
      HighlightRenderer.render(graphics, itemStack, x, y);
    }
  }

  @Unique
  private static boolean slothighlights$isHotbarEnabled() {
    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();
    return config.isReady() && config.hotbar.getPendingValue();
  }

  @Unique
  private static boolean slothighlights$isOverItems() {
    return SlotHighlightsConfig.getInstance().overItems.getPendingValue();
  }
}
