package mod.psycho_pass;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
	@Override
    public void onBlockDestroyedByPlayer(World p_149664_1_, int x, int y, int z, int p_149664_5_)
	{
		try{
			array.remove(arraycount);//動的配列から座標を削除。。。
		}catch(IndexOutOfBoundsException ex){

		}
	}
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
	//ブロック設置時に呼ばれるメゾット
	@Override
	public void onBlockAdded(World p_149726_1_, int x, int y, int z)
	{
		String a = x+","+y+","+z;
		array.add(a);//動的配列に座標を挿入
		arraycount = array.size();//配列のサイズを取得・挿入
	}
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
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
