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
}
