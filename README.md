
# Sculk Microphone
A Minecraft 1.18.2 mod by Towo.
Forge required.
*There are currently no plans to make this mod runnable on Fabric.*

## Downloads
[Sculk Microphone v1.0.0-alpha]()

## Installation
1. [Install Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) if you haven't already.
2. [Download a release of Sculk Microphone](https://github.com/Towodile/SculkMicrophoneMod/releases).
3. Put the *.jar* file in Minecraft's mod folder.
4. Run the game using Forge.
5. If applicable, make sure to grant the application access to your microphone.
6. If you want, you can also install [*Simple Voice Chat*](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)

## How it works
Simply run the mod, and talk into your microphone! Any sculk sensor in a 6 (value is prone to change) block radius should activate. If not, try editing your settings.

Note that in this Minecraft version, Sculk Sensors are unobtainable in the Creative Menu. Type */give @s minecraft:sculk_sensor* to receive one.

## Features
### Current
- Sculk Sensor activation when talking into your microphone.
- Comparators linked to Sculk Sensors will output a redstone signal *(1 to 15)* based on the input volume.
- Customisable options (Options -> Microphone Settings...).
	- Enable/disable microphone listening
	- Microphone sensitivity
	- Sculk Sensor activation threshold
	- Enable/disable additional info on-screen for testing:
		- Your volume and the status of your microphone (enabled/disabled) will appear in-game. When the volume threshold is met, the color of the displayed volume should appear blue.
- Compatible with [henkelmax's *Simple Voice Chat*](https://www.curseforge.com/minecraft/mc-mods/simple-voice-chat)! (proximity chat)
	- *A player's volume does not get carried over when using this mod.*

### To-do
- [ ] Option to change your preferred microphone for users that play without the Simple Voice Chat mod.
- [ ] Update mod to Minecraft version 1.19.
	- [ ] Make Warden mob from this version react to microphone sounds.


### Showcase
<img src="https://media0.giphy.com/media/z9s04twpovuyyzbP3x/giphy.mp4?cid=790b761185392b186c06cf2e096b98e103cdd518a8f605f2&rid=giphy.mp4&ct=g"> 

Simple Voice Chat compatibility! Notice how the microphone symbol in the bottom left corner appears just before the sculk sensor activates.
<br>


#
*Sculk Microphone (c) by Towo*

*Sculk Microphone is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.*

*You should have received a copy of the license along with this work. If not, see http://creativecommons.org/licenses/by-nc-sa/4.0/.*
