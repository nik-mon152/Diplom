package com.example.mycar.ui.Car;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycar.MainActivity;
import com.example.mycar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddCar extends AppCompatActivity {

    EditText addmarka, addmodel, addage, addprobeg, addpower;
    ImageView foto;
    Button addCar;
    Spinner addviewCar, addviewFuel;

    private String[] viewCarStr = {"Автомобиль", "Мотоцикл"};
    private String[] viewFuelStr = {"Бензин", "Дизель", "Газ"};
    String userId;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;
    StorageReference storageReference;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Добавление автомобиля");

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();

        ArrayAdapter<String> viewCarStrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewCarStr);
        viewCarStrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> viewFuelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, viewFuelStr);
        viewFuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bar = findViewById(R.id.progressBarAdd);
        addmarka = findViewById(R.id.countAdd);
        addmodel = findViewById(R.id.AddModel);
        addage = findViewById(R.id.AddAgeCar);
        addprobeg = findViewById(R.id.AddProbeg);
        foto = findViewById(R.id.CarFotoAdd);
        addpower = findViewById(R.id.addpower);

        //Нахождение и создание спиннера для выбора вида транспорта
        addviewCar = findViewById(R.id.viewCar);
        addviewCar.setAdapter(viewCarStrAdapter);
        addviewCar.setPrompt("Выберите вид транспорта");

        //Нахождение и создание спиннера для вида топлива
        addviewFuel = findViewById(R.id.ViewFuel);
        addviewFuel.setAdapter(viewFuelAdapter);

        StorageReference profileRef = storageReference.child("cars/" + fAuth.getCurrentUser().getUid() + "/car.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        DocumentReference documentReference = fstore.collection("Cars").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    if(documentSnapshot.exists()){
                        addmarka.setText(documentSnapshot.getString("Marka"));
                        addmodel.setText(documentSnapshot.getString("Model"));
                        addage.setText(documentSnapshot.getString("Year of issue"));
                        addprobeg.setText(documentSnapshot.getLong("Mileage").intValue() + "");
                        addpower.setText(documentSnapshot.getLong("Power").intValue() + "");
                    }else{
                        Log.d("Сообщение об ошибке", "Ошибка в документе");
                    }
                }
            }
        });

        addCar = findViewById(R.id.btnAddCar);
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addprobeg.getText().toString().isEmpty()){
                    addprobeg.setError("Введите пробег");
                    return;
                }
                if(addpower.getText().toString().isEmpty()){
                    addpower.setError("Введите мощность двигателя");
                    return;
                }
                String marka = addmarka.getText().toString();
                String model = addmodel.getText().toString();
                String age = addage.getText().toString();
                int probeg = Integer.parseInt(addprobeg.getText().toString());
                int power = Integer.parseInt(addpower.getText().toString());
                String viewCar = addviewCar.getSelectedItem().toString();
                String viewFuel = addviewFuel.getSelectedItem().toString();

                if(marka.isEmpty()){
                    addmarka.setError("Введите марку транспорта");
                    return;
                }
                if(model.isEmpty()){
                    addmodel.setError("Введите модель транспорта");
                    return;
                }
                if(age.isEmpty()){
                    addage.setError("Введите год выпуска");
                    return;
                }
                if(probeg == 0){
                    addprobeg.setError("Введите пробег");
                    return;
                }
                if(power == 0){
                    addpower.setError("Введите мощность двигателя");
                    return;
                }

                bar.setVisibility(view.VISIBLE);

                DocumentReference documentReference = fstore.collection("Cars").document(user.getUid());
                Map<String, Object> cars = new HashMap<>();
                cars.put("kind of transport", viewCar);
                cars.put("Marka", marka);
                cars.put("Model", model);
                cars.put("Year of issue", age);
                cars.put("Mileage", probeg);
                cars.put("Power", power);
                cars.put("Type of fuel", viewFuel);

            documentReference.set(cars).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(),"Транспорт добавлен!",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Ошибка в добавлении транспорта!",Toast.LENGTH_SHORT).show();
                    Log.d("Сообщение об ошибке ", "Транспорт не был добавлен " + e.getMessage());
                }
            });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                uploadImageToFirebase(imageUri);
            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        //загрузка фото в firebase Storage
        StorageReference fileRef = storageReference.child("cars/" + fAuth.getCurrentUser().getUid() + "/car.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(foto);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                Toast.makeText(getApplicationContext(),"Вы успешно загрузили фото!",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Произошла ошибка в загрузке фото!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}