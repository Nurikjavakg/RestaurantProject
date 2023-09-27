package peaksoft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String image;
    @Positive
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;
    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Restaurant restaurant;
    @ManyToMany(mappedBy = "menus",cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Cheque> chequeList;
    @OneToOne
    private StopList stopList;
    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private SubCategory subCategory;


}