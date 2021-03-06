package ch.judos.mcmod.customrender;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ch.modjam.generic.blocks.CustomRenderer;
import ch.modjam.generic.blocks.CustomRenderer.Side;

@SuppressWarnings("javadoc")
public class TECarvedDirtRenderer extends TileEntitySpecialRenderer {

	private static CustomRenderer	renderer;
	public static double			CARVE_DEPTH	= 1. / 3;

	public TECarvedDirtRenderer() {
		renderer = new CustomRenderer(TECarvedDirt.getTexture());
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX, double transY, double transZ,
			float f) {
		if (!(ent instanceof TECarvedDirt))
			return;
		TECarvedDirt t = (TECarvedDirt) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		renderBlock(t);
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	public static void renderBlock(IConnecting con) {
		RenderHelper.disableStandardItemLighting();

		HashSet<ForgeDirection> con2 = getConnections(con);

		boolean[][] arr = arrFromConnections(con2);

		render(con2, arr);
		// CLEANUP
		RenderHelper.enableStandardItemLighting();

	}

	private static HashSet<ForgeDirection> getConnections(IConnecting con) {
		HashSet<ForgeDirection> connections = new HashSet<ForgeDirection>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if (dir.offsetY == 0 && con.connectsTo(dir))
				connections.add(dir);
		return connections;
	}

	private static boolean[][] arrFromConnections(HashSet<ForgeDirection> con2) {
		boolean[][] r = new boolean[3][3];
		r[1][1] = true;
		if (con2.contains(NORTH))
			r[1][2] = true;
		if (con2.contains(EAST))
			r[0][1] = true;
		if (con2.contains(SOUTH))
			r[1][0] = true;
		if (con2.contains(WEST))
			r[2][1] = true;
		return r;
	}

	private static void render(HashSet<ForgeDirection> con2, boolean[][] arr) {
		renderer.begin();
		renderTop(con2, arr);
		renderer.end();

		// separately starts and ends the renderer
		renderSidesAndInsides(con2);
	}

	private static void renderSidesAndInsides(HashSet<ForgeDirection> con) {
		double t = 1. / 3;
		ForgeDirection[] dir = { NORTH, WEST, SOUTH, EAST };
		for (ForgeDirection d : dir) {
			renderer.begin();
			if (!con.contains(d)) {
				renderer.quad(-0.5, -0.5, -0.5, 1, 1, Side.FRONT);
				// inside dirt
				renderer.quad(-0.5 + t, +0.5 - t, -0.5 + t, t, t, Side.BACK, 13);
			} else {
				// side dirt
				renderer.quad(-0.5 + t, +0.5 - t, -0.5, t, t, Side.LEFT, 13);
				renderer.quad(0.5 - t, +0.5 - t, -0.5, t, t, Side.RIGHT, 13);
				// outside texture
				renderer.quad(-0.5, -0.5, -0.5, 1, 1 - t, Side.FRONT, 8);
				renderer.quad(-0.5, 0.5 - t, -0.5, t, t, Side.FRONT, 8);
				renderer.quad(0.5 - t, 0.5 - t, -0.5, t, t, Side.FRONT, 8);
			}
			renderer.end();
			GL11.glRotated(90, 0, 1, 0);
		}

		renderer.quad(-0.5, -0.5, -0.5, 1, 1, Side.BOTTOM);
	}

	/**
	 * texture tileNr for arbitrary number of connections
	 */
	static final HashMap<HashSet<ForgeDirection>, Integer>	connectionsToTextureTile	= new HashMap<HashSet<ForgeDirection>, Integer>();
	static final HashMap<HashSet<ForgeDirection>, Integer>	connectionToRotation		= new HashMap<HashSet<ForgeDirection>, Integer>();

	static {
		initialize();
	}

	private static void initialize() {
		connectionToRotation.clear();
		addConnectionToRotationAndTile(12, 0);
		addConnectionToRotationAndTile(3, 0, SOUTH);
		addConnectionToRotationAndTile(3, 1, WEST);
		addConnectionToRotationAndTile(3, 2, NORTH);
		addConnectionToRotationAndTile(3, 3, EAST);

		addConnectionToRotationAndTile(15, 0, WEST, SOUTH);
		addConnectionToRotationAndTile(15, 1, WEST, NORTH);
		addConnectionToRotationAndTile(15, 2, EAST, NORTH);
		addConnectionToRotationAndTile(15, 3, EAST, SOUTH);
		addConnectionToRotationAndTile(2, 0, NORTH, SOUTH);
		addConnectionToRotationAndTile(2, 1, EAST, WEST);

		addConnectionToRotationAndTile(7, 0, WEST, NORTH, SOUTH);
		addConnectionToRotationAndTile(7, 3, EAST, WEST, SOUTH);
		addConnectionToRotationAndTile(7, 2, EAST, NORTH, SOUTH);
		addConnectionToRotationAndTile(7, 1, EAST, WEST, NORTH);

		addConnectionToRotationAndTile(11, 0, EAST, WEST, NORTH, SOUTH);
	}

	private static void addConnectionToRotationAndTile(int tile, int rotation,
			ForgeDirection... dir) {
		HashSet<ForgeDirection> set = new HashSet<ForgeDirection>();
		for (ForgeDirection d : dir)
			set.add(d);
		connectionToRotation.put(set, Integer.valueOf(rotation));
		connectionsToTextureTile.put(set, Integer.valueOf(tile));
	}

	private static void renderTop(HashSet<ForgeDirection> con2, boolean[][] arr) {
		int tileNr = 0;
		if (connectionsToTextureTile.containsKey(con2))
			tileNr = connectionsToTextureTile.get(con2).intValue();
		int rotation = 0;
		if (connectionToRotation.containsKey(con2))
			rotation = connectionToRotation.get(con2).intValue();

		double t = 1. / 3;
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				double y = 0.5;
				if (arr[x][z])
					y = 0.5 - CARVE_DEPTH;
				renderer.quad(+0.5 - t - x * t, y, +0.5 - t - z * t, t, t, Side.TOP, tileNr,
					rotation);
			}
		}
	}

}
