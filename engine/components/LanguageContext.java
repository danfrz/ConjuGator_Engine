package engine.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import engine.Engine;
import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;
import engine.components.err.verb.ConjugationException;
import engine.components.irreg.IrregSet;

public class LanguageContext {
	
	private final String[] nonPrefixes;// array of words that begin with prefixes, but conjugate differently
	private HashMap<String, IrregSet> irregulars;
	private HashMap<String, Subject> cqlSubject;
	private HashMap<String, Tense> cqlTense;
	private final String[] prefixes;
	private final Case[] relevantcases; // This is for programatic extraction of all relevant cases, for output in something like a Declension viewer
	/**
	 * The extension from Engine should
	 * declare a bunch of static final Type
	 * for quick access to different types
	 */
	private final Type[] verbtypes;
	/**
	 * The extension from Engine should
	 * declare a bunch of static final ReplaceablePattern
	 * for quick access to different replaceable patterns
	 */
	private final ReplaceablePattern[] patterns;
	/**
	 * The extension from Engine should
	 * declare a bunch of static final PatternShift
	 * for quick access to different pattern shifts
	 */
	private final PatternShift[] shifts;
	private final Subject[] subjects;
	private Engine conjugator;
	private final Tense[] tenses;
	private final boolean Agglutination;
	private final String language;
	private boolean lockproperties = false;
	private final HashMap<String, Object> properties = new HashMap<>();
	public EngineMonitor monitor;
	public final boolean DEVMODE;
	
	/**
	 * 
	 * @param languageName
	 * @param prefixes
	 * @param nonPrefixes
	 * @param irregulars
	 * @param verbtypes
	 * @param verbtenses
	 * @param patterns
	 * @param shifts
	 * @param gendered_conjugation	
	 * @param gendered_declension
	 * @param relevant_cases
	 */
	@SuppressWarnings("unchecked")
	public LanguageContext(String languageName, EngineMonitor mon, String[] prefixes, String[] nonPrefixes,
							Type[] verbtypes, Tense[] verbtenses, ReplaceablePattern[] patterns, PatternShift[] shifts,
							boolean gendered_conjugation, boolean gendered_declension, Case[] relevant_cases, Subject[] relevant_subjects,
							HashMap<String, Subject> colloquial_names_subjects, HashMap<String, Tense> colloquial_names_tenses, boolean devmode)
	{
		language = languageName;
		monitor = mon;
		this.prefixes = prefixes;
		this.verbtypes = verbtypes;
		this.patterns = patterns;
		this.shifts = shifts;
		this.subjects = relevant_subjects;
		
		tenses = verbtenses;
		int i, len;
		Arrays.sort(nonPrefixes);
		this.nonPrefixes = nonPrefixes;
		if(relevant_cases != null)
		{
			relevantcases = new Case[relevant_cases.length]; 
			len = relevantcases.length;
			for(i = 0; i < len; i++)
				relevantcases[relevant_cases[i].ordinal()] = relevant_cases[i];
		}
		else relevantcases = null;
		Agglutination = false; //?
		cqlTense = colloquial_names_tenses;
		cqlSubject = colloquial_names_subjects;
		DEVMODE = devmode;
	}
	
	/**
	 * 
	 * @param languageName
	 * @param prefixes
	 * @param nonPrefixes
	 * @param irregulars
	 * @param verbtypes
	 * @param verbtenses
	 * @param patterns
	 * @param shifts
	 * @param gendered_conjugation	
	 * @param gendered_declension
	 * @param relevant_cases
	 */
	@SuppressWarnings("unchecked")
	public LanguageContext(String languageName, EngineMonitor mon, String[] prefixes, String[] nonPrefixes,
							Type[] verbtypes, Tense[] verbtenses, ReplaceablePattern[] patterns, PatternShift[] shifts,
							boolean gendered_conjugation, boolean gendered_declension, Case[] relevant_cases, Subject[] relevant_subjects,
							HashMap<String, Subject> colloquial_names_subjects, HashMap<String, Tense> colloquial_names_tenses)
	{
		this(languageName, mon,  prefixes, nonPrefixes, verbtypes, verbtenses, patterns, shifts, gendered_conjugation,  gendered_declension,  relevant_cases,  relevant_subjects, colloquial_names_subjects, colloquial_names_tenses, false);
	}
	
