package com.dpc.framework.common.utils;

import com.dpc.framework.common.exception.ErrorInfoException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * CSV读取工具类
 */
public class CSVReader {
	/**
	 * 要解析的文件名（这里指完整路径）
	 */
	private String fileName = "";
	/**
	 * CSV文件类型
	 */
	private CSVFormat format = CSVFormat.DEFAULT;
	/**
	 * 对应的类
	 */
	private Class<?> entryclass;
	/**
	 * 日期形式，默认为yyyy-mm-dd HH：mm：ss
	 */
	private String dateformat;
	/**
	 * 文件编码格式
	 */
	private Charset charset = Charset.forName("utf-8");
	/**
	 * 按行读取时，当前行数
	 */
	private int line = 0;
	/**
	 * 按行读取时，类的字段与表列数对应关系
	 */
	private LinkedHashMap<String, Integer> colMap;

	/**
	 * 构造方法
	 *
	 * @param fileName
	 *            文件完整路径
	 */
	public CSVReader(String fileName) {
		this.fileName = fileName;
	}

	public CSVReader(Class<?> entryclass, char delimiter, String charset) {
		if (entryclass != null) {
			this.format = CSVFormat.RFC4180.withDelimiter(delimiter).withFirstRecordAsHeader();
			this.entryclass = entryclass;
		} else {
			this.format = CSVFormat.RFC4180.withDelimiter(delimiter);
		}
		if (charset != null && !charset.trim().equals("")) {
			this.charset = Charset.forName(charset);
		}
	}

	/**
	 *
	 * @param fileName
	 *            文件完整路径
	 * @param delimiter
	 *            自定义分隔符
	 */
	public CSVReader(String fileName, char delimiter) {
		this.fileName = fileName;
		this.format = CSVFormat.DEFAULT.withDelimiter(delimiter);
	}

	/**
	 * 构造方法
	 *
	 * @param fileName
	 *            文件完整路径
	 * @param entryclass
	 *            对应的实体类
	 */
	public CSVReader(String fileName, Class<?> entryclass) {
		this.fileName = fileName;
		this.format = format.withFirstRecordAsHeader();
		this.entryclass = entryclass;
	}

	/**
	 * 构造方法
	 *
	 * @param fileName
	 *            文件完整路径
	 * @param entryclass
	 *            对应的实体类
	 * @param delimiter
	 *            分隔符，默认为逗号
	 * @param charset
	 *            编码格式，默认为UTF-8
	 */
	public CSVReader(String fileName, Class<?> entryclass, char delimiter, String charset) {
		this.fileName = fileName;
		if (entryclass != null) {
			this.format = CSVFormat.RFC4180.withDelimiter(delimiter).withFirstRecordAsHeader();
			this.entryclass = entryclass;
		} else {
			this.format = CSVFormat.RFC4180.withDelimiter(delimiter);
		}
		if (charset != null && !charset.trim().equals("")) {
			this.charset = Charset.forName(charset);
		}
	}

