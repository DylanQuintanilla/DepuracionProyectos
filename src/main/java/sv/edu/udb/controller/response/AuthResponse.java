package sv.edu.udb.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldNameConstants
public class AuthResponse {

    private String token;

}
