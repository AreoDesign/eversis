package com.eversis.spaceagencydatahub.entity;

import com.eversis.spaceagencydatahub.dictionary.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "customer")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @NotNull(message = "Customer must have a unique login.")
    private String login;

    @NotNull(message = "Customer must have a password to log in.")
    @NonNull
    @Size(min = 5, max = 25, message = "Password must be within 5 and 25 signs.") // TODO: 2018-11-20 add encryption!
    private String password;

    @NotNull(message = "Customer must have a role assigned.")
    @NonNull
    private Role role;

}
