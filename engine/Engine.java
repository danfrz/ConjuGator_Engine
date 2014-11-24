package engine;

import engine.components.EngineMonitor;
import engine.components.LanguageContext;
import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;
import engine.components.Verb;
import engine.components.Verb_Infinitive;
import engine.components.err.verb.ConjugationException;

public abstract class Engine
{

	public static final byte
		CONJUGATION_FAILED = -1,
		CONJUGATION_NULL = 0,
		CONJUGATION_WORKING = 2,
		CONJUGATION_SUCCESS = 1;
	protected EngineMonitor mon;
	protected byte result = CONJUGATION_NULL;
	public final LanguageContext context;
	public final boolean DEVMODE;
	public Engine()
	{
		this(true);
	}
	
	public Engine(boolean devmode)
	{
		mon = new EngineMonitor(){
			@Override
			public void sendMessage(String msg) {
				System.out.println(msg);
			}
			
			@Override
			public void sendMessage(String msg, int code) {
				if(code == -1) System.err.println(msg);
				else System.out.println(msg);
			}

			@Override
			public void sendMessage(String msg, int code, Throwable e) {
				if(e != null)
					e.printStackTrace();
			}

			@Override
			public void clear() {
			}

		};
		context = createLanguageContext(devmode);
		DEVMODE = devmode;
	}
	
	public Engine(EngineMonitor monitor)
	{
		this(monitor, false);
	}
	
	public Engine(EngineMonitor monitor, boolean devmode)
	{
		mon = monitor;
		context = createLanguageContext();
		DEVMODE = devmode;
	}
	
	
	public final LanguageContext createLanguageContext()
	{
		return createLanguageContext(false);
	}
	/**
	 * Create the context for this language,
	 * serves as storage for all possible parameters
	 * and acts as a unique identifier for this language.
	 * @return
	 */
	public abstract LanguageContext createLanguageContext(boolean devmode);
	
	
	/**
	 * This is so that if the project does not know of which language's
	 * Verb_Infinitive to make. That project can simply call getInfinitive()
	 * instead of having to go through the effort of checking the language. 
	 * 
	 * @param infinitive
	 * @return
	 */
	public abstract Verb_Infinitive getInfinitive(String infinitive);
	
	/**
	 * Conjugate a verb to the subject and tense provided in the verb.
	 * All of the other Conjugate(...) methods refer to this one.
	 * @param v
	 * @return
	 * @throws ConjugationException
	 */
	public String Conjugate(Verb v)
	{
		result = CONJUGATION_WORKING;
		Verb_Infinitive inf = v.getInfinitive();
		if(inf.isIrregular())
		{
			String irr = context.getIrregular(inf.toString(), v.getTense(), v.getSubject());
			if(irr != null)
			{
				result = CONJUGATION_SUCCESS;
				if(inf.hasAccessories() && v.isReaccessorizationEnabled())
				{
					String out = inf.reAccessorize(v, irr);
					v.setConjugated(out);
					return out;
				}
				else
				{
					v.setConjugated(irr);
					return irr;
				}
			}
		}
		if(inf.hasAccessories() && v.isReaccessorizationEnabled())
		{
			String out = inf.reAccessorize(v, diverge(v));
			v.setConjugated(out);
			return out;
		}
		else
		{
			String out = diverge(v);
			v.setConjugated(out);
			return out;
		}
	}
	/**
	 * @return the LanguageContext object appropriate for this conjugator's language
	 */
	public LanguageContext getLanguageContext()
	{
		return context;
	}
	/**
	 * Set's this conjugator's output viewport for debugging
	 * @param e
	 */
	public void setMonitor(EngineMonitor e)
	{
		mon = e;
		context.monitor = e;
	}
	
	/**
	 * @param v
	 * @return an array of Strings from conjugating verb objects
	 * Simply loops through the verb array firing
	 * v[i].Conjugate();
	 */
	public String[] Conjugate(Verb[] v)
	{
		int len = v.length;
		String[] out = new String[len];
		for(int i = 0; i <len; i++)
			out[i] = v[i].Conjugate();
		return out;
	}
	
