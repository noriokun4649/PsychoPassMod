package mod.psycho_pass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class Truncheon extends ItemSword {

	public Truncheon(ToolMaterial material) {
		super(material);
		setTextureName("psycho_pass:truncheon_shrink");
		maxStackSize = 1;
		setCreativeTab(Psycho_pass_core.psycho_pass);
		setUnlocalizedName("truncheon");
		setMaxDamage(material.getMaxUses());
	}
	int mode = 0;
	int count1 = 0;




	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(count1 == 0){
			if (mode == 1){
				mode = 0;
				par3EntityPlayer.playSound("psycho_pass:truncheon_shrink_sound", 1, 1);
			}else{
				mode = 1;
				par3EntityPlayer.playSound("psycho_pass:truncheon_normal_sound", 1, 1);
			}
			count1 = 1;
		}else{
			count1 = 0;
		}
		return par1ItemStack;
	}
	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack stack)
    {
		if (mode == 1){
		entity.playSound("psycho_pass:truncheon", 1, 1);
		}
		return false;
    }

	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
		if (mode == 1){
			target.playSound("psycho_pass:truncheon_attack", 1, 1);
			float healt = target.getHealth();
			target.setHealth(healt-4);
		}else{
		}
        return true;
    }
	@SideOnly(Side.CLIENT)
	private IIcon normalIcon;
	private IIcon changeIcon;
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		normalIcon = register.registerIcon("psycho_pass:truncheon_shrink");
		changeIcon = register.registerIcon("psycho_pass:truncheon_normal");
	}
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack par1ItemStack) {
		IIcon model;
		if (mode == 0){
			model = normalIcon;
		}else {
			model = changeIcon;
		}
		return model;

	}
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}
}
