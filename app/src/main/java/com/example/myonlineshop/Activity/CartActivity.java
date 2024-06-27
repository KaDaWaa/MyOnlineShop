package com.example.myonlineshop.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myonlineshop.Adapter.CartAdapter;
import com.example.myonlineshop.Helper.ChangeNumberItemsListener;
import com.example.myonlineshop.Helper.ManagmentCart;
import com.example.myonlineshop.R;
import com.example.myonlineshop.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart=new ManagmentCart(this);
        calculatorCart();
        setVariable();
        initCartListItems();
        

    }

    private void initCartListItems() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyCartTV.setVisibility(View.VISIBLE);
            binding.cartScrollView.setVisibility(View.GONE);
        }else{
            binding.emptyCartTV.setVisibility(View.GONE);
            binding.cartScrollView.setVisibility(View.VISIBLE);
        }
        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerViewCart.setAdapter(new CartAdapter(managmentCart.getListCart(), this, () ->{ calculatorCart(); initCartListItems();}));
    }

    private void setVariable() {
        binding.backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void calculatorCart() {
        double percentTax=0.17;
        double delivery=15;
        tax=Math.round((managmentCart.getTotalFee()*percentTax*100.0))/100.0;
        double total=Math.round((managmentCart.getTotalFee()+tax+delivery));
        double subtotal=Math.round((managmentCart).getTotalFee());
        binding.subtotalTV.setText("$"+subtotal);
        binding.taxTV.setText("$"+tax);
        binding.deliveryTV.setText("$"+delivery);
        binding.totalTV.setText("$"+total);


    }
}