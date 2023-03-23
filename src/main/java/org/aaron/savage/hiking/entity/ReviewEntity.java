package org.aaron.savage.hiking.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "review", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@Data
@Accessors(chain = true)
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "review_date", nullable = false)
    private String reviewDate;

    @Column(name = "mountain_id", nullable = false)
    private long mountainId;

    @Column(name = "mountain_name", nullable = false)
    private String mountainName;

    @Column(name = "walk_date", nullable = false)
    private String walkDate;

    @Column(name = "rating", nullable = false)
    private String rating;

    @Column(name = "comment", length = 200, nullable = false)
    private String comment;
}