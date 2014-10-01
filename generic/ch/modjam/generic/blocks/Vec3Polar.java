package ch.modjam.generic.blocks;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Vec3Polar {

	/**
	 * 0° in direction of z, 90° in direction of x, clock-wise oriented (viewed from above / Y+)
	 */
	public double	angleYaw;

	/**
	 * 0° on the x-z plane, -90° directing at Y-axis (or up), +90° is down, clock-wise oriented
	 * (viewed from a point in the XZ-plane)
	 */
	public double	anglePitch;

	public double	radius;

	public Vec3Polar() {}

	/**
	 * @param angleYaw
	 * @param anglePitch
	 * @param radius
	 */
	public Vec3Polar(double angleYaw, double anglePitch, double radius) {
		this.angleYaw = angleYaw;
		this.anglePitch = anglePitch;
		this.radius = radius;
	}

	/**
	 *
	 * @param centerDistance
	 * @param origin the calculated corners use the origin as their center point
	 * @return the 4 vertex located on the corner of a plane in the origin facing the point
	 *         represented by this polar vector
	 */
	public Vector4f[] getRectangleCornersInOrigin(float centerDistance, Vector3f origin) {
		Matrix4f transform = new Matrix4f();
		transform.translate(origin);
		transform.rotate((float) this.angleYaw, new Vector3f(0, 1, 0)); // yaw
		transform.rotate((float) this.anglePitch, new Vector3f(1, 0, 0)); // pitch

		float r = centerDistance;
		Vector4f[] corners = new Vector4f[] { new Vector4f(r, r, 0, 1), new Vector4f(-r, r, 0, 1), new Vector4f(
			-r, -r, 0, 1), new Vector4f(r, -r, 0, 1) };

		for (Vector4f v : corners)
			Matrix4f.transform(transform, v, v);
		return corners;
	}

}
