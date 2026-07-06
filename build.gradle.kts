plugins {
  id("me.roundaround.allay")
}

allay {
  displayName.set("Slot Highlights")
  description.set("Add colored borders to inventory slots based on item rarity, names, and enchantments.")
  authors.set(listOf("Roundaround"))
  license.set("MIT")
  repository.set("https://github.com/Roundaround/mc-slot-highlights")
  issues.set("https://github.com/Roundaround/mc-slot-highlights/issues")
  logoFile.set("assets/slothighlights/banner.png")

  release {
    versionType.set("release")
    minecraftVersions("26.1".."26.1.2")
  }
}
