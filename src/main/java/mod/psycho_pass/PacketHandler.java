package mod.psycho_pass;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

	// このMOD用のSimpleNetworkWrapperを生成。チャンネルの文字列は固有であれば何でも良い。MODIDの利用を推奨。
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("psycho_pass");

	public static void init() {
		INSTANCE.registerMessage(MessageKeyPressedHandler.class, MessageKeyPressed.class, 1, Side.SERVER);
	}

}
