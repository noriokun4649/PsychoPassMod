package mod.psycho_pass;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid = Psycho_pass_core.modid, name = Psycho_pass_core.name, version = Psycho_pass_core.version)
public class Psycho_pass_core {
	@SidedProxy(clientSide = "mod.psycho_pass.ClientProxy", serverSide = "mod.psycho_pass.CommonProxy")
	public static CommonProxy proxy;
	@Mod.Instance("PSYCHO_PASS")
	public static Psycho_pass_core INSTANCE;
	public static Logger logger = LogManager.getLogger("psycho_pass");
	public static CreativeTabs psycho_pass = new CreativeTabs("psycho_pass") {
		public Item getTabIconItem() {
			return Psycho_pass_core.pulsegunBullet;
		}
	};
	public static final String modid = "PSYCHO_PASS";
	public static final String name = "PSYCHO_PASS";
	public static final String version = "2.0.6β";

	public static boolean modeinfo;
	public static double seting;
	public static float seting2;
	public static boolean usercheck;
	public static double distance;
	public static boolean log;
	public static Item pulsegun;
	public static Item pulsegunBullet;
	public static Block sibylsystem;
	public static Item MobileTerminal;
	public static Item MobileTerminal1;
	public static Item MobileTerminal2;
	public static Item MobileTerminal3;
	public static Item MobileTerminal4;
	public static Item MobileTerminal5;
	public static Item MobileTerminal6;
	public static Item MobileTerminal7;
	public static Item MobileTerminal8;
	public static Item MobileTerminal9;
	public static Item MobileTerminal10;
	public static Item MobileTerminal11;
	public static Item MobileTerminal12;
	public static Item MobileTerminal13;
	public static EntityPlayer par3EntityPlayer;
	public static Item truncheon;
	public static ToolMaterial truncheon_tool;
	public static int modes = 1;
	public static int games = 1;
	public static int user = 1;
	public static int r = 1;
	public static int texture;
	public static boolean chatmode = true;
	public static String error = "";
    public static int RenderID;

