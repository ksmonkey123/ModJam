package ch.modjam.generic.rendering.customRenderer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ch.modjam.generic.blocks.EFace;

public class Geometry3 extends Geometry2 {

	public Geometry3(String texture) {
		super(texture);
	}

	public Geometry3(Geometry parentPart) {
		super(parentPart);
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
		double xr = b / 2;
		double yr = h / 2;
		double zr = l / 2;
		this.addQuadOnSideWithTex(-xr, yr, -zr, l, b, EFace.TOP, tex);
		this.addQuadOnSideWithTex(-xr, -yr, -zr, b, h, EFace.FRONT, tex);
		this.addQuadOnSideWithTex(xr, -yr, -zr, l, h, EFace.LEFT, tex);
		this.addQuadOnSideWithTex(-xr, -yr, -zr, l, h, EFace.RIGHT, tex);
		this.addQuadOnSideWithTex(-xr, -yr, zr, b, h, EFace.BACK, tex);
		this.addQuadOnSideWithTex(-xr, -yr, -zr, l, b, EFace.BOTTOM, tex);
	}

	public void addCubeWithoutFaces(double b, double h, double l, int tex, EFace... remove) {
		double xr = b / 2;
		double yr = h / 2;
		double zr = l / 2;
		final Set<EFace> mySet = new HashSet<EFace>(Arrays.asList(remove));
		if (!mySet.contains(EFace.TOP))
			this.addQuadOnSideWithTex(-xr, yr, -zr, l, b, EFace.TOP, tex);
		if (!mySet.contains(EFace.FRONT))
			this.addQuadOnSideWithTex(-xr, -yr, -zr, b, h, EFace.FRONT, tex);
		if (!mySet.contains(EFace.LEFT))
			this.addQuadOnSideWithTex(xr, -yr, -zr, l, h, EFace.LEFT, tex);
		if (!mySet.contains(EFace.RIGHT))
			this.addQuadOnSideWithTex(-xr, -yr, -zr, l, h, EFace.RIGHT, tex);
		if (!mySet.contains(EFace.BACK))
			this.addQuadOnSideWithTex(-xr, -yr, zr, b, h, EFace.BACK, tex);
		if (!mySet.contains(EFace.BOTTOM))
			this.addQuadOnSideWithTex(-xr, -yr, -zr, l, b, EFace.BOTTOM, tex);
	}

}
