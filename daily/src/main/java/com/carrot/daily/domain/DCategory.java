package com.carrot.daily.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dcategory_id")
    private Long id;

    @Column(name = "dcategory_name")
    private String name;

    @OneToMany(mappedBy = "dCategory")
    private List<Daily> dailies;
}
