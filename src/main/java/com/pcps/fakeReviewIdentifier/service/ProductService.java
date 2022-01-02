package com.pcps.fakeReviewIdentifier.service;

import com.pcps.fakeReviewIdentifier.model.Product;
import com.pcps.fakeReviewIdentifier.model.User;
import com.pcps.fakeReviewIdentifier.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserSignupService userSignupService;

    @Value("${delete.absolute.path}")
    String imageParentPath;

    public void saveProduct(Product product){
        productRepo.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }
    public Product getProductById(int id){
        return productRepo.getById(id);
    }
    public void deleteProduct(Product product) throws Exception{

        List<User> users1 = product.getUsers();
        List<User> users2 = new ArrayList<>();

        for(User user: users1){
            List<Product> products = user.getProducts();
            products.remove(product);
            user.setProducts(products);
            users2.add(user);
        }
        userSignupService.saveMultipleUsers(users2);
        String imgRelPath = product.getImage();

        productRepo.delete(product);
        if(!(imgRelPath.equals("/image/dummy.jpg"))){
            Files.deleteIfExists(Paths.get(imageParentPath+imgRelPath));
        }
    }
}
