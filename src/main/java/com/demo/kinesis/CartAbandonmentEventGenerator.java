package com.demo.kinesis;

import com.demo.kinesis.model.CartAbandonmentEvent;
import com.demo.kinesis.model.CartItem;
import com.demo.kinesis.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code CartAbandonmentEventGenerator} class is responsible for generating simulated cart abandonment events
 * in an e-commerce system for demonstration purposes. It simulates the behavior of users adding products to
 * their carts and then abandoning them.
 * <p>
 * This class encapsulates the logic to create random cart abandonment events, including the selection of
 * products, customers, sellers, and timestamps. It uses predefined product data, seller IDs, and customer IDs
 * to construct realistic events.
 * <p>
 * Instances of this class are used to generate sample data for testing and showcasing cart abandonment
 * analysis within the e-commerce platform.
 */
public class CartAbandonmentEventGenerator {
    static final Product[] products = {
            new Product("B002PD61Y4", "ELV Car Mount Adjustable Car Phone Holder - Black", 999),
            new Product("B002SZEOLG", "Parker Vector Camouflage Gift Set - Roller Ball Pen", 450),
            new Product("B003B00484", "Redgear Pro Wireless Gamepad", 3999),
            new Product("B003L62T7W", "Butterfly Smart Mixer Grinder", 4999),
            new Product("B078KRFWQB", "Havells Cista Room Heater, White", 3945)
    };
    static final String[] sellerIDs = {"B07B5XJ572", "B07B275VN9", "B07B88KQZ8", "B07CD2BN46", "B07CRL2GY6"};
    static final String[] customerIDs = {"AF6I3MZF3P2HMDTVRZR77JNTYUCQ", "AFEXRCHGLYKM5ZGHJBVX6L5VIOXA",
            "AGVUL37HNVQISEF42ENXXXXMDPRA", "AEWM6BXJQ76ZA3JH3TEG3ORMEDCA", "AFWCCY4OETWIYGV47TLKUZG3NAZA",
            "AG2SGIEE46YOK5J5WFS52KHY4PYA", "AG22QSZIES6VEC3IVAGKQD4N7WHA", "AHFGWOU2ANAHIK6VUKI267DZO5PQ",
            "AE7JCA7MTQHV6XTNF2NQFH5DG6KQ", "AHEPPTU7YZ4YNMCKFBT5PG7W2CHQ", "AG7URP5PKDSGZQUIBSSSVTQCYDBQ",
            "AGVHA7GWJH65MLMZ6UEFQFPFEABA", "AGYUFQB6WUOMBYRLWNULRLC4GQ3A", "AGREWD4V5XIIO7ZZSLOOF5PPW4RA",
            "AEDTSPMMJN5UL33AYZXSBOVGMRLA", "AGPOYBESW4JLTMELJLGMLV4JKJEA", "AGJ2XZ2PPFHMYQ54KPSUGDLHTOIA",
            "AEPLOFVKFHPQH4DFHKQXGKWL24NQ", "AGPOYBESW4JLTMELJLGMLV4JKJEA", "AGJ2XZ2PPFHMYQ54KPSUGDLHTOIA"
    };

    /**
     * Generates a random cart abandonment event with simulated data.
     *
     * @return A `CartAbandonmentEvent` instance representing the simulated event.
     */
    public static CartAbandonmentEvent makeEvent() {
        var tlr = ThreadLocalRandom.current();

        var sellerID = sellerIDs[tlr.nextInt(sellerIDs.length)];
        var customerID = customerIDs[tlr.nextInt(customerIDs.length)];
        List<Product> availableProducts = new ArrayList<>(Arrays.asList(products));

        List<CartItem> cartItems = new ArrayList<>();
        int nProducts = tlr.nextInt(1, products.length);
        for (int i = 0; i < nProducts; i++) {
            Product p = availableProducts.remove(tlr.nextInt(availableProducts.size()));
            var qty = tlr.nextInt(1, 10);
            cartItems.add(new CartItem(p.getName(), p.getCode(), qty, p.getPrice()));
        }

        return CartAbandonmentEvent.builder()
                .cartItems(cartItems)
                .customerID(customerID)
                .sellerID(sellerID)
                .build();
    }
}
