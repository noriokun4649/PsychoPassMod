package mod.psycho_pass;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageKeyPressed implements IMessage {

	public int key;

	public MessageKeyPressed() {
	}

	public MessageKeyPressed(int i) {
		this.key = i;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.key = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.key);
	}
}