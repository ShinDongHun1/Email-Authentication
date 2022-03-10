package com.example.userservice.domain;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ShinD on 2022-03-07.
 */

/**
 * @PrePersist를 사용한다면 Auditing은 필요없다.
 * 그리고 Auditing만으로는 형식을 변경할 수 없다!!!
 */
@MappedSuperclass
@Getter
public class BaseTimeEntity {


    @Column(updatable = false, nullable = false)
    private String createdDate;

    @Column(updatable = true, nullable = false)
    private String modifiedDate;


    @PrePersist
    public void onPrePersist(){
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
