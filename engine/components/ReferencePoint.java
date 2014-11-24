package engine.components;

public enum ReferencePoint
{
	/**
	 * An absolute point in time
	 */
	ABSOLUTE,
	/**
	 * Can mean either there is no reference point,
	 * or that this tense depends or helps another
	 * tense defined elsewhere grammatically
	 */
	CONTEXTUAL,
	/**
	 * Something that happened and is still happening
	 */
	ONGOING,
	/**
	 * Something that grammatically depends on another tense
	 * and is ongoing. 
	 */
	ONGOING_CONTEXTUAL // e.g. abstract imperfect progressive "Some years ago I washed(habitually) the dishes while I was _WATCHING_ TV"
	;
	
	public boolean isRelative()
	{
		return this != ABSOLUTE;
	}
}
