package com.eversis.spaceagencydatahub.entity;

import com.eversis.spaceagencydatahub.dictionary.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Builder
@Data
public class Customer implements Serializable {

    @Id
    @NotNull(message = "Customer must have a unique login.")
    @Column(name = "customer_login")
    private String login;

    @NotNull(message = "Customer must have a password to log in.")
    @NonNull
    @Size(min = 5, max = 25, message = "Password must be within 5 and 25 signs.") // TODO: 2018-11-20 add encryption!
    private String password;

    @NotNull(message = "Customer must have a role assigned.")
    @NonNull
    private Role role;

}
