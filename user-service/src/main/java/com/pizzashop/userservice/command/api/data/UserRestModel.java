package com.pizzashop.userservice.command.api.data;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRestModel {

    @NotNull
    private String login;
    @NotNull
    @Length(min = 8, max = 30)
    private String password;
    @NotNull
    private long age;
}
