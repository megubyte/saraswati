# Saraswati

My friends over at [Shadow Empire](http://shadowempire.in) wanted a quick and easy chat implementation that works across BungeeCord and also utilises the many events in both systems to get a IRC relay that works and shows events that are also notable, not just messages, that can be relayed.

With the previous system there's a lot of fluff that happens in the IRC relay, and there's other things that won't be relayed. Death notices won't be relayed, and /me command output won't also be relayed to IRC. When switching servers you also get this issue where there's a little bit of extra lines than should be neccesary.
```
<relay> [hub] hyprdrv connected
<relay> [creative] hyprdrv connected
<relay> [hub] hyprdrv disconnected
```
This sort of spam becomes a thing of the past!
```
<relay> [hub] hyperdrv connected
<relay> [hub] hyperdrv » [creative]
```

The new chat system will relay full colour, /me messages, and has nice useful features that add that little extra to the game such as pinging users, sounds on messages and more.
