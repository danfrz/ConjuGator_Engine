package engine.components;

public class Accessories {
	private String[] accessories;
	private int acc_type;
	public static final int PREFIX_TRIM = 1, PREFIX_TRIM_SUFFIX = 2, TRIM_REFLEXIVE = 3, PREFIX_TRIM_REFLEXIVE = 4, PREFIX_TRIM_SUFFIX_REFLEXIVE = 5;
	
	/**
	 * Creates a new object containing verb accessories
	 */
	public Accessories(String[] acc, int accesoryPtrn)
	{
		accessories = acc;
		acc_type = accesoryPtrn;
	}
	
	public String[] getAccessories()
	{
		return accessories;
	}
	/**
	 * @return the type of this AccessoryPattern.
	 * @see Accessories.PREFIX_TRIM
	 * @see Accessories.PREFIX_TRIM_SUFFIX
	 */
	public int getAccessoryPattern()
	{
		return acc_type;
	}
	/**
	 * @return the index of the trimmed infinitive in this accessories object.
	 * A trimmed infinitive is an infinitive whose extraneous data (prefixes,
	 * suffixes, reflexives etc.) has been removed.
	 * 
	 */
	public int getTRIMIndex()
	{
		return getTRIMIndex(acc_type);
	}
	
	/**
	 * @param acc_type
	 * @return whether the provided type has a reflexive
	 */
	public static boolean hasReflexive(int acc_type)
	{
		return acc_type == TRIM_REFLEXIVE || acc_type == PREFIX_TRIM_REFLEXIVE || acc_type == PREFIX_TRIM_SUFFIX_REFLEXIVE;
	}
	/**
	 * @return the index of the trimmed infinitive in this accessories object.
	 * A trimmed infinitive is an infinitive whose extraneous data (prefixes,
	 * suffixes, reflexives etc.) has been removed.
	 * 
	 */
	public static int getTRIMIndex(int accPtrn)
	{
		switch(accPtrn)
		{
			case PREFIX_TRIM:
			case PREFIX_TRIM_SUFFIX:
			case PREFIX_TRIM_REFLEXIVE:
			case PREFIX_TRIM_SUFFIX_REFLEXIVE:
				return 1;
			case TRIM_REFLEXIVE:
				return 0;
		}
		return -1;
	}
}
