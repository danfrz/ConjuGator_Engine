package engine.components;

import java.lang.reflect.InvocationTargetException;


import engine.Engine;
import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;



public class Verb {

	private Verb_Infinitive inf;
	private Tense tense;
	private Subject subject;
	private String conjugated_verb;
	
	private byte conj_success = 0;
	private boolean replacePatternEnabled = true;
	private boolean reaccessorizationEnabled = true;
	public Verb(Verb_Infinitive infinitive)
	{
		this.inf = infinitive;
		
	}
	
	public Verb(Verb_Infinitive infinitive, Tense tense)
	{
		this.inf = infinitive;
		this.tense = tense;
	}
	
	public Verb(Verb_Infinitive infinitive, Subject subject)
	{
		this.inf = infinitive;
		this.subject = subject;
		
	}
	
	public Verb(Verb_Infinitive infinitive, Tense tense, Subject subject)
	{
		this.inf = infinitive;
		this.tense = tense;
		this.subject = subject;
	}
	
	public Verb(Tense tense, Subject subject)
	{
		this.tense = tense;
	}
	
	public Verb(Tense tense)
	{
		this.tense = tense;
		
	}
	public Verb(Subject subject)
	{
		this.subject = subject;
	}
	
	/**
	 * I
	 * @param enabled
	 */
	public void enablePatternReplacement(boolean enabled)
	{
		replacePatternEnabled = enabled;
	}
	
	public boolean isPatternReplacementEnabled()
	{
		return replacePatternEnabled;
	}
	/**
	 * Reaccessorication disabling is so that tenses that require the conjugation of other tenses
	 * can disable extraneous accessories.
	 * On such use case is in spanish where the imperative tense
	 * is a modified version of the present tense yo form, but without disable reaccessorization
	 * a reflexive verb would return with "se " 
	 */
	public void enableReaccessorization(boolean enabled)
	{
		reaccessorizationEnabled = enabled; // damn. thats a big variable name
	}
	
	public boolean isReaccessorizationEnabled()
	{
		return reaccessorizationEnabled;
	}
	
	public void setInfinitive(Verb_Infinitive infinitive)
	{
		this.inf = infinitive;
		this.conjugated_verb = null;
	}
	
	public void setTense(Tense tense)
	{
		this.tense = tense;
		this.conjugated_verb = null;
	}
	
	public void setSubject(Subject subject)
	{
		this.subject = subject;
		this.conjugated_verb = null;
	}
	
	public void set(Tense tense, Subject subject)
	{
		this.subject = subject;
		this.tense = tense;
		this.conjugated_verb = null;
	}
	
	public void set(Verb_Infinitive infinitive, Tense tense, Subject subject)
	{
		this.inf = infinitive;
		this.subject = subject;
		this.tense = tense;
		this.conjugated_verb = null;
	}
	
	
	public Verb_Infinitive getInfinitive()
	{
		return inf;
	}
	
	public Tense getTense()
	{
		return tense;
	}
	
	public Subject getSubject()
	{
		return subject;
	}
	
	public void setConjugated(String conj)
	{
		conjugated_verb = conj;
	}
	
	 public final String Forced_Conjugate()
	    {
	        if(inf == null || subject == null || tense == null)
	        {
	        	conj_success = 0;
	            return null;
	        }
	        Engine e = inf.getLanguageContext().getConjugator();
	        conjugated_verb = e.Conjugate(this);
	        conj_success = e.getConjugationResult();
	        return conjugated_verb;
	    }
	    
    public final String Conjugate()
    {
        if(inf == null || subject == null || tense == null)
        {
        	conj_success = 0;
            return null;
        }
        Engine e = inf.getLanguageContext().getConjugator();
        if(conjugated_verb == null)
        {
            conjugated_verb = inf.getLanguageContext().getConjugator().Conjugate(this);
            conj_success = e.getConjugationResult();
        }
        return conjugated_verb;
    }
	
}
