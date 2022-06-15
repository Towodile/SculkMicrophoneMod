

# Sculk Microphone
A Minecraft Forge mod by Towo.

## Downloads
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/sculk-microphone/files/all) 

[PlanetMinecraft](https://www.planetminecraft.com/mod/sculk-microphone/)

## Installation
1. [Install Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) if you haven't already.
2. [Download a release of Sculk Microphone](https://github.com/Towodile/SculkMicrophoneMod/releases).
3. Put the *.jar* file in Minecraft's mod folder.
4. Run the game using Forge.
5. If applicable, make sure to grant the application access to your microphone.

## Current Features
- Sculk Sensor activation when talking into your microphone.
- A Warden will be alerted when you speak.
- Comparators linked to Sculk Sensors will output a redstone signal *(1 to 15)* based on the input volume.
- Customisable options.
- Configurable with commands.
	
![Example using Simple Voice Chat to indicate talking](https://i.giphy.com/media/t9tWlZaPS6yJKNoUao/giphy.webp)
<br>

## How it works
Simply run the mod, and talk into your microphone! Any nearby sculk sensor should activate. If not, try editing your settings.

### Settings
The default settings aren't necessarily the best settings for every microphone, so feel free to change some in 'Options -> Microphone Settings...'
#### Microphone
This options simply allows you to disable the microphone whenever you wish.
#### Sculk Sensor Threshold
This value will determine at what volume level a sculk sensor will activate / a Warden will hear you.

### Commands
Somethings are only configurable with commands. These are often things that affect all players in a server.
#### /sculkmicrophone set ...
##### distance
Typing `/sculkmicrophone set distance [value]` will set the maximum distance at which a player will have to be to voice-activate a sculk sensor.

##### doDynamicRedstone
Typing `/sculkmicrophone set doDynamicRedstone [true|false]` will enable or disable whether a redstone comparator should output a signal depending on the volume of an incoming (microphone) vibration.

##### defaultRedstoneSignal
Typing `/sculkmicrophone set defaultRedstoneSignal [value]` will set the value a comparator outputs if it will not dynamically do so.


*This mod is still in development. There'll be more features and changes to come. Any feedback is appreciated! I hope you enjoy.*

#
*Sculk Microphone (c) by Towo*

*Sculk Microphone is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.*

*You should have received a copy of the license along with this work. If not, see http://creativecommons.org/licenses/by-nc-sa/4.0/.*
