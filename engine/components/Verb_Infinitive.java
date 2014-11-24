package engine.components;

import engine.components.LanguageContext.*;
import engine.components.err.verb.LanguageContextException;



public abstract class Verb_Infinitive
{
	protected final LanguageContext context;
	protected final boolean irregular;
	protected final String infinitive;
	protected final Type type;
	protected final PatternShift shift;
	protected final ReplaceablePattern pattern;
	protected final Accessories acc;
	
	public Verb_Infinitive(String infinitive, LanguageContext context)
	{
		this.context = context;
		infinitive = infinitive.toLowerCase();
		
		
		acc = removeAccessories(infinitive);
		if(acc != null)
		{
			int trim = acc.getTRIMIndex();
			if(trim != -1)
				infinitive = acc.getAccessories()[trim];
		}
		this.infinitive = infinitive;
		
		irregular = context.verbIsIrregular(infinitive);
		
		Object[] info = getInfinitiveInfo(infinitive);
		if(info != null)
		{
			type = (Type)info[0];
			shift = (PatternShift)info[1];
			pattern = (ReplaceablePattern)info[2];
		}
		else {
			type = null;
			shift = null;
			pattern = null;
		}
	}

	public Verb_Infinitive(String infinitive, LanguageContext context, Type t, PatternShift pshift, ReplaceablePattern reppat) throws LanguageContextException
	{
		this(infinitive, context, t, pshift, reppat, null);
	}
	
	public Verb_Infinitive(String infinitive, LanguageContext context, Type t, PatternShift pshift, ReplaceablePattern reppat, Accessories a) throws LanguageContextException
	{
		this.context = context;
		if(!context.DEVMODE)
			throw new LanguageContextException("Can't use this constructor outside of DEVMODE. (DEVMODE in LanguageContext false)");
		infinitive = infinitive.toLowerCase();
		if(a != null)
			acc = a;
		else
		{
			acc = removeAccessories(infinitive);
			if(acc != null)
			{
				int trim = acc.getTRIMIndex();
				if(trim != -1)
					infinitive = acc.getAccessories()[trim];
			}
		}
		
		this.infinitive = infinitive;
		
		irregular = context.verbIsIrregular(infinitive);
		
		type = t;
		shift = pshift;
		pattern = reppat;
		
	}
	
	/*
	 * This method parses the infinitive String for all relevant information for its Verb_Infinitive initialization.
	 * 
	 * 
	 * @returns an array of information for this infinitive
	 * index[0] should be the Type
	 * index[1] should be the PatternShift
	 * index[2] should be the ReplaceablePattern
	 */
	protected abstract Object[] getInfinitiveInfo(String infinitive);
	
	/*
	 * This method removes all prefixes and suffixes and unnecessary attachments to the infinitive
	 * 
	 * @returns an array of everything removed
	 *  index[0] should be the trimmed infinitive
	 * the rest should be the accessories that were removed.
	 */
	protected abstract Accessories removeAccessories(String infinitive);
	/**
	 * @return the infinitive with the accessories placed back onto the infinitive
	 */
	public abstract String reAccessorize(Verb v, String infinitive);
	/**
	 * @return the LanguageContext associated with the language of this infinitive's origin
	 */
	public LanguageContext getLanguageContext()
	{
		return context;
	}
	/**
	 * @return whether this infinitive is irregular
	 */
	public boolean isIrregular() {
		return irregular;
	}
	/**
	 * @return whether this infinitive has an Accessories object
	 */
	public boolean hasAccessories()
	{
		return acc != null;
	}
	
	/**
	 * @return the Accessories object held by this infinitive
	 */
	public Accessories getAccessories()
	{
		return acc;
	}
	/**
	 * @return the verb type associated with this infinitive
	 */
	public Type getVerbType()
	{
		return type;
	}
	
	/**
	 * @return the pattern shift inferred from this infinitive
	 */
	public PatternShift getPatternShift()
	{
		return shift;
	}
	
	/**
	 * @return the replaceable pattern inferred from this infinitive
	 */
	public ReplaceablePattern getReplaceablePattern()
	{
		return pattern;
	}
	
	
	
	
	public String toString()
	{
		return infinitive;
	}
	
}