	@EventHandler
	public void load(FMLInitializationEvent event) {
		// ドミネーターレシピ
		GameRegistry.addRecipe(new ItemStack(pulsegun, 1, 1), "AAA", "AAB", "  B", 'A', Blocks.obsidian, 'B',
				Items.stick);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new WorldInEventHandler());
		if (event.getSide() == Side.CLIENT) {
			ClientRegistry.registerKeyBinding(ClientProxy.modeKey);
			ClientRegistry.registerKeyBinding(ClientProxy.modeKey2);
			ClientRegistry.registerKeyBinding(ClientProxy.modeKey3);
			FMLCommonHandler.instance().bus().register(this);

			this.RenderID = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new SibylSystemBlock_Model());
		}
		// シビュラレシピ
		GameRegistry.addRecipe(new ItemStack(sibylsystem),"XXX","XYX","AAA",'A',new ItemStack(Blocks.quartz_block),'X',new ItemStack(Blocks.glass),'Y',new ItemStack(pulsegunBullet));
		GameRegistry.addRecipe(new ItemStack(pulsegunBullet), "A A", "A A", "A A", 'A', Items.diamond);
		GameRegistry.addRecipe(new ItemStack(MobileTerminal2), "AAA", "ABA", "AAA", 'A', new ItemStack(Items.dye,1,4),'B',pulsegunBullet);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal3),new ItemStack(Items.dye,1,4),MobileTerminal2);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal1), new ItemStack(Items.dye,1,4),MobileTerminal3);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal), new ItemStack(Items.dye,1,4),MobileTerminal1);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal4),new ItemStack(Items.dye,1,4),MobileTerminal3);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal5),new ItemStack(Items.dye,1,4),MobileTerminal4);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal6),new ItemStack(Items.dye,1,4),MobileTerminal5);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal7),new ItemStack(Items.dye,1,4),MobileTerminal6);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal8),new ItemStack(Items.dye,1,4),MobileTerminal7);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal9),new ItemStack(Items.dye,1,4),MobileTerminal8);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal10),new ItemStack(Items.dye,1,4),MobileTerminal9);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal11),new ItemStack(Items.dye,1,4),MobileTerminal10);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal12),new ItemStack(Items.dye,1,4),MobileTerminal11);
		GameRegistry.addShapelessRecipe(new ItemStack(MobileTerminal13),new ItemStack(Items.dye,1,4),MobileTerminal12);
	}
	public static DamageSource causeTestDecoDamage(DecoEntityBullet PlasticEntityBullet, Entity par1Entity) {
		return (new EntityDamageSourceIndirect("DecoEntityBullet", PlasticEntityBullet, par1Entity)).setProjectile();
	}
	public static DamageSource causeTestParaDamage(ParaEntityBullet PlasticEntityBullet, Entity par1Entity) {
		return (new EntityDamageSourceIndirect("ParaEntityBullet", PlasticEntityBullet, par1Entity)).setProjectile();
	}
	public static DamageSource causeTestEriDamage(EriEntityBullet PlasticEntityBullet, Entity par1Entity) {

		return (new EntityDamageSourceIndirect("EriEntityBullet", PlasticEntityBullet, par1Entity)).setProjectile();
	}
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemArmor.ArmorMaterial MobileTerminalMaterial = EnumHelper.addArmorMaterial("MobileTerminal", 0, new int[]{0,0,0,0}, 0);
		truncheon_tool = EnumHelper.addToolMaterial("truncheon_tool",0, 64,0, -4F,30).setRepairItem(new ItemStack(Blocks.obsidian,2));
		PacketHandler.init();
		Configuration config = new Configuration(new File("config/PSYCHO_PASS.cfg"));
		config.load();
		seting = config.get(Configuration.CATEGORY_GENERAL, "Seting interval", 60, "ドミネーターのインターバルを設定").getDouble();
		seting2 = config.get(Configuration.CATEGORY_GENERAL, "Seting Launch sound", 1.0F, "ドミネーターの発射音の音量を設定").getMaxListLength();
		usercheck = config.get("MultiPlay", "User Check",true , "ドミネーターの所有者を初期起動時に登録するかどうか").getBoolean();
		distance = config.get("Developer", "Distance", 20,"シビュラシステムブロックが有効な距離").getDouble();
		log = config.get("Developer", "System Log", false,"ログを表示させるか").getBoolean();
		config.save();
		sibylsystem = new SibylSystemBlock().setBlockTextureName("glass")
				;
		GameRegistry.registerBlock(sibylsystem,"sibylsystem");
		pulsegun = new Pulsegun(1, 20.0F, 200.0D, (int) seting, (int) seting, "psycho_pass:gun1", false, false)
				.setUnlocalizedName("pulsegun").setCreativeTab(psycho_pass);
		GameRegistry.registerItem(pulsegun, "pulsegun");
		GameRegistry.addRecipe(new ItemStack(truncheon), " A ", " A ", " A ", 'A',new ItemStack(Blocks.obsidian));
		truncheon = new Truncheon(truncheon_tool);
		GameRegistry.registerItem(truncheon,"truncheon");
		//シビュラ
		pulsegunBullet = new Item().setUnlocalizedName("pulsegunBullet").setCreativeTab(psycho_pass)
				.setTextureName("psycho_pass:pulsegunbullet").setMaxStackSize(64);
		GameRegistry.registerItem(pulsegunBullet, "pulsegunBullet");
		//公安局ID  常守朱
		MobileTerminal = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal").setCreativeTab(psycho_pass).setMaxStackSize(1);
		GameRegistry.registerItem(MobileTerminal, "MobileTerminal");
		//公安局ID
		MobileTerminal1 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal1").setCreativeTab(psycho_pass).setMaxStackSize(1);
		GameRegistry.registerItem(MobileTerminal1, "MobileTerminal1");
		//公安局ID
		MobileTerminal2 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal2").setCreativeTab(psycho_pass).setMaxStackSize(1);
		GameRegistry.registerItem(MobileTerminal2, "MobileTerminal2");
		//公安局ID
		MobileTerminal3 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal3").setCreativeTab(psycho_pass).setMaxStackSize(1);
		GameRegistry.registerItem(MobileTerminal3, "MobileTerminal3");
		//宜野座伸元  監視官
		MobileTerminal4 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal4").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//宜野座伸元  執行官
		MobileTerminal5 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal5").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//征陸智己  執行官
		MobileTerminal6 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal6").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//縢  秀星  執行官
		MobileTerminal7 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal7").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//六合塚弥生  執行官
		MobileTerminal8 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal8").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//佐々山光留  執行官
		MobileTerminal9 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal9").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//霜月美香  監視官
		MobileTerminal10 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal10").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//須郷徹平  執行官
		MobileTerminal11 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal11").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//須郷徹平  執行官
		MobileTerminal12 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal12").setCreativeTab(psycho_pass).setMaxStackSize(1);
		//東金朔夜  執行官
		MobileTerminal13 = new IdArmor(MobileTerminalMaterial, 0, 1).setTextureName("psycho_pass:mobileterminal").setUnlocalizedName("MobileTerminal13").setCreativeTab(psycho_pass).setMaxStackSize(1);
		GameRegistry.registerItem(MobileTerminal4,"MobileTerminal4");
		GameRegistry.registerItem(MobileTerminal5,"MobileTerminal5");
		GameRegistry.registerItem(MobileTerminal6,"MobileTerminal6");
		GameRegistry.registerItem(MobileTerminal7,"MobileTerminal7");
		GameRegistry.registerItem(MobileTerminal8,"MobileTerminal8");
		GameRegistry.registerItem(MobileTerminal9,"MobileTerminal9");
		GameRegistry.registerItem(MobileTerminal10,"MobileTerminal10");
		GameRegistry.registerItem(MobileTerminal11,"MobileTerminal11");
		GameRegistry.registerItem(MobileTerminal12,"MobileTerminal12");
		GameRegistry.registerItem(MobileTerminal13,"MobileTerminal13");
		}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new GunUI_Handler(new GunUI(Minecraft.getMinecraft())));
		}
	}

	@SubscribeEvent
	public void KeyHandlingEvent(KeyInputEvent event) {
		/*
		 * inputKeyが押されるとisPressed()がtrueになる.
		 */
		Entity entity = Minecraft.getMinecraft().renderViewEntity;
		EntityPlayer entityplayer = (EntityPlayer) entity;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getCurrentEquippedItem();
		/*
		 * if (modeKey3.isPressed()) { if (user == 1) { EntityPlayer entity =
		 * Minecraft.getMinecraft().thePlayer; Item par1Item =
		 * entity.inventory.getCurrentItem().getItem(); } } if
		 * (ClientProxy.modeKey3.isPressed()) {
		 * PacketHandler.INSTANCE.sendToServer(new
		 * MessageKeyPressed(1));//1をサーバーに送る。 }
		 */
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void inputKey(InputEvent.KeyInputEvent event) {
		error = "";
		if (ClientProxy.modeKey3.isPressed()) {
			if (chatmode) {
				chatmode = false;
			} else if (chatmode == false) {
				chatmode = true;
			}
		}
		if (games == 0) {
			if (ClientProxy.modeKey.isPressed()) {
				switch (modes){
				case 0:
					chat(StatCollector.translateToLocal("title.mode.name")
							+ StatCollector.translateToLocal("title.mode0.name"));
					r = 1;// パラライザー
					modes = 1;// 次はエリミネーターへ
					break;
				case 1:
					chat(StatCollector.translateToLocal("title.mode.name")
							+ StatCollector.translateToLocal("title.mode1.name"));
					r = 2;// エリミネーター
					modes = 2;// 次はデコンポーザーへ
					break;
				case 2:
					chat(StatCollector.translateToLocal("title.mode.name")
							+ StatCollector.translateToLocal("title.mode2.name"));
					r = 3;// デコンポーザー
					modes = 3; // 次はパラライザーへ
					break;
				case 3:
					chat(StatCollector.translateToLocal("title.mode.name")
							+ StatCollector.translateToLocal("title.mode3.name"));
					r = 4;//常時解除
					modes = 0; // 次はパラライザーへ
					break;
				}
			}
		}
		if (games == 1) {
			if (ClientProxy.modeKey.isPressed()) {
				chat(StatCollector.translateToLocal("title.modechangeerrer.name"));
				error = StatCollector.translateToLocal("title.modechangeerrer.name");

			}
		}
		if (ClientProxy.modeKey2.isPressed()) {
			if (games == 0) {
				chat(StatCollector.translateToLocal("title.modechange.name")
						+ StatCollector.translateToLocal("title.automode.name"));
				games = 1;
			} else if (games == 1) {
				chat(StatCollector.translateToLocal("title.modechange.name")
						+ StatCollector.translateToLocal("title.manualmode.name"));
				games = 0;
			}
		}
	}
	public static void output (String str){
		if (log){
			logger.info(str);
		}
	}
	//langファイルから読み込み
	public static String getLocal(String str){
		return StatCollector.translateToLocal(str);
	}
	public void chat(String str) {
		if (chatmode == false) {
			FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentTranslation(str));
		}
	}
	// par2World.playSoundAtEntity(par3EntityPlayer,
	// par3EntityPlayer.playSound(
}