package com.bookshelf2.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")


@NamedQueries({
        @NamedQuery(name = User.USER_REPORT, query = User.USER_REPORT_JPQL),
        @NamedQuery(name = User.USER_DELETEAUT, query = User.USER_DELETEAUT_JPQL),
        @NamedQuery(name = User.USER_REST, query = User.USER_REST_JPQL),
        //@NamedQuery(name = User.USER_DELETETOKEN, query = User.USER_DELETETOKEN_JPQL)
})
public class User {

    public static final String USER_REPORT = "User.userReport";
    public static final String USER_REPORT_JPQL = "select u.nickname from User u where u.username = ?1";

    public static final String USER_DELETEAUT= "User.deleteAut";
    public static final String USER_DELETEAUT_JPQL = "delete from Authorities as a where a.id = ?1";

    public static final String USER_REST = "User.findAllNotAdmin";
    public static final String USER_REST_JPQL = "select u.username from User u where u.id not in (select r.user.id from Authorities as r where r.authority = 'ROLE_ADMIN')";

    //public static final String USER_DELETETOKEN = "User.findByUsername";
    //public static final String USER_DELETETOKEN_JPQL = "select v.username from VerificationToken v where v. = ?1";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "username cannot be empty.")
    @Column(unique = true, name = "username")
    private String username;

    @NotEmpty(message = "password cannot be empty.")
    @Column(name = "password")
    private String password;

    private Boolean isUsing2FA = false;

    private String secret;

    private String company;

    @Transient
    private String matchingPassword;

    @Column(unique = true, name = "nickname")
    private String nickname;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true,fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    private List<Authorities> authorities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true,fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    private List<FileInfo> fileInfoList = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "userToken", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PersistentLogin persistentLogin;

    public User() {
        super();
        this.secret = Base32.random();
    }

    public void addAuthorities(Authorities authority){
        authorities.add(authority);
        authority.setUser(this);
    }

    public void addFile(FileInfo fileInfo){
        fileInfoList.add(fileInfo);
        fileInfo.setUser(this);
    }

    public User(Long id, String username, String password, Boolean isUsing2FA, String secret, String matchingPassword, String nickname, Boolean enabled, List<Authorities> authorities, PersistentLogin persistentLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isUsing2FA = isUsing2FA;
        this.secret = secret;
        this.matchingPassword = matchingPassword;
        this.nickname = nickname;
        this.enabled = enabled;
        this.authorities = authorities;
        this.persistentLogin = persistentLogin;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public String getNickname() {
        return nickname;
    }

    /*public List<PersistentLogin> getPersistentLogins() {
        return persistentLogins;
    }

    public void setPersistentLogins(List<PersistentLogin> persistentLogins) {
        this.persistentLogins = persistentLogins;
    }*/

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Boolean getUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(Boolean using2FA) {
        isUsing2FA = using2FA;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
