package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreController {
    private final ProductRepo products;
    private final OrderRepo orders;
    private final Cart cart;

    public StoreController(ProductRepo products, OrderRepo orders, Cart cart) {
        this.products = products; this.orders = orders; this.cart = cart;
    }

    @ModelAttribute("cartCount")
    int cartCount() { return cart.count(); }

    @GetMapping("/")
    String home(Model m) {
        m.addAttribute("products", products.findAll());
        return "products";
    }

    @PostMapping("/cart/add/{id}")
    String add(@PathVariable Long id) { cart.add(id); return "redirect:/"; }

    @PostMapping("/cart/remove/{id}")
    String remove(@PathVariable Long id) { cart.remove(id); return "redirect:/cart"; }

    private List<CartLine> lines() {
        List<CartLine> lines = new ArrayList<>();
        cart.getItems().forEach((id, qty) ->
            products.findById(id).ifPresent(p -> lines.add(new CartLine(p, qty))));
        return lines;
    }

    @GetMapping("/cart")
    String cartPage(Model m) {
        List<CartLine> lines = lines();
        m.addAttribute("lines", lines);
        m.addAttribute("total", lines.stream().mapToDouble(CartLine::getSubtotal).sum());
        return "cart";
    }

    @PostMapping("/checkout")
    String checkout(@RequestParam String name, @RequestParam String address) {
        List<CartLine> lines = lines();
        if (lines.isEmpty()) return "redirect:/cart";
        CustomerOrder o = new CustomerOrder();
        o.setCustomerName(name);
        o.setAddress(address);
        o.setItems(String.join(", ", lines.stream().map(l -> l.getProduct().getName() + " x" + l.getQty()).toList()));
        o.setTotal(lines.stream().mapToDouble(CartLine::getSubtotal).sum());
        orders.save(o);
        cart.clear();
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    String orderList(Model m) {
        m.addAttribute("orders", orders.findAll());
        return "orders";
    }

    @GetMapping("/admin")
    String admin(Model m) {
        m.addAttribute("products", products.findAll());
        return "admin";
    }

    @PostMapping("/admin/products")
    String addProduct(@ModelAttribute Product p) { p.setId(null); products.save(p); return "redirect:/admin"; }

    @PostMapping("/admin/products/{id}/delete")
    String delete(@PathVariable Long id) { products.deleteById(id); return "redirect:/admin"; }
}
