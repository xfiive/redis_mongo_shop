package test.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "deliveries")
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    private String id;

    @Field
    private String address;

    @Field
    private String deliveryDate;

    @Field
    private String deliveryMethod;
}
