package test.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "deliveries")
@NoArgsConstructor
@AllArgsConstructor
public class Delivery implements Serializable {

    @Id
    private String id;

    @Field
    private String address;

    @Field
    private String deliveryDate;

    @Field
    private String deliveryMethod;
}
