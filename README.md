ConjuGator_Engine
=================

A flexible abstract engine for verb conjugation and noun declension algorithms
It's currently-ish in development by me, but not too actively.

I'm only studying and creating this as a fun project, and not strictly for any other reason, I am not a linguistics student outside of four years of spanish classes so some of the structural descisions may not be optimal.

If you have any suggestions or criticisms, I'd be very happy to hear them!


Overview of the Design:

The Engine abstract class controls the pathway that strings that are input are actually conjugated. But before it can be conjugated, the verb needs its information to be initialized other than simply passing its string. This is what the Verb_Infinitive and Verb classes are for.

Verb_Infinitive is an asbtract class that is in charge of analyzing the string of the input verb in infinitive form and determining all of its conjugation characteristics such as Irregularity, "Pattern Shifts," and "Accessories."
Irregular verbs in this context are ONLY singular verbs that are exceptional in their conjugation pattern. If there is a pattern present (e.g. in spanish rather than the preterite form of "jugar" returning "jugo" it should be "juego", the irregular flag should not be set (unless of course it is actually uniquely irregular in any other tense or subject conjugation.)

For cases such as that where the verb's conjguation is irregular but follows a pattern, the PatternShift and ReplaceablePattern classes are used to define string patterns that conjugate "semi-regularly" and define in what cases how those types of verbs should be conjugated.

"Accessories" is just a generic word for prefixes and/or suffixes and any other accessories to verbs a language might have such as the spanish reflexive 'se' (verb correr + se, correrse).

When the Conjugator is initialized, it calls createLanguageContext(), which initializes a LanguageContext defined by that language's implementation.

A LanguageContext object has many parameters: (in retrospect at this point they should just be made into methods so that the constructor isnt so massive)

A list of tenses that that specific language uses and a list of their "colloquial" titles.
A list of subjects that that specific language uses and a list of their "colloquial" titles.
(The list of colloquial tense titles are used for parsing Irregular files and for output)
A list of cases that the language uses.
A list of subjects (FirstSingular, SecondMultiple, etc.) that that language uses.
And some other things.

Any of these can be null, but expect to have problems if you want to use that part of the engine that requires that information.

After the language context is created, the conjugator finishes initialization and can be used.
In order to use the conjugator, you must take your infinitive (String) and create a Verb_Infinitive. When you create a Verb_Infinitive, the accessories (prefixes, suffixes) are removed and stored for later and all that infinitive's information is initialized. After the Verb_Infinitive is created, wrap that infinitive in a Verb object along with what Tense and Subject you would like to conjugate to.

Now that you have that Verb, pass it to your extension from the conjugation engine  
and it should check for irregularities, then pass the resulting Verb to the diverge(Verb v) function, from which is implemented in your language's extension of the engine. 

diverge(Verb v) is what does all of the work of actually conjugating, and implementation after that point is up to who ever writes the actual algorithm.
