08/28/2025
Finished all main mechanics, To add EnPassant, and make illegal Castle into a check. Also Need to add a bunch of test and fix that bug with
NotificationHandler.java

08/(29-30-31)/2025
I have finished the test and with that the project, I still haven't implemented En Passant nor Making castling when under check impossible.
I still consider myself quite content especially with the test system that I have created (Sadly I would have loved to find a error resistant
library to also test some non correct cases but it's probably fine). Also I have discovered that for every test he create a new instance of the
class and having put a process generator in a constructor it crashed my pc a bunch of times :(. If I found the energy I will do some refactor
in a week or two, I'm sure there is plenty to refine around the edges.

09/24/2025
I decided to transform the base of the game to a chess server to serve the godot frontend i'm building, the GUI part is gone since it was pretty busted and I made the project quarkus base to make a bit more professional (I tough about using spring-boot to learn something different but I'm not really in hard coding mood since I'm working much more these dayes)