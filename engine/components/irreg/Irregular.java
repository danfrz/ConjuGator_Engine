package engine.components.irreg;

import java.util.HashMap;

import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;

public interface Irregular
{
	public static final int
	/**
	 * Reserved for sets that hold irregulars
	 */
	IRGPTRN_SET = 0,
	/**
	 * This pattern assumes that the text read in the irreg file has a regular subject pattern,
	 * take for instance this excerpt from the Spanish irregulars file:
	 * 
	 * ir #T @PRESENT voy vas va vamos vasteis van
	 * 
	 * That line assumes the subject pattern yo, tu, elellaud, noso, voso, ellasellosuds,
	 * and all lines that follow the tense irregpattern should be consistent with whatever
	 * verb is listed using this irregpattern.
	 */
	IRGPTRN_TENSE = 1,
	
	/**
	 * This pattern assumes that the text read in the irreg fule has a regular tense pattern,
	 * but that this verb has different conjugations on one(or multiple) specific subjects.
	 * 
	 * ir #S @NOSOTROS vamos fuimos fuera (or whatever future tense is, don't remember) 
	 * 
	 * This means that there must be some implied order to the listing of tenses, instead of subjects
	 */
	IRGPTRN_SUBJECT = 2,
	/**
	 * If you really want to be specific about it and define exactly what the rules are you can use this pattern.
	 * Using this pattern nothing is implied, see below:
	 * 
	 * ir #P @PRETERITE @YO fui @TU fuiste @PRESENT @NOSOTROS vamos @UDS van @FUTURE fuera'n @CONDITIONAL fueri'an
	 * 
	 * The subject and tense is defined on parsing by the last occuring token of that corresponding type.
	 */
	IRGPTRN_SPECIFIC = 3;
	
	/**
	 * @param s
	 * @param t
	 * @return the appropriate irregular conjugation for this verb
	 * 	as according to the given subject and tense.
	 * 	
	 * returns null if the parameters provided don't result
	 * 	in an irregular conjugation.
	 */
	public String get(Subject s, Tense t);

	public String get(Subject s, Tense t, IrregProperty... props);
	/**
	 * @return the infinitive that this irregular corresponds to
	 */
	public String getInfinitive();
	/**
	 * @return the pattern type constant corresponding to this irregular class type
	 */
	public int getPattern();
	/**
	 * Attempts to merge two irregular object's data together,
	 * @returns true if the merge was successful and
	 * 			false if the classes were of incompatable types or
	 * 				otherwise not mergeable 
	 */
	public boolean merge(Irregular other);
	
	
	
	
}
