package engine.components.irreg;

import java.util.HashMap;
import java.util.Map.Entry;

import engine.components.Subjects.Androgynous;
import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;


public class IrregSpecific implements Irregular
{
	private String inf;
	private HashMap<Integer, String> irgs;
	private IrregProperty[] props;
	
	public IrregSpecific(String infinitive)
	{
		inf = infinitive;
		irgs = new HashMap<>();
	}
	
	/**
	 * Adds an irregular conjugation according to the given subject and tense.
	 * These can be overwritten if the Subject and Tense Identifiers are the same
	 * (which they should not be)
	 * If an Androgynous subject is added, it checks whether there are any
	 * gendered added, if not, also set gendered to the same value.
	 * @param s
	 * @param t
	 * @param irg
	 */
	public void add(Subject s, Tense t, String irg)
	{
		add(s,t,null,irg);
	}
	
	/**
	 * Adds an irregular conjugation according to the given subject, tense, and
	 * other properties. These can be overwritten if the Subject, Tense and Properties 
	 * are the same.
	 * If an Androgynous subject is added, it checks whether there are any
	 * gendered added, if not, also set gendered to the same value.
	 * @param s
	 * @param t
	 * @param irg
	 */
	public void add(Subject s, Tense t, IrregProperty[] props, String irg)
	{
		String r = irgs.put(makeHashCode(s,t, props),irg);
		if(s.getGender() != null)
		{
			Subject an = s.getAndro();
			int hash = makeHashCode(an,t, props);
			if(irgs.get(hash) == null)
				irgs.put(hash, irg);
			
		}
		else
		{
			if(s.getAndro() instanceof Androgynous)
			{
				Androgynous an = (Androgynous)s.getAndro();
				Subject[] gen = an.getAsGendered();
				Subject sel;
				int hash;
				for(int i = 0; i < gen.length; i++)
				{
					sel = gen[i];
					hash = makeHashCode(sel, t, props);
					if(irgs.get(hash) == null)
						irgs.put(hash, irg);
				}
			}
		}
		if(r!=null)
			System.err.println("WARNING, \"" + r + "\" has been replaced by " + irg + " because of a colliding hashcode");
	}
	
	/**
	 * Returns the appropriate irregular conjugation for the given subject and tense
	 */
	@Override
	public String get(Subject s, Tense t) {
		
		return irgs.get(makeHashCode(s,t, null));
	}

	@Override
	public String get(Subject s, Tense t, IrregProperty... props) {
		return irgs.get(makeHashCode(s,t,props));
	}

	@Override
	public String getInfinitive() {
		return inf;
	}
	
	private int makeHashCode(Subject s, Tense t, IrregProperty[] props)
	{
		int out;
		out = 11 * s.getIdentifier();
		out = out * 36 * t.getIdentifier();
		if(props == null || props.length < 1)
			return out;
		int n = props.length;
		for(int i = 0; i < n; i++)
			out = out*13 + props.hashCode();
		return out;
	}

	@Override
	public int getPattern() {
		return Irregular.IRGPTRN_SPECIFIC;
	}

	@Override
	public boolean merge(Irregular other) {
		if(!(other instanceof IrregSpecific))
			return false;
		IrregSpecific otr = (IrregSpecific)other;
		irgs.putAll(otr.irgs);
		return true;
	}
	
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		for(String s : irgs.values())
		{
			out.append(s);
			out.append(", ");
		}
		return out.toString();
	}


}
