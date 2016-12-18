package mod.psycho_pass;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class SibylSystemBlock_Model implements ISimpleBlockRenderingHandler
{
		/**インベントリ内のレンダリング,今回はパス**/
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{}
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == this.getRenderId())
		{
			//(始X, 始Y, 始Z, 終X, 終Y, 終Z)の順
			renderer.setOverrideBlockTexture(SibylSystemBlock.base);
			renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.313F, 1F);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;

			renderer.setOverrideBlockTexture(renderer.getBlockIcon(Blocks.glass));
			renderer.setRenderBounds(0F,0.313F, 0F, 1F, 1F, 1F);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;

			renderer.setOverrideBlockTexture(SibylSystemBlock.glass2);
			renderer.setRenderBounds(0.063F, 0.313F, 0.063F, 0.938F,0.938F ,0.938F);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;

			renderer.setOverrideBlockTexture(SibylSystemBlock.brain);
			renderer.setRenderBounds(0.125F ,0.438F , 0.125F ,0.438F,0.875F, 0.875F);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;

			renderer.setOverrideBlockTexture(SibylSystemBlock.brain);
			//renderer.setRenderBounds(10,7,4,14,14,13);
			renderer.setRenderBounds(0.563F,0.438F,0.125F,0.875F,0.875F,0.875F);
			renderer.renderStandardBlock(block, x, y, z);
			renderer.renderAllFaces = true;

			renderer.clearOverrideBlockTexture();
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBoundsFromBlock(block);
			return true;
		}
		return false;
      }
	@Override
	/**インベントリ内でレンダリングするか**/
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}
	/**自らのRenderIDをreturnする**/
	@Override
	public int getRenderId() {

		return Psycho_pass_core.RenderID;
	}

}
