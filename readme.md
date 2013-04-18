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

The main class is called Game.  It is a subclass of the acm class
ConsoleProgram.  The game is played through a console interface, and the
progress is recorded in a GUI screen next to the console.

Added to original assignment:
1.  Letters change color when they are guessed.  For example if the player
	guesses 'A' then the background of the square containing 'A' changes to
green if the word contains 'A' and turns green otherwise.

2.  An animation of falling confetti is shown at the end of the game upon
	winning.

3.  A letter can be guessed either by typing it or by clicking it.  This was a
	way for me to learn to use `synchronized` blocks as well as `wait()` and
`notify()`.

