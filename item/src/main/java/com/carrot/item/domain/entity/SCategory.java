package com.carrot.item.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "scategories")
public class SCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scategoryId;

    @Column(nullable = false)
    private String category;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items = new ArrayList<>();
}
