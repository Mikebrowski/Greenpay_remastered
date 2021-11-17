package com.example.greenpayremastered;



import static java.util.List.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import Adapter.Initiatives;
import database.UserData;


public class LoginActivityScreen extends AppCompatActivity implements RecycleAdapter.IniClickInterface {

    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    ArrayList<Initiatives> initiatives;

    private SearchView searchArea;
    private Button logoutBtn;
    private TextView loginText;
    private Button firstButton;
    private Button secondButton;
    private RecyclerView recycleViewClass;
    private DatabaseReference mDatabase;

    UserData userData = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggetscreen);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        searchArea = (SearchView) findViewById(R.id.searchI);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        loginText = (TextView) findViewById(R.id.loggedInTextview);
        firstButton = (Button) findViewById(R.id.firstButton);
        secondButton = (Button) findViewById(R.id.secondButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("user/").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String usernameOnProfile = snapshot.getValue(UserData.class).getUsername();

                        String emailOnUser = snapshot.getValue(UserData.class).getEmail();
                        String profilePicUser = snapshot.getValue(UserData.class).getProfilePicture();
                        loginText.setText(usernameOnProfile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loginText.setText("Funket ikke korret");
                    }
                });


            }
        });

        searchArea.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //RecycleAdapter.getFilter
                //filter(newText);
                return true;
            }
        });

            recycleViewPopulate();





        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginText.setText("I HAVE CHANGED INTO " + mAuth.getCurrentUser().getEmail());
                firstButton.setText(mAuth.getCurrentUser().getEmail());
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(LoginActivityScreen.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });


    }//end of onCreate



    private void showUserName(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            FirebaseUser user = mAuth.getCurrentUser();
            //userData.setEmail(ds.child(user).getValue(UserData.class).getUsername());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(LoginActivityScreen.this, LoginActivity.class));
        }
    }
    public void recycleViewPopulate(){
        //GRAB DATA HERE
        initiatives = new ArrayList<>();

        initiatives.add((new Initiatives("Gikk til jobben A", R.drawable.mal2, "25 poeng ")));
        initiatives.add(new Initiatives("BBB til jobben B", R.drawable.mal33, "20 poeng "));
        initiatives.add(new Initiatives("Brukte sykkel til jobben", R.drawable.mal3, "20 poeng "));
        initiatives.add(new Initiatives("Kjøpte mat fra lokal bedrift", R.drawable.mal1, "10 poeng "));
        initiatives.add(new Initiatives("Tok med mat til jobben", R.drawable.planet, "15 poeng "));
        initiatives.add(new Initiatives("Brukte sykkel til jobben", R.drawable.mal3, "20 poeng "));
        initiatives.add(new Initiatives("Kjøpte mat fra lokal bedrift", R.drawable.mal2, "5 poeng "));


        RecycleAdapter recycleAdapter = new RecycleAdapter(initiatives, this, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recycleAdapter);


        /*TEST FOR STORRE MENGDE PÅ RESYCLEVIEW KANSKJE SETTE OPP EN FOR EACHITEM .ADD ? //initiatives.forEach();
        ELLER //While(initiatives.add(new Initiatives("",getImage()))); ALTERNATIVE er også for(Map.Entry.setText) */

        //link stream into 2 lists

    }//End of recycleViewPopulate

    @Override
    public void onItemClick(int positionOfTheIni) {
        Toast.makeText(this, "Clicked: "+initiatives.get(positionOfTheIni).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,InitiativesInfo.class);
        intent.putExtra("image",initiatives.get(positionOfTheIni).getImage());
        intent.putExtra("name",initiatives.get(positionOfTheIni).getName());
        intent.putExtra("points",initiatives.get(positionOfTheIni).getPoints());
        startActivity(intent);
    }


    /*
    private void filter(String newText) {
        List<Initiatives> filteredList = new ArrayList<>() {
        for(Initiatives item: initiatives){
            //DO SHIT
            if(item.getName().toLowerCase().startsWith(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        RecycleAdapter.filterList(filteredList);
    }
*/






}//end of app