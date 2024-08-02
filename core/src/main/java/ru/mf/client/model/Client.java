package ru.mf.client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.mf.user.model.User;

import java.time.LocalDateTime;


@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orgName;
    private Long inn;
    private String tenant;
    private Long personalAccount;
    private Long msisdn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private User appUser;
    private LocalDateTime createdDate;
}
