package dhu.cst.zhangcheng171010220.dhutraveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class GalleryActivity extends AppCompatActivity {
    DhuBuilding building;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        this.building = (DhuBuilding) bundle.getSerializable("building");
        this.viewPager2 = findViewById(R.id.gallery_viewPager);
        TextView textViewHead = findViewById(R.id.activity_gallery_head_textView);
        textViewHead.setText(building.name);
        View viewBack = findViewById(R.id.activity_gallery_head_back);
        viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager2.setAdapter(new RecyclerView.Adapter<PictViewHolder>() {
            @NonNull
            @Override
            public PictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(GalleryActivity.this).inflate(R.layout.gallery_picture, parent, false);
                return new PictViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull PictViewHolder holder, int position) {
                holder.imageView.setImage(ImageSource.resource(building.pictures[position]));
            }

            @Override
            public int getItemCount() {
                return building.pictures.length;
            }
        });
    }
    static class PictViewHolder extends RecyclerView.ViewHolder {
        SubsamplingScaleImageView imageView;
        public PictViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (SubsamplingScaleImageView) itemView;
        }
    }
}