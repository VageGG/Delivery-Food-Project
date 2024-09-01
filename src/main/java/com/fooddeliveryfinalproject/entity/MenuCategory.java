package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MenuCategory {
    @Id
    @GeneratedValue
    private long categoryId;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<MenuItem> items;
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
