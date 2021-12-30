package com.huawei.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.huawei.entity.Audio;

/**
 * 
 * @ClassName:  AlbumUtil   
 * @Description:  专辑工具类 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 上午10:02:21   
 *
 */
public class AlbumUtil {

    /**
     * 
     * @Title: getName   
     * @Description: TODO(获取专辑名称)
     * @Author: XIE.YUXI   
     * @param: @param albumHome
     * @param: @return      
     * @return: String      
     * @throws
     */
    public static String getName(String albumHome) {
        
        Document doc = null;
        //1.根据网址获取网页元素
        try {
            doc = Jsoup.connect(albumHome).get();
        } catch (IOException e) {
            System.out.println("获取网页内容失败");
            e.printStackTrace();
        }
        
        //2.解析网页内容并获专辑名称
        Element nameElement = doc.select("h1.title.k_Z").first();
        String name = nameElement.text();
        
        return name;
    }
    
    /**
     * 
     * @Title: getTags   
     * @Description: TODO(获取专辑标签)
     * @Author: XIE.YUXI   
     * @param: @return      
     * @return: String      
     * @throws
     */
    public static String getTags(String albumHome) {
        Document doc = null;
        StringBuilder s = new StringBuilder();
        
        //1.根据网址获取网页元素
        try {
            doc = Jsoup.connect(albumHome).get();
        } catch (IOException e) {
            System.out.println("获取网页内容失败");
            e.printStackTrace();
        }
        
        //2.解析网页内容并获取专辑标签
        Elements tagsElements = doc.select("span.xui-tag-text");
        for(Element tag:tagsElements) {
            s.append(tag.text()+";");
        }
        
        return s.toString();
        
    }
    
    /**
     * 
     * @Title: getAudioCount   
     * @Description: TODO(获取音频条数)
     * @Author: XIE.YUXI   
     * @param: @param albumHome
     * @param: @return      
     * @return: int      
     * @throws
     */
    public static int getAudioCount(String albumHome) {
        
        Document doc = null;
        //1.根据网址获取网页元素
        try {
            doc = Jsoup.connect(albumHome).get();
        } catch (IOException e) {
            System.out.println("获取网页内容失败");
            e.printStackTrace();
        }
        
        //2.解析网页内容并获取音频数量
        Element countElement = doc.select("span[class = _MQ]").first();
        String audioCount = countElement.text();
        return StringUtil.subCount(audioCount);
        
    }
    
    /**
     * 
     * @Title: getAlbumPageCount   
     * @Description: TODO(获取音频页数)
     * @Author: XIE.YUXI   
     * @param: @return      
     * @return: int      
     * @throws
     */
    public static int getAlbumPageCount(int audioCount) {
        //向上取整，获得页面数量
        int pageCount = (int)Math.ceil(1.0*audioCount/Constants.PAGE_SIZE);
        return pageCount;
    }
    
    /**
     * 
     * @Title: getAudioList   
     * @Description: TODO(获取某专辑下的音频列表)
     * @Author: XIE.YUXI   
     * @param: @param aid
     * @param: @param beginPage
     * @param: @param endPage
     * @param: @return      
     * @return: List<Audio>      
     * @throws
     */
    public static List<Audio> getAudioList(int aid, int beginPage, int endPage, String albumHome){
        
        List<Audio> audioList = new ArrayList<>((endPage-beginPage+1)*Constants.PAGE_SIZE);
        
        for(int i = beginPage; i<=endPage; i++) {
            //1.拼接URL
            String audioListUrl = albumHome + Constants.AUDIO_LIST_URL + i;
            
            //2.根据网址获取网页元素
            Document doc = null;
            try {
                doc = Jsoup.connect(albumHome).get();
            } catch (IOException e) {
                System.out.println("获取网页内容失败");
                e.printStackTrace();
            }
            
            //3.查找元素
            Elements audioListElements = doc.select("div.text.Mi_>a");
            for(Element audioElement:audioListElements) {
                
                Audio audio = new Audio();
                
                // 音频标题
                String title = audioElement.text();
                // 音频网址
                String url = albumHome + audioElement.attr("href");
                // 音频ID
                String audioId = StringUtil.subId(audioElement.attr("href"));
                // 音频下载地址
                String audioDownloadUrl = getAudioDownloadUrl(Constants.AUDIO_PATH + audioId);
                
                audio.setAid(aid);
                audio.setTitle(title);
                audio.setUrl(url);
                audio.setId(Integer.parseInt(StringUtil.subId(audioElement.attr("href"))));
                audio.setAudioDownloadUrl(audioDownloadUrl);
                
                audioList.add(audio);
            }            
        }
        
        return audioList;    
    }
    
    /**
     * 
     * @Title: getAudioDownloadUrl   
     * @Description: TODO(获取音频下载链接)
     * @Author: XIE.YUXI   
     * @param: @param url
     * @param: @return      
     * @return: String      
     * @throws
     */
    public static String getAudioDownloadUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).ignoreContentType(true).get();
        } catch (IOException e) {
            System.out.println("获取Json文件内容错误");
            e.printStackTrace();
        }
        Element element = doc.body();
        //拿到页面的json字符串，并转换成map对象
        Map jsonMap = JsonUtil.jsonToMap(element.text());
        
        Map datamap = (Map) jsonMap.get("data");
        JSONArray array = (JSONArray) datamap.get("tracksForAudioPlay"); 
        Map map1 = JsonUtil.jsonToMap(array.get(0).toString());

        return map1.get("src").toString();
    }
}
