![Slot Highlights](https://raw.githubusercontent.com/Roundaround/mc-slot-highlights/refs/heads/main/assets/slothighlights-title-round.png)

[![GitHub Repo stars](https://img.shields.io/github/stars/Roundaround/mc-slot-highlights?style=flat&logo=github)](https://github.com/Roundaround/mc-slot-highlights)

[![Support me on Ko-fi](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/donate/kofi-singular-alt_vector.svg)](https://ko-fi.com/roundaround)

Draws a colored border around inventory slots based on the item inside, so rare, renamed, and enchanted items are easy to spot. Fully client-side, so it works on any server.

> **Status:** unreleased. Not yet on Modrinth or CurseForge, so `allay.modrinth`/`allay.curseforge` project IDs are unset, the publish tasks skip, and there are no store badges yet.

## Building from source

```sh
./gradlew build
```

Dev runs are per loader: `:fabric:runClient`, `:neoforge:runClient`, `:forge:runClient`, and the `runServer` equivalents. Game tests run with `./gradlew :fabric:runClientGameTests` and `:fabric:runServerGameTests`.

The build is an [Allay](https://github.com/Roundaround/allay) consumer and bundles [Trove](https://github.com/Roundaround/trove). Shared code lives in `common/` and is added to each loader subproject via `srcDir`.

## Contributing

Issues and pull requests are welcome at [the issue tracker](https://github.com/Roundaround/mc-slot-highlights/issues).

- Branch from `main`, which tracks the newest supported Minecraft version. Older lines live on their own version-named branches.
- Keep loader-agnostic code in `common/`; only genuinely loader-specific glue belongs in a loader subproject.
- Run `./gradlew build` plus the Fabric game tests before opening a PR, and add a changelog entry under `changelogs/` named for the version you're targeting.

## License

[MIT](LICENSE)
