package com.beko.beans.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private String id;
    private Long version;
    private Date createDate;
    private Date lastUpdateDate;

    @Id
    @Column(name="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "last_update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @PrePersist
    public void preSave(){
        createDate = lastUpdateDate = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        lastUpdateDate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        if (!createDate.equals(that.createDate)) return false;
        if (!id.equals(that.id)) return false;
        if (!lastUpdateDate.equals(that.lastUpdateDate)) return false;
        if (!version.equals(that.version)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + version.hashCode();
        result = 31 * result + createDate.hashCode();
        result = 31 * result + lastUpdateDate.hashCode();
        return result;
    }

    public abstract void lazyInit();
}