	/**
	 * @param v
	 * @return the same verb array, but conjugated.
	 * Simply loops through the verb array firing
	 * v[i].Conjugate();
	 */
	public Verb[] ConjugateToVerb(Verb[] v)
	{
		int len = v.length;
		for(int i = 0; i <len; i++)
			v[i].Conjugate();
		return v;
	}
	
	/**
	 * Conjugate one infinitive to multiple subjects within a single tense.
	 * @param v
	 * @param subjects
	 * @param tense
	 * @return
	 */
	public String[] Conjugate(Verb_Infinitive v, Subject[] subjects, Tense tense)
	{
		int len = subjects.length;
		String[] out = new String[len];
		Verb[] in = ConjugateToVerb(v, subjects, tense);

		for(int i = 0; i <len; i++)
			out[i] = in[i].Conjugate(); 
		return out;
	}
	/**
	 * Conjugate one infinitive to multiple subjects within a single tense.
	 * @param v
	 * @param subjects
	 * @param tense
	 * @return
	 */
	public Verb[] ConjugateToVerb(Verb_Infinitive v, Subject[] subjects, Tense tense)
	{
		int len = subjects.length;
		Verb[] out = new Verb[len];
		int errors = 0;
		long now = System.nanoTime();
		for(int i = 0; i <len; i++)
			try
			{
				Verb verb = new Verb(v,  tense, subjects[i]);
				Conjugate(verb);
				out[i] = verb;
			}
			catch(ConjugationException e)
			{
				mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
				errors++;
			}
			catch(Exception e)
			{
				mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
				errors++;
			}
		now = (System.nanoTime() - now) / 1000000;
		double time = now / 1000d;
		mon.sendMessage("Finished in " + time + " seconds.");
		mon.sendMessage("Conjugation finished of " + v + " with " + errors + " errors.");
		return out;
	}
	
	
	/**
	 * Conjugate multiple infinitives to a certain subject and tense
	 * @param v
	 * @param subject
	 * @param tense
	 * @return
	 */
	public String[] Conjugate(Verb_Infinitive[] v, Subject subject, Tense tense)
	{
		Verb[] verbs = ConjugateToVerb(v,subject,tense);
		int vlen = verbs.length;
		String[] out = new String[vlen];
		for(int i = 0; i < vlen; i++)
			out[i] = verbs[i].Conjugate();
		return out;
	}
	/**
	 * Conjugate multiple infinitives to a certain subject and tense
	 * @param v
	 * @param subject
	 * @param tense
	 * @return
	 */
	public Verb[] ConjugateToVerb(Verb_Infinitive[] v, Subject subject, Tense tense)
	{
		int vlen = v.length;
		Verb[] out = new Verb[vlen];
		int errors = 0;
		long now = System.nanoTime();
		for(int i = 0; i <vlen; i++)
			try {
				Verb b = new Verb(v[i], tense, subject);
				Conjugate(b);
				out[i] = b;
			}
			catch(ConjugationException e)
			{
				mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
				errors++;
			}
			catch(Exception e)
			{
				mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
				errors++;
			}
		now = (System.nanoTime() - now) / 1000000;
		double time = now / 1000d;
		mon.sendMessage("Finished in " + time + " seconds.");
		mon.sendMessage(v.length + "-infinitive Conjugation finished with " + errors + " errors.");
		return out;
	}
	
	/**
	 * Conjugate multiple infinitives and subjects to a single tense
	 * @param v
	 * @param subjects
	 * @param tense
	 * @return a two dimensional String array ordered
	 *  String[verbs][subjects] in the order that they were in the provided array
	 */
	public String[][] Conjugate(Verb_Infinitive[] v, Subject[] subjects, Tense tense)
	{
		int slen = subjects.length,
				vlen = v.length;
		Verb[][] in = ConjugateToVerb(v, subjects, tense);
		String[][] out = new String [vlen][slen];
		for(int i = 0; i < vlen; i++)
		{
			for(int j = 0; j <slen; j++)
			{
				out[i][j] = in[i][j].Conjugate();
			}
		}
		return out;
	}
	/**
	 * Conjugate multiple infinitives and subjects to a single tense
	 * @param v
	 * @param subjects
	 * @param tense
	 * @return a two dimensional String array ordered
	 *  String[verbs][subjects] in the order that they were in the provided array
	 */
	public Verb[][] ConjugateToVerb(Verb_Infinitive[] v, Subject[] subjects, Tense tense)
	{
		int slen = subjects.length,
				vlen = v.length;
		Verb[][] out = new Verb [vlen][slen];
		int errors = 0;
		long now = System.nanoTime();
		for(int i = 0; i <vlen; i++)
			for(int j = 0; j <slen; j++)
				try
				{
					Verb verb = new Verb(v[i], tense, subjects[j]);
					Conjugate(verb);
					out[i][j] = verb;
				}
				catch(ConjugationException e)
				{
					mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
					errors++;
				} catch(Exception e)
				{
					mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
					errors++;
				}
		now = (System.nanoTime() - now) / 1000000;
		double time = now / 1000d;
		mon.sendMessage("Finished in " + time + " seconds.");
		mon.sendMessage(v.length + "-infinitive Conjugation finished with " + errors + " errors.");
		return out;
	}
	
