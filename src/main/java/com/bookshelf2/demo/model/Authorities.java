package com.bookshelf2.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
@Entity
@Table(name = "authorities")
public class Authorities {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username")
        private String username;

        @Column(name = "authority")
        private String authority;

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        private User user;

        public Authorities(String username, String authority, User user) {
                this.username = username;
                this.authority = authority;
                this.user = user;
        }

        public Authorities(String username, String authority) {
                this.username = username;
                this.authority = authority;
        }

        public Authorities() {

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

        public String getAuthority() {
                return authority;
        }

        public void setAuthority(String authority) {
                this.authority = authority;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }
}
