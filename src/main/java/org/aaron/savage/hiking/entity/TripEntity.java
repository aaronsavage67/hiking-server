package org.aaron.savage.hiking.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "trip", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@Data
@Accessors(chain = true)
public class TripEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "organiser_id", unique = true, nullable = false)
    private long organiserId;

    @Column(name = "mountain_id", unique = true, nullable = false)
    private long mountainId;

    @Column(name = "date", length = 10)
    private String date;

    @Column(name = "description", length = 1000)
    private String description;
}
