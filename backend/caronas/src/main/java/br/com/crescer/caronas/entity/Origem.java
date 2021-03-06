package br.com.crescer.caronas.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author alexia.pereira
 */
@Entity
@Table(name = "ORIGEM")
public class Origem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ORIGEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORIGEM")
    @SequenceGenerator(
            name = "SEQ_ORIGEM",
            sequenceName = "SEQ_ORIGEM",
            allocationSize = 1
    )
    private Long idOrigem;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "ENDERECO")
    private String endereco;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LATITUDE")
    private BigDecimal latitude;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LONGITUDE")
    private BigDecimal longitude;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "origem")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Rotina rotina;

    public Origem() {
    }

    public Origem(Long idOrigem) {
        this.idOrigem = idOrigem;
    }

    public Origem(Long idOrigem, String endereco, BigDecimal latitude, BigDecimal longitude) {
        this.idOrigem = idOrigem;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Origem(String endereco, BigDecimal latitude, BigDecimal longitude) {
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(Long idOrigem) {
        this.idOrigem = idOrigem;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

}
