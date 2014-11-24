package engine.components.irreg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import engine.components.LanguageContext;
import engine.components.Subjects.Subject;
import engine.components.Tenses.Tense;
import engine.components.err.verb.ConjugationException;

public class IrregSet implements Irregular
{
	private IrregPatterned ptrnSUBJ;
	private IrregPatterned ptrnTENSE;
	private IrregSpecific spec;
	private List<Irregular> other = new ArrayList<>(); 
	
	private String infinitive;
	
	public IrregSet(String infinitive)
	{
		this.infinitive = infinitive;
	}
	
	@Override
	public String get(Subject s, Tense t) {
		String r = null;
		if(ptrnTENSE != null)
		{
			//System.out.println("Checking tensePattern for irregular for " + infinitive);
			r = ptrnTENSE.get(s, t);
			if(r != null)
				return r;
		}
		if(ptrnSUBJ != null)
		{
			//System.out.println("Checking subjectPattern for irregular for "  + infinitive);
			r = ptrnSUBJ.get(s, t);
			if(r != null)
				return r;
		}
			
		
		if(spec != null)
		{
			r = spec.get(s, t);
			//System.out.println("Checking IrregSpecific for " + infinitive +", found=" + r);
		}
		//System.out.println();
		return r;
	}
	
	@Override
	public String get(Subject s, Tense t, IrregProperty... props) {
		if(props == null || props.length < 1)
			return get(s, t);
		
		return null;
	}

	@Override
	public String getInfinitive() {
		return infinitive;
	}

	@Override
	public int getPattern() {
		return -1;
	}

