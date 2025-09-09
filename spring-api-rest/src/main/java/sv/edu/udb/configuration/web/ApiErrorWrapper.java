package sv.edu.udb.configuration.web;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "Contenedor de errores de la API")
public class ApiErrorWrapper {

    @ArraySchema(schema = @Schema(implementation = ApiError.class))
    private final List<ApiError> errors = new ArrayList<>();

    public final void addApiError(final ApiError error) {
        errors.add(error);
    }

    public final void addFieldError(final String type, final String title, final String source,
                                    final String description) {
        final ApiError error = ApiError
                .builder()
                .status(400)
                .type(type)
                .title(title)
                .description(description)
                .source(source)
                .build();
        errors.add(error);
    }
}
