package io.iljapavlovs.countryphone.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;

@JsonDeserialize(builder = ApiErrorDto.Builder.class)
public class ApiErrorDto {

    private String errorMessage;
    private ApiErrorDto(Builder builder) {
        errorMessage = builder.errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String errorMessage;
        public Builder() {
        }

        public Builder message(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ApiErrorDto build() {
            return new ApiErrorDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiErrorDto apiErrorDto = (ApiErrorDto) o;
        return Objects.equals(errorMessage, apiErrorDto.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage);
    }
}