	/**
	 * Conjugate a verb infinitive to multiple subjects and tenses
	 * @param v
	 * @param subjects
	 * @param tense
	 * @return a two dimensional String array ordered
	 * 	String[tenses][subjects] in the order that they were in the provided array
	 */
	public String[][] Conjugate(Verb_Infinitive v, Subject[] subjects, Tense[] tense)
	{

		int slen = subjects.length,
				tlen = tense.length;
		String[][] out = new String[tlen][slen];
		int errors = 0;
		long now = System.nanoTime();
		for(int i = 0; i <tlen; i++)
			for(int j = 0; j <slen; j++)
				try
				{
					out[i][j] = Conjugate(new Verb(v, tense[i], subjects[j]));
				}
				catch(ConjugationException e)
				{
					mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
					errors++;
				} catch(Exception e)
				{
					mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
					errors++;
				}
		now = (System.nanoTime() - now) / 1000000;
		double time = now / 1000d;
		mon.sendMessage("Finished in " + time + " seconds.");
		mon.sendMessage("Conjugation of " + v +" finished with " + errors + " errors.");
		return out;
	}
	/**
	 * Conjugate a verb infinitive to multiple subjects and tenses
	 * @param v
	 * @param subjects
	 * @param tense
	 * @return a two dimensional String array ordered
	 * 	String[tenses][subjects] in the order that they were in the provided array
	 */
	public Verb[][] ConjugateToVerb(Verb_Infinitive v, Subject[] subjects, Tense[] tense)
	{

		int slen = subjects.length,
				tlen = tense.length;
		Verb[][] out = new Verb[tlen][slen];
		int errors = 0;
		long now = System.nanoTime();
		for(int i = 0; i <tlen; i++)
			for(int j = 0; j <slen; j++)
				try
				{
					Verb verb = new Verb(v, tense[i], subjects[j]);
					Conjugate(verb);
					out[i][j] = verb;
				}
				catch(ConjugationException e)
				{
					mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
					errors++;
				} catch(Exception e)
				{
					mon.sendMessage(e.getClass().getName() + ": " + e.getMessage(), -1, e);
					errors++;
				}
		now = (System.nanoTime() - now) / 1000000;
		double time = now / 1000d;
		mon.sendMessage("Finished in " + time + " seconds.");
		mon.sendMessage("Conjugation of " + v +" finished with " + errors + " errors.");
		return out;
	}
	
	/**
	 * Splits up the conjugation path in a logical manner
	 * @param v
	 * @return
	 */
	public abstract String diverge(Verb v);
	
	/**
	 * @return the byte value corresponding to the result of the last
	 * conjugation performed using this conjugator.
	 */
	public byte getConjugationResult() {
		return result;
	}
	
	/**
	 * @param result
	 * @return the appropriate descriptive string describing the given result 
	 */
	public String getResultString(byte result)
	{
		if(result == CONJUGATION_SUCCESS)
			return "Conjugation succeeded";
		if(result == CONJUGATION_FAILED)
			return "Conjugation failed";
		if(result == CONJUGATION_NULL)
			return "No verbs have been conjugated yet";
		if(result == CONJUGATION_WORKING)
			return "Conjugation status unset";
		return UnsupportedResult;
	}
	
	public static final String UnsupportedResult = "Conjugation returned a result value that hasn't yet been interpreted.";
	
}
