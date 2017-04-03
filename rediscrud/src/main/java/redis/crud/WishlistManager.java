package redis.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WishlistManager {
    public static void main(String[] args) {
        WishlistCrud wishlistCrud = new WishlistCrud();
        ProductCrud productCrud = new ProductCrud();


       /*   ADD ITEMS TO USR'S WISHLIST  */

        String userIdWish = "user:1:wish";
        List<String> products = new ArrayList();
        products.add("product:1");
        products.add("product:12");
        products.add("product:3");
        products.add("product:47");
        products.add("product:56");
        products.add("product:8");

        for (String product : products) {
        products.add("product:10");
            wishlistCrud.createWishlist(userIdWish, product);

        }

        /*   GET ALL PRODUCT ISD IN WISHLIST */
        userIdWish = "user:1:wish";
        Set<String> productIds = wishlistCrud.getWishlist(userIdWish);
        if (productIds != null) {
            System.out.print(userIdWish + " wishlist contains: ");
            for (String product : productIds) {
                System.out.print(product + ", ");
            }
        } else {
            System.out.println("Wishlist is empty");
        }



          /*   GET ALL WISHLIST PRODUCTS*/

        userIdWish = "user:1:wish";
        Set<String> wishlistProducts = wishlistCrud.getWishlist(userIdWish);
        if (wishlistProducts != null) {
            System.out.print(userIdWish + " wishlist contains: ");
            for (String product : wishlistProducts) {
                Map<String,String> productFields = productCrud.getAllProductFields(product);
                System.out.println(product + ":");
                for (String key:productFields.keySet()){
                    System.out.println(key + " - " + productFields.get(key));
                }
            }
        } else {
            System.out.println("Wishlist is empty");
        }

    }

}
