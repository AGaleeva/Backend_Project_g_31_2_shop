package de.aittr.g_31_2_shop.repositories.jdbc;

import de.aittr.g_31_2_shop.domain.jdbc.CommonCart;
import de.aittr.g_31_2_shop.domain.jdbc.CommonCustomer;
import de.aittr.g_31_2_shop.domain.jdbc.CommonProduct;
import de.aittr.g_31_2_shop.domain.interfaces.Cart;
import de.aittr.g_31_2_shop.domain.interfaces.Customer;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.repositories.interfaces.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.aittr.g_31_2_shop.repositories.jdbc.DB_MySqLConnector.getConnection;

@Repository
public class CommonCustomerRepository implements CustomerRepository {

//    cm.id, cm.name, ct.id, pr.id, pr.name, pr.price

    private final String CUSTOMER_ID = "cm.id";
    private final String CUSTOMER_NAME = "cm.name";
    private final String CART_ID = "ct.id";
    private final String PRODUCT_ID = "pr.id";
    private final String PRODUCT_NAME = "pr.name";
    private final String PRICE = "price";

    @Override
    public Customer save(Customer customer) {
        try (Connection connection = getConnection()) {
            String insertCustomerQuery = String.format("INSERT INTO `customer` (`name`, `is_active`) VALUES ('%s', 1)" +
                    ";", customer.getName());

            Statement statement = connection.createStatement();
            statement.execute(insertCustomerQuery, Statement.RETURN_GENERATED_KEYS);

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int id = resultSet.getInt(1);
            customer.setId(id);

            String insertCartQuery = String.format("INSERT INTO `cart` (`customer_id`) VALUES ('%d');", id);
            statement.execute(insertCartQuery, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            int cartId = resultSet.getInt(1);

            Cart cart = new CommonCart(cartId);
            customer.setCart(cart);

            return customer;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {
            String query =
                    "select cm.id, cm.name, ct.id, pr.id, pr.name, pr.price from customer as cm join cart as " + "ct " +
                            "on cm.id = ct.customer_id left join cart_product as cp on ct.id = cp.cart_id left join " +
                            "product as pr on cp.product_id = pr.id where cm.is_active = 1 and (pr.is_active = 1 or " +
                            "pr.is_active is null);";

            ResultSet resultSet = connection.createStatement().executeQuery(query);
            Map<Integer, Customer> customers = new HashMap<>();

            while (resultSet.next()) {

                int customerId = resultSet.getInt(CUSTOMER_ID);
                Customer customer;

                if (customers.containsKey(customerId)) {
                    customer = customers.get(customerId);
                } else {
                    int cartId = resultSet.getInt(CART_ID);
                    Cart cart = new CommonCart(cartId);
                    String customerName = resultSet.getString(CUSTOMER_NAME);
                    customer = new CommonCustomer(customerId, true, customerName, cart);
                    customers.put(customerId, customer);
                }

                int productId = resultSet.getInt(PRODUCT_ID);

                if (productId != 0) {
                    String productName = resultSet.getString(PRODUCT_NAME);
                    double price = resultSet.getDouble(PRICE);
                    Product product = new CommonProduct(productId, true, productName, price);
                    customer.getCart().addProduct(product);
                }
            }

            return new ArrayList<>(customers.values());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*@Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {
            String query =
                    "select cm.id, cm.name, ct.id, pr.id, pr.name, pr.price from customer as cm join cart as " + "ct " +
                            "on cm.id = ct.customer_id left join cart_product as cp on ct.id = cp.cart_id left join " +
                            "product as pr on cp.product_id = pr.id where cm.is_active = 1 and (pr.is_active = 1 or " +
                            "pr.is_active is null);";

            ResultSet resultSet = connection.createStatement().executeQuery(query);
            Map<Integer, Customer> customers = new HashMap<>();

            while (resultSet.next()) {

                int customerId = resultSet.getInt(CUSTOMER_ID);

                customers.computeIfAbsent(customerId, v -> {
                    try {
                        return new CommonCustomer(customerId, true,
                        resultSet.getString(CUSTOMER_NAME), new CommonCart(resultSet.getInt(CART_ID)));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                customers.computeIfPresent(customerId, (k, v) -> v);

                Customer customer = customers.get(customerId);

                int productId = resultSet.getInt(PRODUCT_ID);

                if (productId != 0) {
                    String productName = resultSet.getString(PRODUCT_NAME);
                    double price = resultSet.getDouble(PRICE);
                    Product product = new CommonProduct(productId, true, productName, price);
                    customer.getCart().addProduct(product);
                }
            }

            return new ArrayList<>(customers.values());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

   /* @Override
    public List<Customer> getAll() {
        try (Connection connection = getConnection()) {
            String query =
                    "select cm.id, cm.name, ct.id, pr.id, pr.name, pr.price from customer as cm join cart as " + "ct " +
                            "on cm.id = ct.customer_id left join cart_product as cp on ct.id = cp.cart_id left join " +
                            "product as pr on cp.product_id = pr.id where cm.is_active = 1 and (pr.is_active = 1 or " +
                            "pr.is_active is null);";

            ResultSet resultSet = connection.createStatement().executeQuery(query);
            Map<Integer, Customer> customers = new HashMap<>();

            while (resultSet.next()) {

                int customerId = resultSet.getInt(CUSTOMER_ID);

                if ((customers.putIfAbsent(customerId, new CommonCustomer(customerId, true,
                                resultSet.getString(CUSTOMER_NAME), new CommonCart(resultSet.getInt(CART_ID)))) != null)) {
                    customers.get(customerId);
                }

                int productId = resultSet.getInt(PRODUCT_ID);

                Customer customer = customers.get(customerId);
                if (productId != 0) {
                    String productName = resultSet.getString(PRODUCT_NAME);
                    double price = resultSet.getDouble(PRICE);
                    Product product = new CommonProduct(productId, true, productName, price);
                    customer.getCart().addProduct(product);
                }
            }

            return new ArrayList<>(customers.values());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @Override
    public Customer getById(int id) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Customer customer) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
