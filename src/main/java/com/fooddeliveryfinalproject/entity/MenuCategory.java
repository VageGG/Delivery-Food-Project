package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String name;

    @OneToMany(mappedBy = "menuCategory")
    private List<MenuItem> items;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