	/**
	 * 适用于表头与类字段直接对应的情况
	 *
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> read() throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		return read(fis);
	}

	/**
	 * 提供了表头与类的字段映射的读取方法，返回相应类集合
	 *
	 * @param is
	 *            文件流
	 * @param fieldMap
	 *            表头与类的字段映射，key为表头名，value为字段名
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> read(InputStream is, LinkedHashMap<String, String> fieldMap) throws Exception {
		List<T> list = new ArrayList<T>();
		@SuppressWarnings("unchecked")
		T entry = (T) entryclass.newInstance();
		InputStreamReader reader = new InputStreamReader(is, this.charset);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		String delimiter = getDelimiter(format.getDelimiter());
		if ((line = CSVFileUtil.readLine(br)) != null) {
			LinkedHashMap<String, Integer> colMap = parseFirstLine(line, fieldMap, delimiter);
			while ((line = CSVFileUtil.readLine(br)) != null) {
				if(StringUtils.isBlank(line)){
					continue;
				}
				entry = parse(line, colMap, delimiter);
				list.add(entry);
			}
		}

		br.close();
		reader.close();
		return list;
	}



	/**
	 * 提供了表头与类的字段映射的读取方法，返回相应类集合
	 *
	 * @param fieldMap
	 *            表头与类的字段映射，key为表头名，value为字段名
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> read(LinkedHashMap<String, String> fieldMap) throws Exception {
		List<T> list = new ArrayList<T>();
		@SuppressWarnings("unchecked")
		T entry = (T) entryclass.newInstance();
		InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), this.charset);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		String delimiter = getDelimiter(format.getDelimiter());
		if ((line = br.readLine()) != null) {
			LinkedHashMap<String, Integer> colMap = parseFirstLine(line, fieldMap, delimiter);
			while ((line = br.readLine()) != null) {
				entry = parse(line, colMap, delimiter);
				list.add(entry);
			}
		}

		br.close();
		reader.close();
		return list;
	}

	/**
	 * 将文件解析为List
	 *
	 * @param fieldMap
	 *            表头与类的字段映射，key为表头名，value为字段名
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> read2List(LinkedHashMap<String, String> fieldMap) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String delimiter = getDelimiter(format.getDelimiter());
		InputStream is = new FileInputStream(fileName);
		Scanner sc = new Scanner(is, charset.name());
		String line = sc.nextLine();
		LinkedHashMap<String, Integer> colMap = parseFirstLine(line, fieldMap, delimiter);
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			LinkedHashMap<String, String> resultMap = new LinkedHashMap<String, String>();
			String[] values = line.split(delimiter);
			for (Entry<String, String> entry : fieldMap.entrySet()) {
				String enName = entry.getValue();
				int i = colMap.get(enName);
				resultMap.put(enName, removeQuote(values[i]));
			}

			list.add(resultMap);
		}
		sc.close();
		is.close();
		return list;
	}

	/**
	 * 流式读取，读取一行数据，返回一行的结果
	 *
	 * @return
	 * @throws Exception
	 */
	public <T> T readByLine() throws Exception {
		@SuppressWarnings("unchecked")
		T entry = (T) entryclass.newInstance();
		if (line == 0) {
			String str = readLine(line);
			String delimiter = getDelimiter(format.getDelimiter());
			String[] heads = str.split(delimiter);
			format = CSVFormat.DEFAULT.withDelimiter(format.getDelimiter()).withHeader(heads);
			line++;
		}

		String str = readLine(line);
		line++;
		List<CSVRecord> records = CSVParser.parse(str, format).getRecords();
		if (records.isEmpty() || records.size() > 1) {
			return null;
		}

		CSVRecord record = records.get(0);
		Field[] fields = entryclass.getDeclaredFields();
		for (Field field : fields) {
			String param = field.getName();
			FieldUtil.setFieldValueByName(param, record.get(param), entry, dateformat);
		}

		return entry;

	}

	/**
	 * 该方法适用于有表头和映射关系的大文件读取，读一行返回一行结果。
	 *
	 * @param fieldMap
	 *            表头与类的字段映射，key为表头名，value为字段名
	 * @return
	 * @throws Exception
	 * @throws IllegalAccessException
	 */
	public <T> T readByLine(LinkedHashMap<String, String> fieldMap) throws Exception, IllegalAccessException {
		String delimiter = getDelimiter(format.getDelimiter());
		if (line == 0) {
			String str = readLine(line);
			colMap = parseFirstLine(str, fieldMap, delimiter);
			line++;
		}
		String str = readLine(line);
		line++;
		return parse(str, colMap, delimiter);
	}

	/**
	 * 读取一行数据，无表头，返回一行数据的集合
	 *
	 * @return
	 * @throws IOException
	 */
	public List<String> readByLineWithNoHeader() throws IOException {
		List<String> list = new ArrayList<String>();
		String str = readLine(line);
		List<CSVRecord> records = CSVParser.parse(str, format).getRecords();
		Iterator<String> it = records.get(0).iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
		line++;
		return list;
	}

	/**
	 * @param dateformat
	 *            the dateformat to set
	 */
	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	/**
	 * 读取指定行数据
	 *
	 * @param lineNum
	 * @return
	 * @throws IOException
	 */
	private String readLine(int lineNum) throws IOException {
		InputStream is = new FileInputStream(fileName);
		Scanner sc = new Scanner(is, charset.name());
		int i = 0;
		String line = "";
		while (sc.hasNext()) {
			if (i == lineNum) {
				line = sc.nextLine();
				break;
			}
			i++;
			sc.nextLine();
		}
		sc.close();
		is.close();
		return line;
	}

