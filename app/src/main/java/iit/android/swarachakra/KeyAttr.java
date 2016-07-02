package iit.android.swarachakra;

/**
 * 
 * @author manideep polireddi
 *
 */
public class KeyAttr {
	public int code;
	public String label;
	public boolean showIcon;
	public String icon;
	public boolean showChakra;
	public boolean showCustomChakra;
	public String[] customChakraLayout;
	public boolean changeLayout;
	public String layout;
	public boolean isException;
	
	/**
	 * Constructor of KeyAttr with all variables
	 * @param code key code of the key
	 * @param label key label of the key
	 * @param showIcon whether to show and icon instead of a label
	 * @param icon 
	 * @param showChakra
	 * @param showCustomChakra
	 * @param customChakraLayout
	 * @param changeLayout
	 * @param layout
	 */
	public KeyAttr(int code, String label, boolean showIcon, String icon, boolean showChakra,
			boolean showCustomChakra, String[] customChakraLayout,
			boolean changeLayout, String layout, boolean isException){
		this.code = code;
		this.label = label;
		this.showIcon = showIcon;
		this.icon = icon;
		this.showChakra = showChakra;
		this.showCustomChakra = showCustomChakra;
		this.customChakraLayout = customChakraLayout;
		this.changeLayout = changeLayout;
		this.layout = layout;
		this.isException = isException;
	}
	
	public KeyAttr(){
		this.code = 0;
		this.label = "";
		this.showIcon = false;
		this.icon = "";
		this.showChakra = false;
		this.showCustomChakra = false;
		this.customChakraLayout = new String[] {};
		this.changeLayout = false;
		this.layout = "";
		this.isException = false;
	}
}
