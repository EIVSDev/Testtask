package com.mycompany.testtask;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    public static String LOG_TAG = "my_log";
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


                    Log.e("Coord",contactList.get(position).getEmail()
                       );


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
                           Log.e("RESON","SUCces");

                        contactList = response.body();
                        adapter = new ContactAdapter(MainActivity.this, contactList);
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Ошибка, данные не получены", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ContactPOJO>> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Нет сети", Toast.LENGTH_LONG).show();
        }

    }

    private void runSplash() {
        SplashFragment splashfragment= new SplashFragment();
        fragmentManager.beginTransaction().replace(R.id.parentLayout,splashfragment).addToBackStack(null).commit();
    }

}
