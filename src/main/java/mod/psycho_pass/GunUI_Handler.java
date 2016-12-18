package mod.psycho_pass;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class GunUI_Handler {
	public GunUI_Handler(mod.psycho_pass.GunUI gun) {
		vampierGui = gun;
	}

	private GunUI vampierGui;

	@SubscribeEvent(receiveCanceled = true)
	public void onEvent(RenderGameOverlayEvent.Pre event) {
		EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
		if (entityPlayerSP == null)
			return; // just in case
		// HUD描画
		vampierGui.renderGameOverlay(0, false, event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
	}
}
