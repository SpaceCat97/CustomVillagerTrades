package uk.co.dotcode.customvillagertrades.configs;

import net.minecraft.nbt.CompoundNBT;

public class NBTData {

	public String nbtName;
	public String dataType;
	public String data;

	public NBTData() {
		if (nbtName == null) {
			nbtName = "";
		}

		if (dataType == null) {
			dataType = "string";
		}

		if (data == null) {
			data = "";
		}
	}

	/**
	 * Possible data types: String, Boolean, Integer, Float, Byte, Long
	 */
	public CompoundNBT getTag() {
		CompoundNBT tag = new CompoundNBT();

		if (dataType.equalsIgnoreCase("string")) {
			tag.putString(nbtName, data);
		} else if (dataType.equalsIgnoreCase("boolean")) {
			tag.putBoolean(nbtName, Boolean.parseBoolean(data));
		} else if (dataType.equalsIgnoreCase("integer")) {
			tag.putInt(nbtName, Integer.parseInt(data));
		} else if (dataType.equalsIgnoreCase("float")) {
			tag.putFloat(nbtName, Float.parseFloat(data));
		} else if (dataType.equalsIgnoreCase("byte")) {
			tag.putByte(nbtName, Byte.parseByte(data));
		} else if (dataType.equalsIgnoreCase("long")) {
			tag.putLong(nbtName, Long.parseLong(data));
		} else {
			tag.putString(nbtName, data);
		}

		return tag;
	}
}
