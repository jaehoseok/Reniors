package domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Company {

    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;
}