	public void setIrregulars(HashMap<String, IrregSet> irregulars)
	{
		if(this.irregulars == null)
			this.irregulars = irregulars;
		else System.err.println("Can't overwrite irregulars after they have been set");
	}
	/**
	 * Disables any user from modifying properties
	 */
	public void lockProperties()
	{
		lockproperties = true;
	}
	
	/**
	 * If adding a collection, it is recommended to use Collections.unmodifiableCollection(),
	 * properties are meant to be unmodifiable.
	 * 
	 * @param property
	 * @param o
	 */
	public void setProperty(String property, Object o)
	{
		if(lockproperties) return;
		if(properties.get(property)==null)
			properties.put(property, o);
		
		else System.err.println("Tried to override " + property + " property.");
	}
	
	public Object getProperty(String property)
	{
		if(!DEVMODE && lockproperties)
			throw new ConjugationException("Properties must be locked before they can be returned in non-devmode.");
		return properties.get(property);
	}
	
	/**
	 * Check whether this language has the given tense
	 * @param t
	 * @return
	 */
	public boolean hasTense(Tense t)
	{
		return Arrays.binarySearch(tenses, t) != -1;
	}
	
	/**
	 * Sets the conjugator object tied to this language, if it has been set.
	 * @param ConjuGator
	 */
	public void setConjugator(Engine ConjuGator)
	{
		if(conjugator != null)
			return;
		conjugator = ConjuGator;
	}
	
	/**
	 * @return the conjugator object tired to this language, if it was set.
	 */
	public Engine getConjugator()
	{
		return conjugator;
	}
	
	/**
	 * Checks if the given infinitive starts with a prefix, but shouldn't be
	 * conjugated according to it. 
	 *  
	 * @param infinitive
	 * @return
	 */
	public final boolean verbContainsNonPrefix(String infinitive)
	{
		int r = Arrays.binarySearch(nonPrefixes, infinitive);
		return r > -1;
	}
	
	/**
	 * @return the name of the language this context refers to.
	 */
	public final String getLanguageName()
	{
		return language;
	}
	
	/**
	 * @param infinitive
	 * @return whether this infinitive should is irregular
	 * in ANY tense
	 */
	public boolean verbIsIrregular(String infinitive)
	{
		return irregulars.get(infinitive) != null;
	}
	
	/**
	 * @param infinitive
	 * @param t
	 * @param s
	 * @return the irregular conjugation for the given infinitive, tense and subject
	 * or null if there is no irregular conjugation 
	 */
	public String getIrregular(String infinitive, Tense t, Subject s)
	{
		IrregSet key = irregulars.get(infinitive);
		if(key != null)
			return key.get(s, t);
		return null;
	}
	/*
	public final int ordinal(Type t)
	{
		int a = t.getBitMask() - 1;
		int len = verbtypes.length;
		if(a < len && a > -1)
		{
			if(verbtypes[a] == t)
				return a;
		}
		
		for(a = 0; a < len; a++)
			if(verbtypes[a] == t)
				return a;
		
		return -1;
	}*/
	
	public Subject[] getSubjects()
	{
		return subjects.clone();
	}
	
	public Tense[] getTenses()
	{
		return tenses.clone();
	}
	
	public PatternShift[] getPatternShifts()
	{
		return shifts.clone();
	}
	
	public ReplaceablePattern[] getReplaceablePatterns()
	{
		return patterns.clone();
	}
	
	public Type[] getTypes()
	{
		return verbtypes.clone();
	}
	
	public Case[] getCases()
	{
		return relevantcases.clone();
	}
	
