package com.huawei.entity;

/**
 * 
 * @ClassName:  Audio   
 * @Description:  音频实体类 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 上午9:56:55   
 *
 */
public class Audio {
    private Integer id;//音频id
    private Integer aid;//音频对应的专辑id
    private String title;//音频标题
    private String url;//音频链接
    private String audioDownloadUrl;
    
    public Audio() {
        
    }

    public Audio(Integer id, Integer aid, String title, String url, String audioDownloadUrl) {
        super();
        this.id = id;
        this.aid = aid;
        this.title = title;
        this.url = url;
        this.audioDownloadUrl = audioDownloadUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

    public String getAudioDownloadUrl() {
        return audioDownloadUrl;
    }

    public void setAudioDownloadUrl(String audioDownloadUrl) {
        this.audioDownloadUrl = audioDownloadUrl;
    }

    @Override
    public String toString() {
        return "Audio [id=" + id + ", aid=" + aid + ", title=" + title + ", url=" + url + ", audioDownloadUrl="
                + audioDownloadUrl + "]";
    }
    
    
    
    

}
