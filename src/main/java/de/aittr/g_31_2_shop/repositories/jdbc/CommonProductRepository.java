package de.aittr.g_31_2_shop.repositories.jdbc;

import de.aittr.g_31_2_shop.domain.jdbc.CommonProduct;
import de.aittr.g_31_2_shop.domain.interfaces.Product;
import de.aittr.g_31_2_shop.enums.Status;
import de.aittr.g_31_2_shop.repositories.interfaces.ProductRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static de.aittr.g_31_2_shop.repositories.jdbc.DB_MySqLConnector.getConnection;

@Repository
public class CommonProductRepository implements ProductRepository {

    private final String ID = "id";
    private final String PRICE = "price";
    private final String NAME = "name";

    @Override
    public Product save(Product product) {
        try (Connection connection = getConnection()) {

            String query = String.format(Locale.US, "INSERT INTO `product` (`name`, `price`, `is_active`) VALUES " +
                    "('%s', '%.2f', '1');", product.getName(), product.getPrice());
            connection.createStatement().execute(query);

            query = "SELECT id FROM product ORDER BY id DESC LIMIT 1;";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();
            int id = resultSet.getInt(ID);

            product.setId(id);
            return product;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection connection = getConnection()) {

            String query = "SELECT id, name, price FROM product WHERE is_active = 1;";

            ResultSet resultSet = connection.createStatement().executeQuery(query);
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(ID);
                String name = resultSet.getString(NAME);
                double price = resultSet.getDouble(PRICE);
                Product product = new CommonProduct(id, true, name, price);
                products.add(product);
            }

            return products;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(int id) {
        try (Connection connection = getConnection()) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Product product) {
        try (Connection connection = getConnection()) {
            String query = String.format(Locale.US, "UPDATE `product` SET `name` = '%s', `price` = '%.2f' WHERE " +
                    "(`id`" + " " + "= " + "'%d')" + ";", product.getName(), product.getPrice(), product.getId());
            connection.createStatement().execute(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = getConnection()) {
            String query = String.format("UPDATE `product` SET `is_active` = '0' WHERE (`id` = '%d');", id);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void changeStatusById(int id, Status status) {
        try (Connection connection = getConnection()) {
            String query = String.format("UPDATE `product` SET `is_active` = '%d' WHERE (`id` = '%d');",
                    status.getValue(), id);
            connection.createStatement().execute(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
