![Slot Highlights](https://raw.githubusercontent.com/Roundaround/mc-slot-highlights/refs/heads/main/assets/slothighlights-title-round.png)

![](https://img.shields.io/badge/Loader-Fabric%20|%20Forge%20|%20NeoForge-313e51?style=for-the-badge)
![](https://img.shields.io/badge/MC-26.1-313e51?style=for-the-badge)
![](https://img.shields.io/badge/Side-Client-313e51?style=for-the-badge)

[![Modrinth Downloads](https://img.shields.io/modrinth/dt/custom-paintings-mod?style=flat&logo=modrinth&color=00AF5C)](https://modrinth.com/mod/custom-paintings-mod)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/1560408?style=flat&logo=curseforge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/rounds-custom-paintings)
[![GitHub Repo stars](https://img.shields.io/github/stars/Roundaround/mc-slot-highlights?style=flat&logo=github)](https://github.com/Roundaround/mc-slot-highlights)

[![Support me on Ko-fi](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/donate/kofi-singular-alt_vector.svg)](https://ko-fi.com/roundaround)

-----

Ever lose track of the good stuff in a chest full of junk? Slot Highlights draws a colored border around inventory
slots based on the item inside, so rare, renamed, and enchanted items are easy to spot. It works in every container
screen as well as on the hotbar, and since it's fully client-side you can use it on any server.

## Highlight conditions

Each item is checked against three conditions, in order, and the first one that matches picks the border color:

1. **Custom names** - Items renamed with an anvil get their own highlight, gold by default.
2. **Enchanted** - Enchanted items (including enchanted books) can get a dedicated color. This one is off by default,
   since the game already bumps the rarity of enchanted items and they'll show up with a rarity highlight anyway.
3. **Rarity** - Items are highlighted with their rarity's color: yellow for uncommon, aqua for rare, and light purple
   for epic. The color is read straight from the rarity itself, so if another mod adds its own rarities, those will
   highlight with the right colors without any extra setup. Common items are skipped by default to keep your inventory
   from turning into a wall of white borders.

## Border style

By default the border fades in toward the bottom of the slot, which keeps things fairly subtle. If you want something
bolder, you can draw the full ring instead, add an inner glow, bevel the corners, or draw the border on top of the item
rather than behind it.

## Mod configuration

All settings are client-side and live in `<minecraft directory>/config/slothighlights.toml`. The easiest way to change
them is in game, either through [Mod Menu](https://modrinth.com/mod/modmenu)'s configure button on Fabric or the mod
list's config button on NeoForge and Forge.

**Highlight hotbar slots** (`hotbar`): `true|false` - Whether to also draw highlights on the hotbar and offhand slots.
Default is `true`.

**Draw full border** (`fullBorder`): `true|false` - Whether to draw the entire border ring at full strength. When
`false`, the border instead fades from transparent at the top of the slot to opaque at the bottom. Default is `false`.

**Square corners** (`squareCorners`): `true|false` - Whether to fill in the border's corner pixels. When `false`, the
corners are clipped for a beveled look. Default is `true`.

**Draw over items** (`overItems`): `true|false` - Whether to draw the border on top of the item icon instead of
underneath it. Default is `false`.

**Extra glow** (`extraGlow`): `true|false` - Whether to add a soft glow just inside the border. Default is `false`.

**Highlight named items** (`highlightNamed`): `true|false` - Whether to highlight items that have been given a custom
name, e.g. with an anvil. Default is `true`.

**Named item color** (`namedColor`): `Hex color; #RRGGBB` - The highlight color for custom-named items. Default is
`#FFAA00`.

**Highlight enchanted items** (`highlightEnchanted`): `true|false` - Whether to give enchanted items (including
enchanted books) a dedicated highlight color instead of their rarity's. Default is `false`.

**Enchanted item color** (`enchantedColor`): `Hex color; #RRGGBB` - The highlight color for enchanted items. Only
applies when `highlightEnchanted` is `true`. Default is `#B24BF3`.

**Highlight items by rarity** (`highlightRarity`): `true|false` - Whether to highlight items using their rarity's
color. The color is read from the rarity itself, so modded rarities use their own colors automatically. Default is
`true`.

**Include common items** (`highlightCommon`): `true|false` - Whether rarity highlighting also applies to common
(white) items. Most items are common, so this gets noisy. Only applies when `highlightRarity` is `true`. Default is
`false`.

**Rarity color overrides** (`rarityColors`): `List[Text]; "<rarity>=#RRGGBB"` - Manual color overrides for individual
rarities, keyed by rarity name, e.g. `"epic=#FF55FF"`. Works for modded rarities too. This option does not show up on
the config screen and can only be edited in the config file. Default is `[]` (empty).
