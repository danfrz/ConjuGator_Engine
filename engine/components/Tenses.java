package engine.components;



public class Tenses {
	
	public static final int TENSE_INFINITIVE = 0,
			TENSE_PRESENT = TENSE_INFINITIVE + 1,
			TENSE_PRETERITE	= TENSE_INFINITIVE + 2,
			TENSE_IMPERFECT = TENSE_INFINITIVE + 3,
			TENSE_GERUND	= TENSE_INFINITIVE + 4,
			TENSE_IMPERATIVE	= TENSE_INFINITIVE + 5,
			TENSE_CONDITIONAL	= TENSE_INFINITIVE + 6,
			TENSE_FUTURE		= TENSE_INFINITIVE + 7,
			TENSE_PRESENTPARTICIPLE		= TENSE_INFINITIVE + 8,
			TENSE_PASTPARTICIPLE		= TENSE_INFINITIVE + 9,
			TENSE_PRESENTSUBJUNCTIVE	= TENSE_INFINITIVE + 10,
			TENSE_PASTSUBJUNCTIVE		= TENSE_INFINITIVE + 11,
			TENSE_FUTURESUBJUNCTIVE		= TENSE_INFINITIVE + 12,
			
			/**
			 * 50 tense indices are reserved for default tenses,
			 * try not to override them in extensions
			 * 
			 * The order in which new languages
			 * should be ordered is the order in which they are added.
			 */
			TENSE_RESERVE_DEFAULT = 49,
			TENSE_RESERVE_SPANISH = 54,
			TENSE_RESERVE_ICELANDIC = 59
			;
	
	public static final int TYPE_TENSE = 0,
			TYPE_ASPECT = 1,
			TYPE_MOOD = 2;
	
	public enum VerbTenses implements Tense
	{
		Infinitive
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() {return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override public int getIdentifier() {return TENSE_INFINITIVE;}
		},
		
		Present
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return true;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() {return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.ABSOLUTE;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_PRESENT;
			}
		},
		Preterite
		{
			@Override public boolean isPast() {return true;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.ABSOLUTE;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_PRETERITE;
			}
		},
		Imperfect
		{
			@Override public boolean isPast() {return true;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_IMPERFECT;
			}
		},
		Gerund
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public boolean isFuture() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.ONGOING_CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_GERUND;
			}
		},
		Imperative
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return true;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public boolean isFuture() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.ABSOLUTE;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_IMPERATIVE;
			}
		},
		Conditional
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_CONDITIONAL;
			}
		},
		Future
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public boolean isFuture() { return true;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.ABSOLUTE;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_FUTURE;
			}
		},
		PresentParticiple
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return true;}
			@Override public boolean isParticiple() {return true;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public boolean isFuture() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_PRESENTPARTICIPLE;
			}
		},
		PastParticiple
		{
			@Override public boolean isPast() {return true;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isParticiple() {return true;}
			@Override public boolean isSubjunctive() { return false;}
			@Override public boolean isFuture() { return false;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_TENSE;}
			@Override
			public int getIdentifier() {
				return TENSE_PASTPARTICIPLE;
			}
		},
		PresentSubjunctive
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return true;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return true;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_MOOD;}
			@Override
			public int getIdentifier() {
				return TENSE_PRESENTSUBJUNCTIVE;
			}
		},
		PastSubjunctive
		{
			@Override public boolean isPast() {return true;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isFuture() { return false;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return true;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_MOOD;}
			@Override
			public int getIdentifier() {
				return TENSE_PASTSUBJUNCTIVE;
			}
		},
		FutureSubjunctive
		{
			@Override public boolean isPast() {return false;}
			@Override public boolean isPresent() {return false;}
			@Override public boolean isFuture() { return true;}
			@Override public boolean isParticiple() {return false;}
			@Override public boolean isSubjunctive() { return true;}
			@Override public ReferencePoint getReferencePoint() {return ReferencePoint.CONTEXTUAL;}
			@Override public int getTAMType() {return TYPE_MOOD;}
			@Override
			public int getIdentifier() {
				return TENSE_FUTURESUBJUNCTIVE;
			}
		},
		
		
	}
	/**
	 * Returns the Tense object associated with the given identifier
	 * or null if identifier not recognized
	 * @param by_id
	 * @return
	 */
	public static Tense getTense(int by_id)
	{
		switch(by_id)
		{
			case  TENSE_INFINITIVE:	return VerbTenses.Infinitive;
			case  TENSE_PRESENT:	return VerbTenses.Present;
			case  TENSE_PRETERITE:	return VerbTenses.Preterite;
			case  TENSE_IMPERFECT : return VerbTenses.Imperfect;
			
			case  TENSE_GERUND:return VerbTenses.Gerund;
			case  TENSE_IMPERATIVE:	return VerbTenses.Imperative;
			
			case  TENSE_CONDITIONAL:return VerbTenses.Conditional;
			case  TENSE_FUTURE:		return VerbTenses.Future;
			
			case  TENSE_PRESENTPARTICIPLE:	return VerbTenses.PresentParticiple;
			case  TENSE_PASTPARTICIPLE:		return VerbTenses.PastParticiple;
			
			case  TENSE_PRESENTSUBJUNCTIVE:	return VerbTenses.PresentSubjunctive;
			case  TENSE_PASTSUBJUNCTIVE:	return VerbTenses.PastSubjunctive;
			case  TENSE_FUTURESUBJUNCTIVE:	return VerbTenses.FutureSubjunctive;
			
			
			
			default:
				return null;
		}
	}
	
	
	/**
	 * Tenses aren't only just grammatical tenses.
	 * Tenses here include tense, aspect and mood all in each entry.
	 * If the tenses provided in VerbTenses don't accurately and specifically account
	 * for every tense a certain language needs, implement this interface into new tense classes.
	 * @author dan
	 */
	public static interface Tense
    {
		/**
		 * @return whether this Tense is in the present
		 */
        public abstract boolean isPresent();
        /**
		 * @return whether this Tense is in the past
		 */
        public abstract boolean isPast();
        /**
		 * @return whether this Tense is in the future
		 */
        public abstract boolean isFuture();
        /**
		 * @return whether this Tense is a participle
		 */
        public abstract boolean isParticiple();
        /**
		 * @return whether this Tense is subjunctive
		 */
        public abstract boolean isSubjunctive();
        /**
		 * @return the ReferencePoint this tense applies to
		 * @see ReferencePoint
		 */
        public abstract ReferencePoint getReferencePoint();
        /**
         * @return the unique integer ID appropriate for this tense
         */
        public abstract int getIdentifier();
        /**
         * @return whether this Tense object is a Tense, Aspect, or Mood
         */
        public abstract int getTAMType();
    }
	
}
