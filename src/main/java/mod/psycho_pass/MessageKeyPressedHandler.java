package mod.psycho_pass;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class MessageKeyPressedHandler implements IMessageHandler<MessageKeyPressed, IMessage> {

	@Override
	public IMessage onMessage(MessageKeyPressed message, MessageContext ctx) {
		EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
		// 受け取ったMessageクラスのkey変数の数字をチャットに出力

		entityPlayer.addChatComponentMessage(new ChatComponentText(String.format("Received byte %d", message.key)));
		return null;
	}
}