package engine.util.components;

import engine.components.LanguageContext;
import engine.components.Verb_Infinitive;

public interface VViewInfinitiveOverride {
	public Verb_Infinitive getInfinitive(String infinitive, LanguageContext lang);
}
