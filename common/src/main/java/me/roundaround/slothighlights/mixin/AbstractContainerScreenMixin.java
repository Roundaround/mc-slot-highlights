package me.roundaround.slothighlights.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.roundaround.slothighlights.client.HighlightRenderer;
import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {
  @Inject(method = "extractSlot", at = @At("HEAD"))
  private void slothighlights$beforeSlot(GuiGraphicsExtractor context, Slot slot, int mouseX, int mouseY,
      CallbackInfo ci) {
    if (!isOverItems()) {
      HighlightRenderer.render(context, slot.getItem(), slot.x, slot.y);
    }
  }

  @Inject(method = "extractSlot", at = @At("TAIL"))
  private void slothighlights$afterSlot(GuiGraphicsExtractor context, Slot slot, int mouseX, int mouseY,
      CallbackInfo ci) {
    if (isOverItems()) {
      HighlightRenderer.render(context, slot.getItem(), slot.x, slot.y);
    }
  }

  private static boolean isOverItems() {
    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();
    return config.isReady() && config.overItems.getPendingValue();
  }
}
