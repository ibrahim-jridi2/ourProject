    package com.campers.now.models;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import lombok.*;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;

    import javax.persistence.*;
    import java.io.Serializable;
    import java.time.Instant;
    import java.util.Date;
    import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int numberReserved;
    private int campingPeriod;
    private boolean isActive;
    private boolean isConfirmed;
    private float totalAmount;
    @Temporal(TemporalType.DATE)
    private Date dateStart;
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant modifiedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private CampingCenter campingCenter;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"campingCenter"})
    private List<Activity> activities;
}