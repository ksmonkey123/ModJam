package testj.customrender;

import net.minecraft.client.renderer.Tessellator;

/**
 * @author j
 * TODO: rotation for textures
 * TODO: rotation for the whole block
 */
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
		quad(x, y, z, width, height, visibleFrom, visibleFrom.getTileIndex(),0);
	}
	
	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom, int useTextureTileIndex) {
		quad(x, y, z, width, height, visibleFrom, useTextureTileIndex,0);
	}
	

	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom, int useTextureTileIndex,int texRotation) {

		double[] delta = sizeToDeltaCoords(visibleFrom, width, height);

		Tessellator t = Tessellator.instance;

		double xp = x + delta[0];
		double yp = y + delta[1];
		double zp = z + delta[2];

		int tx = useTextureTileIndex % 4;
		int ty = useTextureTileIndex / 4;

		double us = 0,vs = 0;
		
		double dU = width * uvPerTile;
		double dV = height * uvPerTile;
		
		switch (visibleFrom) {
			case TOP: {
				us = tx * uvPerTile + (0.5-xp) * uvPerTile;
				vs = ty * uvPerTile + (0.5-zp) * uvPerTile;
				dU = height * uvPerTile;
				dV = width * uvPerTile;
				break;
			}
			case FRONT: {
				us = tx * uvPerTile + (0.5-xp) * uvPerTile;
				vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				break;
			}
			case LEFT: {
				us = tx * uvPerTile + (0.5-zp) * uvPerTile;
				vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				break;
			}
			case RIGHT: {
				us = tx * uvPerTile + (z + 0.5) * uvPerTile;
				vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				break;
			}
			case BACK: {
				us = tx * uvPerTile + (x + 0.5) * uvPerTile;
				vs = ty * uvPerTile + (0.5-yp) * uvPerTile;
				break;
			}
			case BOTTOM: {
				us = tx * uvPerTile + (0.5-xp) * uvPerTile;
				vs = ty * uvPerTile + (z+0.5) * uvPerTile;
				dU = height * uvPerTile;
				dV = width * uvPerTile;
				break;
			}
			default: {
				throw new RuntimeException("Unhandled enum type: "+visibleFrom);
			}
		}
		
		
		double up = us + dU;
		double vp = vs + dV;
		
		double[] u = {us,us,up,up};
		double[] v = {vs,vp,vp,vs};
		double[] cx = null;
		double[] cy = null;
		double[] cz = null;
		
		
		switch(visibleFrom) {
			case TOP: {
				cx=new double[]{xp,xp,x,x};
				cy=new double[]{y,y,y,y};
				cz=new double[]{zp,z,z,zp};
				break;
			}
			case FRONT: {
				cx=new double[]{xp,xp,x,x};
				cy=new double[]{yp,y,y,yp};
				cz=new double[]{z,z,z,z};
				break;
			}
			case LEFT: {
				cx=new double[]{x,x,x,x};
				cy=new double[]{yp,y,y,yp};
				cz=new double[]{zp,zp,z,z};
				break;
			}
			case RIGHT: {
				cx=new double[]{x,x,x,x};
				cy=new double[]{yp,y,y,yp};
				cz=new double[]{z,z,zp,zp};
				break;
			}
			case BACK: {
				cx=new double[]{x,x,xp,xp};
				cy=new double[]{yp,y,y,yp};
				cz=new double[]{z,z,z,z};
				break;
			}
			case BOTTOM: {
				cx=new double[]{xp,xp,x,x};
				cy=new double[]{y,y,y,y};
				cz=new double[]{z,zp,zp,z};
				break;
			}
		}
		
		for(int i=0;i<4;i++)
			t.addVertexWithUV(cx[i],cy[i],cz[i],u[i],v[i]);
		
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
