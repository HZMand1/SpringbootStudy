package io.renren.webreptile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexStringUtils {
    public static String regexString(String targetStr, String patternStr){
        Pattern pattern = Pattern.compile(patternStr);
        // 定义一个matcher用来做匹配
        Matcher matcher = pattern.matcher(targetStr);
        // 如果找到了
        if (matcher.find()) {
            // 打印出结果
            // System.out.println(matcher.group(1));
            return matcher.group(1);
        }
        return "";
    }
}
