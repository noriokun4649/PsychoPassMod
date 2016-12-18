package mod.psycho_pass;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy {

	// キーのUnlocalizedName、バインドするキーの対応整数値（Keyboardクラス参照のこと）、カテゴリー名
	@SideOnly(Side.CLIENT)
	public static final KeyBinding modeKey = new KeyBinding("key.modeKey.name", Keyboard.KEY_F,
			"title.psycho_passkey.name");
	public static final KeyBinding modeKey2 = new KeyBinding("key.modeKey2.name", Keyboard.KEY_C,
			"title.psycho_passkey.name");
	public static final KeyBinding modeKey3 = new KeyBinding("key.modeKey3.name", Keyboard.KEY_G,
			"title.psycho_passkey.name");


	public void onRight(){

	}
}
