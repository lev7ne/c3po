package ru.mf.client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.mf.user.model.User;

import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "client_mo")
@Getter
@Setter
public class ClientMo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;
    @Column(name = "org_name")
    private String orgName;
    private long inn;
    private String tenant;
    private int personalAccount;
    private long msisdn;
    private boolean vip;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccc_id", referencedColumnName = "id")
    private User ccc;
    @CreatedDate
    private LocalDateTime createdDate;
}