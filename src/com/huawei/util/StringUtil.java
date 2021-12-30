package com.huawei.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @ClassName:  StringUtil   
 * @Description:  TODO(字符串工具类) 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 上午10:51:56   
 *
 */
public class StringUtil {
    
    /**
     * 
     * @Title: subCount   
     * @Description: TODO(获取专辑音频数)
     * @Author: XIE.YUXI   
     * @param: @param str
     * @param: @return      
     * @return: int      
     * @throws
     */
    public static int subCount(String str) {
        if(str!=null&&str.trim().length()!=0) {
            int index = str.indexOf("（");
            String countStr = "";
            if(index != -1)
            {
                countStr = str.substring(index+1,str.length() - 1);
            }
            return Integer.parseInt(countStr);
        }else {
            return 0;
        }   
    }
    
    /**
     * 
     * @Title: subId   
     * @Description: TODO(获取音频和专辑id)
     * @Author: XIE.YUXI   
     * @param: @param url
     * @param: @return      
     * @return: String      
     * @throws
     */
    public static String subId(String url) {
        if(url!=null&&url.trim().length()!=0) {
            int index = url.lastIndexOf("/");
            String id = url.substring(index+1);
            return id;
        }else {
            return "";
        }     
    }
}
