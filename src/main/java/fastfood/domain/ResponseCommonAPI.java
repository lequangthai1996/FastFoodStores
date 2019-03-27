package fastfood.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponseCommonAPI implements Serializable {
    private Object data;
    private Object error;
    private String message;
    private Integer totalRow;
    private Boolean success;

    public ResponseCommonAPI() {
    }

    public ResponseCommonAPI(Object data, Object error, String message, Integer totalRow, Boolean success) {
        this.data = data;
        this.error = error;
        this.message = message;
        this.totalRow = totalRow;
        this.success = success;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class Builder {
        private Object data;
        private Integer totalRow;
        private Object error;
        private String message;
        private Boolean success;

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder totalRow(Integer totalRowa) {
            this.totalRow = totalRow;
            return this;
        }

        public Builder error(Object error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        public ResponseCommonAPI build() {
            return new ResponseCommonAPI(data, error, message, totalRow, success);
        }
    }
}
