package peaksoft.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cheque")
@AllArgsConstructor
@NoArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private BigDecimal priceAverage;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<MenuItem> menus = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();

    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt = ZonedDateTime.now();
    }

}