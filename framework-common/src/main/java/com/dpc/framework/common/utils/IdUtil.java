package com.dpc.framework.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * ID生成器
 */
public class IdUtil {

	private IdUtil(){

	}

	/**
	 * 获取crc32字符
	 * 
	 * @param str
	 * @return Long
	 */
	public static Long getCRC32(String str) {
		CRC32 crc32 = new CRC32();
		crc32.update(str.getBytes());
		return crc32.getValue();
	}


	/**
	 * 获取uuid
	 * 
	 * @return uuid字符串格式 dcf3cf01-2307-4ce0-ade8-e485831eabbb
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 获取uuid，并转换为long
	 * 
	 * @return Long
	 */
	public static Long getLongUUID() {
		UUID uuid = UUID.randomUUID();
		ByteArrayOutputStream ba = new ByteArrayOutputStream(16);
		DataOutputStream da = new DataOutputStream(ba);
		try {
			da.writeLong(uuid.getMostSignificantBits());
			da.writeLong(uuid.getLeastSignificantBits());
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] bytes = ba.toByteArray();
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.put(bytes, 0, bytes.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}
}
