package me.roundaround.slothighlights.highlight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import me.roundaround.trove.config.option.StringConfigOption;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * Decides which highlight color (0xRRGGBB, no alpha), if any, applies to an
 * item. Conditions are checked in priority order; first match wins.
 */
public final class HighlightResolver {
  private static List<String> lastOverrideList = null;
  private static Map<String, Integer> overrides = Map.of();

  private HighlightResolver() {
  }

  public static Integer resolve(ItemStack stack) {
    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();

    if (config.highlightNamed.getPendingValue() && stack.has(DataComponents.CUSTOM_NAME)) {
      return parseRgb(config.namedColor);
    }

    if (config.highlightEnchanted.getPendingValue() && isEnchanted(stack)) {
      return parseRgb(config.enchantedColor);
    }

    if (!config.highlightRarity.getPendingValue()) {
      return null;
    }

    Rarity rarity = stack.getRarity();
    if (rarity == Rarity.COMMON && !config.highlightCommon.getPendingValue()) {
      return null;
    }

    Integer override = overrideFor(config, rarity);
    if (override != null) {
      return override;
    }

    // Rarity's own color, so modded rarities render their custom color
    // automatically.
    Integer color = rarity.color().getColor();
    return color != null ? color & 0x00FFFFFF : 0xFFFFFF;
  }

  private static boolean isEnchanted(ItemStack stack) {
    if (stack.isEnchanted()) {
      return true;
    }
    ItemEnchantments stored = stack.get(DataComponents.STORED_ENCHANTMENTS);
    return stored != null && !stored.isEmpty();
  }

  private static Integer overrideFor(SlotHighlightsConfig config, Rarity rarity) {
    List<String> entries = config.rarityColors.getPendingValue();
    if (entries != lastOverrideList) {
      Map<String, Integer> parsed = new HashMap<>();
      for (String entry : entries) {
        int split = entry.indexOf('=');
        Integer rgb = split > 0 ? parseRgb(entry.substring(split + 1)) : null;
        if (rgb != null) {
          parsed.put(entry.substring(0, split).toLowerCase(), rgb);
        }
      }
      overrides = parsed;
      lastOverrideList = entries;
    }
    return overrides.get(rarity.getSerializedName());
  }

  private static Integer parseRgb(StringConfigOption option) {
    Integer rgb = parseRgb(option.getPendingValue());
    return rgb != null ? rgb : 0xFFFFFF;
  }

  private static Integer parseRgb(String value) {
    if (value == null || value.length() != 7 || value.charAt(0) != '#') {
      return null;
    }
    try {
      return Integer.parseInt(value.substring(1), 16) & 0x00FFFFFF;
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
