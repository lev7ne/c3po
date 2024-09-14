package ru.mf.company.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.mf.user.model.User;

import java.time.LocalDate;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company")
@SequenceGenerator(name = "company_id_seq")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orgName;

    private Long inn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private User appUser;

    @CreatedDate
    private LocalDate createdDate;

}
