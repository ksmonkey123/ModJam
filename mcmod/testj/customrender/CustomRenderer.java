package testj.customrender;

import net.minecraft.client.renderer.Tessellator;

public class CustomRenderer {

	private String tex;
	private int tileSize;
	private double uvPerTile;

	/**
	 * @param textureNameAndPath
	 *            the full identifier of the texture e.g.
	 *            "modid:textures/blocks/eg.png" <br>
	 *            it is split into 4x4 tiles, these are numbered from top left
	 *            to bottom right 0-15
	 */
	public CustomRenderer(String textureNameAndPath) {
		this.tex = textureNameAndPath;
		this.tileSize = 4;
		this.uvPerTile = 1. / this.tileSize;
	}

	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom) {
		quad(x,y,z,width,height,visibleFrom,visibleFrom.getTileIndex());
	}

	private void quad(double x, double y, double z, double width,
			double height, Side visibleFrom, int useTextureTileIndex) {
		double[] delta = sizeToDeltaCoords(visibleFrom,width,height);
		
		Tessellator t = Tessellator.instance;
		
		t.addVertex(x+delta[0], y+delta[1], z);
		t.addVertex(x+delta[0], y, z+delta[2]);
		t.addVertex(x, y, z);
		t.addVertex(x, y+delta[1], z+delta[2]);
		
	}

	private double[] sizeToDeltaCoords(Side visibleFrom, double width,
			double height) {
		int sideI = visibleFrom.getIndex();
		double[] delta = new double[3];
		for(int i=0;i<3;i++)
			delta[i] = width * widthToXYZ[i][sideI] + height * heightToXYZ[i][sideI];
		return delta;
	}

	/**
	 * 1st index: 0=x,1=y,2=z<br>
	 * 2nd index: sideIndex<br>
	 * defines the mapping from the side width to dx, dy and dz
	 */
	private static final int[][] widthToXYZ = { //
			{ 0, 0, 0, 1, 0, 1 }, //
			{ 0, 0, 0, 0, 0, 0 }, //
			{ 1, 1, 1, 0, 1, 0 } };
	
	private static final int[][] heightToXYZ = { //
		{ 1, 1, 0, 0, 0, 0 }, //
		{ 0, 0, 1, 1, 1, 1 }, //
		{ 0, 0, 0, 0, 0, 0 } };

	public enum Side {
		TOP(1, 0), BOTTOM(9, 1), LEFT(4, 2), FRONT(5, 3), RIGHT(6, 4), 
		BACK(10, 5);
		private int tileIndex;
		private int index;

		Side(int tileIndex, int index) {
			// the index of the tile in the texture for this side
			this.tileIndex = tileIndex;
			// a unique index for each side, starting at 0
			this.index = index;
		}

		public int getTileIndex() {
			return tileIndex;
		}
		public int getIndex() {
			return index;
		}
	}
}
