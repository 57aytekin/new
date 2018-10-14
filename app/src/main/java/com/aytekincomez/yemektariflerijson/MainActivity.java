package com.aytekincomez.yemektariflerijson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.aytekincomez.yemektariflerijson.Adapter.YemekAdapter;
import com.aytekincomez.yemektariflerijson.Model.Yemek;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HttpHandler httpHandler;
    ProgressDialog progressDialog;
    ArrayList<Yemek> yemekArrayList;
    ListView liste;
    YemekAdapter yemekAdapter;
    Context context;
    private static String URL = "https://raw.githubusercontent.com/57aytekin/deneme/master/yemekler.json";
                                //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //yemekAdapter = new YemekAdapter(this,yemekArrayList);


        liste = (ListView) findViewById(R.id.liste);
        new getRecipe().execute();



    }

    private class getRecipe extends AsyncTask<Void, Void, Void>{
        ArrayList<Yemek> yemekArrayList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Lütfen Bekleyin");
            progressDialog.setCancelable(false);
            progressDialog.show();
            /*
            İŞlemin Basladıgında
             */
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            yemekAdapter = new YemekAdapter(MainActivity.this,yemekArrayList);
            if(progressDialog.isShowing()){
                liste.setAdapter(yemekAdapter);
                progressDialog.dismiss();
            }
            /*
            İŞlemin tamamlandıgında
             */
        }

        @Override
        protected Void doInBackground(Void... voids) {
            httpHandler = new HttpHandler();
            String jsonString = httpHandler.makeServiceCall(URL);

            if(jsonString != null){

                Log.d("JSON", jsonString);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray yemekler = jsonObject.getJSONArray("yemekler");

                    for(int i=0; i<yemekler.length(); i++){
                        JSONObject yemek = yemekler.getJSONObject(i);
                        int yemekId = yemek.getInt("yemek_id");
                        String yemekAdi = yemek.getString("yemek_adi");
                        String yemekAciklama = yemek.getString("yemek_aciklama");
                        String yemekTur = yemek.getString("yemek_tur");
                        String yemekPisirmeSuresi = yemek.getString("yemek_pisirme_suresi");
                        String yemekHazirlamaSuresi = yemek.getString("yemek_hazirlama_suresi");
                        String yemekKisiSayisi = yemek.getString("yemek_kisi_sayisi");
                        String yemekResim = yemek.getString("yemek_resim");
                        String yemekMalzemeler = yemek.getString("yemek_malzemeler");

                        Yemek ymk = new Yemek(
                                yemekId,
                                yemekAdi,
                                yemekAciklama,
                                yemekTur,
                                yemekPisirmeSuresi,
                                yemekHazirlamaSuresi,
                                yemekKisiSayisi,
                                yemekResim,
                                yemekMalzemeler
                        );

                        yemekArrayList.add(ymk);
                    }
                }catch (Exception e){
                    Log.d("Hata",e.getLocalizedMessage());
                }
            }else{
                Log.d("JSON_RESPONSE","Sayfa Kaynagı Boş");
            }
            return null;
            /*
            İşlmein gerceklestirme zamanında.
             */
        }
    }
}
