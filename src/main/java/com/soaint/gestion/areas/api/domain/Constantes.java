package com.soaint.gestion.areas.api.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "Constantes"
//    uniqueConstraints = @UniqueConstraint(
//        name = "ux_Constantes_Codigo",
//        columnNames = "Codigo")
)
@Data
@NoArgsConstructor
public class Constantes implements Serializable {

    private static final long serialVersionUID = 1L;   // ← obligatorio

    @Id
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ParentId", length = 50)
    private String parentId;

    @Column(name = "Codigo", length = 50, nullable = false)
    private String codigo;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", length = 250)
    private String descripcion;

    @Column(name = "Activo")
    private Integer activo;

    /* ----- si no necesitas navegar la jerarquía, elimina estas dos ----- */
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentId",
                referencedColumnName = "Codigo",
                insertable = false, updatable = false,
                foreignKey = @ForeignKey(name = "fk_Constantes_Padre"))
    private Constantes padre;

    @OneToMany(mappedBy = "padre")
    private Set<Constantes> hijos;
    */
}
