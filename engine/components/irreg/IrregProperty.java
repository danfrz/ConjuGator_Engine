package engine.components.irreg;

import engine.components.err.verb.LanguageContextException;

public interface IrregProperty {
	String getName();
	
	/**
		Parses property info from Irregular file.
		@P:NAME:INFO:INFO:...:INFO
		
		this function is passed the last lines after the name,
		so "INFO:INFO:...:INFO" and should set this property object's values
		according to that information. 
		
	 * @param info
	 * @return
	 * @throws LanguageContextException if the info is of an invalid type
	 */
	boolean parseInfo(String info);
	//forced implementation because irregspecific needs it to function properly
	public int hashCode();
}
