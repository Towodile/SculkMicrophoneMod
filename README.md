
<img src="https://i.postimg.cc/sfhVzsKQ/logo.png" alt="logo" width="1000"/>
A Minecraft Forge mod by Towo. <br>
Allows sculk-related blocks and entities to listen to your microphone!

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
![Example using Simple Voice Chat to indicate talking](https://i.giphy.com/media/ghaaaska1fdGEwVheC/giphy.webp)
<br> Above GIFs use the Simple Voice Chat mod to indicate the player is talking.

## How it works
Simply run the mod, and talk into your microphone! Any nearby sculk sensor/warden should activate. If not, try editing your settings.

### Settings
- **Microphone** <br>This options simply allows you to disable the microphone whenever you wish.
- **Sculk Sensor Threshold** <br>This value will determine at what volume level a sculk sensor will activate / a Warden will hear you.

*After editing your settings, be sure to hit 'Apply' or 'Done' to apply your changes!*

### Commands
Somethings are only configurable with commands. These are often things that affect all players in a server.
#### /sculkmicrophone set ...
- `/sculkmicrophone set distance [value]` will set the maximum distance at which a player will have to be to voice-activate a vibration listener.
- `/sculkmicrophone set doDynamicRedstone [true|false]` will enable or disable whether a redstone comparator should output a signal depending on the volume of an incoming (microphone) vibration.
- `/sculkmicrophone set defaultRedstoneSignal [value]` will set the value a comparator outputs if it will not dynamically do so.


*This mod is still in development. There'll be more features and changes to come. Any feedback is appreciated! I hope you enjoy.*

#
*Sculk Microphone (c) by Towo*

*Sculk Microphone is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.*

*You should have received a copy of the license along with this work. If not, see http://creativecommons.org/licenses/by-nc-sa/4.0/.*
