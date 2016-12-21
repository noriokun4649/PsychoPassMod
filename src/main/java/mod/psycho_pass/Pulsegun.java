package mod.psycho_pass;

import java.io.*;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import static mod.psycho_pass.Psycho_pass_core.output;

public class Pulsegun extends Item {
    int r = new java.util.Random().nextInt(2);
    private int maxdamage;
    private float range;
    private double attack;
    private int wait_attack;
    private int wait_reload;
    private String gunsound;
    private Boolean knockback;
    private Boolean fire;
    private int mode = 1;
    private int b = 1;

    // 装填数，射程距離(弓の最大1.0F)，攻撃力補正，攻撃後のWAIT，リロード後のWAIT，効果音，ノックバックの有無，炎上効果の有無
    public Pulsegun(int par1, float par2, double par3, int par4, int par5, String par6, Boolean par7, Boolean par8) {
        this.maxdamage = par1;
        this.range = par2;
        this.attack = par3 * 10;
        this.wait_attack = par4;
        this.wait_reload = par5;
        this.gunsound = par6;
        this.knockback = par7;
        this.fire = par8;
        this.maxStackSize = 1;
        this.setMaxDamage(this.maxdamage);
    }

    private boolean o = true;
    Timer timer = new Timer();

    // 右クリック
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack != null) {//アイテムスタックがからじゃなかったら
            if (par1ItemStack.getTagCompound() == null) {//NBTがNullだったら
                par1ItemStack.setTagCompound(new NBTTagCompound());//新しい空のNBTを書き込む
            }
        }
        String parent = Psycho_pass_core.config.getConfigFile().getParent();
        File file = new File(parent + "\\PSYCHO_PASS.data");
        try {
            par1ItemStack.getTagCompound().setBoolean("SibylSystem", false);
            //int size = SibylSystemBlock.array.size();
            //BlockPos pos =new BlockPos(SibylSystemBlock.array.get(size-1));
            //Psycho_pass_core.output(par2World.getBlockState(pos).getBlock());

            FileReader fl = new FileReader(file);
            BufferedReader bf = new BufferedReader(fl);
            String text = bf.readLine();
            while (text != null) {
                String[] str1Ary = text.split(",");

                int posx = Integer.parseInt(str1Ary[0]);
                int posy = Integer.parseInt(str1Ary[1]);
                int posz = Integer.parseInt(str1Ary[2]);

                if (par2World.getBlock(posx, posy, posz) == Psycho_pass_core.sibylsystem) {//動的配列よりブロックを取得 シビュラだったら
                    //距離をConfigより取得
                    double distance = Psycho_pass_core.distance;
                    //プレイヤーの座標
                    int nowx = (int) par3EntityPlayer.posX;
                    int nowy = (int) par3EntityPlayer.posY;
                    int nowz = (int) par3EntityPlayer.posZ;
                    //プレイヤーとブロックの距離算出
                    int x = posx - nowx;
                    int y = posy - nowy;
                    int z = posz - nowz;
                    //デバッグ用出力----ログ----
                    /*
                    output("posx:"+posx);
                    output("posy:"+posy);
                    output("posz:"+posz);
                    output("nowx:"+nowx);
                    output("nowy:"+nowy);
                    output("nowz:"+nowz);
                    output("x:"+Math.abs(x));
                    output("y:"+Math.abs(y));
                    output("z:"+Math.abs(z));
                    */
                    //-----ログ終了-----
                    //変数から検索範囲を取得して、絶対値に変換したプレイヤーとブロックの距離を比較。
                    if ((Math.abs(x) <= distance) && (Math.abs(y) <= distance) && (Math.abs(z) <= distance)) {    //距離内だったら・・・true
                        output("true");
                        par1ItemStack.getTagCompound().setBoolean("SibylSystem", true);
                        break;
                    } else {                                                                        //距離外だったら・・・false
                        output("false");
                        par1ItemStack.getTagCompound().setBoolean("SibylSystem", false);
                    }
                } else {                                                                            //シビュラじゃないとき・・・false
                    output("false");
                    par1ItemStack.getTagCompound().setBoolean("SibylSystem", false);
                }
                text = bf.readLine();

            }
            bf.close();
            /*
            for (String posatai : SibylSystemBlock.array){//拡張forで配列のすべてのデータにアクセス

				String[] str1Ary = posatai.split(",");

				int posx = Integer.parseInt(str1Ary[0]);
				int posy = Integer.parseInt(str1Ary[1]);
				int posz = Integer.parseInt(str1Ary[2]);

				if (par2World.getBlock(posx,posy,posz) == Psycho_pass_core.sibylsystem){//動的配列よりブロックを取得 シビュラだったら
					//距離をConfigより取得
					double distance =Psycho_pass_core.distance;
					//プレイヤーの座標
					int nowx = (int)par3EntityPlayer.posX;
					int nowy = (int)par3EntityPlayer.posY;
					int nowz = (int)par3EntityPlayer.posZ;
					//プレイヤーとブロックの距離算出
					int x = posx-nowx;
					int y = posy-nowy;
					int z = posz-nowz;
					//デバッグ用出力----ログ----
					Psycho_pass_core.output("posx:"+posx);
					Psycho_pass_core.output("posy:"+posy);
					Psycho_pass_core.output("posz:"+posz);
					Psycho_pass_core.output("nowx:"+nowx);
					Psycho_pass_core.output("nowy:"+nowy);
					Psycho_pass_core.output("nowz:"+nowz);
					Psycho_pass_core.output("x:"+Math.abs(x));
					Psycho_pass_core.output("y:"+Math.abs(y));
					Psycho_pass_core.output("z:"+Math.abs(z));
					//-----ログ終了-----
					//変数から検索範囲を取得して、絶対値に変換したプレイヤーとブロックの距離を比較。
					if ((Math.abs(x) <= distance)&&(Math.abs(y) <= distance)&&(Math.abs(z) <= distance)){	//距離内だったら・・・true
						Psycho_pass_core.output("true");
						par1ItemStack.getTagCompound().setBoolean("SibylSystem", true);
						break;
					}else{																		//距離外だったら・・・false
						Psycho_pass_core.output("false");
						par1ItemStack.getTagCompound().setBoolean("SibylSystem", false);
					}
				}else{																			//シビュラじゃないとき・・・false
					Psycho_pass_core.output("false");
					par1ItemStack.getTagCompound().setBoolean("SibylSystem", false);
				}
			}
			*/
            output("----------------------------------");
        } catch (Exception ex) {//例外！
            output(ex+"");
            //ex.printStackTrace();
            par1ItemStack.getTagCompound().setBoolean("SibylSystem", false);
            if (!file.exists()) {
                output("データが見つかりませんでした");
            }else {
                output("データの読み込み中にエラーが発生しました。");
            }
        }


        if (!par2World.isRemote) {// SERVER側か判定
            if (par1ItemStack != null) {// アイテムスタックが空じゃない場合
                if (par1ItemStack.getTagCompound() == null) {// NBTがNullの場合
                    par1ItemStack.setTagCompound(new NBTTagCompound());// 新しく空のNBTを作成
                }
                int fast = 0;
                fast = par1ItemStack.getTagCompound().getByte("fast") + 1;
                if (fast == 1) { // 初期起動か判定
                    par1ItemStack.getTagCompound().setByte("fast", (byte) 2);
                    par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:boot", 5.0F, 1.0F);
                    String playerUUID = par3EntityPlayer.getUniqueID().toString(); // 現在のUUIDを取得
                    String playerName = par3EntityPlayer.getDisplayName(); // 現在のNameを取得
                    par1ItemStack.getTagCompound().setString("playername", playerName); // 初期起動時にNameをNBTTAGに挿入
                    par1ItemStack.getTagCompound().setString("UUID", playerUUID); // 初期起動時にUUIDをNBTTAGに挿入
                    par1ItemStack.getTagCompound().setBoolean("ofline", true);
                } else {
                    if (!par3EntityPlayer.capabilities.isCreativeMode) {// サバイバル時のみ使用可
                        Item getitem = null;
                        if (par3EntityPlayer.inventory.armorItemInSlot(2) == null)// Armorslotのチェストが空の場合
                        {
                            getitem = Psycho_pass_core.pulsegun;// 適当なアイテムを挿入ｗ
                        } else {
                            getitem = par3EntityPlayer.inventory.armorItemInSlot(2).getItem();// 空じゃない場合
                            // 入ってるアイテムを挿入
                        }
                        StringBuilder itemname = new StringBuilder();
                        itemname.append(getitem.getUnlocalizedName());
                        itemname.delete(0, 5);
                        int nagasa = itemname.length();
                        if (nagasa == 15) {
                            itemname.delete(nagasa - 1, nagasa);
                        } else if (nagasa == 16) {
                            itemname.delete(nagasa - 2, nagasa);
                        }

                        if (itemname.toString().equals("MobileTerminal")
                                || par1ItemStack.getTagCompound().getByte("tagmode") == 4)// チェストにIDが入ってたら
                        {
                            // Psycho_pass_core.output("同じ");
                            if (!par1ItemStack.getTagCompound().getString("before").equals(getitem.toString())) {// もし、IDが変わっていたら
                                par1ItemStack.getTagCompound().setBoolean("iderror", false); // 再度ユーザー認証するように
                            }
                            par1ItemStack.getTagCompound().setString("before", getitem.toString());
                            // 初期起動じゃなかったら通常の処理へ
                            String tagUUID = par1ItemStack.getTagCompound().getString("UUID"); // NBTTAGに入ってるUUIDを取得
                            String nowUUID = par3EntityPlayer.getUniqueID().toString(); // 現在のUUIDを取得
                            if (tagUUID.equals(nowUUID) || !Psycho_pass_core.usercheck) {// UUIDとプレイヤー名を比較
                                // 耐久値が0（ダメージ値max）ならシュビラの有無判定へ
                                if (par1ItemStack.getItemDamage() == this.maxdamage) {
                                    // シュビラがあれば消費して耐久値回復
                                    if (/*
										 * par3EntityPlayer.inventory.hasItem(
										 * Psycho_pass_core.pulsegunBullet)||
										 */par1ItemStack.getTagCompound().getBoolean("SibylSystem")) {
										/*
										 * if
										 * (par3EntityPlayer.inventory.hasItem(
										 * Psycho_pass_core.pulsegunBullet)){
										 * par1ItemStack.getTagCompound().
										 * setBoolean("bullet", true); }else{
										 * par1ItemStack.getTagCompound().
										 * setBoolean("bullet", false); }
										 */
                                        if (par1ItemStack.getTagCompound().getBoolean("ofline")) { // NBTがオンラインかどうかをcheck
                                            par1ItemStack.getTagCompound().setBoolean("system", false); // 通信エラーを初めてに変更
                                            if (par1ItemStack.getTagCompound().getBoolean("iderror")) { // NBTがIDエラーかどうかをcheck
                                                par1ItemStack.getTagCompound().setBoolean("idsystem", false); // IDエラーを初めてに変更

                                                if (Psycho_pass_core.games == 1) // モードによって動作を切り替え
                                                // ランダムモード
                                                {
                                                    mode = MathHelper.getRandomIntegerInRange(par2World.rand, 6, 1);
                                                    switch (mode) {
                                                        case 1:
                                                            par2World.playSoundAtEntity(par3EntityPlayer,
                                                                    "psycho_pass:check", 5.0F, 1.0F);
                                                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                                            mode = 2;
                                                            break;
                                                        case 2:
                                                            par2World.playSoundAtEntity(par3EntityPlayer,
                                                                    "psycho_pass:check1", 5.0F, 1.0F);
                                                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                                            mode = 3;
                                                            break;
                                                        case 3:
                                                            par2World.playSoundAtEntity(par3EntityPlayer,
                                                                    "psycho_pass:check2", 5.0F, 1.0F);
                                                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                                            mode = 4;
                                                            break;
                                                        case 4:
                                                            par2World.playSoundAtEntity(par3EntityPlayer,
                                                                    "psycho_pass:check3", 5.0F, 1.0F);
                                                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                                            mode = 5;
                                                            break;
                                                        case 5:
                                                            par2World.playSoundAtEntity(par3EntityPlayer,
                                                                    "psycho_pass:check4", 5.0F, 1.0F);
                                                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 1);
                                                            mode = 6;
                                                            break;
                                                        case 6:
                                                            par2World.playSoundAtEntity(par3EntityPlayer,
                                                                    "psycho_pass:check5", 5.0F, 1.0F);
                                                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 2);
                                                            mode = 1;
                                                            break;
                                                    }
                                                }else {// マニュアルモード
                                                    if (Psycho_pass_core.r == 1) // rが1のとき下記の測定音を再生
                                                    // パラライザー
                                                    {
                                                        switch (b) {
                                                            case 1:
                                                                par2World.playSoundAtEntity(par3EntityPlayer,
                                                                        "psycho_pass:check", 5.0F, 1.0F);
                                                                b = 2;
                                                                break;
                                                            case 2:
                                                                par2World.playSoundAtEntity(par3EntityPlayer,
                                                                        "psycho_pass:check1", 5.0F, 1.0F);
                                                                b = 3;
                                                                break;
                                                            case 3:
                                                                par2World.playSoundAtEntity(par3EntityPlayer,
                                                                        "psycho_pass:check2", 5.0F, 1.0F);
                                                                b = 4;
                                                                break;
                                                            case 4:
                                                                par2World.playSoundAtEntity(par3EntityPlayer,
                                                                        "psycho_pass:check3", 5.0F, 1.0F);
                                                                b = 1;
                                                                break;
                                                        }
                                                        par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                                    }
                                                    if (Psycho_pass_core.r == 2) // rが2のとき下記の測定音を再生
                                                    // エリミネーター
                                                    {
                                                        par2World.playSoundAtEntity(par3EntityPlayer,
                                                                "psycho_pass:check4", 5.0F, 1.0F);
                                                        par1ItemStack.getTagCompound().setByte("tagmode", (byte) 1);
                                                    }
                                                    if (Psycho_pass_core.r == 3) // rが3のとき下記の測定音を再生
                                                    // でコンポーザー
                                                    {
                                                        par2World.playSoundAtEntity(par3EntityPlayer,
                                                                "psycho_pass:check5", 5.0F, 1.0F);
                                                        par1ItemStack.getTagCompound().setByte("tagmode", (byte) 2);
                                                    }
                                                    if (Psycho_pass_core.r == 4) {
                                                        par2World.playSoundAtEntity(par3EntityPlayer,
                                                                "psycho_pass:special", 5.0F, 1.0F);
                                                        par1ItemStack.getTagCompound().setByte("tagmode", (byte) 4);
                                                    }
                                                }
                                                // par3EntityPlayer.inventory.consumeInventoryItem(Psycho_pass_core.pulsegunBullet);//
                                                // シュビラ消費
                                                par1ItemStack.damageItem(this.maxdamage * -1,
                                                        par3EntityPlayer);/* 耐久値回復 */

                                            } else {// IDエラー後チェストに公安局IDがあったら以下の処理
                                                StringBuilder itemname1 = new StringBuilder();
                                                itemname1.append(getitem.getUnlocalizedName());// アーマースロットに入ってるアイテムのローカライズ名を取得
                                                itemname1.delete(0, 5);// 文字列からitem.を削除
                                                String lowname = itemname1.toString().toLowerCase();// サウンドファイル名ように小文字に変換
                                                par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:" + lowname,
                                                        5.0F, 1.0F);// ユーザー認証を再生
                                                par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                                par1ItemStack.getTagCompound().setBoolean("iderror", true);// NBTをオンラインにする
                                            }

                                        } else {// 通信エラー後インベントリにシビュラシステムがあったら以下の処理
                                            par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:systemonline",
                                                    5.0F, 1.0F);// オンラインを再生
                                            par1ItemStack.getTagCompound().setBoolean("ofline", true);// NBTをオンラインにする
                                        }
                                        // par2World.playSoundAtEntity(par3EntityPlayer,
                                        // par2World.playSoundAtEntity(par3EntityPlayer,
                                        // par3EntityPlayer.attackTime =
                                        // this.wait_reload;
                                    } else if (par1ItemStack.getItemDamage() == 1) {
                                        par1ItemStack.getTagCompound().setBoolean("ofline", false); // NBTをオフラインにする
                                        if (!par1ItemStack.getTagCompound().getBoolean("system")) { // 初めての通信エラーかcheck
                                            par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:error", 1.0F,
                                                    1.0F); // シュビラが手持ちアイテム内にないときに通信エラーを再生
                                            par1ItemStack.getTagCompound().setBoolean("system", true); // 初めてを解除
                                        } else {
                                            par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:lock", 5.0F,
                                                    1.0F); // 2回目以降は空打ち音
                                        }
                                    }
                                } else {
                                    // if
                                    // (par3EntityPlayer.getLastAttackerTime()
                                    // == 0) // 連射防止
                                    // {
                                    if (!par1ItemStack.getTagCompound().getString("before")
                                            .equals(getitem.toString())) {// もし、IDが変わっていたら
                                        par1ItemStack.getTagCompound().setBoolean("iderror", false); // 再度ユーザー認証するように
                                    }
                                    int gettagmode = par1ItemStack.getTagCompound().getByte("tagmode");
                                    ParaEntityBullet paraentitybullet = new ParaEntityBullet(par2World,
                                            par3EntityPlayer, this.range * 2.0F);
                                    EriEntityBullet erientitybullet = new EriEntityBullet(par2World, par3EntityPlayer,
                                            this.range * 2.0F);
                                    DecoEntityBullet decoentitybullet = new DecoEntityBullet(par2World,
                                            par3EntityPlayer, this.range * 2.0F);
                                    String soundtype = "";
                                    if (gettagmode == 0 || gettagmode == 4) {
                                        soundtype = "psycho_pass:pulsegun";// 発射音
                                    } else if (gettagmode == 1) {
                                        soundtype = "psycho_pass:pulsegun1";// 発射音
                                    } else {
                                        soundtype = "psycho_pass:pulsegun2";// 発射音
                                    }
                                    // クリティカル
                                    int ran = (int) (Math.random() * 10);
                                    if (ran < 2) {
                                        paraentitybullet.setIsCritical(true);
                                        erientitybullet.setIsCritical(true);
                                        decoentitybullet.setIsCritical(true);
                                    }
                                    // 攻撃力
                                    if (gettagmode != 0 || gettagmode != 4) {
                                        erientitybullet.setDamage(paraentitybullet.getDamage() + this.attack);
                                        decoentitybullet.setDamage(paraentitybullet.getDamage() + this.attack * 500);
                                    }
                                    // ノックバック
                                    if (this.knockback == true) {
                                        paraentitybullet.setKnockbackStrength(1);
                                        erientitybullet.setKnockbackStrength(1);
                                        decoentitybullet.setKnockbackStrength(1);
                                    }
                                    // 炎上
                                    if (this.fire == true) {
                                        paraentitybullet.setFire(100);
                                        erientitybullet.setFire(100);
                                        decoentitybullet.setFire(100);
                                    }
                                    if (!par1ItemStack.getTagCompound().getBoolean("SibylSystem") || gettagmode != 4) {// 常時解除モードigaiのNBTが書き込まれてたら
                                        par1ItemStack.damageItem(1, par3EntityPlayer); // 耐久値消費
                                    } else if (Psycho_pass_core.r != 4 || Psycho_pass_core.games == 1) {// 測定モードが自動もしくは、常時解除モード以外だったら
                                        par1ItemStack.damageItem(1, par3EntityPlayer);// 消費
                                    }
                                    par2World.playSoundAtEntity(par3EntityPlayer, soundtype, 1.0F, 1.0F); // 発射音
                                    if (gettagmode == 0 || gettagmode == 4) {
                                        par2World.spawnEntityInWorld(paraentitybullet); // エンティティ呼びだし
                                    } else if (gettagmode == 1) {
                                        par2World.spawnEntityInWorld(erientitybullet); // エンティティ呼びだし
                                    } else if (gettagmode == 2) {
                                        par2World.spawnEntityInWorld(decoentitybullet); // エンティティ呼びだし
                                    }
                                    if (gettagmode != 4) {
                                        par1ItemStack.getTagCompound().setByte("tagmode", (byte) 0);
                                    }
                                    // par3EntityPlayer.attackTime =
                                    // this.wait_attack;
                                }
                            } else {// UUIDとプレイヤー名が一致しなかった場合 不正ユーザーを再生
                                if (!par1ItemStack.getTagCompound().getString("newuser").equals(nowUUID)) {
                                    par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:injustice", 5.0F, 1.0F);
                                    par1ItemStack.getTagCompound().setString("newuser", nowUUID);
                                } else {
                                    par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:lock", 5.0F, 1.0F);
                                }
                                par1ItemStack.getTagCompound().setByte("tagmode", (byte) 3);
                            }
                        } else {// チェストにIDが入ってなかったら
                            // Psycho_pass_core.output("違う");
                            par1ItemStack.getTagCompound().setBoolean("iderror", false);// NBTをIDエラーにする
                            if (!par1ItemStack.getTagCompound().getBoolean("idsystem")) {// 初めてのIDエラーかcheck
                                par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:injustice", 1.0F, 1.0F); // IDエラー時に不正ユーザーを再生
                                par1ItemStack.getTagCompound().setBoolean("idsystem", true);// 初めてを解除
                            } else {
                                par2World.playSoundAtEntity(par3EntityPlayer, "psycho_pass:lock", 5.0F, 1.0F);// 2回目以降は空打ち音
                            }
                            if (par1ItemStack.getItemDamage() == 0 && !par3EntityPlayer.capabilities.isCreativeMode) {// もしドミネーターが打てる状態だったら
                                // par3EntityPlayer.inventory.addItemStackToInventory(new
                                // ItemStack(Psycho_pass_core.pulsegunBullet));
                                // //アイテム「シビュラシステム」を返却
                                par1ItemStack.damageItem(1, par3EntityPlayer); // その後撃てない状態にする。
                            }
                            par1ItemStack.getTagCompound().setByte("tagmode", (byte) 3);
                        }
                    }
                }
            }
        }
        return par1ItemStack;
    }

    // 右クリック時の動作
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        if (par1ItemStack.getItemDamage() == this.maxdamage) {
            return EnumAction.block;
        } else {
            return EnumAction.bow;
        }
    }

    // エンチャント不可
    public boolean isItemTool(ItemStack par1ItemStack) {
        return false;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        return par1ItemStack;
    }

    public boolean isFull3D() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    private IIcon normalIcon;
    @SideOnly(Side.CLIENT)
    private IIcon changeIcon;
    @SideOnly(Side.CLIENT)
    private IIcon changeIcon2;
    @SideOnly(Side.CLIENT)
    private IIcon changeIcon3;
    @SideOnly(Side.CLIENT)
    private IIcon changeIcon4;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        normalIcon = register.registerIcon("psycho_pass:pulsegun10");
        changeIcon = register.registerIcon("psycho_pass:pulsegun11");
        changeIcon2 = register.registerIcon("psycho_pass:pulsegun12");
        changeIcon3 = register.registerIcon("psycho_pass:pulsegun13");
        changeIcon4 = register.registerIcon("psycho_pass:pulsegun14");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack par1ItemStack) {
        if (par1ItemStack.stackTagCompound == null) {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }
        int tagmode = par1ItemStack.stackTagCompound.getByte("tagmode");
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 0) {
            return normalIcon;
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 1) {
            return changeIcon;
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 2) {
            return changeIcon2;
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 3) {
            return changeIcon3;
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 4) {
            return changeIcon4;
        }
        return normalIcon;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconIndex(stack);
    }

    /*
    public void changeMode(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
        final int tagmode = par1ItemStack.stackTagCompound.getByte("tagmode");
        // int mode = par1ItemStack.s
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 0) {
            String s = StatCollector.translateToLocal("title.mode0.name");
            player.addChatComponentMessage(new ChatComponentTranslation(s, new Object[0]));
        }
    }
     */
    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        if (par1ItemStack.stackTagCompound == null) {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }
        String s = null;
        int tagmode = par1ItemStack.stackTagCompound.getByte("tagmode");
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 0) {
            s = StatCollector.translateToLocal("title.mode0.name");
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 1) {
            s = StatCollector.translateToLocal("title.mode1.name");
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 2) {
            s = StatCollector.translateToLocal("title.mode2.name");
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 3) {
            s = StatCollector.translateToLocal("title.usererror.name");
        }
        if (par1ItemStack != null && par1ItemStack.hasTagCompound() && tagmode == 4) {
            s = StatCollector.translateToLocal("title.mode3.name");
        }
        return (StatCollector.translateToLocal(
                new StringBuilder().append(getUnlocalizedNameInefficiently(par1ItemStack)).append(".name").toString())
                + " (" + s + ")").trim();
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3EntityPlayer, int itemSlot, boolean isSelected) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List list, boolean advanced) {
        if (Psycho_pass_core.games == 1) {
            list.add(StatCollector.translateToLocal("title.modenow.name")
                    + (ChatFormatting.RED + (StatCollector.translateToLocal("title.automode.name"))));
            list.add(StatCollector.translateToLocal("title.autoinfo.name"));
        } else {
            list.add(StatCollector.translateToLocal("title.modenow.name")
                    + (ChatFormatting.RED + (StatCollector.translateToLocal("title.manualmode.name"))));
            list.add(StatCollector.translateToLocal("title.manualinfo.name"));
        }
        if (Psycho_pass_core.usercheck) {
            String tagUUID = StatCollector.translateToLocal("title.listnull.name");
            String playername = StatCollector.translateToLocal("title.listnull.name");
            int fast = par1ItemStack.getTagCompound().getByte("fast");
            if (fast == 2) {
                tagUUID = par1ItemStack.getTagCompound().getString("UUID");
                playername = par1ItemStack.getTagCompound().getString("playername");
                ;
            }
            list.add(StatCollector.translateToLocal("title.username.name") + playername);
            list.add(StatCollector.translateToLocal("title.useruuid.name") + tagUUID);
        }
    }
}
