package mod.psycho_pass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class WorldInEventHandler {
	byte count =0;
	/*ワールドに入った時に呼ばれるイベント。PlasticCoreのsubメゾットに引き渡し、バージョンチェック*/
	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
	{
		if ( event.world.isRemote && event.entity  instanceof EntityPlayer && count == 0) {//クライアント処理＆プレイヤー出現認識
			count =1;
			String ver =sub();
			Psycho_pass_core.logger.info(Psycho_pass_core.getLocal("pplogger.newversion")+ver); //ログに最新バージョンを表示
			if ( ver.equals(Psycho_pass_core.version) ){//プレイ中のバージョンと最新バージョンが一致したら
				playerChatMessage(ChatFormatting.YELLOW+StatCollector.translateToLocal("psycho_pass.matchver.name"));//最新バージョンを使用してると表示
			}else if(ver ==null || ver.equals("error")){//ネットワークに接続できなかったら
				playerChatMessage(ChatFormatting.RED+StatCollector.translateToLocal("psycho_pass.error.name"));
			}else if(ver.equals("not")){
				playerChatMessage(ChatFormatting.RED+StatCollector.translateToLocal("psycho_pass.not.name"));
			}else{//一致しなかったら
				playerChatMessage(ChatFormatting.GREEN+StatCollector.translateToLocal("psycho_pass.oldver.name"));
				playerChatMessage(ChatFormatting.GREEN+StatCollector.translateToLocal("psycho_pass.newver.name")+ChatFormatting.RED+ver);//最新バージョンをチャットに表示
				playerChatMessage(ChatFormatting.GREEN+StatCollector.translateToLocal("psycho_pass.nowver.name")+ChatFormatting.RED+Psycho_pass_core.version);//プレイ中のバージョンをチャットに表示
			}
			if (sub2(false).equals("kill"))//MOD配布終了時
			{
				Psycho_pass_core.logger.warn("！MODのサポートは終了したため、強制クラッシュ！");
				throw new RuntimeException(Psycho_pass_core.modid+": This mod is no longer supported.");
			}
			if (!(sub2(false).equals("error"))){    //情報取得ができたら
				if(sub2(false).equals("infonot")){//情報取得できても情報がなかったら
					playerChatMessage(ChatFormatting.DARK_GREEN+StatCollector.translateToLocal("psycho_pass.infonot.name"));//お知らせはないと表示
				}else{     //情報があったら
					playerChatMessage(ChatFormatting.DARK_GREEN+StatCollector.translateToLocal("psycho_pass.info.name"));
					sub2(true);//情報をドロップボックスのテキストファイルから読み込んで表示
				}
			}else{//情報取得できなかったら
				Psycho_pass_core.logger.warn(Psycho_pass_core.getLocal("psycho_pass.infoerror.name"));
				playerChatMessage(ChatFormatting.RED+StatCollector.translateToLocal("psycho_pass.infoerror.name"));//ネットワークエラー
			}
		}
	}
	public static void playerChatMessage (String msg){
		FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentTranslation(msg));
	}
	/**
	 * バージョンチェックメインソース  ログイン時に呼び出されます。
	 */
	public static String sub(){
		String ver ="not";
		try {
			URL url = new URL("https://dl.dropboxusercontent.com/s/qx7cq7irzxfuwfl/psycho_pass_ver.txt");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			InputStreamReader isr = new InputStreamReader(http.getInputStream(), "Shift_JIS");
			BufferedReader br = new BufferedReader(isr);
			String line_buffer;
			while ( null != (line_buffer = br.readLine() ) ) {
				ver =line_buffer;
			}
			br.close();
			isr.close();
			http.disconnect();
		}
		catch( Exception e ) {
			ver ="error";
			Psycho_pass_core.logger.warn(Psycho_pass_core.getLocal("pplogger.versionerror")+e);
		}
		return ver;
	}
	/**
	 * バージョンチェックサブソース  ログイン時に呼び出されます。
	 */
	public static String sub2(boolean falnd){
		String type="infonot";
		try {
			URL url1 = new URL("https://dl.dropboxusercontent.com/s/cjkgxpfyr18jphj/psycho_pass_info.txt");
			HttpURLConnection http1 = (HttpURLConnection)url1.openConnection();
			http1.setRequestMethod("GET");
			http1.connect();
			InputStreamReader isr1 = new InputStreamReader(http1.getInputStream(), "Shift_JIS");
			BufferedReader br1 = new BufferedReader(isr1);
			String line_buffer1;
			while ( null != (line_buffer1 = br1.readLine() ) ) {
				if (falnd){
					WorldInEventHandler.playerChatMessage(ChatFormatting.DARK_GREEN+"[Psycho-PassMOD]"+line_buffer1);
				}
				type = line_buffer1;
			}
			br1.close();
			isr1.close();
			http1.disconnect();
		}
		catch( Exception e ) {
			Psycho_pass_core.logger.warn(Psycho_pass_core.getLocal("pplogger.versionerror")+e);
			type ="error";
		}
		return type;
	}
}
