package example;

import java.util.List;

public class Department {
    private final String id;
    private final String name;
    private final List<String> productIds;
    private final List<String> departmentIds;

    public Department(String id, String name, List<String> productIds, List<String> departmentIds) {
        this.id = id;
        this.name = name;
        this.productIds = productIds;
        this.departmentIds = departmentIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public List<String> getDepartmentIds() {
        return departmentIds;
    }


    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", productIds=" + productIds +
                ", departmentIds=" + departmentIds +
                '}';
    }
}
