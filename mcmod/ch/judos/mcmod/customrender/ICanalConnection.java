package ch.judos.mcmod.customrender;

@SuppressWarnings("javadoc")
public interface ICanalConnection {
	
	public boolean acceptsLiquid();
	
	public boolean providesLiquid();
	
	public int getMaxLiquidAmount();
	
	public int getCurrentLiquidAmount();
	
	/**
	 * @param amount
	 * @return the amount that was actually filled
	 */
	public int fillLiquid(int amount);
}
