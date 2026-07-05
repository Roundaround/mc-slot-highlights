package me.roundaround.slothighlights.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.slothighlights.client.HighlightRenderer;
import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Gui.class)
public abstract class GuiMixin {
  @Inject(method = "extractSlot", at = @At("HEAD"))
  private void slothighlights$beforeHotbarSlot(GuiGraphicsExtractor context, int x, int y, DeltaTracker deltaTracker,
      Player player, ItemStack stack, int seed, CallbackInfo ci) {
    if (isHotbarEnabled() && !isOverItems()) {
      HighlightRenderer.render(context, stack, x, y);
    }
  }

  @Inject(method = "extractSlot", at = @At("TAIL"))
  private void slothighlights$afterHotbarSlot(GuiGraphicsExtractor context, int x, int y, DeltaTracker deltaTracker,
      Player player, ItemStack stack, int seed, CallbackInfo ci) {
    if (isHotbarEnabled() && isOverItems()) {
      HighlightRenderer.render(context, stack, x, y);
    }
  }

  private static boolean isHotbarEnabled() {
    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();
    return config.isReady() && config.hotbar.getPendingValue();
  }

  private static boolean isOverItems() {
    return SlotHighlightsConfig.getInstance().overItems.getPendingValue();
  }
}
