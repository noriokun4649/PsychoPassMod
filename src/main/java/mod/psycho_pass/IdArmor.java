package mod.psycho_pass;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class IdArmor extends ItemArmor {

    //renderIndexは以下でArmorTextureを指定する場合はどんな値でも良い。
    //0 is cloth, 1 is chain, 2 is iron, 3 is diamond and 4 is gold.
    public IdArmor(ItemArmor.ArmorMaterial armorMaterial, int renderIndex, int type) {
        super(armorMaterial, renderIndex, type);
    }
    //ItemStack:防具のItemStack, Entity:装備してるEntity, Slot:装備してるスロット, Layer:オーバーレイかどうか
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
        return "psycho_pass:textures/armor/armor.png";
   }
}