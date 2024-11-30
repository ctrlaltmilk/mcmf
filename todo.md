# Important
* Custom command for spawning in sroms
  * Drag+drop upload flasher
* ~~UXN expansion memory slots~~
  * Put these slots in the UI
* Remove computers from execution when unloaded
* UXN serial /type support

# Less Important
* Argument parsing (from serial i/o events)
* Actually implement device sided-ness
* Serial device sided-ness
* Serial Terminal
  * Escape code support (in progress)
  * Save state to NBT data

# Ideas
* Work "Magic Smoke Demon: a mob that is spawned whenever a computer block is blown up or destroy by unconventional means. This mob drops captured magic smoke: an item used to craft capacitors which can be thrown and explode on impact.
  " into something more reasonable
  * Maybe it spawns when the computer is broken while running
    * broken computer drops blown capacitors
    * magic smoke monster drops magic smoke
      * reloads blown capacitors
      * maybe used for higher tiers of computers????
* [UXF font](https://wiki.xxiivv.com/site/ufx_format.html) support 
  
# MVP Requirements
* Fully functional device # selection
* No computer execution leaks

# Release Requirements
* Rendered in world screen devices
