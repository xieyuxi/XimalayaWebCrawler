package com.huawei.entity;

/**
 * 
 * @ClassName:  Album   
 * @Description:  专辑实体类 
 * @author: XIE.YUXI 
 * @date:   2021年12月30日 上午9:55:28   
 *
 */
public class Album {
    
    private Integer id;//专辑id
    private String name;//专辑名称
    private String tags;//专辑标签
    private Integer audioCount;//专辑声音条数
    private Integer pageCount;//专辑总页数
    
    public Album() {
        
    }
    
    public Album(Integer id, String name, String tags, Integer audioCount, Integer pageCount) {
        super();
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.audioCount = audioCount;
        this.pageCount = pageCount;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public Integer getAudioCount() {
        return audioCount;
    }
    public void setAudioCount(Integer audioCount) {
        this.audioCount = audioCount;
    }
    public Integer getPageCount() {
        return pageCount;
    }
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
    @Override
    public String toString() {
        return "Album [id=" + id + ", name=" + name + ", tags=" + tags + ", audioCount=" + audioCount + ", pageCount="
                + pageCount + "]";
    }
    
    
}
