package com.openclassrooms.entrevoisins.ui.neighbour_details;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class NeighbourDetailsActivity extends AppCompatActivity {
    private NeighbourApiService mApiService;
    private Neighbour mNeighbour;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbarLayout);
        toolBarLayout.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mUsernameTV = findViewById(R.id.usernameTV);
        TextView mAddressTV = findViewById(R.id.addressTV);
        TextView mPhoneNumberTV = findViewById(R.id.phoneNumberTV);
        TextView mFacebookTV = findViewById(R.id.facebookUrlTV);
        TextView mAboutMeDetailsTV = findViewById(R.id.aboutMeDetailsTV);
        ImageView mUserFavBackground = findViewById(R.id.userBackgroundIV);
        fab = findViewById(R.id.fab);
        int position = 0;
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mNeighbour = (Neighbour) args.getSerializable("KEY_NEIGHBOUR");
            mApiService = DI.getNeighbourApiService();
            mUsernameTV.setText(mNeighbour.getName());
            mAddressTV.setText(mNeighbour.getAddress());
            mPhoneNumberTV.setText(mNeighbour.getPhoneNumber());
            mAboutMeDetailsTV.setText(mNeighbour.getAboutMe());
            mFacebookTV.setText("www.facebook.fr/" + mNeighbour.getName().toLowerCase());
            Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mUserFavBackground);
            toolBarLayout.setTitle(mNeighbour.getName());
            updateFavoriteIcon();
            initializeView();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateFavoriteIcon() {
        if(mApiService.getFavorites().contains(mNeighbour)) {
            fab.setImageResource(R.drawable.ic_baseline_star_24_yellow);
        } else {
            fab.setImageResource(R.drawable.ic_baseline_star_border_24_yellow);
        }
    }

    private void initializeView () {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mApiService.getFavorites().contains(mNeighbour)) {
                    mApiService.deleteFavorite(mNeighbour);
                    fab.setImageResource(R.drawable.ic_baseline_star_border_24_yellow);
                    Context supContext = getApplicationContext();
                    CharSequence text = "Favoris supprimé";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(supContext, text, duration);
                    toast.show();
                } else {
                    mApiService.addFavorite(mNeighbour);
                    fab.setImageResource(R.drawable.ic_baseline_star_24_yellow);
                    Context supContext2 = getApplicationContext();
                    CharSequence text = "Favoris ajouté";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(supContext2, text, duration);
                    toast.show();
                }
            }
        });
    }
}
