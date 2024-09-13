package com.smartShoppe.login.Entity;

import com.smartShoppe.login.Enum.Role;
import com.smartShoppe.login.Util.ValidationError;
import com.smartShoppe.login.Util.ValidationResult;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_details")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name  = "id")
    private Long uid;

    @NotBlank(message = "First name must not be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotBlank(message = "Last name must not be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Invalid email address")
    @Column(name = "email", unique = true)
    private String email;

    @Pattern(regexp = "^[0-9]*$", message = "Invalid mobile number format")
    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @NotNull(message = "Customer type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Role role;

    @NotBlank(message = "Password must not be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_timestamp")
    private Timestamp createdTimestamp;

    @UpdateTimestamp
    @Column(name = "modified_timestamp")
    private Timestamp modifiedTimestamp;

    @AssertTrue(message = "Either email or mobile number must be provided")
    private boolean isEmailOrMobileNumberProvided() {
        return (email != null && !email.isEmpty()) || (mobileNumber != null && !mobileNumber.isEmpty());
    }

    @PrePersist
    @PreUpdate
    public void prePersistOrUpdate() {
        if (email != null && email.trim().isEmpty()) {
            email = null;
        }
        if (mobileNumber != null && mobileNumber.trim().isEmpty()) {
            mobileNumber = null;
        }
    }

    public ValidationResult<UserEntity> validate(){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(this);
        if (!violations.isEmpty()){
            List<ValidationError> result = violations.stream()
                    .map(violation ->
                            new ValidationError(violation.getPropertyPath().toString(),
                                    violation.getMessage())).collect(Collectors.toList());
            return ValidationResult.error(result);
        }
        return ValidationResult.success(this);
    }

}
