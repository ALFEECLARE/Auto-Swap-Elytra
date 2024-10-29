package jp.ne.clane.autoSwapElytra;

import jp.ne.clane.autoSwapElytra.commons.ConfigBase;

public class AutoSwapElytraConfig extends ConfigBase {
	  public static SwapMode armorSwapMode = SwapMode.valueOf("VALUE");
	  public static int swapSlot = 0;
	  public static boolean ignoreArmorTier = false; 
	  public static boolean isSwapFireworks = true;
	  public static boolean isFireworksOffhand = false;
	  public static boolean isUseSmallFireworksStack = true;
	  
	  public AutoSwapElytraConfig() {
		  super(AutoSwapElytraConfig.class,AutoSwapElytraMain.MOD_ID);
	  }
	  
	  public enum SwapMode {
		  VALUE,PREUSED,OFFHAND,SLOT;
	  }
}
