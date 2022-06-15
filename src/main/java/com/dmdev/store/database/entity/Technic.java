package com.dmdev.store.database.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "baskets")
@Builder
@Entity
public class Technic implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    private Integer price;

    private Integer amount;

    private String image;

    @Builder.Default
    @OneToMany(mappedBy = "technic")
    private List<Basket> baskets = new ArrayList<>();
}
