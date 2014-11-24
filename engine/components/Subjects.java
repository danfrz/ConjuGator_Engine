package engine.components;



public class Subjects
{
	/**
	 * Subjects are nouns, (but not with proper names) used for both conjugation and declension 
	 */
	public interface Subject
	{
	    public Gender getGender();
	    public Subject getAndro();
	    
	    public boolean isPlural();
	    public boolean isFirstPerson();
	    public boolean isSecondPerson();
	    public boolean isThirdPerson();
	    /**
	     * @return the name associated with this subject or null if there is none.
	     */
	    public String getProperName();
	    /**
	     * @return the unique identifier appropriate for this subject
	     * @see Subjects.SJ_ANDRO_FS
	     * @see Subjects.SJ_ANDRO_SM
	     * @see Subjects.SJ_GENDER_TSF
	     */
	    public int getIdentifier();
	    //Could be extended upon and add methods like isGoat or what ever relevant information might be necessary for Declension / ConjuGation
	}
	
	public static final int
		SJ_ANDRO_FS = 0,
		SJ_ANDRO_SS = SJ_ANDRO_FS+1,
		SJ_ANDRO_TS = SJ_ANDRO_FS+2,
		SJ_ANDRO_FM = SJ_ANDRO_FS+3,
		SJ_ANDRO_SM = SJ_ANDRO_FS+4,
		SJ_ANDRO_TM = SJ_ANDRO_FS+5;
	
