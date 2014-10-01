package ch.modjam.generic;

import net.minecraft.util.Vec3;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import ch.modjam.generic.blocks.Vec3C;

public class Test {

	public static void main(String[] args) {

		Vec3C startVec = new Vec3C(0, 0, 0);
		Vec3C endVec = new Vec3C(5, 4, 1);

		Vec3 relPos = endVec.subtract(startVec);
		float angleYaw = (float) ((Math.atan2(relPos.xCoord, relPos.zCoord) + Math.PI) % (Math.PI * 2));
		float anglePitch = (float) (Math.atan2(relPos.yCoord, Math.hypot(relPos.xCoord,
			relPos.zCoord)));

		System.out.println("yaw: " + (angleYaw / Math.PI * 180) + "°  (" + angleYaw + ")");
		System.out.println("pitch: " + (anglePitch / Math.PI * 180));

		Matrix4f transform = new Matrix4f();
		transform.rotate(angleYaw, new Vector3f(0, 1, 0)); // yaw
		transform.rotate(anglePitch, new Vector3f(1, 0, 0)); // pitch

		// Matrix4f pitch = new Matrix4f();
		// pitch.rotate(anglePitch, new Vector3f(1, 0, 0));

		float r = 2;
		Vector4f[] edges = new Vector4f[] { new Vector4f(r, r, 0, 1), new Vector4f(-r, r, 0, 1), new Vector4f(
			-r, -r, 0, 1), new Vector4f(r, -r, 0, 1) };

		for (Vector4f v : edges)
			Matrix4f.transform(transform, v, v);

		for (Vector4f v : edges)
			System.out.println(v);
	}
}
