package Utils


case class SalesInputData(user_id: Int, product_id: String, gender: String, age: String, occupation: String, city: String, city_tenure: String, marital_status: String, product_category1: String, product_category2: String, product_category3: String, purchaseAmount: String)

case class AgeGroupPurchasesSalesInputData( age: String,  purchaseAmount: String)

case class ProductCategoryCitySalesInputData( product_category1: String,city: String)

case class UserAgePurchaseSalesInputData(gender: String, age: String, purchaseAmount: String)

