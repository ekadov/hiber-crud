package org.eugene.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@Accessors(chain = true)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "content", unique = false, nullable = false)
    private String content;

    @Column(name = "created", unique = false, nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", unique = false, nullable = true)
    private LocalDateTime updated;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    private Writer writerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", unique = false, nullable = false)
    private PostStatus status;

    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Label> labels;

}
