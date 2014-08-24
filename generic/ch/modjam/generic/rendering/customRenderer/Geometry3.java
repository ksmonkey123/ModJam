package ch.modjam.generic.rendering.customRenderer;

import java.util.Arrays;
import java.util.HashSet;

import ch.modjam.generic.blocks.EFace;

public class Geometry3 extends Geometry2 {

	/**
	 * solely for method conversion purposes
	 */
	private static final HashSet<EFace>	EMPTY_SET	= new HashSet<EFace>();
	private double						offX		= 0;
	private double						offY		= 0;
	private double						offZ		= 0;

	public Geometry3(String texture) {
		super(texture);
	}

	public Geometry3(Geometry parentPart) {
		super(parentPart);
	}

	public void setOffset(double offX, double offY, double offZ) {
		this.offX = offX;
		this.offY = offY;
		this.offZ = offZ;
	}

	public void addStandardCube() {
		addCubeOfRadius(0.5);
	}

	public void addCubeOfRadius(double d) {
		double r = d / 2;
		this.addQuadOnSide(-r, r, -r, d, d, EFace.TOP);
		this.addQuadOnSide(-r, -r, -r, d, d, EFace.FRONT);
		this.addQuadOnSide(r, -r, -r, d, d, EFace.LEFT);
		this.addQuadOnSide(-r, -r, -r, d, d, EFace.RIGHT);
		this.addQuadOnSide(-r, -r, r, d, d, EFace.BACK);
		this.addQuadOnSide(-r, -r, -r, d, d, EFace.BOTTOM);
	}

	public void addCube(double b, double h, double l, int tex) {
		addCubeWithoutFaces(b, h, l, tex, false, EMPTY_SET);
	}

	public void addCubeWithoutFaces(double b, double h, double l, int tex, EFace... remove) {
		addCubeWithoutFaces(b, h, l, tex, false, remove);
	}

	public void addCubeWithoutFaces(double b, double h, double l, int tex, boolean facesInverted,
			EFace... remove) {
		final HashSet<EFace> removed = new HashSet<EFace>(Arrays.asList(remove));
		addCubeWithoutFaces(b, h, l, tex, facesInverted, removed);
	}

	public void addCubeWithoutFaces(double b, double h, double l, int tex, boolean facesInverted,
			HashSet<EFace> remove) {
		double xr = b / 2;
		double yr = h / 2;
		double zr = l / 2;
		if (!remove.contains(EFace.TOP))
			this.addQuadOnSideWithTex(-xr, yr, -zr, l, b, EFace.TOP.invert(facesInverted), tex);
		if (!remove.contains(EFace.FRONT))
			this.addQuadOnSideWithTex(-xr, -yr, -zr, b, h, EFace.FRONT.invert(facesInverted), tex);
		if (!remove.contains(EFace.LEFT))
			this.addQuadOnSideWithTex(xr, -yr, -zr, l, h, EFace.LEFT.invert(facesInverted), tex);
		if (!remove.contains(EFace.RIGHT))
			this.addQuadOnSideWithTex(-xr, -yr, -zr, l, h, EFace.RIGHT.invert(facesInverted), tex);
		if (!remove.contains(EFace.BACK))
			this.addQuadOnSideWithTex(-xr, -yr, zr, b, h, EFace.BACK.invert(facesInverted), tex);
		if (!remove.contains(EFace.BOTTOM))
			this.addQuadOnSideWithTex(-xr, -yr, -zr, l, b, EFace.BOTTOM.invert(facesInverted), tex);
	}

	/**
	 * TODO: remove public, make it private
	 */
	@Override
	public void addQuadOnSideWithTex(double x, double y, double z, double width, double height,
			EFace visibleFrom, int useTextureTileIndex) {
		super.addQuadOnSideWithTex(x + this.offX, y + this.offY, z + this.offZ, width, height,
			visibleFrom, useTextureTileIndex);
	}

}
