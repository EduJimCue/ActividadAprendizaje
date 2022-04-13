package Restaurant;

import Restaurant.dao.*;
import Restaurant.domain.Dish;
import Restaurant.domain.Order;
import Restaurant.domain.Restaurant;
import Restaurant.domain.User;
import Restaurant.exceptions.UserNotFoundException;
import Restaurant.util.DateUtils;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;
    private User currentUser;

    public Menu() {
        keyboard = new Scanner(System.in);
    }

    public void connect() {
        database = new Database();
        connection = database.getConnection();
    }

    public void showMenu() throws SQLException {
        connect();
        login();

        String choice = null;

        // TODO Añadir operaciones para realizar con el usuario y otras para usuario/libro
        do {
            System.out.println("Comida a domicilio (usuario actual: " + currentUser.getName() + ")");
            System.out.println("1. Añadir un plato");
            System.out.println("2. Buscar un Restaurante");
            System.out.println("3. Eliminar un Restaurante");
            System.out.println("4. Modificar un Restaurante");
            System.out.println("5. Ver todo el catálogo de restaurantes");
            System.out.println("6. Registrar un nuevo usuario");
            System.out.println("7. Registrar un pedido");
            System.out.println("8. Ver los detalles de un pedido");
            System.out.println("9. Marcar un pedido como pagado");
            System.out.println("10. Ver pedidos entre 2 fechas determinadas");
            System.out.println("11. Prueba de cambio de usuario");
            System.out.println("s. Salir");
            System.out.print("Opción: ");
            choice = keyboard.nextLine();

            switch (choice) {
                case "1":
                    addDish();
                    break;
                case "2":
                    searchRestaurant();
                    break;
                case "3":
                    deleteRestaurant();
                    break;
                case "4":
                    modifyRestaurant();
                    break;
                case "5":
                    showCatalog();
                    break;
                case "6":
                    addUser();
                    break;
                case "7":
                    addOrder();
                    break;
                case "8":
                    showOrderDetails();
                    break;
                case "9":
                    payOrder();
                    break;
                case "10":
                    showOrdersBetweenDates();
                    break;
                case "11":
                    login();
                    break;
            }
        } while (!choice.equals("s"));
    }

    private void login() {
        System.out.print("¿Cuál es tu usuario? ");
        String username = "EduJimCue";
        System.out.print("¿Cuál es tu contraseña? ");
        String password = "1234";

        UserDao userDao = new UserDao(connection);
        try {
            currentUser = userDao.getUser(username, password)
                    .orElseThrow(UserNotFoundException::new);
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
            System.exit(0);
        } catch (UserNotFoundException unfe) {
            System.out.println(unfe.getMessage());
            System.exit(0);
        }
    }

    public void addDish() throws SQLException {
        DishDao dishDao = new DishDao(connection);

        System.out.print("Precio del plato: ");
        float price = Integer.parseInt(keyboard.nextLine());
        System.out.print("Nombre del plato: ");
        String name = keyboard.nextLine();
        System.out.print("Nombre del restaurante que lo sirve: ");
        String restaurantName = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        Optional<Restaurant> optionalRestaurant = restaurantDao.findByName(restaurantName);
        Restaurant restaurant = optionalRestaurant.orElse(new Restaurant("No se ha encontrado el restaurante", 0, ""));
        Dish dish = new Dish(price, name, restaurant);

        try {
            dishDao.add(dish,restaurant);
            System.out.println("El plato se ha añadido correctamente");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("No se ha podido añadir el plato. Inténtelo de nuevo");
        }
    }

    public void searchRestaurant() {
        System.out.print("Búsqueda por nombre: ");
        String name = keyboard.nextLine();

        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            Optional<Restaurant> optionalRestaurant = restaurantDao.findByName(name);
            Restaurant restaurant = optionalRestaurant.orElse(new Restaurant("No se ha encontrado el restaurante", 0, ""));

            System.out.println(restaurant.getName());
            System.out.println(restaurant.getAdress());
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    public void deleteRestaurant() {
        System.out.print("Nombre del restaurante a eliminar: ");
        String name = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            boolean deleted = restaurantDao.delete(name);
            if (deleted)
                System.out.println("El restaurante se ha eliminado correctamente");
            else
                System.out.println("El restaurante no se ha podido borrar. No existe");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    public void modifyRestaurant() throws SQLException {
        System.out.print("Nombre del restaurante a modificar: ");
        String name = keyboard.nextLine();
        RestaurantDao restaurantDao = new RestaurantDao(connection);
         boolean exist = restaurantDao.existRestaurant(name);

        if (exist){
            System.out.print("Nuevo Nombre: ");
            String newName = keyboard.nextLine();
             System.out.print("Nuevo Codigo Postal: ");
            float newPostalCode = Float.parseFloat(keyboard.nextLine());
            System.out.print("Nueva Direccion: ");
            String newAdress = keyboard.nextLine();
            Restaurant newRestaurant = new Restaurant(newName.trim(), newPostalCode, newAdress.trim());

        try {
            boolean modified = restaurantDao.modify(name, newRestaurant);
            if (modified)
                System.out.println("El libro se ha modificado correctamente");
            else
                System.out.println("El libro no se ha podido modificar. No existe");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
        }
        else {
            System.out.println("El restaurante no existe");
        }
    }

    public void showCatalog() {
        RestaurantDao restaurantDao = new RestaurantDao(connection);
        try {
            ArrayList<Restaurant> restaurants = restaurantDao.findAll();
            for (Restaurant restaurant : restaurants) {
                System.out.println(restaurant.getName());
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
        }
    }

    private void addUser() {
        UserDao userDao = new UserDao(connection);
        System.out.print("Introduce el nombre de usuario: ");
        String name = keyboard.nextLine();
        System.out.print("Introduce el username: ");
        String username = keyboard.nextLine();
        System.out.print("Introduce la contraseña: ");
        String password = keyboard.nextLine();
        User user = new User(name,username,password);
        try {
            userDao.add(user);
        } catch (SQLException sqle) {

        }
    }

    private void addOrder() throws SQLException {
        System.out.print("¿A que restaurante quieres encargar?");
        String restaurant = keyboard.nextLine();
        DishDao dishDao = new DishDao(connection);
        ArrayList<Dish> dishes = dishDao.findbyRestaurant(restaurant);
        for (Dish dish : dishes) {
            System.out.println(dish.getName());
        }

        System.out.print("¿Qué platos quieres encargar? (separados por comas) ");
        String orderDishes = keyboard.nextLine();

        try {
            String[] dishArray = orderDishes.split(",");

            ArrayList<Optional<Dish>> dishList = new ArrayList<>();
            for (String dishName : dishArray) {
                Optional<Dish> dish1 = dishDao.findByNameAndRestaurant(dishName.trim(),restaurant);

                dishList.add(dish1);
            }

            OrderDao orderDao = new OrderDao(connection);
            orderDao.addOrder(currentUser, dishList);
            System.out.println("El pedido se ha creado correctamente");
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }


    private void showOrderDetails() {
        OrderDao orderDao = new OrderDao(connection);

        // TODO El usuario indicará qué pedido quiere ver

        Order order = orderDao.getOrder();

        // TODO Mostrar datos del pedido
    }

    private void payOrder() {
        OrderDao orderDao = new OrderDao(connection);

        // TODO El usuario indicará el pedido

        orderDao.payOrder();
    }

    private void showOrdersBetweenDates() {
        System.out.print("Desde (dd.MM.yyyy): ");
        String fromDateString = keyboard.nextLine();
        System.out.print("Hasta (dd.MM.yyyy): ");
        String toDateString = keyboard.nextLine();
        LocalDate fromDate = DateUtils.parseLocalDate(fromDateString);
        LocalDate toDate = DateUtils.parseLocalDate(toDateString);

        OrderDao orderDao = new OrderDao(connection);
        try {
            List<Order> orders = orderDao.getOrdersBetweenDates(fromDate, toDate);
            orders.forEach(System.out::println);
        } catch (SQLException sqle) {
            System.out.println("No se ha podido comunicar con la base de datos. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }
}
