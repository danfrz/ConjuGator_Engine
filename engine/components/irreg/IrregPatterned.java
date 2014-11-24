package engine.components.irreg;

import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;
import engine.components.err.verb.ConjugationException;

public class IrregPatterned implements Irregular
{
	public static Subject[] default_subject_order;
	public static Tense[] default_tense_order;
	
	private int irgptrn;
	private Object[] inputordr;
	private Object[] order;
	private IrregProperty[] props;
	private String[][] irgs;
	private String infinitive;
	
	public IrregPatterned(String infinitive, Subject[] s, String[][] irregs)
	{
		irgptrn = Irregular.IRGPTRN_SUBJECT;
		inputordr = s;
		irgs = irregs;
		order = default_tense_order;
		this.infinitive = infinitive;
	}
	
	public IrregPatterned(String infinitive, Tense[] s, String[][] irregs)
	{
		irgptrn = Irregular.IRGPTRN_TENSE;
		inputordr = s;
		irgs = irregs;
		order = default_subject_order;
		this.infinitive = infinitive;
	}
	
	public IrregPatterned(String infinitive, Subject[] s, String[][] irregs, IrregProperty... props)
	{
		irgptrn = Irregular.IRGPTRN_SUBJECT;
		inputordr = s;
		irgs = irregs;
		order = default_tense_order;
		this.infinitive = infinitive;
		this.props = props;
	}
	
	public IrregPatterned(String infinitive, Tense[] s, String[][] irregs, IrregProperty... props)
	{
		irgptrn = Irregular.IRGPTRN_TENSE;
		inputordr = s;
		irgs = irregs;
		order = default_subject_order;
		this.infinitive = infinitive;
		this.props = props;
	}
	
	public IrregPatterned(String infinitive, Subject[] s, String[][] irregs, Tense[] assumed_order)
	{
		irgptrn = Irregular.IRGPTRN_SUBJECT;
		inputordr = s;
		irgs = irregs;
		order = assumed_order;
		this.infinitive = infinitive;
	}
	
	public IrregPatterned(String infinitive, Tense[] s, String[][] irregs, Subject[] assumed_order)
	{
		irgptrn = Irregular.IRGPTRN_TENSE;
		inputordr = s;
		irgs = irregs;
		order = assumed_order;
		this.infinitive = infinitive;
	}
	
	public IrregPatterned(String infinitive, Subject[] s, String[][] irregs, Tense[] assumed_order, IrregProperty... props)
	{
		irgptrn = Irregular.IRGPTRN_SUBJECT;
		inputordr = s;
		irgs = irregs;
		order = assumed_order;
		this.infinitive = infinitive;
		this.props = props;
	}
	
	public IrregPatterned(String infinitive, Tense[] s, String[][] irregs, Subject[] assumed_order, IrregProperty... props)
	{
		irgptrn = Irregular.IRGPTRN_TENSE;
		inputordr = s;
		irgs = irregs;
		order = assumed_order;
		this.infinitive = infinitive;
		this.props = props;
	}
	
	public static void setOrder(Subject[] assumed_order)
	{
		default_subject_order = assumed_order;
	}
	
	public static void setOrder(Tense[] assumed_order)
	{
		default_tense_order = assumed_order;
	}
	
	@Override
	public String get(Subject s, Tense t) {
		int id = -1, depth = -1;
		if(irgptrn == Irregular.IRGPTRN_TENSE)
		{
			int len = inputordr.length;
			for(int i = 0; i < len; i++)
			{
				if(inputordr[i] == t)
				{
					id = i;
					break;
				}
			}
			if(id < 0) return null;
			
			Subject[] o =(Subject[])order;
			Subject sel;
			boolean hasGender = s.getGender() != null;
			for(int i = 0; i < o.length; i++)
			{
				sel = o[i];
				if(sel == s)
				{
					depth = i;
					break;
				}
				else
					
				{
					if(hasGender && sel.getGender() == null)
					{
						if(s.getAndro() == sel)
						{
							Subject andro = s.getAndro();
							depth = i;
							System.out.println(s + " defaulted to androgynous " + andro);
							break;
						}
					}
				}
			}
			if(depth < 0)
				return null;
		}
		else if(irgptrn == Irregular.IRGPTRN_SUBJECT)
		{
			int len = inputordr.length;
			for(int i = 0; i < len; i++)
			{
				if(inputordr[i] == s)
				{
					id = i;
					break;
				}
			}
			if(id < 0) return null;
			
			Tense[] o =(Tense[])order;
			
			for(int i = 0; i < o.length; i++)
				if(o[i] == t)
				{
					depth = i;
					break;
				}
			if(depth < 0)
				return null;
		}
		else
			return null;
		//System.out.println("returning pattern [" + id + ", " + depth + "]: " + irgs[id][depth]);
		//System.out.println();
		return irgs[id][depth];
	}
	
