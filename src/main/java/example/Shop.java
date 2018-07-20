package example;

import java.util.List;

public class Shop {
    private final String id;
    private final String name;
    private final List<String> departmentIds;



    private final List<String> customerIds;


    public Shop(String id, String name, List<String> departmentIds, List<String> customerIds) {
        this.id = id;
        this.name = name;
        this.departmentIds = departmentIds;
        this.customerIds = customerIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getDepartmentIds() {
        return departmentIds;
    }

    public List<String> getCustomerIds() {
        return customerIds;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", departmentIds=" + departmentIds +
                ", customerIds=" + customerIds +
                '}';
    }
}
