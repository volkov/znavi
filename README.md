# znavi

![Build](https://github.com/volkov/znavi/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/15771.svg)](https://plugins.jetbrains.com/plugin/15771)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/15771.svg)](https://plugins.jetbrains.com/plugin/15771)

<!-- Plugin description -->
Provides additional navigation actions for java in intellij idea.
## Show usages of containg method (`alt H`)
"Find usages" intellij action with call hierarchy is very powerful, but it always opens in tool window. 
Current plugin allows making one step of call hierarchy with popup menu ("Show usages of containing method").

## Go to parameter declaration (`alt O`)
Navigate to parameter declaration from argument. 
Usefull with idea builtin "Next Identifier" or "Navigate to next usage" from this plugin.

[Demo video](https://youtu.be/ZOxGMqgNVo0)

## Navigate to next(`alt U`)/previous(`alt I`) usage
Also plugin adds navigation to next/previous usage of element under cursor. 
It's interation over "Show usages" popup without opening popup.

Most of the code copied and pasted from origina intellij actions.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "znavi"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/volkov/znavi/releases/latest) and install it manually using
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
