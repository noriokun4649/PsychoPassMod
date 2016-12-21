package mod.psycho_pass;

import java.io.*;
import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static mod.psycho_pass.Psycho_pass_core.output;

public class SibylSystemBlock extends Block {

	public static ArrayList<String> array = new ArrayList<String>();//動的配列宣言
	private int arraycount= 0;//カウント・・・・・・
	public SibylSystemBlock() {
		super(Material.grass);
		setBlockName("sibylsystem");
		setCreativeTab(Psycho_pass_core.psycho_pass);
		setHardness(10F);
	}

	//ブロック破壊時に呼ばれるメゾット
    /*
	@Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int x, int y, int z, int p_149664_5_)
	{

	}
	*/
	 public static IIcon base;
	 public static IIcon brain;
	 public static IIcon glass2;
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        base = p_149651_1_.registerIcon("psycho_pass:base");
        brain = p_149651_1_.registerIcon("psycho_pass:brain");
        glass2 = p_149651_1_.registerIcon("glass_yellow");
    }
    boolean no = true;
	//ブロック設置時に呼ばれるメゾットp
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
	    if (no) {
	        no = false;
            output("設置メソッド　呼び出し");
            String a = x + "," + y + "," + z;
            try {
                String parent = Psycho_pass_core.config.getConfigFile().getParent();
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(parent + "\\PSYCHO_PASS.data"), true));
                bw.write(a);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
                output("ファイル作成失敗");
                String parent = Psycho_pass_core.config.getConfigFile().getParent();
                File af = new File(parent+"\\PSYCHO_PASS.data");
                af.delete();
            }
        }else {
	        no = true;
        }

        //array.add(a);//動的配列に座標を挿入
		//arraycount = array.size();//配列のサイズを取得・挿入
	}


    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    //ブロック破壊時
    @Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
        super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);

        try{
            array.remove(arraycount);//動的配列から座標を削除。。。
        }catch(IndexOutOfBoundsException ex){
            output("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa error w");
        }
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return Psycho_pass_core.RenderID;
   }
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return true;
    }
    @SideOnly(Side.CLIENT)
    public String getItemIconName()
    {
        return "psycho_pass:sibylsystem";
    }
}
