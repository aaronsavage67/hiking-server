package org.aaron.savage.hiking.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "mountain", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@Data
@Accessors(chain = true)
public class MountainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "height", nullable = false, length = 10)
    private String height;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "region", length = 100)
    private String region;

    @Column(name = "coords", length = 100)
    private String coords;

    @Column(name = "route_image", length = 250)
    private String routeImage;
}
