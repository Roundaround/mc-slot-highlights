package me.roundaround.slothighlights.gametest;

import me.roundaround.allay.api.gametest.ClientGameTest;
import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import me.roundaround.trove.config.option.ConfigOption;
import me.roundaround.trove.gametest.*;
import me.roundaround.trove.gametest.screenshot.Region;
import me.roundaround.trove.gametest.screenshot.ScreenshotSpec;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

@ClientGameTest
public class ChestHighlightsGoldenTest implements ClientTest {
  // Generic 9x3 container GUI: 176x166 centered on the 427x240 (854x480 @ 2)
  // capture; chest slot grid starts at (8, 18) within it.
  private static final int GUI_LEFT = (427 - 176) / 2;
  private static final int GUI_TOP = (240 - 166) / 2;

  @Override
  public void runTest(ClientTestContext context) {
    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();
    // The dev run dir may hold a saved config with non-default values.
    // Pin every pending value to its default, then opt into the enchanted ring.
    config.getAll().forEach(ConfigOption::setDefault);
    config.highlightEnchanted.setValue(true);
    context.onCleanup(() -> config.highlightEnchanted.setDefault());

    try (ClientWorld world = context.worldBuilder().create()) {
      context.stabilizeForScreenshots();

      BlockPos chestPos = world.playerBlockPos().north(2);
      world.setBlock(chestPos, Blocks.CHEST);
      context.waitTicks(2);

      String at = chestPos.getX() + " " + chestPos.getY() + " " + chestPos.getZ();
      world.runCommand("item replace block " + at + " container.0 with minecraft:stone");
      world.runCommand("item replace block " + at + " container.2 with minecraft:golden_apple");
      world.runCommand("item replace block " + at + " container.4 with minecraft:enchanted_golden_apple");
      world.runCommand(
          "item replace block " + at + " container.6 with" + " minecraft:diamond_sword[custom_name='\"Excalibur\"']");
      world.runCommand("item replace block " + at + " container.8 with" +
                       " minecraft:enchanted_book[stored_enchantments={\"minecraft:efficiency\":3}]");
      context.waitTicks(2);

      ClientMenu chest = world.openMenu(chestPos, ContainerScreen.class);
      context.waitTicks(5);
      assertPresent(
          chest,
          Items.STONE,
          Items.GOLDEN_APPLE,
          Items.ENCHANTED_GOLDEN_APPLE,
          Items.DIAMOND_SWORD,
          Items.ENCHANTED_BOOK
      );

      context.assertScreenshot(ScreenshotSpec.named("chest-highlights")
          .crop(Region.gui(GUI_LEFT, GUI_TOP, 176, 166))
          .redact(slotGlintArea(4))
          .redact(slotGlintArea(8)));
    }
  }

  private static void assertPresent(ClientMenu chest, Item... items) {
    for (Item item : items) {
      if (chest.findSlot(item) < 0) {
        throw new GameTestAssertionException("chest is missing " + item + " — item command failed?");
      }
    }
  }

  private static Region slotGlintArea(int index) {
    return Region.gui(GUI_LEFT + 9 + 18 * (index % 9), GUI_TOP + 19 + 18 * (index / 9), 14, 14);
  }
}
