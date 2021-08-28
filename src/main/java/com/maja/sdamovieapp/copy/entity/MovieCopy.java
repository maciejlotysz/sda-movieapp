package com.maja.sdamovieapp.copy.entity;

import com.maja.sdamovieapp.copy.enums.CopyStatusEnum;
import com.maja.sdamovieapp.copy.enums.DiscTypeEnum;
import com.maja.sdamovieapp.movie.entity.Movie;
import com.maja.sdamovieapp.order.entity.MovieCopyOrder;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private UUID copyId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CopyStatusEnum status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "disc_type")
    private DiscTypeEnum discType;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "movieCopy")
    private List<MovieCopyOrder> movieCopyOrders = new ArrayList<>();
}
