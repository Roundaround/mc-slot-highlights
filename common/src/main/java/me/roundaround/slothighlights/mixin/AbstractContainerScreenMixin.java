package me.roundaround.slothighlights.mixin;

import me.roundaround.slothighlights.client.HighlightRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {
  @Inject(method = "extractSlot", at = @At("HEAD"))
  private void slothighlights$beforeSlot(
      GuiGraphicsExtractor graphics,
      Slot slot,
      int mouseX,
      int mouseY,
      CallbackInfo ci
  ) {
    HighlightRenderer.renderUnder(graphics, slot.getItem(), slot.x, slot.y);
  }

  @Inject(method = "extractSlot", at = @At("TAIL"))
  private void slothighlights$afterSlot(
      GuiGraphicsExtractor graphics,
      Slot slot,
      int mouseX,
      int mouseY,
      CallbackInfo ci
  ) {
    HighlightRenderer.renderOver(graphics, slot.getItem(), slot.x, slot.y);
  }
}
