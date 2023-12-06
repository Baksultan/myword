package com.example.myword.model.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_texts")
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String s3Key;

    @Column(nullable = false)
    private String URL;

    @Column(nullable = false)
    private Long views;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @PrePersist
    public void generateUniqueUrl() {
        String uniqueUrl = UUID.randomUUID().toString();
        this.URL = uniqueUrl;
    }

}
