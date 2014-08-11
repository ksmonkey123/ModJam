package ch.modjam.generic.blocks.customRenderer;

import ch.modjam.generic.blocks.Axis;
import ch.modjam.generic.blocks.EFace;

public class Geometry2 extends Geometry {

	/**
	 * the amount of tiles that are beside each other in the texture (only width)
	 */
	private final static int		tileSize	= 4;
	/**
	 * float value that is used per tile of the texture
	 */
	private final static double		uvPerTile	= 1. / tileSize;

	/**
	 * 1st index: 0=x,1=y,2=z<br>
	 * 2nd index: sideIndex<br>
	 * defines the mapping from the side width to dx, dy and dz
	 */
	private static final int[][]	widthToXYZ	= { //
												{ 0, 0, 0, 1, 0, 1 }, //
												{ 0, 0, 0, 0, 0, 0 }, //
												{ 1, 1, 1, 0, 1, 0 } };

	private static final int[][]	heightToXYZ	= { //
												{ 1, 1, 0, 0, 0, 0 }, //
												{ 0, 0, 1, 1, 1, 1 }, //
												{ 0, 0, 0, 0, 0, 0 } };

	public Geometry2(String texture) {
		super(texture);
	}

	public void addQuadOnSide(double x, double y, double z, double width, double height,
			EFace visibleFrom) {
		addQuadOnSideWithTexRot(x, y, z, width, height, visibleFrom, visibleFrom.getTileIndex(), 0);
	}

	public void addQuadOnSideWithTex(double x, double y, double z, double width, double height,
			EFace visibleFrom, int useTextureTileIndex) {
		addQuadOnSideWithTexRot(x, y, z, width, height, visibleFrom, useTextureTileIndex, 0);
	}

	public void addQuadOnSideWithTexRot(double x, double y, double z, double width, double height,
			EFace visibleFrom, int useTextureTileIndex, int texRotation) {

		// calculates the absolute coordinates of the points that are offset by width,height
		double[] delta = size2dTo3dDeltaCoordinates(visibleFrom, width, height);
		double xp = x + delta[0];
		double yp = y + delta[1];
		double zp = z + delta[2];

		double[][] coord = { { -xp, x }, { -yp, y }, { -zp, z } };

		double us = 0, vs = 0, dU, dV;

		boolean[] swaped = { (texRotation % 2) == 1 };

		int[] uc, vc; // {value (+delta[n]/2 or -delta[n]/2), coordinate
						// (0=x,1=y,2=z)}

		int[][] ucvc = calcUcVcFromSide(visibleFrom, swaped);
		uc = ucvc[0];
		vc = ucvc[1];

		int rot = texRotation;
		while (rot > 0) {
			rot--;
			int[] temp = uc;
			uc = arr(vc[0], vc[1]);
			vc = arr(1 - temp[0], temp[1]);
		}

		int tx = useTextureTileIndex % 4;
		int ty = useTextureTileIndex / 4;
		us = tx * uvPerTile + (0.5 + coord[uc[1]][uc[0]]) * uvPerTile;
		vs = ty * uvPerTile + (0.5 + coord[vc[1]][vc[0]]) * uvPerTile;

		if (!swaped[0]) {
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

		// get the final coordinates for all points of the quad
		double[][] c = getFinalCoordinatesFromMinMaxSide(visibleFrom, x, xp, y, yp, z, zp);
		double[] cx = c[0];
		double[] cy = c[1];
		double[] cz = c[2];

		for (int i = 0; i < 4; i++)
			this.addPoint((float) cx[i], (float) cy[i], (float) cz[i],
				(float) u[(i + texRotation) % 4], (float) v[(i + texRotation) % 4]);
	}

	private static int[][] calcUcVcFromSide(EFace visibleFrom, boolean[] swaped) {
		int[] uc, vc;
		switch (visibleFrom) {
			case TOP: {
				uc = arr(0, Axis.X.index);
				vc = arr(0, Axis.Z.index);
				swaped[0] = !swaped[0];
				break;
			}
			case FRONT: {
				uc = arr(0, Axis.X.index);
				vc = arr(0, Axis.Y.index);
				break;
			}
			case LEFT: {
				uc = arr(0, Axis.Z.index);
				vc = arr(0, Axis.Y.index);
				break;
			}
			case RIGHT: {
				uc = arr(1, Axis.Z.index);
				vc = arr(0, Axis.Y.index);
				break;
			}
			case BACK: {
				uc = arr(1, Axis.X.index);
				vc = arr(0, Axis.Y.index);
				break;
			}
			case BOTTOM: {
				uc = arr(0, Axis.X.index);
				vc = arr(1, Axis.Z.index);
				swaped[0] = !swaped[0];
				break;
			}
			default: {
				throw new RuntimeException("Unhandled enum type: " + visibleFrom);
			}
		}
		return new int[][] { uc, vc };
	}

	/**
	 * @param visibleFrom
	 * @param x
	 * @param xp
	 * @param y
	 * @param yp
	 * @param z
	 * @param zp
	 * @return
	 */
	private static double[][] getFinalCoordinatesFromMinMaxSide(EFace visibleFrom, double x,
			double xp, double y, double yp, double z, double zp) {
		double[] cx, cy, cz;
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
			case BOTTOM: // fixes potential nullpointer warning
			default: {
				cx = new double[] { xp, xp, x, x };
				cy = new double[] { y, y, y, y };
				cz = new double[] { z, zp, zp, z };
				break;
			}
		}
		return new double[][] { cx, cy, cz };
	}

	private static double[] size2dTo3dDeltaCoordinates(EFace visibleFrom, double width,
			double height) {
		int sideI = visibleFrom.getIndex();
		double[] delta = new double[3];
		for (int i = 0; i < 3; i++)
			delta[i] = width * widthToXYZ[i][sideI] + height * heightToXYZ[i][sideI];
		return delta;
	}

	private static int[] arr(int... values) {
		return values;
	}

}
