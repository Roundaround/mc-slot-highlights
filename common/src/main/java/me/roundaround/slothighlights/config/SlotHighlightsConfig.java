package me.roundaround.slothighlights.config;

import me.roundaround.slothighlights.generated.Constants;
import me.roundaround.trove.config.ConfigPath;
import me.roundaround.trove.config.manage.ModConfigImpl;
import me.roundaround.trove.config.manage.store.GameScopedFileStore;
import me.roundaround.trove.config.option.BooleanConfigOption;
import me.roundaround.trove.config.option.ColorConfigOption;
import me.roundaround.trove.config.option.StringListConfigOption;
import me.roundaround.trove.util.Color;

import java.util.List;
import java.util.regex.Pattern;

public class SlotHighlightsConfig extends ModConfigImpl implements GameScopedFileStore {
  private static final Pattern RARITY_OVERRIDE = Pattern.compile("[a-z0-9_.-]+=#[0-9a-fA-F]{6}");

  private static SlotHighlightsConfig instance = null;

  public static SlotHighlightsConfig getInstance() {
    if (instance == null) {
      instance = new SlotHighlightsConfig();
    }
    return instance;
  }

  public BooleanConfigOption hotbar;
  public BooleanConfigOption fullBorder;
  public BooleanConfigOption squareCorners;
  public BooleanConfigOption overItems;
  public BooleanConfigOption extraGlow;
  public BooleanConfigOption highlightNamed;
  public ColorConfigOption namedColor;
  public BooleanConfigOption highlightEnchanted;
  public ColorConfigOption enchantedColor;
  public BooleanConfigOption highlightRarity;
  public BooleanConfigOption highlightCommon;
  public StringListConfigOption rarityColors;

  private SlotHighlightsConfig() {
    super(Constants.MOD_ID, "client");
  }

  @Override
  protected void registerOptions() {
    this.hotbar = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("hotbar"))
        .setDefaultValue(true)
        .setComment("Highlight hotbar slots.")
        .build()).clientOnly().commit();
    this.fullBorder = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("fullBorder"))
        .setDefaultValue(false)
        .setComment("Draw the full border instead of a bottom fade.")
        .build()).clientOnly().commit();
    this.squareCorners = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("squareCorners"))
        .setDefaultValue(true)
        .setComment("Square instead of beveled corners.")
        .build()).clientOnly().commit();
    this.overItems = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("overItems"))
        .setDefaultValue(false)
        .setComment("Draw borders over items instead of under.")
        .build()).clientOnly().commit();
    this.extraGlow = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("extraGlow"))
        .setDefaultValue(false)
        .setComment("Add a soft inner glow.")
        .build()).clientOnly().commit();

    this.highlightNamed = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("highlightNamed"))
        .setDefaultValue(true)
        .setComment("Highlight custom-named items.")
        .build()).clientOnly().commit();
    this.namedColor = this.buildRegistration(ColorConfigOption.builder(ConfigPath.of("namedColor"))
        .setDefaultValue(Color.parse("#FFAA00"))
        .setComment("Color for custom-named items (#RRGGBB).")
        .build()).clientOnly().commit();

    this.highlightEnchanted = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of(
        "highlightEnchanted"))
        .setDefaultValue(false)
        .setComment("Highlight enchanted items with a dedicated color.")
        .build()).clientOnly().commit();
    this.enchantedColor = this.buildRegistration(ColorConfigOption.builder(ConfigPath.of("enchantedColor"))
        .setDefaultValue(Color.parse("#B24BF3"))
        .setComment("Color for enchanted items (#RRGGBB).")
        .build()).clientOnly().commit();

    this.highlightRarity = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("highlightRarity"))
        .setDefaultValue(true)
        .setComment("Highlight items using their rarity's color.")
        .build()).clientOnly().commit();
    this.highlightCommon = this.buildRegistration(BooleanConfigOption.yesNoBuilder(ConfigPath.of("highlightCommon"))
        .setDefaultValue(false)
        .setComment("Also highlight common items.")
        .build()).clientOnly().commit();
    this.rarityColors = this.buildRegistration(StringListConfigOption.builder(ConfigPath.of("rarityColors"))
        .setDefaultValue(List.of())
        .setComment("Per-rarity color overrides, e.g. \"epic=#FF55FF\".")
        .addValidator((value, option) -> value.stream().allMatch(RARITY_OVERRIDE.asMatchPredicate()))
        .hideFromConfigScreen()
        .build()).clientOnly().commit();
  }
}
