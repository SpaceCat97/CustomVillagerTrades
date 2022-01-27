Current supported Minecraft Versions: 1.16, 1.18

CurseForge: https://www.curseforge.com/minecraft/mc-mods/custom-villager-trades
Discord: https://discord.gg/R4yaRqr2gN

Custom Villager Trades is a mod that integrates seamlessly into the vanilla villager trading system.
Ideal for ModPack authors, it uses JSON filetypes to allow you to create and customise your own trades.

When first loaded, this mod will generate example trades for the armorer profession.

HOW DOES IT WORK?
This mod reads json files from the "config/custom villager trades" directory. The directory will be created (with an example file) when you load the mod for the first time (and if no config files are present).
The profession modified will depend on what you put inside the file.
After getting the trades, it will inject in to the villager trade event provided by forge (exactly how other mods do it), the only difference is you can create the trade exactly how you want it.
The event is set as "LOWEST" priority. This means that it should run last (allowing you to overwrite modded trades - no promises!).
The mod will perform a check on each trade to make sure they are valid. If there are any problems it will then report this on the console.
The file must be built as specified in the wiki. Examples are provided.


FEATURES
- A fully customizable trade creation system where you can define basic or advanced trades using JSON.
- Modify trades for any profession using a simple named config file (including the nitwit).
- You can set "global" trades, these will be applied to any villager that can trade (call the config file "all.json")
- Trades can be applied based on level.
- All aspects of the trade can be modified (trade experience, discounts, etc.)
- Enchantments can be applied to any trade item. This allows for whacky unique items that can't be acquired via normal (vanilla) means.
- Enchantments can now be randomized in 3 ways, read below.
- Enchantments can be blacklisted, usable with random enchantment feature.
- Modded item and block support.
- Modded profession support - you can add trades to other people's villagers.
- Remove all vanilla trades - you can do this per-profession.
- Vanilla wanderer trades can be modified/replaced.
- Custom potion trades, including random and blacklisted.
- Custom suspicious stew trades.
- Custom treasure map trades (with modded structure support).
- Custom tipped arrow trades (with modded potion effect support). Includes random and blacklisted.
- Custom potions and tipped arrows can be coloured.
- Force an NPC's level up whilst in creative (press UP arrow key).
- Custom NBTs (basic and advanced).
- Modder/developer support.


WARNINGS / FAQ
Note that this mod does not give you the option to create trades for villagers who have no profession/unemployed.
Your trade is not guaranteed to appear every time - this is because of the trade pools. You can remove all existing trades.
If you are unsure what the item ID is, press f3+h ingame to enable tooltips and hover over the item you want to add.
If you have any problems launching the game with a custom config, please first start the game without a config and see if it goes to the title screen. If it does then your config is not formatted correctly. There will also be an error message in the console/log if it is caused by custom villager trades.
If you remove a trade that a villager already has, the villager will still keep that trade because it is stored in the NBT.
The wanderer must have a minimum of 5 "common" and 1 "rare" trades.


"MY TRADE IS NOT APPEARING"
If your config entry is not created properly, the mod will throw a warning explaining what is wrong with that entry. It will then try to add the rest of the trades.
This is an example of what you would see if there is a problem:
[14:26:03] [Worker-Main-9/INFO] [customvillagertrades/]: Checking custom villager trades...
[14:26:03] [Worker-Main-9/WARN] [customvillagertrades/]: Unable to add a custom trade! Reason: item/block does not exist - dotcoinmod:ironcoin
[14:26:03] [Worker-Main-9/INFO] [customvillagertrades/]: Check complete!

If you don't know the JSON language, please take the time to learn it before coming to me with problems - it is an easy language to learn, read and understand.
