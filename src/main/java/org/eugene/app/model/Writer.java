package org.eugene.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "writers")
@Data
@Accessors(chain = true)
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "first_name", unique = false, nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = false, nullable = false)
    private String lastName;

    @ToString.Exclude
    @OneToMany(mappedBy = "writerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Post> posts;
}
