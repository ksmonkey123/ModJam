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
		quad(x, y, z, width, height, visibleFrom, visibleFrom.getTileIndex());
	}

	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom, int useTextureTileIndex) {

		double[] delta = sizeToDeltaCoords(visibleFrom, width, height);

		Tessellator t = Tessellator.instance;

		double xp = x + delta[0];
		double yp = y + delta[1];
		double zp = z + delta[2];

		int tx = useTextureTileIndex % 4;
		int ty = useTextureTileIndex / 4;

		// double us,vs,dU,dV,up,vp;
//		out(x,xp,y,yp,z,zp,us,up,vs,vp);

		switch (visibleFrom) {
			case TOP: {
				double us = tx * uvPerTile + (0.5-xp) * uvPerTile;
				double vs = ty * uvPerTile + (0.5-zp) * uvPerTile;
				double dU = height * uvPerTile;
				double dV = width * uvPerTile;
				double up = us + dU;
				double vp = vs + dV;
				
				t.addVertexWithUV(xp, y, zp, us, vs);
				t.addVertexWithUV(xp, y, z, us, vp);
				t.addVertexWithUV(x, y, z, up, vp);
				t.addVertexWithUV(x, y, zp, up, vs);
				break;
			}
			case FRONT: {
				double us = tx * uvPerTile + (0.5-xp) * uvPerTile;
				double vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				double dU = width * uvPerTile;
				double dV = height * uvPerTile;
				double up = us + dU;
				double vp = vs + dV;
				
				t.addVertexWithUV(xp, yp, z, us, vs);
				t.addVertexWithUV(xp, y, z, us, vp);
				t.addVertexWithUV(x, y, z, up, vp);
				t.addVertexWithUV(x, yp, z, up, vs);
				break;
			}
			case LEFT: {
				double us = tx * uvPerTile + (0.5-zp) * uvPerTile;
				double vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				double dU = width * uvPerTile;
				double dV = height * uvPerTile;
				double up = us + dU;
				double vp = vs + dV;

				t.addVertexWithUV(x, yp, zp, us, vs);
				t.addVertexWithUV(x, y, zp, us, vp);
				t.addVertexWithUV(x, y, z, up, vp);
				t.addVertexWithUV(x, yp, z, up, vs);
				break;
			}
			case RIGHT: {
				double us = tx * uvPerTile + (z + 0.5) * uvPerTile;
				double vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				double dU = width * uvPerTile;
				double dV = height * uvPerTile;
				double up = us + dU;
				double vp = vs + dV;

				t.addVertexWithUV(x, yp, z, us, vs);
				t.addVertexWithUV(x, y, z, us, vp);
				t.addVertexWithUV(x, y, zp, up, vp);
				t.addVertexWithUV(x, yp, zp, up, vs);
				break;
			}
			case BACK: {
				double us = tx * uvPerTile + (x + 0.5) * uvPerTile;
				double vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				double dU = width * uvPerTile;
				double dV = height * uvPerTile;
				double up = us + dU;
				double vp = vs + dV;

				t.addVertexWithUV(x, yp, z, us, vs);
				t.addVertexWithUV(x, y, z, us, vp);
				t.addVertexWithUV(xp, y, z, up, vp);
				t.addVertexWithUV(xp, yp, z, up, vs);
				break;
			}
			case BOTTOM: {
				double us = tx * uvPerTile + (0.5-xp) * uvPerTile;
				double vs = ty * uvPerTile + (z+0.5) * uvPerTile;
				double dU = height * uvPerTile;
				double dV = width * uvPerTile;
				double up = us + dU;
				double vp = vs + dV;

				t.addVertexWithUV(xp, y, z, us, vs);
				t.addVertexWithUV(xp, y, zp, us, vp);
				t.addVertexWithUV(x, y, zp, up, vp);
				t.addVertexWithUV(x, y, z, up, vs);
				break;
			}
		}
	}

	private void out(double ... c) {
		String[] name= {"x","xp","y","yp","z","zp","us","up","vs","vp"};
		StringBuffer s =new StringBuffer();
		for(int i=0;i<c.length;i++)
			s.append(name[i]+":"+c[i]+", ");
		System.out.println(s.substring(0, s.length()-2));
	}

	private double[] sizeToDeltaCoords(Side visibleFrom, double width,
			double height) {
		int sideI = visibleFrom.getIndex();
		double[] delta = new double[3];
		for (int i = 0; i < 3; i++)
			delta[i] = width * widthToXYZ[i][sideI] + height
					* heightToXYZ[i][sideI];
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
		TOP(1, 0), BOTTOM(9, 1), LEFT(4, 2), FRONT(5, 3), RIGHT(6, 4), BACK(10,
				5);
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

	public void standardCube() {
		cubeOfRadius(0.5);
	}
	
	public void cubeOfRadius(double r){
		double d = 2*r;
		this.quad(-r, r, -r, d, d, Side.TOP);
		this.quad(-r,-r,-r,d,d,Side.FRONT);
		this.quad(r,-r,-r,d,d,Side.LEFT);
		this.quad(-r,-r,-r,d,d,Side.RIGHT);
		this.quad(-r,-r,r,d,d,Side.BACK);
		this.quad(-r,-r,-r,d,d,Side.BOTTOM);
	}
}
