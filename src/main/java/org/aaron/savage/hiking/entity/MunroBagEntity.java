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

    @Column(name = "username", nullable = false, length = 25)
    private String username;

    @Column(name = "mountain_id", nullable = false)
    private long mountainId;

    @Column(name = "mountain_name", nullable = false)
    private String mountainName;

    @Column(name = "date", length = 10)
    private String date;

    @Column(name = "rating", length = 10)
    private String rating;
}