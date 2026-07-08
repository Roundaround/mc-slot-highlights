package me.roundaround.slothighlights.client;

import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import me.roundaround.slothighlights.highlight.HighlightResolver;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

/**
 * Draws a 1px highlight border around a 16x16 slot at (x, y). By default the
 * border fades in toward the bottom of the slot; fullBorder draws the whole
 * ring at full strength.
 */
public final class HighlightRenderer {
  private static final int BORDER_ALPHA = 0xEE000000;

  private HighlightRenderer() {
  }

  public static void render(GuiGraphicsExtractor context, ItemStack stack, int x, int y) {
    if (stack.isEmpty()) {
      return;
    }

    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();
    if (!config.isReady()) {
      return;
    }

    Integer rgb = HighlightResolver.resolve(stack);
    if (rgb == null) {
      return;
    }

    boolean fullBorder = config.fullBorder.getPendingValue();
    int inset = config.squareCorners.getPendingValue() ? 0 : 1;
    // Fade style leaves the top edge fully transparent so the border fades
    // in down the sides and lands opaque along the bottom.
    int topColor = fullBorder ? (BORDER_ALPHA | rgb) : rgb;
    int bottomColor = BORDER_ALPHA | rgb;

    context.fillGradient(x, y + 1, x + 1, y + 15, topColor, bottomColor);
    context.fillGradient(x + 15, y + 1, x + 16, y + 15, topColor, bottomColor);
    if (fullBorder) {
      context.fill(x + inset, y, x + 16 - inset, y + 1, topColor);
    }
    context.fill(x + inset, y + 15, x + 16 - inset, y + 16, bottomColor);

    if (config.underGlow.getPendingValue()) {
      int topGlow = thirdAlpha(topColor);
      int bottomGlow = thirdAlpha(bottomColor);
      context.fillGradient(x + 1, y + 1, x + 2, y + 15, topGlow, bottomGlow);
      context.fillGradient(x + 14, y + 1, x + 15, y + 15, topGlow, bottomGlow);
      if (fullBorder) {
        context.fill(x + 1, y + 1, x + 15, y + 2, topGlow);
      }
      context.fill(x + 1, y + 14, x + 15, y + 15, bottomGlow);
    }
  }

  private static int thirdAlpha(int color) {
    return ((color >>> 24) / 3) << 24 | (color & 0x00FFFFFF);
  }
}