	@Override
	public String get(Subject s, Tense t, IrregProperty... props) {
		int id = -1, depth = -1;
		if(irgptrn == Irregular.IRGPTRN_TENSE)
		{
			int len = inputordr.length;
			for(int i = 0; i < len; i++)
			{
				if(inputordr[i] == t)
				{
					id = i;
					break;
				}
			}
			if(id < 0) return null;
			
			Subject[] o =(Subject[])order;
			Subject sel;
			boolean hasGender = s.getGender() != null;
			for(int i = 0; i < o.length; i++)
			{
				sel = o[i];
				if(sel == s)
				{
					depth = i;
					break;
				}
				else
					
				{
					if(hasGender && sel.getGender() == null)
					{
						if(s.getAndro() == sel)
						{
							Subject andro = s.getAndro();
							depth = i;
							System.out.println(s + " defaulted to androgynous " + andro);
							break;
						}
					}
				}
			}
			if(depth < 0)
				return null;
		}
		else if(irgptrn == Irregular.IRGPTRN_SUBJECT)
		{
			
			int len = inputordr.length;
			for(int i = 0; i < len; i++)
			{
				if(inputordr[i] == s)
				{
					id = i;
					break;
				}
			}
			if(id < 0) return null;
			
			Tense[] o =(Tense[])order;
			
			for(int i = 0; i < o.length; i++)
				if(o[i] == t)
				{
					depth = i;
					break;
				}
			if(depth < 0)
				return null;
			
		}
		else
			return null;
		//System.out.println("returning pattern [" + id + ", " + depth + "]: " + irgs[id][depth]);
		//System.out.println();
		return irgs[id][depth];
	}

	public int getPattern()
	{
		return irgptrn;
	}
	
	@Override
	public String getInfinitive() {
		return infinitive;
	}

	@Override
	public boolean merge(Irregular other) {
		if(!(other instanceof IrregPatterned))
			return false;
		IrregPatterned otr = (IrregPatterned)other;
		if(otr.irgptrn != irgptrn)
			return false;
		
		String[][] otrirgs = otr.irgs;
		int concat = irgs.length + otrirgs.length;
		int depth = irgs[0].length;
		if(depth != otrirgs[0].length)
			throw new ConjugationException("Failed to merge irregulars, array depths incompatible (" + depth + " and " + otrirgs[0].length + ")");
		
		String[][] out = new String[concat][irgs[0].length];
		int len = irgs.length;
		int working_i = 0;
		for(int i = 0; i < len; i++)
		{
			for(int j = 0; j < depth; j++)
				out[working_i][j] =irgs[i][j]; 
			working_i++;
		}
		len = otrirgs.length;
		for(int i = 0; i < len; i++)
		{
			for(int j = 0; j < depth; j++)
				out[working_i][j] =otrirgs[i][j]; 
			working_i++;
		}
		Object[] temp = new Object[inputordr.length + otr.inputordr.length];
		System.arraycopy(inputordr, 0, temp, 0, inputordr.length);
		System.arraycopy(otr.inputordr, 0, temp, inputordr.length, otr.inputordr.length);
		inputordr = temp;
		irgs = out;
		return true;
	}
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		if(irgs != null)
		{
			for(int i = 0; i < irgs.length; i++)
			
				for(int j = 0; j < irgs[i].length; j++)
				{
					out.append(irgs[i][j]);
					out.append(", ");
				}
		}
		else out.append("null");
		
		
		return out.toString();
	}

	
}
