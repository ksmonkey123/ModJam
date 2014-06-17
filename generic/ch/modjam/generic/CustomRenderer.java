package ch.modjam.generic;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

/**
 * supports easy rendering for custom blocks.<br>
 * Call the methods begin() and end() before calling quad(...)
 * 
 * @author j
 */
public class CustomRenderer {

	private String tex;
	private int tileSize;
	private double uvPerTile;
	private ForgeDirection direction;

	/**
	 * the front face will point to north
	 * 
	 * @param textureNameAndPath
	 *            the full identifier of the texture e.g.
	 *            "modid:textures/blocks/eg.png" <br>
	 *            it is split into 4x4 tiles, these are numbered from top left
	 *            to bottom right 0-15
	 */
	public CustomRenderer(String textureNameAndPath) {
		this(textureNameAndPath,ForgeDirection.NORTH);
	}
	
	/**
	 * the front face will point to north
	 * 
	 * @param textureNameAndPath
	 *            the full identifier of the texture e.g.
	 *            "modid:textures/blocks/eg.png" <br>
	 *            it is split into 4x4 tiles, these are numbered from top left
	 *            to bottom right 0-15
	 * @param dir front face of block will point into this direction
	 */
	public CustomRenderer(String textureNameAndPath,ForgeDirection dir) {
		this.tex = textureNameAndPath;
		this.tileSize = 4;
		this.uvPerTile = 1. / this.tileSize;
		this.direction = dir;
	}

	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom) {
		quad(x, y, z, width, height, visibleFrom, visibleFrom.getTileIndex(), 0);
	}

	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom, int useTextureTileIndex) {
		quad(x, y, z, width, height, visibleFrom, useTextureTileIndex, 0);
	}

	public void quad(double x, double y, double z, double width, double height,
			Side visibleFrom, int useTextureTileIndex, int texRotation) {
		double[] delta = sizeToDeltaCoords(visibleFrom, width, height);

		double xp = x + delta[0];
		double yp = y + delta[1];
		double zp = z + delta[2];
		double[][] coord={{-xp,x},{-yp,y},{-zp,z}};
		
		int tx = useTextureTileIndex % 4;
		int ty = useTextureTileIndex / 4;

		double us = 0, vs = 0, dU, dV;

		boolean swaped = (texRotation % 2) == 1;
		
		int[] uc,vc; // {value (+delta[n]/2 or -delta[n]/2), coordinate (0=x,1=y,2=z)}

		switch (visibleFrom) {
			case TOP: {
				uc = arr(0,Coord.X.index);
				vc = arr(0,Coord.Z.index);
				swaped = !swaped;
				break;
			}
			case FRONT: {
				uc = arr(0,Coord.X.index);
				vc = arr(0,Coord.Y.index);
				break;
			}
			case LEFT: {
				uc = arr(0,Coord.Z.index);
				vc = arr(0,Coord.Y.index);
				break;
			}
			case RIGHT: {
				uc = arr(1,Coord.Z.index);
				vc = arr(0,Coord.Y.index);
				break;
			}
			case BACK: {
				uc = arr(1,Coord.X.index);
				vc = arr(0,Coord.Y.index);
				break;
			}
			case BOTTOM: {
				uc = arr(0,Coord.X.index);
				vc = arr(1,Coord.Z.index);
				swaped = !swaped;
				break;
			}
			default: {
				throw new RuntimeException("Unhandled enum type: "
						+ visibleFrom);
			}
		}
		
		int rot=texRotation;
		while(rot>0) {
			rot--;
			int[] temp = uc;
			uc=arr(vc[0],vc[1]);
			vc=arr(1-temp[0],temp[1]);
		}
		
		us = tx * uvPerTile + (0.5 + coord[uc[1]][uc[0]]) * uvPerTile;
		vs = ty * uvPerTile + (0.5 + coord[vc[1]][vc[0]]) * uvPerTile;

		if (!swaped) {
			dU = width * uvPerTile;
			dV = height * uvPerTile;
		} else {
			dU = height * uvPerTile;
			dV = width * uvPerTile;
		}

		double up = us + dU;
		double vp = vs + dV;

		double[] u = { us, us, up, up };
		double[] v = { vs, vp, vp, vs };
		double[] cx = null;
		double[] cy = null;
		double[] cz = null;

		switch (visibleFrom) {
			case TOP: {
				cx = new double[] { xp, xp, x, x };
				cy = new double[] { y, y, y, y };
				cz = new double[] { zp, z, z, zp };
				break;
			}
			case FRONT: {
				cx = new double[] { xp, xp, x, x };
				cy = new double[] { yp, y, y, yp };
				cz = new double[] { z, z, z, z };
				break;
			}
			case LEFT: {
				cx = new double[] { x, x, x, x };
				cy = new double[] { yp, y, y, yp };
				cz = new double[] { zp, zp, z, z };
				break;
			}
			case RIGHT: {
				cx = new double[] { x, x, x, x };
				cy = new double[] { yp, y, y, yp };
				cz = new double[] { z, z, zp, zp };
				break;
			}
			case BACK: {
				cx = new double[] { x, x, xp, xp };
				cy = new double[] { yp, y, y, yp };
				cz = new double[] { z, z, z, z };
				break;
			}
			case BOTTOM: {
				cx = new double[] { xp, xp, x, x };
				cy = new double[] { y, y, y, y };
				cz = new double[] { z, zp, zp, z };
				break;
			}
		}

		for (int i = 0; i < 4; i++)
			Tessellator.instance.addVertexWithUV(cx[i], cy[i], cz[i], u[(i + texRotation) % 4],
					v[(i + texRotation) % 4]);
	}
	
	private void rotateQuadBasedOnBlockRotation() {
		switch(this.direction) {
			case WEST: {
				GL11.glRotated(90, 0,1,0);
				break;
			}
			case SOUTH: {
				GL11.glRotated(180, 0,1,0);
				break;
			}
			case EAST: {
				GL11.glRotated(270, 0,1,0);
				break;
			}
			case UP: {
				GL11.glRotated(90, 1, 0,0);
				break;
			}
			case DOWN: {
				GL11.glRotated(-90, 1, 0, 0);
				break;
			}
			case NORTH:{}
			case UNKNOWN:{}
			default:{}
		}
		
	}

	private int[] arr(int ... values) {
		return values;
	}

	private enum Coord {
		X(0),Y(1),Z(2);
		private int index;
		private Coord(int i) {
			this.index=i;
		}
	}
	
	private void out(double... c) {
		String[] name = {
				"x", "xp", "y", "yp", "z", "zp", "us", "up", "vs", "vp" };
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < c.length; i++)
			s.append(name[i] + ":" + c[i] + ", ");
		System.out.println(s.substring(0, s.length() - 2));
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

	public void cubeOfRadius(double r) {
		double d = 2 * r;
		this.quad(-r, r, -r, d, d, Side.TOP);
		this.quad(-r, -r, -r, d, d, Side.FRONT);
		this.quad(r, -r, -r, d, d, Side.LEFT);
		this.quad(-r, -r, -r, d, d, Side.RIGHT);
		this.quad(-r, -r, r, d, d, Side.BACK);
		this.quad(-r, -r, -r, d, d, Side.BOTTOM);
	}

	public void begin() {
		GL11.glPushMatrix();
		rotateQuadBasedOnBlockRotation();
		Tessellator.instance.startDrawingQuads();
	}
	
	public void end() {
		Tessellator.instance.draw();
		GL11.glPopMatrix();
	}

	public void setDirection(ForgeDirection dir) {
		this.direction = dir;
	}
}
