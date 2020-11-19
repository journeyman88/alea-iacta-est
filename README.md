# alea-iacta-est
Alea Iacta Est is a Discord bot designed to help running RPG sessions over the network.

It can be added to your server using this link:
https://discord.com/oauth2/authorize?client_id=777835776757989427&scope=bot&permissions=0

The bot supports system specific rolls and also has an expression solver to work on more generic rolls.
## Expression Engine
The expression engine can solve compounded expression in algebrical sum, and can interpret, beside simple integer constants, the following notations:

- **Simple Dice notation** *NdX*, for example 2d8 - which means roll two dice each with eight faces.
- **Roll'n'Keep notation** *NdXkY*, for example 10d6k5 - which means roll ten dice each with six faces, then sum together the 5 highest values
- **Roll Above notation** *NdX/Y*, for example 5d10/7 - which means roll five dice each with ten faces, then count those with a value higher than, or equal to seven
- **Roll Below notation** *NdX\Y*, for example 3d20\13 - which means roll three dice each with twenty faces, then count those with a value lesser than, or equal to thirteen


## Supported systems
The system-specific code is isolated in several plugins, as such the best way to know which systems the instance can support is using the `!alea system` command, which reports a list of systems and the relative commands (generally two, a 3 character shortcut and a long command).

At the moment of writing the systems supported are the following:

- 7th Sea 2nd Edition
- Brass Age
- Project H.O.P.E. 2nd Edition
- Shadowrun 5th Edition
- The Dark Eye 5th Edition
- Vampire the Masquerade 5th Edition
