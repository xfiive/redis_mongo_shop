package test.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String surname;

    @Field
    private String email;

    @Field
    private String phone;
}




