package user.dto;

public class UserDto extends BaseUserDto {
    public int age;
    public CountUser countUser = new CountUser();

    public String getUserId() {
        return "super.getUserId();";
    }

    public String getClassName() {
        return super.className;
    }

    public static class CountUser{
        int userCount;
        public void doSth() {

        }

    }

}
