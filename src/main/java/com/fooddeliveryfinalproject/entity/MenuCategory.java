package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu_categories")
public class MenuCategory {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private long categoryId;

    private String name;

    @OneToMany(mappedBy = "menuCategory")
    private List<MenuItem> items;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
