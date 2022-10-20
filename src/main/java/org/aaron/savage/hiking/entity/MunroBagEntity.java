package org.aaron.savage.hiking.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "munro_bag", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@Data
@Accessors(chain = true)
public class MunroBagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "mountain_id", unique = true, nullable = false, length = 25)
    private long mountainId;

    @Column(name = "date", length = 10)
    private String date;

    @Column(name = "rating", length = 10)
    private String rating;

    @Column(name = "comments", length = 100)
    private String comments;
}