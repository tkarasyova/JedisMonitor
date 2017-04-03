package redis.crud;


import java.util.HashMap;
import java.util.Map;

public class ProductManager {
    public static void main(String[] args) {
        ProductCrud productCrud = new ProductCrud();

       /*   ADD PRODUCT  */

        String productId = "product:1";
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "Jacket");
        map.put("brand", "CHANNEL");
        map.put("price", "111");
        map.put("color", "blue");
        map.put("size", "S");

        productCrud.createProduct(productId, map);



    /*   GET PRODUCT  */
        Map<String, String> resultMap = productCrud.getAllProductFields(productId);
        System.out.println("Info for product '" + productId + "'");
        if (resultMap != null)

        {
            for (String keyMap : resultMap.keySet()) {
                System.out.println(keyMap + " " + resultMap.get(keyMap));
            }
        } else

        {
            System.out.println("Product is not found");
        }

    /* GET ONE FIELD OF PRODUCT */

        productId = "product:1";
        String field = "brand";
        String result = productCrud.getProductField(productId, field);
        if (result != null) {
            System.out.println("The field '" + field + "' of product " + productId + " is '" + result + "'");
        } else {
            System.out.println("Product is not found");
        }

    /* UPDATE PRODUCT FIELD*/

        productId = "product:1";
        field = "brand";
        String value = "Gucci";
        if (productCrud.updateProduct(productId,field,value)){
            System.out.println("The field '"+field+"' was set with new value "+value);
        }
        else {
            System.out.println("Product not found");
        }

    /* DELETE PRODUCT*/

      /*  productId = "product:1";
        if (productCrud.removeProduct(productId))
        {
            System.out.println("The product '"+productId+"' was deleted");
        }
        else {
            System.out.println("Product not found");
        }*/


    }
}