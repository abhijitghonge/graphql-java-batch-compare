# Sample Output
```
=== BatchedExecutionStrategy ===
getDepartmentsForShops batch: [Shop{id='shop-1', name='Shop 1', departmentIds=[department-1, department-2, department-3]}, Shop{id='shop-2', name='Shop 2', departmentIds=[department-4, department-5, department-6]}, Shop{id='shop-3', name='Shop 3', departmentIds=[department-7, department-8, department-9]}]
getProductsForDepartments batch: [Department{id='department-1', name='Department 1', productIds=[product-1]}, Department{id='department-2', name='Department 2', productIds=[product-2]}, Department{id='department-3', name='Department 3', productIds=[product-3]}, Department{id='department-4', name='Department 4', productIds=[product-4]}, Department{id='department-5', name='Department 5', productIds=[product-5]}, Department{id='department-6', name='Department 6', productIds=[product-6]}, Department{id='department-7', name='Department 7', productIds=[product-7]}, Department{id='department-8', name='Department 8', productIds=[product-8]}, Department{id='department-9', name='Department 9', productIds=[product-9]}]

ExecutionResult: {data={shops=[{id=shop-1, name=Shop 1, departments=[{id=department-1, name=Department 1, products=[{id=product-1, name=Product 1}]}, {id=department-2, name=Department 2, products=[{id=product-2, name=Product 2}]}, {id=department-3, name=Department 3, products=[{id=product-3, name=Product 3}]}]}, {id=shop-2, name=Shop 2, departments=[{id=department-4, name=Department 4, products=[{id=product-4, name=Product 4}]}, {id=department-5, name=Department 5, products=[{id=product-5, name=Product 5}]}, {id=department-6, name=Department 6, products=[{id=product-6, name=Product 6}]}]}, {id=shop-3, name=Shop 3, departments=[{id=department-7, name=Department 7, products=[{id=product-7, name=Product 7}]}, {id=department-8, name=Department 8, products=[{id=product-8, name=Product 8}]}, {id=department-9, name=Department 9, products=[{id=product-9, name=Product 9}]}]}]}}

=== AsyncExecutionStrategy with DataLoader ===
getDepartmentsForShops batch: [Shop{id='shop-1', name='Shop 1', departmentIds=[department-1, department-2, department-3]}]
getProductsForDepartments batch: [Department{id='department-1', name='Department 1', productIds=[product-1]}]
getProductsForDepartments batch: [Department{id='department-2', name='Department 2', productIds=[product-2]}]
getProductsForDepartments batch: [Department{id='department-3', name='Department 3', productIds=[product-3]}]
getDepartmentsForShops batch: [Shop{id='shop-2', name='Shop 2', departmentIds=[department-4, department-5, department-6]}]
getProductsForDepartments batch: [Department{id='department-4', name='Department 4', productIds=[product-4]}]
getProductsForDepartments batch: [Department{id='department-5', name='Department 5', productIds=[product-5]}]
getProductsForDepartments batch: [Department{id='department-6', name='Department 6', productIds=[product-6]}]
getDepartmentsForShops batch: [Shop{id='shop-3', name='Shop 3', departmentIds=[department-7, department-8, department-9]}]
getProductsForDepartments batch: [Department{id='department-7', name='Department 7', productIds=[product-7]}]
getProductsForDepartments batch: [Department{id='department-8', name='Department 8', productIds=[product-8]}]
getProductsForDepartments batch: [Department{id='department-9', name='Department 9', productIds=[product-9]}]

ExecutionResult: {data={shops=[{id=shop-1, name=Shop 1, departments=[{id=department-1, name=Department 1, products=[{id=product-1, name=Product 1}]}, {id=department-2, name=Department 2, products=[{id=product-2, name=Product 2}]}, {id=department-3, name=Department 3, products=[{id=product-3, name=Product 3}]}]}, {id=shop-2, name=Shop 2, departments=[{id=department-4, name=Department 4, products=[{id=product-4, name=Product 4}]}, {id=department-5, name=Department 5, products=[{id=product-5, name=Product 5}]}, {id=department-6, name=Department 6, products=[{id=product-6, name=Product 6}]}]}, {id=shop-3, name=Shop 3, departments=[{id=department-7, name=Department 7, products=[{id=product-7, name=Product 7}]}, {id=department-8, name=Department 8, products=[{id=product-8, name=Product 8}]}, {id=department-9, name=Department 9, products=[{id=product-9, name=Product 9}]}]}]}}
```
