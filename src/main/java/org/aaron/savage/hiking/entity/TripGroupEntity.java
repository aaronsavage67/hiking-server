package org.aaron.savage.hiking.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "trip_group", uniqueConstraints = {@UniqueConstraint(columnNames = "trip_id")})
@Data
@Accessors(chain = true)
public class TripGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "trip_id", unique = true, nullable = false)
    private long tripId;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "status", nullable = false)
    private String status;
}
