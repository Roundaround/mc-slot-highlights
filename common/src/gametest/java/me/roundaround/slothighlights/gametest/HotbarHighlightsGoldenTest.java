package me.roundaround.slothighlights.gametest;

import me.roundaround.allay.api.gametest.ClientGameTest;
import me.roundaround.slothighlights.config.SlotHighlightsConfig;
import me.roundaround.trove.config.option.ConfigOption;
import me.roundaround.trove.gametest.ClientTest;
import me.roundaround.trove.gametest.ClientTestContext;
import me.roundaround.trove.gametest.ClientWorld;
import me.roundaround.trove.gametest.screenshot.Region;
import me.roundaround.trove.gametest.screenshot.ScreenshotSpec;
import net.minecraft.core.BlockPos;

@ClientGameTest
public class HotbarHighlightsGoldenTest implements ClientTest {
  // 427x240 capture (854x480 @ 2): the 182x22 hotbar is anchored bottom-center
  // with the offhand slot ~29px further left; pad a pixel for the selection
  // frame's overhang.
  private static final Region HOTBAR_REGION = Region.gui((427 - 182) / 2 - 30, 240 - 24, 182 + 31, 24);

  @Override
  public void runTest(ClientTestContext context) {
    SlotHighlightsConfig config = SlotHighlightsConfig.getInstance();
    config.getAll().forEach(ConfigOption::setDefault);
    context.onCleanup(() -> config.getAll().forEach(ConfigOption::setDefault));

    try (ClientWorld world = context.worldBuilder().create()) {
      context.stabilizeForScreenshots();

      // The hotbar texture is translucent, so the world shows through it and
      // every run gets a fresh seed. Seal the player in a lightless box and
      // pin the camera so the backdrop is deterministic black.
      BlockPos p = world.playerBlockPos();
      world.runCommand("fill " + (p.getX() - 3) + " " + (p.getY() - 1) + " " + (p.getZ() - 3) + " " +
                       (p.getX() + 3) + " " + (p.getY() + 4) + " " + (p.getZ() + 3) +
                       " minecraft:black_concrete hollow");
      world.runCommand("tp @s " + (p.getX() + 0.5) + " " + p.getY() + " " + (p.getZ() + 0.5) + " 0 0");
      context.waitTicks(10);

      // Slot 0 stays empty so no held item renders into the capture strip.
      world.runCommand("item replace entity @s hotbar.1 with minecraft:golden_apple");
      world.runCommand("item replace entity @s hotbar.2 with" +
                       " minecraft:diamond_sword[custom_name=\"\\u00a7bFrostbrand\"]");
      world.runCommand("item replace entity @s hotbar.3 with" +
                       " minecraft:stick[custom_data={slot_highlight:\"#00FF7F\"}]");
      world.runCommand("item replace entity @s weapon.offhand with" +
                       " minecraft:stone[custom_data={slot_highlight:\"red\"}]");
      context.waitTicks(10);

      context.assertScreenshot(ScreenshotSpec.named("hotbar-highlights").crop(HOTBAR_REGION));

      config.hotbar.setValue(false);
      context.waitTicks(2);
      context.assertScreenshot(ScreenshotSpec.named("hotbar-disabled").crop(HOTBAR_REGION));
    }
  }
}
