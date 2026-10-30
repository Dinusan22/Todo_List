package DS.TECH.HelloWorld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Audited;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="UserTable")
public class User {
        @Id
        @GeneratedValue
        Long id;

        String fullName;
        @Email
        String email;
        String password;


    }
