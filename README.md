# AutoReply

AutoReply is a mod that reads in-game chat and parses them based on phrases to automatically reply to the chat.

## Building

```
./gradlew build
```

## Installing

Place the .jar in your mods folder with [Fabric](http://fabricmc.net/use/) and [Cotton](https://minecraft.curseforge.com/projects/cotton?gameCategorySlug=mc-mods&projectID=318160) installed.

## How to Use

AutoReply comes with a bunch of commands to customize responses.
- `/arrecurtick [Number of Ticks Between Sends]` sets the number of ticks between each recurring message.
- `/arrecurphrase [Recurring Phrase]` sets the recurring message.
- `/arrecurtoggle` toggles the recurring message.
- `/arrecur [Enabled?]` turns on/off the recurring message.
- `/aradditem [Item Name] [Trigger Term]` adds a Trigger Term that is recognized from chat.  Item Name will be associated with that term.  For example: Item Name = "Slimeballs", Trigger Term = "slime".
- `/araddin [In Phrase]` adds a pre-trigger term phrase such as "anyone selling" or "anyone have".
- `/araddout [Out Phrase]` adds a phrase to be said after Item Name.  For example: "[item] can be found at /warp Shop!"  [item] will be replaced with the associated Item Name.
- `/arreload` reloads the AutoReply config file.
