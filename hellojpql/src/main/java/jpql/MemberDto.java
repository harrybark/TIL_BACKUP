package jpql;

public class MemberDto {

    private String useraname;
    private int age;

    public MemberDto() {
    }

    public MemberDto(String useraname, int age) {
        this.useraname = useraname;
        this.age = age;
    }

    public String getUseraname() {
        return useraname;
    }

    public void setUseraname(String useraname) {
        this.useraname = useraname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
