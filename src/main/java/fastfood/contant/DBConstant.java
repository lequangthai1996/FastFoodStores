package fastfood.contant;

public class DBConstant {
    public enum ROLE {
        USER("USER"),
        SALE("SALE"),
        ADMIN("ADMIN");
        private  String name;

        ROLE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum LEVEL {
        PARENT(0),
        CHILD(1);

        private Integer value;

        LEVEL(Integer value) {
            this.value =value;
        }

        public Integer getValue() {
            return value;
        }
    }

    public enum OrderStatus {
        Cancel("0"),Pending("1"),Approved("2"), Finished("3");


        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        OrderStatus(String value) {
            this.value = value;
        }
    }
}
