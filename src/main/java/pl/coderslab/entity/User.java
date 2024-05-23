    package pl.coderslab.entity;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotNull;

    import java.util.Set;

    @Entity
    @Table(name="users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        @NotNull
        private String username;

        @Column(unique = true)
        @Email
        @NotNull
        private String email;

        @NotNull
        private String password;

        @NotNull
        private boolean enabled;

        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @NotNull
        private Set<Authority> authorities;

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

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Set<Authority> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Set<Authority> authorities) {
            this.authorities = authorities;
        }
    }
