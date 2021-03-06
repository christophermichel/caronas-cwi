package br.com.crescer.caronas.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author alexia.pereira
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(
            name = "SEQ_USUARIO",
            sequenceName = "SEQ_USUARIO",
            allocationSize = 1
    )
    private Long idUsuario;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NOME")
    private String nome;

    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Size(min = 1, max = 200)
    @Column(name = "EMAIL")
    private String email;

    @Size(max = 25)
    @Column(name = "GENERO")
    private String genero;

    @Column(name = "ID_AUTORIZACAO")
    @Size(min = 1, max = 200)
    @Basic(optional = false)
    @NotNull
    private String idAutorizacao;

    @Column(name = "SENHA")
    @Size(min = 1, max = 200)
    @Basic(optional = false)
    @NotNull
    private String senha;

    @Column(name = "URL_FOTO")
    @Size(min = 1, max = 500)
    private String urlFoto;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "usuario")
    private List<Notificacao> notificacaoList;

    public Usuario() {
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(String nome, String email, String genero, String idAutorizacao, String senha) {
        this.nome = nome;
        this.email = email;
        this.genero = genero;
        this.idAutorizacao = idAutorizacao;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String genero, String idAutorizacao, String senha, List<Notificacao> notificacaoList) {
        this.nome = nome;
        this.email = email;
        this.genero = genero;
        this.idAutorizacao = idAutorizacao;
        this.senha = senha;
        this.notificacaoList = notificacaoList;
    }

    public Usuario(String nome, String email, String genero, String idAutorizacao, String senha, String urlFoto, List<Notificacao> notificacaoList) {
        this.nome = nome;
        this.email = email;
        this.genero = genero;
        this.idAutorizacao = idAutorizacao;
        this.senha = senha;
        this.urlFoto = urlFoto;
        this.notificacaoList = notificacaoList;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdAutorizacao() {
        return idAutorizacao;
    }

    public void setIdAutorizacao(String idAutorizacao) {
        this.idAutorizacao = idAutorizacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void criptografarSenha() {
        this.senha = new BCryptPasswordEncoder().encode(this.senha);
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public List<Notificacao> getNotificacaoList() {
        return notificacaoList;
    }

    public void setNotificacaoList(List<Notificacao> notificacaoList) {
        this.notificacaoList = notificacaoList;
    }

}
