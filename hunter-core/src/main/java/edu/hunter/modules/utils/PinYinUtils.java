/**
 * @(#)PinYinUtils.java 2013年12月26日
 *
 * Copyright 2008-2013 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Woo Cupid
 * @date 2013年12月26日
 * @version $Revision$
 */
public class PinYinUtils {

	private static HanyuPinyinOutputFormat of = new HanyuPinyinOutputFormat();
	static {
		of.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		of.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		of.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	/**
	 * convert every word of the string to possible list
	 * 
	 * 123a陈真a -> [[123a], [chen, c], [zhen, z], [a]]
	 * 
	 * @param source
	 * @return
	 */
	public static ArrayList<Collection<String>> convert(String source) {

		ArrayList<Collection<String>> result = Lists.newArrayList();
		ArrayList<String> list = splitCn(source);
		for (String c : list) {
			if ((c.length() == 1) && c.matches("[\\u4E00-\\u9FA5]")) {
				Set<String> toPY = Sets.newHashSet(c);
				try {
					String[] asPinyin = PinyinHelper.toHanyuPinyinStringArray(c.charAt(0), of);
					for (String pinyin : asPinyin) {
						toPY.add(pinyin);
						toPY.add(String.valueOf(pinyin.charAt(0)));
					}
					result.add(toPY);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					// ignore
				}
			} else {
				result.add(Sets.newHashSet(c));
			}
		}

		return result;
	}

	public static ArrayList<String> splitCn(String target) {
		ArrayList<String> list = Lists.newArrayList();
		Pattern p = Pattern.compile("([\\u4e00-\\u9fa5]?)([^\\u4e00-\\u9fa5]+)?");
		Matcher matcher = p.matcher(target);
		while (matcher.find()) {
			if (StringUtils.isNotBlank(matcher.group(1))) {
				list.add(matcher.group(1));
			}
			if (StringUtils.isNotBlank(matcher.group(2))) {
				list.add(matcher.group(2));
			}
		}
		return list;
	}

	public static ArrayList<String> combine(String target) {
		ArrayList<Collection<String>> converted = convert(target);
		ArrayList<String> combined = combine(converted);
		// combined.add(0, target);
		return combined;
	}

	/**
	 * @param converted
	 * @return
	 */
	private static ArrayList<String> combine(ArrayList<Collection<String>> target) {

		ArrayList<String> result = Lists.newArrayList();
		if (target != null) {
			if (target.size() == 1) {
				Collection<String> removed = target.remove(0);
				for (String word : removed) {
					result.add(word);
				}
			} else if (target.size() > 1) {
				Collection<String> item0 = target.remove(0);
				ArrayList<String> item1 = combine(target);
				for (String m : item0) {
					for (String n : item1) {
						result.add(m + n);
					}
				}
			}
		}

		return result;
	}
}
