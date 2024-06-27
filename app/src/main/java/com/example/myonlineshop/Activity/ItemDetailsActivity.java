package com.example.myonlineshop.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myonlineshop.Adapter.ColorAdapter;
import com.example.myonlineshop.Adapter.SizeAdapter;
import com.example.myonlineshop.Adapter.SliderAdapter;
import com.example.myonlineshop.Domain.ItemsDomain;
import com.example.myonlineshop.Domain.SliderItems;
import com.example.myonlineshop.Helper.ManagmentCart;
import com.example.myonlineshop.R;
import com.example.myonlineshop.databinding.ActivityItemDetailsBinding;

import java.util.ArrayList;

public class ItemDetailsActivity extends BaseActivity {
    ActivityItemDetailsBinding binding;
    private ItemsDomain object;
    private int numberOrder=1;
    private ManagmentCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityItemDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        managementCart=new ManagmentCart(this);
        
        getBundles();
        initBanners();
        initSize();
        initColor();

    }

    private void initColor() {
        ArrayList<String >list=new ArrayList<>();
        list.add("#006fc4");
        list.add("#daa048");
        list.add("#398d41");
        list.add("#0c3c72");
        list.add("#829db5");

        binding.recyclerViewColorSelection.setAdapter(new ColorAdapter(list));
        binding.recyclerViewColorSelection.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void initSize() {
        ArrayList<String > list=new ArrayList<>();
        list.add("XS");
        list.add("S");
        list.add("M");
        list.add("L");
        list.add("XL");
        list.add("XXL");
        binding.recyclerViewSizeSelection.setAdapter(new SizeAdapter(list));
        binding.recyclerViewSizeSelection.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void initBanners() {
        ArrayList<SliderItems> sliderItems=new ArrayList<>();
        for(int i=0;i<object.getPicUrl().size();i++){
            sliderItems.add(new SliderItems(object.getPicUrl().get(i)));
        }
        binding.viewpagerItemPicSlider.setAdapter(new SliderAdapter(sliderItems,binding.viewpagerItemPicSlider));
        binding.viewpagerItemPicSlider.setClipToPadding(false);
        binding.viewpagerItemPicSlider.setClipChildren(false);
        binding.viewpagerItemPicSlider.setOffscreenPageLimit(3);
        binding.viewpagerItemPicSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        object= (ItemsDomain) getIntent().getSerializableExtra("object");
        binding.ItemTitleTV.setText(object.getTitle());
        binding.ItemPriceTV.setText("$"+object.getPrice());
        binding.ItemRatingBar.setRating((float) object.getRating());
        binding.itemRatingTV.setText(object.getRating()+" Rating");
        binding.productDescriptionTV.setText(object.getDescription());
        binding.addToCartButton.setOnClickListener(v->{
            object.setNumberinCart(numberOrder);
            managementCart.insertItem(object);
        });

        binding.backButton.setOnClickListener(v -> finish());

    }
}