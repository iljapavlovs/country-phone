package io.iljapavlovs.homework.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Objects;

@JsonDeserialize(builder = ApiErrorDto.Builder.class)
public class ApiErrorDto {

    private String message;
    private ApiErrorDto(Builder builder) {
        message = builder.message;
    }

    public String getMessage() {
        return message;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String message;
        public Builder() {
        }

        public Builder message(String message) {
            this.message = message;
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
        return Objects.equals(message, apiErrorDto.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
