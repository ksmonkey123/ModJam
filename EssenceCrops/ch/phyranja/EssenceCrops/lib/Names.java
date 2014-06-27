package ch.phyranja.EssenceCrops.lib;

import ch.phyranja.EssenceCrops.essences.Essence;

@SuppressWarnings("javadoc")
public class Names {
	/*
	 * All names must be lower case
	 */

	public static final String NeutralES = "neutral_es";
	public static final String NeutralEP = "neutral_ep";
	public static final String NeutralEPetal = "neutral_e_petal";
	public static String[] seeds= new String[Essence.values().length];
	public static String[] plants= new String[Essence.values().length];
	public static String[] petals= new String[Essence.values().length];
	
	
	public static void initNames(){
		
		
		for(Essence essence:Essence.values()){
			seeds[essence.ordinal()]=essence.toString() + "_es";
			plants[essence.ordinal()]=essence.toString() + "_ep";
			petals[essence.ordinal()]=essence.toString() + "_e_petal";
		}
		
		
	}
	
}
