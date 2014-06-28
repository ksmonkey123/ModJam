package ch.phyranja.EssenceCrops.lib;

import ch.phyranja.EssenceCrops.essences.Essence;

@SuppressWarnings("javadoc")
public class Names {
	/*
	 * All names must be lower case
	 */

	public static String[] seeds= new String[Essence.values().length];
	public static String[] plants= new String[Essence.values().length];
	public static String[] petals= new String[Essence.values().length];
	public static String[] smallCapsules= new String[Essence.values().length];
	public static String[] bigCapsules= new String[Essence.values().length];
	
	
	public static void initNames(){
		
		for(Essence essence:Essence.values()){
			seeds[essence.ordinal()]=essence.toString() + "_es";
			plants[essence.ordinal()]=essence.toString() + "_ep";
			petals[essence.ordinal()]=essence.toString() + "_e_petal";
			smallCapsules[essence.ordinal()]=essence.toString() + "_small_ec";
			bigCapsules[essence.ordinal()]=essence.toString() + "_big_ec";
		}
		
		
	}
	
}