	@Override
	public boolean merge(Irregular ir) {
		if(ir.getPattern() == Irregular.IRGPTRN_SUBJECT)
		{
			IrregPatterned irr = (IrregPatterned)ir;
			if(irr.getPattern() == Irregular.IRGPTRN_SUBJECT)
			{
				if(ptrnSUBJ == null)
				{
					ptrnSUBJ = irr;
					return true;
				}
				else 
					return ptrnSUBJ.merge(irr);
			}
			return false;
		}
		
		if(ir.getPattern() == Irregular.IRGPTRN_TENSE)
		{
			IrregPatterned irr = (IrregPatterned)ir;
			if(irr.getPattern() == Irregular.IRGPTRN_TENSE)
			{
				if(ptrnTENSE == null)
				{
					ptrnTENSE = irr;
					return true;
				}
				else return ptrnTENSE.merge(irr);
			}
			return false;
		}
		
		if(ir.getPattern() == Irregular.IRGPTRN_SPECIFIC)
		{
			IrregSpecific irr = (IrregSpecific)ir;
			if(irr.getPattern() == Irregular.IRGPTRN_SPECIFIC)
			{
				if(spec == null)
				{
					spec = irr;
					return true;
				}
				else return spec.merge(irr);
			}
			return false;
		}
		
		other.add(ir);
		return true;
	}
	
	
	/**
	 * 
	 * @param f
	 * @param context
	 * @return
	 * @throws FileNotFoundException
	 */
	public static HashMap<String, IrregSet> parseIrregularFile(String path, LanguageContext context) throws FileNotFoundException
	{
		InputStream f = IrregSet.class.getResourceAsStream(path);
		HashMap<String, IrregSet> out = new HashMap<>();
		
		Tense[] tordr = IrregPatterned.default_tense_order;
		Subject[] sordr = IrregPatterned.default_subject_order;
		Tense selt = null;
		Subject sels = null;
		HashMap<String, Subject> subjectkeys = new HashMap<String, Subject>();
		HashMap<String, Tense> tensekeys = new HashMap<String, Tense>();
		int inferred = 0;
		int mode = -1;
		boolean addinfin = false;
		
		ArrayList<Object> buffer = new ArrayList<>();
		Scanner reader = new Scanner(f);
		String token;
		while(reader.hasNext())
		{
			token = reader.nextLine();
			if(token.trim().startsWith("START"))
				break;
		}
		
		IrregSet sel = null;
		IrregSpecific temp = null;
		pojo temp2 = null;
		System.out.println("Beginning to parse irregular file " + path);
		while(reader.hasNext())
		{
			token = reader.next();
			if(token.length() > 1)
			{
				if(token.charAt(0) == '@')
				{
					char next = token.charAt(1);
					if(next == '/')
					{
						selt = null;
						sels = null;
						inferred = 0;
					}
					else if(next == 'T')
					{
						if(mode == Irregular.IRGPTRN_SUBJECT)
							System.err.println("Error parsing file, can not set tense while in inferred tense mode!");
						inferred=0;
						if(token.length() > 3) //@T:
						{
							token = token.substring(3, token.length());
							selt = tensekeys.get(token);
							if(selt == null)
								selt = context.parseTense(token);
							if(selt == null)
								System.err.println("Error parsing file, Tense " + token + " not recognized.");
							if(temp2 != null)
								buffer.add(temp2);
							temp2 = new pojo(selt);
						}
					}
					else if(next == 'S')
					{
						if(mode == Irregular.IRGPTRN_TENSE)
							System.err.println("Error parsing file, can not set subject while in inferred subject mode!");
						inferred=0;

						if(token.length() > 3) //@S:
						{
							token = token.substring(3, token.length());
							sels = subjectkeys.get(token);
							if(sels == null)
								sels = context.parseSubject(token);
							if(sels == null)
								System.err.println("Error parsing file, Subject " + token + "  not recognized.");
								
							if(temp2 != null)
								buffer.add(temp2);
							
							temp2 = new pojo(sels);
						}
					}
					else
					{
						token = token.substring(1, token.length()); // is this necessary?
					}
				}
				else if(token.charAt(0) == '>')
				{
					token = token.substring(1, token.length());
					sel = new IrregSet(token);
					out.put(token, sel);
				}
				else if(token.charAt(0) == '#')
				{
					if(token.charAt(1) == '/')
					{
						switch(mode)
						{
							case Irregular.IRGPTRN_SPECIFIC:
								sel.merge(temp);
								break;
							case Irregular.IRGPTRN_SUBJECT:
								if(temp2 != null)
									buffer.add(temp2);
								
								if(!buffer.isEmpty())
								{
									int sz = buffer.size();
									String[][] outS = new String[sz][0];
									Subject[] outSubject = new Subject[sz];
									pojo selpojo;
									for(int i = 0; i < sz; i++)
									{
										selpojo = ((pojo)buffer.get(i));
										outS[i] = selpojo.getArray();
										outSubject[i] = selpojo.getSubject();
									}
									
									IrregPatterned pat = new IrregPatterned(sel.infinitive, outSubject, outS, tordr);
									sel.merge(pat);
									buffer.clear();
								}
								temp2 = null;
								break;
							case Irregular.IRGPTRN_TENSE:

								if(temp2 != null)
								{
									buffer.add(temp2);
								}
								
								if(!buffer.isEmpty())
								{
									int sz = buffer.size();
									String[][] outS = new String[sz][0];
									Tense[] outTense = new Tense[sz];
									pojo selpojo;
									for(int i = 0; i < sz; i++)
									{
										selpojo = ((pojo)buffer.get(i));
										outS[i] = selpojo.getArray();
										outTense[i] = selpojo.getTense();
									}
									try
									{
										IrregPatterned pat = new IrregPatterned(sel.infinitive, outTense, outS, sordr);
										sel.merge(pat);
										buffer.clear();
									}
									catch(ConjugationException ce)
									{
										System.err.println("Failed to initialize and merge new pattern: " + ce.getMessage());
									}
								}
								temp2 = null;
								break;
						}
						selt = null;
						sels = null;
						mode = -1;
						inferred = 0;
						buffer.clear();
						temp2 = null;
						temp = null;
					}
					
					token = token.substring(1, token.length());
					if(token.charAt(0) == 'S')
					{
						if(token.length() == 1)
						{
							mode = Irregular.IRGPTRN_SUBJECT;
							inferred = 0;
							selt = null;
							sels = null;
							buffer.clear();
						}
						else if(token.startsWith("SORD"))
						{
							buffer.clear();
							while(reader.hasNext())
							{
								token = reader.next();
								if(token.startsWith("#/"))
									break;
								Subject subj = subjectkeys.get(token);
								if(subj == null)
									subj = context.parseSubject(token);
								if(subj != null)
									buffer.add(subj);
								else 
									System.err.println("Subject " + token + " in " + path + " not recognized, ignoring.");
							}
							int len = buffer.size();
							sordr = new Subject[len];
							
							for(int i = 0; i < len; i++)
								sordr[i] = (Subject)buffer.get(i);
							inferred = 0;
							selt = null;
							sels = null;
						}
						else if(token.equals("SUBJECTS"))
						{
							while(reader.hasNext())
							{
								token = reader.next();
								if(token.contains("#/"))
									break;
								int index = token.indexOf('=');
								if(index != -1)
								{
									String subjectS = token.substring(0, index);
									Subject subject = context.parseSubject(subjectS);
									if(subject != null)
										subjectkeys.put(token.substring(index+1, token.length()), subject);
									else
									{
										System.err.println("Subject " + subjectS + " in irregular file " + path + " not recognized, assuming break.");
										break;
									}
								}
								else
								{
									System.err.println( "In irregular file " + path + ", " + token + " isn't an assignation. Assuming break.");
									break;
								}
							}
						}
					}else if(token.charAt(0) == 'T')
					{
						if(token.length() == 1)
						{
							mode = Irregular.IRGPTRN_TENSE;
							inferred = 0;
							selt = null;
							sels = null;
							buffer.clear();
						}
						else if(token.startsWith("TORD"))
						{
							buffer.clear();
							while(reader.hasNext())
							{
								token = reader.next();
								if(token.startsWith("#/"))
									break;
								Tense tense = tensekeys.get(token);
								if(tense == null)
									tense = context.parseTense(token);
								if(tense != null)
									buffer.add(tense);
								else 
									System.err.println("Tense " + token + " in " + path + " not recognized, ignoring.");
							}
							int len = buffer.size();
							tordr = new Tense[len];
							
							for(int i = 0; i < len; i++)
								tordr[i] = (Tense)buffer.get(i);
							inferred = 0;
							selt = null;
							sels = null;
						}
						else if(token.equals("TENSES"))
						{
							while(reader.hasNext())
							{
								token = reader.next();
								if(token.contains("#/"))
									break;
								int index = token.indexOf('=');
								if(index != -1)
								{
									String tenseS = token.substring(0, index);
									Tense tense = context.parseTense(tenseS);
									if(tense != null)
										tensekeys.put(token.substring(index+1, token.length()), tense);
									else
									{
										System.err.println("Tense " + tenseS + " in irregular file " + path + " not recognized, assuming break.");
										break;
									}
								}
								else
								{
									System.err.println( "In irregular file " + path + ", " + token + " isn't an assignation. Assuming break.");
									break;
								}
							}
						}
					}else if(token.equals("P"))
					{
						mode = Irregular.IRGPTRN_SPECIFIC;
						temp = new IrregSpecific(sel.infinitive);
						inferred = 0;
						selt = null;
						sels = null;
						temp2 = null;
					}
				}
				else
					addinfin = true;
			}
			else
				addinfin = true;
			
			if(addinfin)
			{
				if(mode > -1)
				{
					
					switch(mode)
					{
						case Irregular.IRGPTRN_SPECIFIC:
							if(sels != null && selt != null)
							{
								if(temp!=null)
									temp.add(sels, selt, token);
								else System.err.println("Can't add conjugation \"" + token + "\". In specific mode, but outside of #P region");
							}
							else System.err.println("Can't add conjugation \"" + token + "\", both tense and subject need to be set.");
							
							break;
						case Irregular.IRGPTRN_SUBJECT:
							if(tordr != null && tordr.length > 0)
							{
								selt = tordr[inferred];
								
								if(sels != null)
									if(temp2 != null)
										temp2.addConjugate(token);
									else System.err.println("Can't add conjugation \"" + token + "\". In subject mode, but outside of #S region");
								else System.err.println("Can't add conjugation \"" + token + "\", subject null");
								
								inferred++;
								if(inferred >= tordr.length)
									inferred = 0;
							}
							else System.err.println("Can't add conjugation \"" + token + "\", tense order hasn't been set");
							
							break;
						case Irregular.IRGPTRN_TENSE:
							if(sordr != null && sordr.length > 0)
							{
								sels = sordr[inferred];
								
								if(selt != null)
									if(temp2!=null)
										temp2.addConjugate(token);
									else System.err.println("Can't add conjugation \"" + token + "\". In tense mode, but outside of #T region");
								else System.err.println("Can't add conjugation \"" + token + "\", tense null");
								
								inferred++;
								if(inferred >= sordr.length)
									inferred = 0;
							}
							else System.err.println("Can't add conjugation \"" + token + "\", subject order hasn't been set");
							break;
					}
				}
				addinfin = false;
			}
		}
		reader.close();
		System.out.println("Finished parsing IrregSet HashMap from file " + path + ".");
		return out;
	}
	
