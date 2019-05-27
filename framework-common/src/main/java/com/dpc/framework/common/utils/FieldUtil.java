package com.dpc.framework.common.utils;

import com.dpc.framework.common.exception.ErrorInfoException;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 操作实体类的字段工具类
 *
 * @author liuhaiyun
 * @email liuhaiyun@boco.com.cn
 * @version v1.0
 * @time 2018年1月22日 下午5:33:51
 * @modify <BR/>
 *         修改内容：<BR/>
 *         修改人员：<BR/>
 *         修改时间：<BR/>
 */
public class FieldUtil {

	/**
	 * 获取实体类的字段名数组
	 * 
	 * @param clazz
	 * @return
	 */
	public static String[] getFields(Class<?> clazz) {
		Field[] selfFields = clazz.getDeclaredFields();
		int length = selfFields.length;
		String[] fields = new String[length];
		for (int i = 0; i < length; i++) {
			fields[i] = selfFields[i].getName();
		}
		return fields;
	}

	/**
	 * @MethodName getFieldByName
	 * @Description 根据字段名获取字段
	 * @param fieldName
	 *            字段名
	 * @param clazz
	 *            包含该字段的类
	 * @return 字段
	 */
	public static Field getFieldByName(String fieldName, Class<?> clazz) {
		// 拿到本类的所有字段
		Field[] selfFields = clazz.getDeclaredFields();

		// 如果本类中存在该字段，则返回
		for (Field field : selfFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		// 否则，查看父类中是否存在此字段，如果有则返回
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null && superClazz != Object.class) {
			return getFieldByName(fieldName, superClazz);
		}

		// 如果本类和父类都没有，则返回空
		return null;
	}

	/**
	 * @MethodName setFieldValueByName
	 * @Description 根据字段名给对象的字段赋值
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param o
	 *            对象
	 */
	public static void setFieldValueByName(String fieldName, Object fieldValue, Object o, String dateformat)
			throws Exception {

		Field field = getFieldByName(fieldName, o.getClass());
		if (dateformat == null || dateformat.trim().equals("")) {
			dateformat = "EEE MMM dd HH:mm:ss ZZZ yyyy";
		}
		if (fieldValue == null || fieldValue.toString().trim().equals("")) {
			return;
		}
		if (field != null) {
			field.setAccessible(true);
			// 获取字段类型
			Class<?> fieldType = field.getType();

			// 根据字段类型给字段赋值
			if (String.class == fieldType) {
				field.set(o, String.valueOf(fieldValue));
			} else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
				field.set(o, Integer.parseInt(fieldValue.toString()));
			} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
				field.set(o, Long.valueOf(fieldValue.toString()));
			} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
				field.set(o, Float.valueOf(fieldValue.toString()));
			} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
				field.set(o, Short.valueOf(fieldValue.toString()));
			} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
				field.set(o, Double.valueOf(fieldValue.toString()));
			} else if (Character.TYPE == fieldType) {
				if ((fieldValue != null) && (fieldValue.toString().length() > 0)) {
					field.set(o, Character.valueOf(fieldValue.toString().charAt(0)));
				}
			} else if (Date.class == fieldType) {
				field.set(o, new SimpleDateFormat(dateformat, Locale.UK).parse(fieldValue.toString()));
			} else {
				field.set(o, fieldValue);
			}
		} else {
			throw new ErrorInfoException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
		}
	}

	/**
	 * 根据字段名获取字段值
	 * 
	 * @param fieldName
	 *            字段名
	 * @param o
	 *            对象
	 * @return 字段值
	 * @throws Exception
	 */
	public static Object getFieldValueByName(String fieldName, Object o) throws Exception {

		Object value = null;
		Field field = getFieldByName(fieldName, o.getClass());

		if (field != null) {
			field.setAccessible(true);
			value = field.get(o);
		} else {
			throw new ErrorInfoException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
		}

		return value;
	}

}
