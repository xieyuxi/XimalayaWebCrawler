package com.huawei.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.huawei.entity.Album;
import com.huawei.entity.Audio;
import com.huawei.util.AlbumUtil;
import com.huawei.util.Constants;
import com.huawei.util.JDBCUtil;
import com.huawei.util.StringUtil;

/**
 * 
 * @ClassName:  Main2   
 * @Description:  TODO(多线程插入测试) 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 下午6:04:14   
 *
 */
public class Main2 {
    
    // 插入数据库语句
    public static String insertAlbum = "insert t_album(id,name,tags,audioCount,pageCount) values(?,?,?,?,?)";
    public static String insertAudio = "insert t_audio(id,aid,title,url,audioDownloadUrl) values(?,?,?,?,?)";
    
    //删除语句
    public static String delAlbum = "delete from t_album";
    public static String delAudio = "delete from t_audio";
    
    public static void main(String[] args) {      
        
        //1.清空所有数据
        delAll();
        //2.插入专辑
        Integer aid = insertAlbum();
        //3.单线程插入音频
//        singleTest(aid,1,4);
        //4.多线程插入音频
        threadTest(aid,1,4);

    }
        
    /**
     * 
     * @Title: SingleTest   
     * @Description: TODO(单线程插入音频)
     * @Author: XIE.YUXI   
     * @param: @param aid
     * @param: @param i
     * @param: @param j      
     * @return: void      
     * @throws
     */
    private static void singleTest(Integer aid, int beginPage, int endPage) {
        long begin = System.currentTimeMillis();
        insertAudio(aid, beginPage, endPage);
        long end = System.currentTimeMillis();
        System.out.println("单线程执行时间为："+(end - begin)+"毫秒");
        
    }
    
    /**
     * 
     * @Title: threadTest   
     * @Description: TODO(多线程插入音频)
     * @Author: XIE.YUXI   
     * @param: @param aid
     * @param: @param i
     * @param: @param j      
     * @return: void      
     * @throws
     */
    private static void threadTest(Integer aid, int beginPage, int endPage) {
        ExecutorService executorService = Executors.newFixedThreadPool(endPage-beginPage+1);
        long begin = System.currentTimeMillis();
        for(int i = beginPage; i <= endPage; i++)
        {
            final int a = i;
            executorService.execute(new Runnable() {
                
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在被执行");
                    // 插入音频
                    insertAudio(aid,a,a);
                }
            });
        }
        // 线程池不再接收新任务
        executorService.shutdown();
        
        while(true) {
            if(executorService.isTerminated()) {
                long end = System.currentTimeMillis();
                System.out.println("多线程用时："+(end - begin)+"毫秒");
                break;
            }
        }
        
    }
    /**
     * 
     * @Title: insertAudio   
     * @Description: TODO(插入音频)
     * @Author: XIE.YUXI   
     * @param: @param aid
     * @param: @param beginPage
     * @param: @param endPage      
     * @return: void      
     * @throws
     */
    private static void insertAudio(Integer aid, int beginPage, int endPage) {
        List<Audio> audioList =  AlbumUtil.getAudioList(aid,1,1,Constants.ALBUM_HOME);
        for(Audio audio:audioList) {
            JDBCUtil.executeUpdate(insertAudio, audio.getId(), audio.getAid(), 
                    audio.getTitle(),audio.getUrl(),audio.getAudioDownloadUrl()); 
    
        }
    }
    /**
     * 
     * @Title: insertAlbum   
     * @Description: TODO(插入专辑)
     * @Author: XIE.YUXI   
     * @param: @return      
     * @return: Integer      
     * @throws
     */
    private static Integer insertAlbum() {
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
        
        JDBCUtil.executeUpdate(insertAlbum, aid,name,tags,audioCount,albumPageCount);
        
        return aid;
    }
    /**
     * 
     * @Title: delAll   
     * @Description: TODO(清空数据)
     * @Author: XIE.YUXI   
     * @param:       
     * @return: void      
     * @throws
     */
    private static void delAll() {
        JDBCUtil.executeUpdate(delAlbum);
        JDBCUtil.executeUpdate(delAudio);
    }


}
