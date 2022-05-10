
# Sculk Microphone
A Minecraft mod by Towo.
<br><br>Forge required.
<br>*Microphone may not work on MacOS due to system's privacy setting*

Best experienced with [proximity chat](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)!

## Downloads
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/sculk-microphone/files/all)
[PlanetMinecraft](https://www.planetminecraft.com/mod/sculk-microphone/)

## Installation
1. [Install Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) if you haven't already.
2. [Download a release of Sculk Microphone](https://github.com/Towodile/SculkMicrophoneMod/releases).
3. Put the *.jar* file in Minecraft's mod folder.
4. Run the game using Forge.
5. If applicable, make sure to grant the application access to your microphone.
6. If you want, you can also install [*Simple Voice Chat*](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)

## Features
### Current
- Sculk Sensor activation when talking into your microphone.
- Comparators linked to Sculk Sensors will output a redstone signal *(1 to 15)* based on the input volume.
- Customisable options.
- Configurable with commands.
- Compatible with [henkelmax's *Simple Voice Chat*](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)! (proximity chat)
	
![Simple Voice Chat compatibility](https://i.giphy.com/media/t9tWlZaPS6yJKNoUao/giphy.webp)

### To-do
- [ ] Option to change your preferred microphone for users that play without the Simple Voice Chat mod.
- [ ] Update mod to Minecraft version 1.19.
	- [ ] Make Warden mob from this version react to microphone sounds.


<br>

## How it works
Simply run the mod, and talk into your microphone! Any nearby sculk sensor should activate. If not, try editing your settings.

### Settings
The default settings aren't necessarily the best settings for every microphone, so feel free to change some in 'Options -> Microphone Settings...'
#### Microphone
This options simply allows you to disable the microphone whenever you wish. This option will be disabled by default whenever another microphone-using mod is installed.
#### Sensitivity
The greater the value, the easier it is for your volume-level to rise. If you notice your microphone picking up a lot of small sounds, try turning this down.
#### On-Screen Info
For testing-purposes, you can enable this setting and view your current microphone status along with your volume level. The color of this text will turn blue once the threshold for sculk listeners has been met.
#### Sculk Sensor Threshold
This value will determine at what volume level a sculk sensor will activate.

### Commands
Somethings are only configurable with commands. These are often things that affect all players in a server.
#### /sculkmicrophone set ...
##### distance
Typing */sculkmicrophone set distance [value]* will set the maximum distance at which a player will have to be to voice-activate a sculk sensor.

##### doDynamicRedstone
Typing */sculkmicrophone set doDynamicRedstone [true|false]* will enable or disable whether a redstone comparator should output a signal depending on the volume of an incoming (microphone) vibration.
Please note: for clients that have this mod's microphone disabled and instead have another mod's microphone enabled, this will always be *false*.

##### defaultRedstoneSignal
Typing */sculkmicrophone set defaultRedstoneSignal* will set the value a comparator outputs if it will not dynamically do so.


#
*Sculk Microphone (c) by Towo*

*Sculk Microphone is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.*

*You should have received a copy of the license along with this work. If not, see http://creativecommons.org/licenses/by-nc-sa/4.0/.*
