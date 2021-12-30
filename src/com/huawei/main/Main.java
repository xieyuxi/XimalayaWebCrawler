package com.huawei.main;

import java.util.ArrayList;
import java.util.List;

import com.huawei.entity.Album;
import com.huawei.entity.Audio;
import com.huawei.util.AlbumUtil;
import com.huawei.util.Constants;
import com.huawei.util.JDBCUtil;
import com.huawei.util.StringUtil;

/**
 * 
 * @ClassName:  Main   
 * @Description:  TODO(正常执行插入) 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 下午6:03:55   
 *
 */
public class Main {
    
    // 插入数据库语句
    public static String insertAlbum = "insert t_album(id,name,tags,audioCount,pageCount) values(?,?,?,?,?)";
    public static String insertAudio = "insert t_audio(id,aid,title,url,audioDownloadUrl) values(?,?,?,?,?)";
    public static void main(String[] args) {      
                
        //1.获取专辑名称
        String name = AlbumUtil.getName(Constants.ALBUM_HOME);

        
        //2.获取专辑标签
        String tags = AlbumUtil.getTags(Constants.ALBUM_HOME);

        
        //3.获取音频数量
        int audioCount = AlbumUtil.getAudioCount(Constants.ALBUM_HOME);
       
        //4.获取专辑页数
        int albumPageCount = AlbumUtil.getAlbumPageCount(audioCount);  
        
        //5.获取专辑id
        Integer aid = Integer.parseInt(StringUtil.subId(Constants.ALBUM_HOME));
        
//        Album album = new Album();
//        album.setName(name);
//        album.setTags(tags);
//        album.setAudioCount(audioCount);
//        album.setPageCount(albumPageCount);
//        album.setId(aid);
        
        JDBCUtil.executeUpdate(insertAlbum, aid,name,tags,audioCount,albumPageCount);
        
        //5.获取音频列表
        List<Audio> audioList =  AlbumUtil.getAudioList(aid,1,1,Constants.ALBUM_HOME);
        for(Audio audio:audioList) {
            JDBCUtil.executeUpdate(insertAudio, audio.getId(), audio.getAid(), 
                    audio.getTitle(),audio.getUrl(),audio.getAudioDownloadUrl());
        }
        

    }


}
