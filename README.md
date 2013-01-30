In a somewhat recent interview, I was asked how I would add Roman numbers together without first
converting them into decimal notation.  I had to work completely within the string domain.
I stumbled through the question and did not present a good answer.  I went home and made an attempt
to code a working solution.  I sent it in the solution.  I looked it over months later and
discovered it had a bug.  I missed a fairly important test case.  My bad.  I made another attempt with
a better set of test cases.  This is my solution.

Entry Points
============

There is a crude executable [Main][main] class that you can execute.  It takes two Roman numerals from the command line, adds
them together, and prints the result to stdout.

If you have the intent to use this body of code as a library, consider using the class [RomanAdder][romanadder] as
your entry point.  It has one public method, Add.  This method takes two strings as arguments and returns the sum in 
string format.  It will throw Runtime exceptions if you pass it invalid Roman numerals. 

Unit Tests
==========

There are unit tests located [here][junit].

Enjoy.

[main]: romanadder/tree/master/src/com/lehmoine/romantwo/Main.java
[romanadder]: romanadder/tree/master/src/com/lehmoine/romantwo/RomanAdder.java
[junit]: romanadder/tree/master/src/com/lehmoine/romantwo/junit