	private static class pojo
	{
		private Object o;
		private ArrayList<String> set;
		boolean tense;
		public pojo(Subject o)
		{
			this.o=o;
			set = new ArrayList<>();
			tense = false;
		}
		public pojo(Tense o)
		{
			this.o=o;
			set = new ArrayList<>();
			tense = true;
		}
		public void addConjugate(String conj)
		{
			set.add(conj);
		}
		
		public Tense getTense()
		{
			if(tense)
				return (Tense)o;
			else return null;
		}
		
		public Subject getSubject()
		{
			if(!tense)
				return (Subject)o;
			else return null;
		}
		
		public String[] getArray()
		{
			return set.toArray(new String[set.size()]);
		}
	}
	
	public String toVerboseString()
	{
		StringBuilder out = new StringBuilder("IrregSet");
		if(ptrnSUBJ != null)
		{
			out.append("   [S:");
			out.append(ptrnSUBJ.toString());
			out.append("]");
		}
		if(ptrnTENSE != null)
		{
			out.append("   [T:");
			out.append(ptrnTENSE.toString());
			out.append("]");
		}
		if(spec != null)
		{
			out.append("   [P:");
			out.append(spec.toString());
			out.append("]");
		}
		out.append('\n');
		return out.toString();
	}
	
	public String toString()
	{
		/*
		StringBuilder out = new StringBuilder("IrregSet");
		if(ptrnSUBJ != null)
			out.append("[S]");
		if(ptrnTENSE != null)
			out.append("[T]");
		if(spec != null)
			out.append("[P]");
		return out.toString();
		 */
		return toVerboseString();
	}

	
}
