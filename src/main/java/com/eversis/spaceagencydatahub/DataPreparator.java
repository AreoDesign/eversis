package com.eversis.spaceagencydatahub;

import com.eversis.spaceagencydatahub.dictionary.ImageType;
import com.eversis.spaceagencydatahub.dictionary.Role;
import com.eversis.spaceagencydatahub.dictionary.Status;
import com.eversis.spaceagencydatahub.entity.Customer;
import com.eversis.spaceagencydatahub.entity.Mission;
import com.eversis.spaceagencydatahub.entity.Order;
import com.eversis.spaceagencydatahub.entity.OrderProductDetails;
import com.eversis.spaceagencydatahub.entity.OrderStatusDetails;
import com.eversis.spaceagencydatahub.entity.Product;
import com.eversis.spaceagencydatahub.repository.CustomerRepository;
import com.eversis.spaceagencydatahub.repository.MissionRepository;
import com.eversis.spaceagencydatahub.repository.OrderProductDetailsRepository;
import com.eversis.spaceagencydatahub.repository.OrderRepository;
import com.eversis.spaceagencydatahub.repository.OrderStatusDetailsRepository;
import com.eversis.spaceagencydatahub.repository.ProductRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Random;

@Component
@Slf4j
public class DataPreparator {

    private MissionRepository missionRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private OrderProductDetailsRepository orderProductDetailsRepository;
    private OrderStatusDetailsRepository orderStatusDetailsRepository;

    public DataPreparator(MissionRepository missionRepository, ProductRepository productRepository,
                          CustomerRepository customerRepository, OrderRepository orderRepository,
                          OrderProductDetailsRepository orderProductDetailsRepository,
                          OrderStatusDetailsRepository orderStatusDetailsRepository) {
        this.missionRepository = missionRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderProductDetailsRepository = orderProductDetailsRepository;
        this.orderStatusDetailsRepository = orderStatusDetailsRepository;
    }

    static Random random = new Random();

    public void prepare() {
        //======================= CREATING PRODUCTS =======================
        Mission mission1 = createMission("Andromeda", ImageType.HYPERSPECTRAL);
        Mission mission2 = createMission("Calliope", ImageType.MULTISPECTRAL);
        Mission mission3 = createMission("Odys", ImageType.PANCHROMATIC);
        missionRepository.saveAll(Sets.newHashSet(mission1, mission2, mission3));
        Product prod1 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/1", mission1);
        Product prod2 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/2", mission1);
        Product prod3 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/3", mission2);
        Product prod4 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/4", mission2);
        Product prod5 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/5", mission3);
        Product prod6 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/6", mission3);
        Product prod7 = createProduct(random.nextInt(100) + 1, "http://space-agency-data-hub.com/images/7", mission3);
        productRepository.saveAll(Sets.newHashSet(prod1, prod2, prod3, prod4, prod5, prod6, prod7));
        //======================= CREATING CUSTOMERS =======================
        Customer customer = createUser(Role.CUSTOMER);
        Customer manager = createUser(Role.CONTENT_MANAGER);
        customerRepository.saveAll(Sets.newHashSet(customer, manager));
        //======================= CREATING SAMPLE ORDERS FOR CUSTOMER =======================
        Order order1 = createOrder(customer);
        Order order2 = createOrder(customer);
        Order order3 = createOrder(customer);
        orderRepository.saveAll(Lists.newArrayList(order1, order2, order3)); // here need to save a list, cause only id differentiate orders and it is assigned during save on database
        OrderProductDetails orderProdDet1 = createOrderProductDetails(order1, prod1, 1);
        OrderProductDetails orderProdDet2 = createOrderProductDetails(order1, prod2, 1);
        OrderProductDetails orderProdDet3 = createOrderProductDetails(order1, prod3, 1);
        OrderProductDetails orderProdDet4 = createOrderProductDetails(order2, prod5, 2);
        OrderProductDetails orderProdDet5 = createOrderProductDetails(order2, prod6, 3);
        OrderProductDetails orderProdDet6 = createOrderProductDetails(order3, prod7, 1);
        orderProductDetailsRepository.saveAll(Lists.newArrayList(orderProdDet1, orderProdDet2, orderProdDet3, orderProdDet4, orderProdDet5, orderProdDet6));
        OrderStatusDetails orderStatusDet1 = createOrderStatusDetails(order1, Status.DRAFT);
        OrderStatusDetails orderStatusDet2 = createOrderStatusDetails(order1, Status.CANCELLED);
        OrderStatusDetails orderStatusDet3 = createOrderStatusDetails(order2, Status.DRAFT);
        OrderStatusDetails orderStatusDet4 = createOrderStatusDetails(order2, Status.PENDING);
        OrderStatusDetails orderStatusDet5 = createOrderStatusDetails(order3, Status.DRAFT);
        OrderStatusDetails orderStatusDet6 = createOrderStatusDetails(order3, Status.COMPLETED);
        orderStatusDetailsRepository.saveAll(Lists.newArrayList(orderStatusDet1, orderStatusDet2, orderStatusDet3, orderStatusDet4, orderStatusDet5, orderStatusDet6));
    }

    private static Mission createMission(String missionName, ImageType imageType) {
        return Mission.builder(missionName)
                      .imageType(imageType)
                      .startDate(Instant.now())
                      .endDate(null)
                      .build();
    }

    private static Product createProduct(Integer price, String url, Mission mission) {
        return Product.builder()
                      .price(BigDecimal.valueOf(price))
                      .url(url)
                      .footprintLatitude(Precision.round(random.nextDouble() * 100, 2))
                      .footprintLongitude(Precision.round(random.nextDouble() * 100, 2))
                      .footprintAltitude(Precision.round(random.nextDouble() * 100, 2))
                      .footprintFourthCoordinate(Precision.round(random.nextDouble() * 100, 2))
                      .mission(mission)
                      .build();
    }

    private static Customer createUser(Role role) {
        switch (role) {
            case CUSTOMER:
                return Customer.builder()
                               .login("customer")
                               .password("customer")
                               .role(Role.CUSTOMER)
                               .build();
            case CONTENT_MANAGER:
                return Customer.builder()
                               .login("manager")
                               .password("manager")
                               .role(Role.CONTENT_MANAGER)
                               .build();
            default:
                String errMsg = String.format("Exception was thrown while creating user with role '%s'", role);
                log.error(errMsg);
                throw new RuntimeException(errMsg);
        }
    }

    private static Order createOrder(Customer customer) {
        return Order.builder()
                    .customer(customer)
                    .build();
    }

    private static OrderProductDetails createOrderProductDetails(Order order, Product product, Integer quantity) {
        return OrderProductDetails.builder()
                                  .order(order)
                                  .product(product)
                                  .quantity(quantity)
                                  .build();
    }

    private OrderStatusDetails createOrderStatusDetails(Order order, Status status) {
        return OrderStatusDetails.builder()
                                 .order(order)
                                 .status(status)
                                 .build();
    }

}
