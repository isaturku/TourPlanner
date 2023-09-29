package persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tours")
@Data
@NoArgsConstructor
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    String description;
    @Column(name = "start_location")
    String startLocation;
    @Column(name = "end_location")
    String endLocation;
    @Column(name = "transport_type")
    String transportType;
    String distance;
    @Column(name = "estimated_time")
    String estimatedTime;
    @Column(name = "route_info")
    String routeInfo;
    Integer ratings;
    @OneToMany(fetch = FetchType.EAGER)
            @JoinColumn(name = "tour_id")
    List<TourLog> logs;
    public Tour(String name){
        this.name = name;
    }
}