	/**
	 * 解析第一行，返回类字段和行数的对应关系
	 *
	 * @param line
	 * @param fieldMap
	 * @param delimiter
	 * @return
	 * @throws Exception
	 */
	private LinkedHashMap<String, Integer> parseFirstLine(String line, LinkedHashMap<String, String> fieldMap,
	                                                      String delimiter) throws Exception {
		line = line.replace("\uFEFF", "");
		String[] records = line.split(delimiter);
		List<String> fieldList = Arrays.asList(records);

		boolean isExist = true;
		for (String field : fieldMap.keySet()) {
			if (!fieldList.contains(field)) {
				isExist = false;
				break;
			}
		}
		if (!isExist) {
			throw new ErrorInfoException("csv中缺少必要的字段，或字段名称有误");
		}
		LinkedHashMap<String, Integer> colMap = new LinkedHashMap<String, Integer>();
		for (int i = 0; i < records.length; i++) {
			String record = removeQuote(records[i]);
			String field = fieldMap.get(record);
			if (field != null) {
				colMap.put(fieldMap.get(record), i);
			}
		}
		return colMap;
	}

	private String removeQuote(String record) {
		if(StringUtils.isBlank(record)){
			return record;
		}
		if(record.startsWith("\"") && record.endsWith("\"")){
			return record.substring(1,record.length()-1);
		}

		return record;
	}

	/**
	 * 解析一行数据，返回对应的类
	 *
	 * @param line
	 * @param colMap
	 * @param delimiter
	 * @return
	 * @throws Exception
	 * @throws IllegalAccessException
	 */
	private <T> T parse(String line, LinkedHashMap<String, Integer> colMap, String delimiter)
			throws Exception, IllegalAccessException {
		@SuppressWarnings("unchecked")
		T entry = (T) entryclass.newInstance();
		//String[] record = line.split(delimiter);
		List<String> record = CSVFileUtil.fromCSVLinetoArray(line);
		Field[] fields = entryclass.getDeclaredFields();
		for (Field field : fields) {
			String param = field.getName();
			if (colMap.containsKey(param)) {
				String value = record.get(colMap.get(param));
				FieldUtil.setFieldValueByName(param, removeQuote(value), entry, dateformat);
			}
		}
		return entry;
	}

	/**
	 * 有表头的数据读取方法，返回相应类集合
	 *
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> read(InputStream is) throws Exception {
		List<T> list = new ArrayList<T>();
		List<CSVRecord> records = CSVParser.parse(is, charset, format).getRecords();
		for (CSVRecord record : records) {
			@SuppressWarnings("unchecked")
			T entry = (T) entryclass.newInstance();
			Field[] fields = entryclass.getDeclaredFields();
			for (Field field : fields) {
				String param = field.getName();
				FieldUtil.setFieldValueByName(param, removeQuote(record.get(param)), entry,
						dateformat);
			}
			list.add(entry);
		}
		is.close();
		return list;
	}

//	/**
//	 * 有表头的数据读取方法，返回相应类集合
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	public <T> List<T> read(InputStream is, LinkedHashMap<String,String> colMap) throws Exception {
//		List<T> list = new ArrayList<T>();
//		List<CSVRecord> records = CSVParser.parse(is, charset, format).getRecords();
//		for (CSVRecord record : records) {
//			@SuppressWarnings("unchecked")
//			T entry = (T) entryclass.newInstance();
//			Field[] fields = entryclass.getDeclaredFields();
//			for (Field field : fields) {
//				String param = field.getName();
//				if(!colMap.containsKey(param)){
//					continue;
//				}
//				FieldUtil.setFieldValueByName(param, removeQuote(record.get(colMap.get(param))), entry,
//						dateformat);
//			}
//			list.add(entry);
//		}
//		is.close();
//		return list;
//	}

	private String getDelimiter(char delimiter) {
		if (delimiter == '|') {
			return "\\|";
		} else if (delimiter == ' ') {
			return ",";
		} else {
			return String.valueOf(delimiter);
		}
	}

	/**
	 * @return the charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

}