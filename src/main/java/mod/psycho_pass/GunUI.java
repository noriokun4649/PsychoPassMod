package mod.psycho_pass;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiStreamIndicator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GunUI extends GuiIngame {
	protected static ResourceLocation dominatorBlurTexPath = new ResourceLocation(
			"psycho_pass:textures/more/pararaiza_lock1.png");
	protected final Minecraft mc;
	protected final GuiNewChat persistantChatGUI;
	protected final GuiStreamIndicator field_152127_m;

	public GunUI(Minecraft p_i1036_1_) {
		super(p_i1036_1_);
		this.mc = p_i1036_1_;
		this.persistantChatGUI = new GuiNewChat(p_i1036_1_);
		this.field_152127_m = new GuiStreamIndicator(this.mc);
	}

	// 現在の状態を取得
	public String gameCatch() {
		String games = StatCollector.translateToLocal("title.null.name");
		switch (Psycho_pass_core.games){
		case 0:
			games = StatCollector.translateToLocal("title.manualmode.name");
			break;
		case 1:
			games = StatCollector.translateToLocal("title.automode.name");
			break;

		}
		return games;
	}

	// 現在の状態を取得
	public String modeCatch() {
		String modes = StatCollector.translateToLocal("title.null.name");
		if (Psycho_pass_core.games == 1) {
			modes = StatCollector.translateToLocal("title.modeerror.name");
		} else {
			switch (Psycho_pass_core.r){
			case 1:
				modes = StatCollector.translateToLocal("title.mode0.name");
				break;
			case 2:
				modes = StatCollector.translateToLocal("title.mode1.name");
				break;
			case 3:
				modes = StatCollector.translateToLocal("title.mode2.name");
				break;
			case 4:
				modes = StatCollector.translateToLocal("title.mode3.name");
				break;
			default : Psycho_pass_core.error = StatCollector.translateToLocal("title.modegeterror.name");
			}
		}
		return modes;
	}


	@Override
	public void renderGameOverlay(float p_73830_1_, boolean p_73830_2_, int p_73830_3_, int p_73830_4_) {
		ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int k = scaledresolution.getScaledWidth();
		int l = scaledresolution.getScaledHeight();
		FontRenderer fontrenderer = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);
		ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
		// アイテムスタックにデータが入っててかつ、選択中のアイテムがドミネーターでさらにNBTタグから初期起動後かどうかしらべて初期起動後なら表示
		if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null
				&& itemstack.getItem() == Psycho_pass_core.pulsegun
				&& itemstack.stackTagCompound.getByte("fast") == 2) {
			this.renderDominatorBlur(k, l);// 表示
			int centerHeight =l/2;
			fontrenderer.drawStringWithShadow(StatCollector.translateToLocal("title.modenow.name") + gameCatch(), 2,centerHeight-30,16777215);// 取得したのを描写
			fontrenderer.drawStringWithShadow(StatCollector.translateToLocal("title.modenow1.name") + modeCatch(), 2,centerHeight+10, 16777215);// 取得したのを描写
			fontrenderer.drawStringWithShadow(Psycho_pass_core.error, 2, centerHeight+20, 16711935);// エラーがあれば表示
			boolean CreativeMode = this.mc.thePlayer.capabilities.isCreativeMode;
			if (CreativeMode){
				fontrenderer.drawStringWithShadow(StatCollector.translateToLocal("title.notCreativeMode.name"), 2, centerHeight+30, 16711935);
			}
			if (itemstack.stackTagCompound.getByte("tagmode") == 4){
				dominatorBlurTexPath = new ResourceLocation("psycho_pass:textures/more/pararaiza_unlock1.png");
			}else {
				if (itemstack.getItemDamage() == 0) {// ドミネーターが使用できる状態の場合
					byte tagmode = itemstack.getTagCompound().getByte("tagmode");
					switch (tagmode){
					case 0 : dominatorBlurTexPath = new ResourceLocation("psycho_pass:textures/more/pararaiza_unlock1.png");
						break;
					case 1 : dominatorBlurTexPath = new ResourceLocation("psycho_pass:textures/more/elmineta_unlock1.png");
						break;
					case 2 : dominatorBlurTexPath = new ResourceLocation("psycho_pass:textures/more/decompoiser_unlock1.png");
						break;
					}
				} else {// ドミネーターが使用できない場合
					dominatorBlurTexPath = new ResourceLocation("psycho_pass:textures/more/pararaiza_lock1.png");
				}
				if (itemstack.stackTagCompound.getByte("tagmode") == 3) {
					dominatorBlurTexPath = new ResourceLocation("psycho_pass:textures/more/pararaiza_lock1.png");
				}
						// fontrenderer.drawStringWithShadow("Communication
						// error...", 150, 150, 0xFF0000);
						// fontrenderer.drawStringWithShadow("Cannot establish a
						// link with the system.", 150, 160, 0xFF0000);
			}
			int i1;
			int j1;
			int k1;
			if (!this.mc.playerController.enableEverythingIsScrewedUpMode()) {
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.getTextureManager().bindTexture(widgetsTexPath);
				InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
				this.zLevel = -90.0F;
				this.drawTexturedModalRect(k / 2 - 91, l - 22, 0, 0, 182, 22);
				this.drawTexturedModalRect(k / 2 - 91 - 1 + inventoryplayer.currentItem * 20, l - 22 - 1, 0, 22, 24,
						22);
				this.mc.getTextureManager().bindTexture(icons);
				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(775, 769, 1, 0);
				this.drawTexturedModalRect(k / 2 - 7, l / 2 - 7, 0, 0, 16, 16);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				this.mc.mcProfiler.startSection("bossHealth");
				this.renderBossHealth();
				this.mc.mcProfiler.endSection();
				if (this.mc.playerController.shouldDrawHUD()) {
					this.func_110327_a(k, l);
				}
				this.mc.mcProfiler.startSection("actionBar");
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				RenderHelper.enableGUIStandardItemLighting();
				for (i1 = 0; i1 < 9; ++i1) {
					j1 = k / 2 - 90 + i1 * 20 + 2;
					k1 = l - 16 - 3;
					this.renderInventorySlot(i1, j1, k1, p_73830_1_);
				}
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				this.mc.mcProfiler.endSection();
				GL11.glDisable(GL11.GL_BLEND);
			}
			int l4;
			if (this.mc.thePlayer.getSleepTimer() > 0) {
				this.mc.mcProfiler.startSection("sleep");
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				l4 = this.mc.thePlayer.getSleepTimer();
				float f2 = (float) l4 / 100.0F;
				if (f2 > 1.0F) {
					f2 = 1.0F - (float) (l4 - 100) / 10.0F;
				}
				j1 = (int) (220.0F * f2) << 24 | 1052704;
				drawRect(0, 0, k, l, j1);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				this.mc.mcProfiler.endSection();
			}
			l4 = 16777215;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			i1 = k / 2 - 91;
			int l1;
			int i2;
			int j2;
			int k2;
			float f3;
			short short1;
			if (this.mc.thePlayer.isRidingHorse()) {
				this.mc.mcProfiler.startSection("jumpBar");
				this.mc.getTextureManager().bindTexture(Gui.icons);
				f3 = this.mc.thePlayer.getHorseJumpPower();
				short1 = 182;
				l1 = (int) (f3 * (float) (short1 + 1));
				i2 = l - 32 + 3;
				this.drawTexturedModalRect(i1, i2, 0, 84, short1, 5);
				if (l1 > 0) {
					this.drawTexturedModalRect(i1, i2, 0, 89, l1, 5);
				}
				this.mc.mcProfiler.endSection();
			} else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
				this.mc.mcProfiler.startSection("expBar");
				this.mc.getTextureManager().bindTexture(Gui.icons);
				j1 = this.mc.thePlayer.xpBarCap();
				if (j1 > 0) {
					short1 = 182;
					l1 = (int) (this.mc.thePlayer.experience * (float) (short1 + 1));
					i2 = l - 32 + 3;
					this.drawTexturedModalRect(i1, i2, 0, 64, short1, 5);
					if (l1 > 0) {
						this.drawTexturedModalRect(i1, i2, 0, 69, l1, 5);
					}
				}
				this.mc.mcProfiler.endSection();
				if (this.mc.thePlayer.experienceLevel > 0) {
					this.mc.mcProfiler.startSection("expLevel");
					boolean flag2 = false;
					l1 = flag2 ? 16777215 : 8453920;
					String s3 = "" + this.mc.thePlayer.experienceLevel;
					j2 = (k - fontrenderer.getStringWidth(s3)) / 2;
					k2 = l - 31 - 4;
					boolean flag1 = false;
					fontrenderer.drawString(s3, j2 + 1, k2, 0);
					fontrenderer.drawString(s3, j2 - 1, k2, 0);
					fontrenderer.drawString(s3, j2, k2 + 1, 0);
					fontrenderer.drawString(s3, j2, k2 - 1, 0);
					fontrenderer.drawString(s3, j2, k2, l1);
					this.mc.mcProfiler.endSection();
				}
			}
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	protected void renderDominatorBlur(int par1, int par2) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		this.mc.getTextureManager().bindTexture(dominatorBlurTexPath);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, (double) par2, -90.0D, 0.0D, 1.0D);
		tessellator.addVertexWithUV((double) par1, (double) par2, -90.0D, 1.0D, 1.0D);
		tessellator.addVertexWithUV((double) par1, 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}