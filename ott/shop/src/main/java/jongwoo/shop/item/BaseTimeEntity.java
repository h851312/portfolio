package jongwoo.shop.item;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

//@MappedSuperclass
//@Getter
//@EntityListeners(AuditingEntityListener.class)
//public class BaseTimeEntity {
//
//    @CreatedDate
//    private LocalDate createdDate;
//
//    @LastModifiedDate
//    private LocalDate modifiedDate;
//}