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

## Configuration

The configuration file can be located at .minecraft/config/AutoReplyConfigFile.json5.
- `persistentChat (boolean)` determines whether to chat a phrase repeatedly or not.
- `persistentPhrase (String)` is the message that gets sent repeatedly.
- `timeBetween (int)` is the number of ticks between message sends.
- `terms (ArrayList<String>)` are the terms that the mod looks for in chat paired with the output item name.  For example, the entry "slime|Slimeballs" will look for "slime" in chat and use "Slimeballs" as the output item name.
- `ins (ArrayList<String>)` are the introductory phrases that the mod looks for such as "anyone selling" or "who has".
- `outs (ArrayList<String>)` are the concluding output phrases.  The string `[item]` gets replaced with the item name.  For example, the entry "[item] can be found at my shop!" outputs to "Slimeballs can be found at my shop!".
- `shopItems (ArrayList<String>)` is a list of what you have in shop.  This is used if someone wants to learn more about an item you have.  An example entry would be "Slimeballs|1|$25" where the first part is the item name, second part is the quantity sold at a time, and the third part is the price of the item including the monetary symbol.
- `blacklist` is a list of words or phrases to not respond to.  This is important to prevent spamming the chat.  For example, if someone buys from your shop and every time they do so the shop plugin prints "[Shop] Player has bought 1 slimeball from your shop" then a good item to add to the blacklist is "[shop]".
