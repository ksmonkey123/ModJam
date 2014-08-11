package ch.modjam.generic.blocks.customRenderer;

import ch.modjam.generic.blocks.EFace;

public class Geometry3 extends Geometry2 {

	public Geometry3(String texture) {
		super(texture);
	}

	public void addStandardCube() {
		addCubeOfRadius(0.5);
	}

	public void addCubeOfRadius(double r) {
		double d = 2 * r;
		this.addQuadOnSide(-r, r, -r, d, d, EFace.TOP);
		this.addQuadOnSide(-r, -r, -r, d, d, EFace.FRONT);
		this.addQuadOnSide(r, -r, -r, d, d, EFace.LEFT);
		this.addQuadOnSide(-r, -r, -r, d, d, EFace.RIGHT);
		this.addQuadOnSide(-r, -r, r, d, d, EFace.BACK);
		this.addQuadOnSide(-r, -r, -r, d, d, EFace.BOTTOM);
	}

}