	/**
	 * @deprecated
	 */
	public final int ordinal(PatternShift s)
	{
		int a = s.getBitMask() - 1;
		int len = shifts.length;
		if(a < len && a > -1)
		{
			if(shifts[a] == s)
				return a;
		}
		
		for(a = 0; a < len; a++)
			if(shifts[a] == s)
				return a;
		
		return -1;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 * @deprecated
	 */
	public final int ordinal(ReplaceablePattern p)
	{
		int a = p.getBitMask() - 1;
		int len = patterns.length;
		if(a < len && a > -1)
		{
			if(patterns[a] == p)
				return a;
		}
		
		for(a = 0; a < len; a++)
			if(patterns[a] == p)
				return a;
		
		return -1;
	}
	
	
	/**
	 * Checks whether infinitive begins with any of the PREFIXES
	 * and removes it.
	 * 
	 * Before removing the prefix, filter to make sure that the prefix is
	 * not a part of the verb itself.
	 * 
	 * For instance "to rearm" would be valid,
	 *  because the root verb 'to arm' still has the same meaning if the prefix were removed.
	 * While Replicate or Reply would not be valid
	 *  because the verbs 'to plicate' and 'to ply' do not maintain the same meaning
	 *  as the original verb, and may conjugate incorrectly. 
	 * 
	 * 
	 * @return an array of two Strings,
	 * [0] is the prefix that was removed,
	 * 	   it may be null if there was no prefix
	 * [1] is the trimmed string
	 */
	public String[] removePrefix(String infinitive)
    {
        infinitive = infinitive.toLowerCase();
        
        String pref;
        int len = prefixes.length;
        for(int i =0; i < len; i++)
        {
        	pref = prefixes[i];
            if(infinitive.startsWith(pref))
                return new String[]{pref, infinitive.substring(pref.length())};
        }
        return null;
    }
	
	/**
	 * @param tense
	 * @return parses the name of the tense and returns
	 * the appropriate Tense object or null if Tense not recognized.
	 */
	public Tense parseTense(String tense)
	{
		return cqlTense.get(tense);
	}
	
	/**
	 * @return an array of the name of what the tenses are called (in English)
	 * in this language. For instance in some languages Subjunctive can only mean Present Subjunctive  
	 * 
	 */
	public String[] getColloquialTenses()
	{
		return getKeySet(cqlTense);
	}
	
	public String[] getColloquialSubjects()
	{
		return getKeySet(cqlSubject);
	}
	
	private static final String[] getKeySet(HashMap<String, ?> hash)
	{
		String[] out = new String[hash.size()];
		int i = 0;
		for(String key : hash.keySet())
		{
			out[i] = key;
			i++;
		}
		return out;
	}
	
	public Subject parseSubject(String subject)
	{
		return cqlSubject.get(subject);
	}
	
	/**
	 * Upon implementing this interface,
	 * create a class for both its Regular and Irregular forms
	 * 
	 * For instance VerbType in English might be
	 * enum VerbType implements Type
	 * {
	 * 		Regular
	 * }
	 * 
	 * Other languages may include more types like in Spanish
	 * 		AR
	 * 		ER
	 * 		IR
	 * 
	 */
	public static interface Type
    {
        public int getIdentifier();
    }
	
	public static interface ReplaceablePattern
	{
		/**
		 * @return the bitmask that corresponds to this object's pattern.
		 * The reason that this and {@link PatternShift} have bitmask instead of identifiers,
		 * is because they are both OR'd into a single integer on save to file.  
		 * 
		 */
        public int getBitMask();
        public PatternShift getDefaultPatternShift();
        /**
         * @return
         */
        public String get();
    }
    
    public static interface PatternShift
    {
    	/**
		 * @return the bitmask that corresponds to this object's pattern.
		 * The reason that this and {@link ReplaceablePattern} have bitmask instead of identifiers,
		 * is because they are both OR'd into a single integer on save to file.  
		 * 
		 */
        public int getBitMask();
        public ReplaceablePattern getDefaultPattern();
    }

}
