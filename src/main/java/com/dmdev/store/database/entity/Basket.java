package com.dmdev.store.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Basket implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technic_id")
    private Technic technic;

    public void setUser(User user){
        this.user = user;
        this.user.getBaskets().add(this);
    }

    public void setTechnic(Technic technic){
        this.technic = technic;
        this.technic.getBaskets().add(this);
    }
}
