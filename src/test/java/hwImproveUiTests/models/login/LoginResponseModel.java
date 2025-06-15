package hwImproveUiTests.models.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseModel {

    String userId,
            username,
            password,
            token,
            expires,
            created_date;

    @JsonProperty("isActive") // Явно указываем имя поля в JSON
    boolean isActive;

}