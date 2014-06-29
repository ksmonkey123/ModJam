package ch.phyranja.EssenceCrops.lib;

import ch.phyranja.EssenceCrops.essences.EssenceType;

@SuppressWarnings("javadoc")
public class Names {
	/*
	 * All names must be lower case
	 */

	public static final String Extractor = "e_extractor";
	public static String[] seeds= new String[EssenceType.values().length];
	public static String[] plants= new String[EssenceType.values().length];
	public static String[] petals= new String[EssenceType.values().length];
	public static String[] smallCapsules= new String[EssenceType.values().length];
	public static String[] bigCapsules= new String[EssenceType.values().length];
	public static String[] essences= new String[EssenceType.values().length];
	
	
	public static void initNames(){
		
		for(EssenceType essence:EssenceType.values()){
			seeds[essence.ordinal()]=essence.toString() + "_es";
			plants[essence.ordinal()]=essence.toString() + "_ep";
			petals[essence.ordinal()]=essence.toString() + "_e_petal";
			smallCapsules[essence.ordinal()]=essence.toString() + "_small_ec";
			bigCapsules[essence.ordinal()]=essence.toString() + "_big_ec";
			essences[essence.ordinal()]=essence.toString() + "_essence";
		}
		
		
	}
	
}
