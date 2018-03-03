package com.mycompany.testtask;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private ListView listView;
    private List<ContactPOJO> contactList;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         fragmentManager=getFragmentManager();
         runSplash();

           listView = (ListView) findViewById(R.id.listView);
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, UserInformationScreen.class);
                i.putExtra("longitude", contactList.get(position).getAddress().getGeo().getLng());
                i.putExtra("latitude", contactList.get(position).getAddress().getGeo().getLat());
                i.putExtra("adress",contactList.get(position).getAddress().getStreet());
                i.putExtra("name",contactList.get(position).getName());
                i.putExtra("email",contactList.get(position).getEmail());
                i.putExtra("fhone",contactList.get(position).getPhone());
                i.putExtra("web",contactList.get(position).getWebsite());
                startActivity(i);
            }
        });

        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Подождите");
            dialog.setMessage("ожидаем информацию");
            dialog.show();

            ApiService api = RetroClient.getApiService();
            Call<List<ContactPOJO>> call = api.getMyGSON();
            call.enqueue(new Callback<List<ContactPOJO>>() {

                @Override
                public void onResponse(Call<List<ContactPOJO>> call, Response<List<ContactPOJO>> response) {

                    dialog.dismiss();

                    if (response.isSuccessful()) {
                            contactList = response.body();

                        try {
                            FileOutputStream fileOutput = getApplicationContext().openFileOutput("example.txt", MODE_PRIVATE);
                            ObjectOutputStream os = new ObjectOutputStream(fileOutput);
                                os.writeObject(contactList);
                                os.close();
                                fileOutput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                            adapter = new ContactAdapter(MainActivity.this, contactList);
                            listView.setAdapter(adapter);
                    } else {
                        dialog.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<List<ContactPOJO>> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        } else {
            read();
        }
    }

    void read(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput("example.txt");
            ObjectInputStream is = new ObjectInputStream(fis);
            Object one = is.readObject();
            contactList = (List<ContactPOJO>)one;
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        adapter = new ContactAdapter(MainActivity.this, contactList);
        listView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "Нет сети", Toast.LENGTH_LONG).show();
    }

    private void runSplash() {
        SplashFragment splashfragment= new SplashFragment();
        fragmentManager.beginTransaction().replace(R.id.parentLayout,splashfragment).addToBackStack(null).commit();
    }

}
