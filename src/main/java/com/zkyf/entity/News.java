package com.zkyf.entity;

import com.zkyf.entity.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 */
@Entity
@Table(name = "t_news")
public class News  implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
    @Column(length = 2000)
    private String title;
    @Column(length = 16777216)
    private String content;
    private String olddate;
    private int type;
    private int state;
    private String tag;
    @Column(length = 2000)
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOlddate() {
        return olddate;
    }

    public void setOlddate(String olddate) {
        this.olddate = olddate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