	/**
	 * An enumeration containing various non-gendered (androgynous) subjects
	 * @author dan
	 */
	public static enum Androgynous implements Subject
	{
		FirstSingular
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return true;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Subject getAndro(){return this;}
			@Override public String getProperName() {return null;}
	        private final Subject[] asGendered = new Subject[]{Gendered.FirstSingularF, Gendered.FirstSingularM};
	        @Override public Subject[] getAsGendered(){return asGendered;}
			@Override
			public int getIdentifier() {
				return SJ_ANDRO_FS;
			}
		},
		SecondSingular
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return true;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Subject getAndro(){return this;}
			@Override public String getProperName() {return null;}
	        private final Subject[] asGendered = new Subject[]{Gendered.SecondSingularF, Gendered.SecondSingularM};
	        @Override public Subject[] getAsGendered(){return asGendered;}
			@Override
			public int getIdentifier() {
				return SJ_ANDRO_SS;
			}
		},
		ThirdSingular
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Subject getAndro(){return this;}
			@Override public String getProperName() {return null;}
	        private final Subject[] asGendered = new Subject[]{Gendered.ThirdSingularF, Gendered.ThirdSingularM};
	        @Override public Subject[] getAsGendered(){return asGendered;}
			@Override
			public int getIdentifier() {
				return SJ_ANDRO_TS;
			}
		},
		FirstMultiple
	    {
	        @Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return true;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Subject getAndro(){return this;}
			@Override public String getProperName() {return null;}
	        private final Subject[] asGendered = new Subject[]{Gendered.FirstMultipleF, Gendered.FirstMultipleM};
	        @Override public Subject[] getAsGendered(){return asGendered;}
			@Override
			public int getIdentifier() {
				return SJ_ANDRO_FM;
			}
	    },
	    SecondMultiple
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return true;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Subject getAndro(){return this;}
			@Override public String getProperName() {return null;}
	        private final Subject[] asGendered = new Subject[]{Gendered.SecondMultipleF, Gendered.SecondMultipleM};
	        @Override public Subject[] getAsGendered(){return asGendered;}
			@Override
			public int getIdentifier() {
				return SJ_ANDRO_SM;
			}
	    },
	    ThirdMultiple
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Subject getAndro(){return this;}
			@Override public String getProperName() {return null;}
	        private final Subject[] asGendered = new Subject[]{Gendered.ThirdMultipleF, Gendered.ThirdMultipleM};
	        @Override public Subject[] getAsGendered(){return asGendered;}
			@Override
			public int getIdentifier() {
				return SJ_ANDRO_TM;
			}
	    };
        @Override public Gender getGender(){return null;}
        public abstract Subject[] getAsGendered();
        /**
    	 * Attempts to find the appropriate Androgynous Subject according to the given unique identifier,
    	 * if your extended conjugator has unique subjects with unique identifiers, then this will
    	 * return null
    	 * @param id
    	 * @return subject according to the given identifier or null
    	 */
        static Androgynous getSubject(int id)
	    {
	    	switch(id)
	    	{
	    		case SJ_ANDRO_FS: return Androgynous.FirstSingular;
	    		case SJ_ANDRO_SS: return Androgynous.SecondSingular;
	    		case SJ_ANDRO_TS: return Androgynous.ThirdSingular;
	    		case SJ_ANDRO_FM: return Androgynous.FirstMultiple;
	    		case SJ_ANDRO_SM: return Androgynous.SecondMultiple;
	    		case SJ_ANDRO_TM: return Androgynous.ThirdMultiple;
	    		default:
	    			return null;
	    	}
	    }
        
	}
	
	public static final int
		SJ_GENDER_FSM = 10,
		SJ_GENDER_FSF = SJ_GENDER_FSM+1,
		SJ_GENDER_SSM = SJ_GENDER_FSM+2,
		SJ_GENDER_SSF = SJ_GENDER_FSM+3,
		SJ_GENDER_TSM = SJ_GENDER_FSM+4,
		SJ_GENDER_TSF = SJ_GENDER_FSM+5,
		SJ_GENDER_TSN = SJ_GENDER_FSM+6,

		SJ_GENDER_FMM = SJ_GENDER_FSM+7,
		SJ_GENDER_FMF = SJ_GENDER_FSM+8,
		SJ_GENDER_SMM = SJ_GENDER_FSM+9,
		SJ_GENDER_SMF = SJ_GENDER_FSM+10,
		SJ_GENDER_TMM = SJ_GENDER_FSM+11,
		SJ_GENDER_TMF = SJ_GENDER_FSM+12,
		SJ_GENDER_TMN = SJ_GENDER_FSM+13;
				
	/**
	 * An enumeration containing various gendered subjects
	 * @author dan
	 */
	public static enum Gendered implements Subject
	{
		FirstSingularM
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return true;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.M;}
	        @Override public Subject getAndro(){return Androgynous.FirstSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_FSM;
			}
		},
		FirstSingularF
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return true;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.F;}
	        @Override public Subject getAndro(){return Androgynous.FirstSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_FSF;
			}
		},
		SecondSingularM
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return true;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.M;}
	        @Override public Subject getAndro(){return Androgynous.SecondSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_SSM;
			}
		},
		SecondSingularF
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return true;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.F;}
	        @Override public Subject getAndro(){return Androgynous.SecondSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_SSF;
			}
		},
		ThirdSingularM
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Gender getGender(){return Gender.M;}
	        @Override public Subject getAndro(){return Androgynous.ThirdSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_TSM;
			}
		},
		ThirdSingularF
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Gender getGender(){return Gender.F;}
	        @Override public Subject getAndro(){return Androgynous.ThirdSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_TSF;
			}
		},
		ThirdSingularN
		{
			@Override public boolean isPlural(){return false;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Gender getGender(){return Gender.N;}
	        @Override public Subject getAndro(){return Androgynous.ThirdSingular;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_TSN;
			}
		},
		FirstMultipleM
	    {
	        @Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return true;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.M;}
	        @Override public Subject getAndro(){return Androgynous.FirstMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_FMM;
			}
	    },
	    FirstMultipleF
	    {
	        @Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return true;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.F;}
	        @Override public Subject getAndro(){return Androgynous.FirstMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_FMF;
			}
	    },
	    SecondMultipleM
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return true;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.M;}
	        @Override public Subject getAndro(){return Androgynous.SecondMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_SMM;
			}
	    },
	    SecondMultipleF
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return true;}
	        @Override public boolean isThirdPerson(){return false;}
	        @Override public Gender getGender(){return Gender.F;}
	        @Override public Subject getAndro(){return Androgynous.SecondMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_SMF;
			}
	    },
	    ThirdMultipleM
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Gender getGender(){return Gender.M;}
	        @Override public Subject getAndro(){return Androgynous.ThirdMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_TMM;
			}
	    },
	    ThirdMultipleF
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Gender getGender(){return Gender.F;}
	        @Override public Subject getAndro(){return Androgynous.ThirdMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_TMF;
			}
	    },
	    ThirdMultipleN
	    {
	    	@Override public boolean isPlural(){return true;}
	        @Override public boolean isFirstPerson(){return false;}
	        @Override public boolean isSecondPerson(){return false;}
	        @Override public boolean isThirdPerson(){return true;}
	        @Override public Gender getGender(){return Gender.N;}
	        @Override public Subject getAndro(){return Androgynous.ThirdMultiple;}
			@Override public String getProperName() {return null;}
			@Override
			public int getIdentifier() {
				return SJ_GENDER_TMN;
			}
	    }
	    ;
	    /**
		 * Attempts to find the appropriate Gendered Subject according to the given unique identifier,
		 * if your extended conjugator has unique subjects with unique identifiers, then this will
		 * return null
		 * @param id
		 * @return subject according to the given identifier or null
		 */
	    static Gendered getSubject(int id)
	    {
	    	switch(id)
	    	{
		    	case SJ_GENDER_FSM: return Gendered.FirstSingularM;
	    		case SJ_GENDER_FSF: return Gendered.FirstSingularF;
	    		case SJ_GENDER_SSM: return Gendered.SecondSingularM;
	    		case SJ_GENDER_SSF: return Gendered.SecondSingularF;
	    		case SJ_GENDER_TSM: return Gendered.ThirdSingularM;
	    		case SJ_GENDER_TSF: return Gendered.ThirdSingularF;
	    		case SJ_GENDER_TSN: return Gendered.ThirdSingularN;
	
	    		case SJ_GENDER_FMM: return Gendered.FirstMultipleM;
	    		case SJ_GENDER_FMF: return Gendered.FirstMultipleF;
	    		case SJ_GENDER_SMM: return Gendered.SecondMultipleM;
	    		case SJ_GENDER_SMF: return Gendered.SecondMultipleF;
	    		case SJ_GENDER_TMM: return Gendered.ThirdMultipleM;
	    		case SJ_GENDER_TMF: return Gendered.ThirdMultipleF;
	    		case SJ_GENDER_TMN: return Gendered.ThirdMultipleN;
	    		
	    	}
	    	return null;
	    }
	}
	/**
	 * Attempts to find the appropriate Subject according to the given unique identifier,
	 * if your extended conjugator has unique subjects with unique identifiers, then this will
	 * return null
	 * @param id
	 * @return subject according to the given identifier or null
	 */
	static Subject getSubject(int id)
    {
    	switch(id)
    	{
    		case SJ_ANDRO_FS: return Androgynous.FirstSingular;
    		case SJ_ANDRO_SS: return Androgynous.SecondSingular;
    		case SJ_ANDRO_TS: return Androgynous.ThirdSingular;
    		case SJ_ANDRO_FM: return Androgynous.FirstMultiple;
    		case SJ_ANDRO_SM: return Androgynous.SecondMultiple;
    		case SJ_ANDRO_TM: return Androgynous.ThirdMultiple;
    	
    		case SJ_GENDER_FSM: return Gendered.FirstSingularM;
    		case SJ_GENDER_FSF: return Gendered.FirstSingularF;
    		case SJ_GENDER_SSM: return Gendered.SecondSingularM;
    		case SJ_GENDER_SSF: return Gendered.SecondSingularF;
    		case SJ_GENDER_TSM: return Gendered.ThirdSingularM;
    		case SJ_GENDER_TSF: return Gendered.ThirdSingularF;
    		case SJ_GENDER_TSN: return Gendered.ThirdSingularN;

    		case SJ_GENDER_FMM: return Gendered.FirstMultipleM;
    		case SJ_GENDER_FMF: return Gendered.FirstMultipleF;
    		case SJ_GENDER_SMM: return Gendered.SecondMultipleM;
    		case SJ_GENDER_SMF: return Gendered.SecondMultipleF;
    		case SJ_GENDER_TMM: return Gendered.ThirdMultipleM;
    		case SJ_GENDER_TMF: return Gendered.ThirdMultipleF;
    		case SJ_GENDER_TMN: return Gendered.ThirdMultipleN;
    		
    	}
    	return null;
    }
	
}
