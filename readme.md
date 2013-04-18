Hangman
=======

This is assignment 4 from the Stanford Engineering Everywhere course in Java,
available at <a
href="http://see.stanford.edu/see/courses.aspx">see.stanford.edu</a>.

The game is Hangman.  A word is chosen randomly from a Lexicon and the player
must guess that word letter by letter before he is hung.  The player is hung
after 8 incorrect guesses.  After each incorrect guess a part of a stick figure
body is drawn on the gallows.  First the head, then the torso, then an arm,
etc.  If every body part is drawn before the word is guessed, the player loses.
Otherwise, the player wins.

The main class is called Game.  It is a subclass of the acm class ConsoleProgram.  The game is played through a console interface, and the progress is recorded in a GUI screen next to the console.

TODO: Activate mouse listeners in canvas so that either a console guess or a
mouse click guess can be entered.  This is a good way to learn about
concurrency. Since the console method `readLine()` occupies the main thread
completely, I need two threads, one listening for a mouse click on the letters
and another listening for letters typed into the console.  If either thread
finishes, the other should be cancelled and the result evaluated.
