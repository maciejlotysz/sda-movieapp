package com.maja.sdamovieapp.copy.entity;

import com.maja.sdamovieapp.copy.enums.DiscTypeEnum;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.order.entity.MovieCopyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "movie_copies")
public class MovieCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "copy_id", unique = true)
    private String copyId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "disc_type")
    private DiscTypeEnum discType;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "movieCopy")
    private Set<MovieCopyOrder> movieCopyOrders = new HashSet<>();
}
