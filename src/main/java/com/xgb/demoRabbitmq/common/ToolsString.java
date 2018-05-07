package com.xgb.demoRabbitmq.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串Tools
 * 
 * @author Administrator
 *
 */
public class ToolsString {
	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            传入要截取的字符串
	 * @param begin
	 *            开始截取
	 * @param end
	 *            结束截取
	 * @return
	 */
	public static String getString(String str, String begin, String end) {
		String str1 = str.substring(str.lastIndexOf(begin) + 1, str.lastIndexOf(end));
		return str1;
	}

	/**
	 * 从一段字符串中获取其中的图片路径
	 * 
	 * @param str
	 * @return 获得图片字符集合
	 */
	public static List<String> getImg(String str) {
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "src=\"(.*?)\"";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(str);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}

	/**
	 * 斜杠转换
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param type [转换类型type == 0 “/”转“\”] [type == 1 “\”转“/”]
	 * @return 返回转换后的字符串
	 */
	public static String SlashConversion(String str, int type) {
		String returnStr = "";
		if (type == 0) {// “/”转“\”
			returnStr = str.replaceAll("/", "\\\\");
		}
		if (type == 1) {// “\”转“/”
			returnStr = str.replaceAll("\\\\", "/");
		}
		return returnStr;
	}

	/**
	 * 获取A~Z的随机字符
	 * 
	 * @param digit
	 *            字符位数 int类型
	 * @param caseWrite
	 *            是否忽略大小写 true 忽略 false 不忽略
	 * @return 返回随机A~Z的特点位数的字符
	 */
	public static String GetRandomStrATOZ(int digit, boolean caseWrite) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < digit; ++i) {
			int number = random.nextInt(2);
			if (caseWrite) {
				number = random.nextInt(1);
			}
			long result = 0;
			switch (number) {
			case 0:
				/* A-Z 的 ASCII 码值[65,90] */
				result = Math.round(Math.random() * 25 + 65);
				sb.append(String.valueOf((char) result));
				break;
			case 1:
				/* a-z 的 ASCII 码值[97,122] */
				result = Math.round(Math.random() * 25 + 97);
				sb.append(String.valueOf((char) result));
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 获取0~9的随机随机数
	 * 
	 * @param digit
	 *            控制位数
	 * @return 返回随机数
	 */
	public static String GetRandomIntegerZeroToNine(int digit) {
		StringBuffer sb = new StringBuffer();
		if (digit > 0) {
			for (int i = 0; i < digit; i++) {
				sb.append(Math.random() * 10);
			}
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 验证是否是4位纯数字
	 * 
	 * @param number
	 * @return
	 */
	public static boolean verifyNumber(String number) {
		boolean result = false;
		// 正则表达式规则：0-9之间的数字连续出现4次
		Pattern pattern = Pattern.compile("[0-9]{4}");
		Matcher matcher = pattern.matcher(number);
		// 匹配成功就表明是纯数字
		if (matcher.matches())
			result = true;

		return result;
	}

	/**
	 * 验证是否有重复字符
	 * 
	 * @param number
	 * @return
	 */
	public static boolean verifyRepeat(String number) {
		boolean result = false;
		// 从第2位数开始，每位都与它前面的所有数比较一遍
		for (int i = 1; i < number.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (number.charAt(i) == number.charAt(j)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 生成不重复的digit位随机数
	 * 
	 * @param digit
	 *            位数
	 * @return String
	 */
	public static String generateRandNumber(int digit) {
		if (digit <= 0) {
			return "0";
		}
		StringBuffer randBuffer = new StringBuffer();
		String scopeStr = "0123456789";
		Random random = new Random();
		for (int i = 0; i < digit; i++) {
			int num = random.nextInt(scopeStr.length());
			randBuffer.append(scopeStr.charAt(num));
			// 将每次获取到的随机数从scopeStr中移除
			scopeStr = scopeStr.replace(String.valueOf(scopeStr.charAt(num)), "");
		}
		return randBuffer.toString();
	}

	/**
	 * 计算猜测结果
	 * 
	 * @param answer
	 *            正确答案
	 * @param number
	 *            猜测的数字
	 * @return xAyB
	 */
	public static String guessResult(String answer, String number) {
		// 位置与数字均相同
		int rightA = 0;
		// 数字存在但位置不对
		int rightB = 0;

		// 计算“A”的个数
		for (int i = 0; i < 4; i++) {
			// 位置与数字均相同
			if (number.charAt(i) == answer.charAt(i)) {
				rightA++;
			}
		}

		// 计算“B”的个数
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				// 位置不相同
				if (i != j) {
					if (number.charAt(i) == answer.charAt(j)) {
						rightB++;
					}
				}
			}
		}
		return String.format("%sA%sB", rightA, rightB);
	}

	/**
	 * 获取图片保存地址
	 * 
	 * @param appRoot
	 *            项目下的webRoot路径
	 *            获取方式：req.getSession().getServletContext().getRealPath("");
	 * @param webRoot
	 *            图片存放的初始不完整路径 获取方式："upload/CodeImg/"
	 * @return strArr[] 下标0的为图片存放磁盘文件夹路径 下标1的为图片存放磁盘文件路径 下标2的为图片存放数据库的后部分路径(
	 *         <img/>可以直接使用的路径)
	 */
	public static String[] getFilePath(String appRoot, String webRoot) {
		Calendar now = Calendar.getInstance();
		int mou = now.get(Calendar.MONTH) + 1;// 月
		Date date = new Date();
		// [saveFilePath]服务器保存文件路径
		String saveFilePath = webRoot + now.get(Calendar.YEAR) + mou + now.get(Calendar.DAY_OF_MONTH);
		String path = appRoot;

		String imgLastName = date.getTime() + ".jpg";// 文件名称 xxx.jpg
		String imgDiskPath = saveFilePath;// 保存在的文件夹名称

		String imgDataBasePath = webRoot + now.get(Calendar.YEAR) + mou + now.get(Calendar.DAY_OF_MONTH) + "/"
				+ imgLastName;// 保存在数据库内的名称
		String diskPath = ToolsString.SlashConversion(path, 1) + "/" + imgDiskPath;
		diskPath = diskPath.replaceAll(":", ":/");
		// [diskPath] "d://qrcode.jpg"
		String[] strArr = new String[3];
		strArr[0] = diskPath;
		strArr[1] = diskPath + "/" + imgLastName;
		strArr[2] = imgDataBasePath;
		return strArr;
	}

	/**
	 * 去掉字符串的第一个‘[’ 和最后一个 ‘]’
	 * 
	 * @param str
	 * @return 返回去掉后的字符串
	 */
	public static String getStrRemoveBracket(String str) {
		if (str != null && !"".equals(str)) {
			StringBuffer sb = new StringBuffer(str);
			if ("[".equals(String.valueOf(sb.charAt(0)))) {
				sb = sb.deleteCharAt(0);
				if ("]".equals(String.valueOf(sb.charAt(sb.length() - 1)))) {
					sb = sb.deleteCharAt(sb.length() - 1);
				}
			}
			return sb.toString();
		}
		return str;
	}

	/**
	 * 生成订单编号:格式DAXI+年月日(该方法不包括后四位编号，由于编号是按照当天订单数量决定)
	 * 
	 * @param StringBuffer
	 *            sb
	 * @return StringBuffer sb
	 */
	public static StringBuffer getOrderNo(StringBuffer sb) {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int daye = now.get(Calendar.DAY_OF_MONTH);
		sb.append(year);
		if (month < 10) {
			sb.append(0);
		}
		sb.append(month);
		if (daye < 10) {
			sb.append(0);
		}
		sb.append(daye);
		return sb;
	}

	/**
	 * 比较两个字符串并返回相同部分
	 * 
	 * @param str1
	 *            eg:"刘烨,孙坚,王二小,蜘蛛侠,钢铁侠,毛剑卿";
	 * @param str2
	 *            eg:"王二小,李占军,刘胡兰,毛剑卿";
	 * @return 返回相同的字符串 王二小,毛剑卿
	 */
	public static String getIdenticalString(String str1, String str2) {
		String[] arr1 = str1.split(",");
		String[] arr2 = str2.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr2.length; i++) {
			for (int j = 0; j < arr1.length; j++) {
				if (arr1[j].equals(arr2[i])) {
					sb.append(arr1[j] + ",");
				}
			}
		}
		String str = sb.toString().substring(0, sb.toString().length());
		return str;
	}

 

	/**
	 * 清除重复字符串
	 * @param str
	 * @return
	 */
	public static String removeSameString(String str,String del) {
		Set<String> mlinkedset = new LinkedHashSet<String>();
		String[] strarray = str.split(del);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strarray.length; i++) {
			if (!mlinkedset.contains(strarray[i])) {
				mlinkedset.add(strarray[i]);
				sb.append(strarray[i] + " ");
			}
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	
	/**
	 * 获取某个范围的随机数
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static int getRandom(int min,int max) {
        Random random = new Random();
        int r = random.nextInt(max)%(max-min+1) + min;
		return r;
	}
}